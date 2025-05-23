package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

    private MechanicGateway mg = Factories.persistence.forMechanic();
    private MechanicDto dto;

    public UpdateMechanic(MechanicDto arg) {
        ArgumentChecks.isNotNull(arg, "Invalid dto");
        ArgumentChecks.isNotBlank(arg.id, "Invalid id");
        ArgumentChecks.isNotNull(arg.id, "Invalid id");
        ArgumentChecks.isNotBlank(arg.name, "Invalid name");
        ArgumentChecks.isNotNull(arg.name, "Invalid name");
        ArgumentChecks.isNotBlank(arg.surname, "Invalid surname");
        ArgumentChecks.isNotNull(arg.surname, "Invalid surname");

        this.dto = new MechanicDto();
        dto.id = arg.id;
        dto.name = arg.name;
        dto.surname = arg.surname;
    }

    @Override
    public Void execute() throws BusinessException {
        checkMechanicExists();
        updateMechanic();
        return null;
    }

    private void checkMechanicExists() throws BusinessException {
        Optional<MechanicRecord> omr = mg.findById(dto.id);
        BusinessChecks.exists(omr, "The mechanic doesnt exists");
    }

    private void updateMechanic() {
        MechanicRecord mr = DtoAssembler.toRecord(dto);
        mg.update(mr);
    }
}
