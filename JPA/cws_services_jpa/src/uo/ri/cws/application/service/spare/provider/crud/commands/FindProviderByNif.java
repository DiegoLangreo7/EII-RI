package uo.ri.cws.application.service.spare.provider.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.provider.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindProviderByNif implements Command<Optional<ProviderDto>> {

    private String nif;
    private ProviderRepository repo = Factories.repository.forProvider();

    public FindProviderByNif(String argNif) {
        ArgumentChecks.isNotNull(argNif, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(argNif, "Invalid argument nif");
        this.nif = argNif;
    }

    @Override
    public Optional<ProviderDto> execute() {
        return repo.findByNif(nif).map(m -> DtoAssembler.toDto(m));
    }

}
