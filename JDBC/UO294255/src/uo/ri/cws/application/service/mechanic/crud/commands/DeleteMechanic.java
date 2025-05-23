package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private MechanicGateway mg = Factories.persistence.forMechanic();
    private InterventionGateway ig = Factories.persistence.forIntervention();
    private WorkOrderGateway wog = Factories.persistence.forWorkOrder();
    private String idMechanic;

    public DeleteMechanic(String idMechanic) {
        ArgumentChecks.isNotNull(idMechanic, "Id cannot be null");
        ArgumentChecks.isNotBlank(idMechanic, "Invalid id");
        this.idMechanic = idMechanic;
    }

    @Override
    public Void execute() throws BusinessException {
        checkMechanicExists();
        checkCanBeDeleted();
        deleteMechanic();
        return null;
    }

    private void deleteMechanic() {
        mg.remove(idMechanic);
    }

    private void checkMechanicExists() throws BusinessException {
        Optional<MechanicRecord> omr = mg.findById(idMechanic);
        BusinessChecks.exists(omr, "The mechanic doesnt exists");
    }

    private void checkCanBeDeleted() throws BusinessException {
        List<InterventionRecord> interventions = ig
            .findByMechanicId(idMechanic);
        Optional<InterventionRecord> optionalIntervention = interventions
            .stream()
            .findFirst();
        BusinessChecks.doesNotExist(optionalIntervention,
            "It has an intervention associated");

        List<WorkOrderRecord> workOrders = wog.findByMechanicId(idMechanic);
        Optional<WorkOrderRecord> optionalWorkOrder = workOrders
            .stream()
            .findFirst();
        BusinessChecks.doesNotExist(optionalWorkOrder,
            "It has a work order associated");
    }

}
