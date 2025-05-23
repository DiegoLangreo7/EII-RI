package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;

public class FindAllMechanics implements Command<List<MechanicDto>> {

    private MechanicGateway mg = Factories.persistence.forMechanic();

    @Override
    public List<MechanicDto> execute() {
        return DtoAssembler.toDtoList(mg.findAll());
    }
}
