package uo.ri.cws.application.service.spare.order.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.order.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Order;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class Receive implements Command<OrderDto> {

    private OrderRepository repo = Factories.repository.forOrder();
    private String orderCode;

    public Receive(String code) {
        ArgumentChecks.isNotNull(code, "Argument is invalid, code is null");
        this.orderCode = code;
    }

    @Override
    public OrderDto execute() throws BusinessException {
        Optional<Order> oo = repo.findByCode(orderCode);
        BusinessChecks.exists(oo, "The order doesnt exists");

        Order order = oo.get();
        BusinessChecks.isTrue(order.isPending(),
            "The order isnt in PENDING state");

        order.receive();

        return DtoAssembler.toDto(order);
    }
}
