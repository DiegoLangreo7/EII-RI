package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicGateway mg = Factories.persistence.forMechanic();
    private MechanicDto dto;

    public AddMechanic(MechanicDto arg) {
        ArgumentChecks.isNotNull(arg, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(arg.nif, "Invalid argument id");
        ArgumentChecks.isNotBlank(arg.name, "Invalid argument name");
        ArgumentChecks.isNotBlank(arg.surname, "Invalid argument surname");

        this.dto = new MechanicDto();
        dto.id = UUID.randomUUID().toString();
        dto.version = 1L;
        dto.nif = arg.nif;
        dto.name = arg.name;
        dto.surname = arg.surname;
    }

    @Override
    public MechanicDto execute() throws BusinessException {
        checkMechanicDoesNotExist();
        insertMechanic();
        return dto;
    }

    private void insertMechanic() throws BusinessException {
        MechanicRecord mr = DtoAssembler.toRecord(dto);
        mg.add(mr);
    }

    private void checkMechanicDoesNotExist() throws BusinessException {
        Optional<MechanicRecord> omr = mg.findByNif(dto.nif);
        BusinessChecks.doesNotExist(omr, "The mechanic already exists");
    }
}
