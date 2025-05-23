package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicById implements Command<Optional<MechanicDto>> {

    private MechanicGateway mg = Factories.persistence.forMechanic();
    private String idMechanic;

    public FindMechanicById(String id) {
        ArgumentChecks.isNotNull(id, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(id, "Invalid argument id");
        this.idMechanic = id;
    }

    @Override
    public Optional<MechanicDto> execute() {
        Optional<MechanicRecord> recordOpt = mg.findById(idMechanic);

        if (recordOpt.isPresent()) {
            return DtoAssembler.toDtoOpt(recordOpt.get());
        } else {
            return Optional.empty();
        }
    }

}
