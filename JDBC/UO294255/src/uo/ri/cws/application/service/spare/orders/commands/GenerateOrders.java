package uo.ri.cws.application.service.spare.orders.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.order.OrderGateway;
import uo.ri.cws.application.persistence.spares.orderlines.OrderLineGateway;
import uo.ri.cws.application.persistence.spares.orderlines.OrderLineGateway.OrderLineRecord;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderLineDto;
import uo.ri.cws.application.service.spare.orders.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class GenerateOrders implements Command<List<OrderDto>> {

    private SparePartGateway spg = Factories.persistence.forSparePart();
    private SupplyGateway sg = Factories.persistence.forSupply();
    private OrderGateway og = Factories.persistence.forOrder();
    private OrderLineGateway olg = Factories.persistence.forOrderLines();

    @Override
    public List<OrderDto> execute() throws BusinessException {
        List<SparePartRecord> spareParts = spg.findAll();
        List<OrderDto> orders = generateOrdersFromSpareParts(spareParts);
        saveOrders(orders);
        return orders;
    }

    private List<OrderDto> generateOrdersFromSpareParts(List<SparePartRecord> spareParts)
        throws BusinessException {

        List<OrderDto> orders = new ArrayList<>();

        for (SparePartRecord spare : spareParts) {
            if (shouldCreateOrderFor(spare)) {
                SupplyRecord supply = findBestSupply(spare);
                if (supply == null) {
                    continue;
                }

                OrderDto order = findOrderForProvider(orders, supply.provider.id);
                if (order == null) {
                    order = createOrder(supply);
                    orders.add(order);
                }

                addLineToOrder(order, spare, supply);
            }
        }
        return orders;
    }

    private boolean shouldCreateOrderFor(SparePartRecord spare)
        throws BusinessException {
        
        // Si el stock está por encima del mínimo, no se genera pedido
        if (spare.stock >= spare.minStock) {
            return false;
        }
        
        // Se busca si ya hay un pedido pendiente para esa pieza
        List<OrderLineRecord> lines = olg.findBySparePartId(spare.id);
        return lines.isEmpty();
    }

    private SupplyRecord findBestSupply(SparePartRecord spare)
        throws BusinessException {
        
        List<SupplyRecord> supplies = sg.findBySparePartCode(spare.code);
        if (supplies.isEmpty()) {
            return null;
        }
        
        SupplyRecord best = supplies.get(0);
        for (SupplyRecord s : supplies) {
            if (isBetterSupply(s, best)) {
                best = s;
            }
        }
        return best;
    }

    private boolean isBetterSupply(SupplyRecord a, SupplyRecord b) {
        if (a.price < b.price) {
            return true;
        }
        if (a.price == b.price && a.deliveryTerm < b.deliveryTerm) {
            return true;
        }
        return a.price == b.price && a.deliveryTerm == b.deliveryTerm &&
               a.provider.nif.compareTo(b.provider.nif) < 0;
    }

    private OrderDto findOrderForProvider(List<OrderDto> orders, String providerId) {
        for (OrderDto order : orders) {
            if (order.provider.id.equals(providerId)) {
                return order;
            }
        }
        return null;
    }

    private OrderDto createOrder(SupplyRecord supply) {
        OrderDto order = new OrderDto();
        order.id = UUID.randomUUID().toString();
        order.code = UUID.randomUUID().toString().substring(0, 6);
        order.provider.id = supply.provider.id;
        order.provider.nif = supply.provider.nif;
        order.provider.name = supply.provider.name;
        order.orderedDate = LocalDate.now();
        order.amount = 0.0;
        order.state = "PENDING";
        order.lines = new ArrayList<>();
        return order;
    }

    private void addLineToOrder(OrderDto order, SparePartRecord spare,
        SupplyRecord supply) {
        OrderLineDto line = new OrderLineDto();
        line.sparePart.id = supply.sparePart.id;
        line.sparePart.code = supply.sparePart.code;
        line.sparePart.description = supply.sparePart.description;
        line.price = supply.price;
        line.quantity = spare.maxStock - spare.stock;
        order.lines.add(line);
        order.amount += line.price * line.quantity;
    }

    private void saveOrders(List<OrderDto> orders) throws BusinessException {
        for (OrderDto order : orders) {
            og.add(DtoAssembler.toRecord(order));
            List<OrderLineRecord> orderLineRecords =
                DtoAssembler.toOrderLineRecordList(order);
            for (OrderLineRecord line : orderLineRecords) {
                olg.add(line);
            }
        }
    }
}
