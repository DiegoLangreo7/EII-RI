package uo.ri.cws.application.service.spare.provider.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.provider.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindProviderByName implements Command<List<ProviderDto>> {

    private ProviderGateway pg = Factories.persistence.forProvider();
    private String providerName;

    public FindProviderByName(String argName) {
        ArgumentChecks.isNotNull(argName,
            "Invalid argument name, cannot be null");
        ArgumentChecks.isNotBlank(argName, "Invalid argument name");
        this.providerName = argName;
    }

    @Override
    public List<ProviderDto> execute() {

        return DtoAssembler.toDtoList(pg.findByName(providerName));

    }

}
