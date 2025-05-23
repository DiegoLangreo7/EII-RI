package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

	private String mechanicId;

	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotBlank(mechanicId);
		this.mechanicId = mechanicId;
	}

	@Override
	public Void execute() throws BusinessException {

		MechanicRepository repo = Factories.repository.forMechanic();
		
		Optional<Mechanic> om = repo.findById(mechanicId);
		BusinessChecks.exists(om,"Mechanic does not exists");
		
		Mechanic m = om.get();
		BusinessChecks.isTrue(m.getAssigned().isEmpty(), "The mechanic has workorders");
		BusinessChecks.isTrue(m.getInterventions().isEmpty(), "The mechanic already has interventions");
		repo.remove(m);
		
		return null;
	}

}
