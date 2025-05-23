package uo.ri.cws.application.service.spare.order.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.order.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByCode implements Command<Optional<OrderDto>> {

    OrderRepository repo = Factories.repository.forOrder();
    private String code;

    public FindByCode(String code) {
        ArgumentChecks.isNotNull(code, "Invalid argument, code is null");
        this.code = code;
    }

    @Override
    public Optional<OrderDto> execute() throws BusinessException {
        return repo.findByCode(code).map(o -> DtoAssembler.toDto(o));
    }
    
}
