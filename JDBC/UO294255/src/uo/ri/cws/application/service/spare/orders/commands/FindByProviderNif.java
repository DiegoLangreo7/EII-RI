package uo.ri.cws.application.service.spare.orders.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.order.OrderGateway;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.orders.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByProviderNif implements Command<List<OrderDto>> {

    private OrderGateway og = Factories.persistence.forOrder();
    private String provider_nif;

    public FindByProviderNif(String nif) {
        ArgumentChecks.isNotNull(nif, "Invalid argument, nif is null");
        this.provider_nif = nif;
    }

    @Override
    public List<OrderDto> execute() throws BusinessException {
        return DtoAssembler.toDtoList(og.findByProviderNif(provider_nif));

    }

}
