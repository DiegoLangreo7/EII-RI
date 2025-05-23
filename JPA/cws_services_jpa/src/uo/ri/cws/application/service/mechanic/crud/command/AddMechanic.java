package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

	private MechanicDto dto;

	public AddMechanic(MechanicDto arg) {
		ArgumentChecks.isNotNull(arg);
		ArgumentChecks.isNotBlank(arg.nif);
		ArgumentChecks.isNotBlank(arg.name);
		ArgumentChecks.isNotBlank(arg.surname);
		this.dto = arg;
	}

	@Override
	public MechanicDto execute() throws BusinessException {
		MechanicRepository repo = Factories.repository.forMechanic();

		Optional<Mechanic> om = repo.findByNif(dto.nif);
		BusinessChecks.doesNotExist(om, "Mechanic already exists");

		// antes le pasabamos un mecanico, ahora le pasamos un mecanico
		Mechanic m = new Mechanic(dto.nif, dto.name, dto.surname);

		repo.add(m);

		dto.id = m.getId();
		return dto;
	}

}
