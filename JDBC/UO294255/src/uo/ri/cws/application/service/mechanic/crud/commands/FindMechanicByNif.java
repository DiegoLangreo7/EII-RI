package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicByNif implements Command<Optional<MechanicDto>> {

    private MechanicGateway mg = Factories.persistence.forMechanic();
    private String nif;

    public FindMechanicByNif(String argNif) {
        ArgumentChecks.isNotNull(argNif, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(argNif, "Invalid argument nif");
        this.nif = argNif;
    }

    @Override
    public Optional<MechanicDto> execute() {
        Optional<MechanicRecord> recordOpt = mg.findByNif(nif);

        if (recordOpt.isPresent()) {
            return DtoAssembler.toDtoOpt(recordOpt.get());
        } else {
            return Optional.empty();
        }
    }
}
