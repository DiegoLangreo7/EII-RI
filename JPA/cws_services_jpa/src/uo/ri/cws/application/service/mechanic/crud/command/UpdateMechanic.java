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

public class UpdateMechanic implements Command<Void>{

	private MechanicDto dto;

	public UpdateMechanic(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotNull(dto.nif);
        ArgumentChecks.isNotNull(dto.name);
        ArgumentChecks.isNotNull(dto.surname);
		ArgumentChecks.isNotBlank(dto.nif);
        ArgumentChecks.isNotBlank(dto.name);
        ArgumentChecks.isNotBlank(dto.surname);
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		MechanicRepository repo = Factories.repository.forMechanic();

		Optional<Mechanic> om = repo.findById(dto.id);
		BusinessChecks.exists(om,"Mechanic does not exists");
		
		Mechanic m = om.get();
		
		BusinessChecks.hasVersion(dto.version, m.getVersion()); //para que no ocurra lo de Bob y Alice...
		
		m.setName( dto.name);
		m.setSurname(dto.surname);
		
		//no hay nada que hacer, m (Mechanic) como lo recupero del mapeador, esta siempre persistente
		//todo lo que le hagamos se modifica automaticamente
		return null;
	}

}
