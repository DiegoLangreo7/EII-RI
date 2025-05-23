package uo.ri.cws.application.service.spare.orders.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.order.OrderGateway;
import uo.ri.cws.application.persistence.spares.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.persistence.spares.orderlines.OrderLineGateway;
import uo.ri.cws.application.persistence.spares.orderlines.OrderLineGateway.OrderLineRecord;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.orders.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class Receive implements Command<OrderDto> {

    private final OrderGateway orderGateway = Factories.persistence.forOrder();
    private final OrderLineGateway orderLineGateway = Factories.persistence.forOrderLines();
    private final SparePartGateway sparePartGateway = Factories.persistence.forSparePart();
    private final String orderCode;
    private OrderRecord orderRecord;
    private List<OrderLineRecord> orderLines;

    public Receive(String code) {
        ArgumentChecks.isNotNull(code, "Argument is invalid, code is null");
        this.orderCode = code;
    }

    @Override
    public OrderDto execute() throws BusinessException {
        cargarOrder();
        validarEstado();
        cargarLineas();
        actualizarOrder();
        actualizarStockYPricio();
        return DtoAssembler.toDtoWithLines(orderRecord, orderLines);
    }

    private void cargarOrder() throws BusinessException {
        Optional<OrderRecord> or = orderGateway.findByCode(orderCode);
        BusinessChecks.exists(or, "The order doesn't exist");
        this.orderRecord = or.get();
    }

    private void validarEstado() throws BusinessException {
        BusinessChecks.isTrue(orderRecord.state.equals("PENDING"),
            "The order isn't in PENDING state");
    }

    private void cargarLineas() {
        orderLines = orderLineGateway.findByOrderCode(orderCode);
    }

    private void actualizarOrder() {
        orderRecord.receptionDate = LocalDate.now();
        orderRecord.state = "RECEIVED";
        orderGateway.receive(orderRecord.code, orderRecord.receptionDate, orderRecord.state);
    }

    private void actualizarStockYPricio() throws BusinessException {
        for (OrderLineRecord line : orderLines) {
            Optional<SparePartRecord> optionalSpare = sparePartGateway.findBySparePartCode(line.sparePartCode);
            BusinessChecks.exists(optionalSpare, "The spare part doesn't exist");
            SparePartRecord spare = optionalSpare.get();

            double precioConMargen = line.price * 1.20;
            double nuevoPrecio = calcularPrecio(spare, line.quantity, precioConMargen);
            int nuevoStock = spare.stock + line.quantity;

            sparePartGateway.updateStockAndPrice(nuevoStock, nuevoPrecio, line.sparePartCode);
        }
    }

    private double calcularPrecio(SparePartRecord sp, int unidadesRecibidas, double precioConMargen) {
        int unidadesExistentes = sp.stock;
        double precioActual = sp.price;
        return ((unidadesExistentes * precioActual)
            + (unidadesRecibidas * precioConMargen))
            / (unidadesExistentes + unidadesRecibidas);
    }
}
