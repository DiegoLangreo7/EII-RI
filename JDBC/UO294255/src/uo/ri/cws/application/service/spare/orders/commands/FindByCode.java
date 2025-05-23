package uo.ri.cws.application.service.spare.orders.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.order.OrderGateway;
import uo.ri.cws.application.persistence.spares.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.persistence.spares.orderlines.OrderLineGateway;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.orders.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByCode implements Command<Optional<OrderDto>> {

    private OrderGateway og = Factories.persistence.forOrder();
    private OrderLineGateway olg = Factories.persistence.forOrderLines();
    private String code;

    public FindByCode(String code) {
        ArgumentChecks.isNotNull(code, "Invalid argument, code is null");
        this.code = code;
    }

    @Override
    public Optional<OrderDto> execute() throws BusinessException {
        Optional<OrderRecord> recordOpt = og.findByCode(code);
        if (recordOpt.isPresent()) {
            return DtoAssembler.toDtoOpt(recordOpt.get(),
                olg.findByOrderCode(code));
        } else {
            return Optional.empty();
        }
    }
}
