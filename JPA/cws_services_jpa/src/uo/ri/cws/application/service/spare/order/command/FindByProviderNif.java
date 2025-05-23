package uo.ri.cws.application.service.spare.order.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.order.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByProviderNif implements Command<List<OrderDto>> {

    private String provider_nif;
    private OrderRepository repo = Factories.repository.forOrder();

    public FindByProviderNif(String nif) {
        ArgumentChecks.isNotNull(nif, "Invalid argument, nif is null");
        this.provider_nif = nif;
    }

    @Override
    public List<OrderDto> execute() throws BusinessException {
        return DtoAssembler.toOrdersDtoList(repo.findByProviderNif(provider_nif));
    }

}
