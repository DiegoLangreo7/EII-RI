package uo.ri.cws.application.service.spare.provider.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.provider.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindProviderByNif implements Command<Optional<ProviderDto>> {

    private ProviderGateway pg = Factories.persistence.forProvider();
    private String nif;

    public FindProviderByNif(String argNif) {
        ArgumentChecks.isNotNull(argNif, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(argNif, "Invalid argument nif");
        this.nif = argNif;
    }

    @Override
    public Optional<ProviderDto> execute() {
        Optional<ProviderRecord> recordOpt = pg.findByNif(nif);

        if (recordOpt.isPresent()) {
            return DtoAssembler.toDtoOpt(recordOpt.get());
        } else {
            return Optional.empty();
        }
    }

}
