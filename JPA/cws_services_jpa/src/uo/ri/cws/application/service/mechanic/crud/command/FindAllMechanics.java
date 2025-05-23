package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class FindAllMechanics implements Command<List<MechanicDto>> {

    @Override
    public List<MechanicDto> execute() {

        MechanicRepository repo = Factories.repository.forMechanic();

        List<Mechanic> list = repo.findAll();

        return DtoAssembler.toMechanicDtoList(list);
    }

}
