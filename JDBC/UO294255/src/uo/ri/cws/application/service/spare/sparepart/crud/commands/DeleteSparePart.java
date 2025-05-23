package uo.ri.cws.application.service.spare.sparepart.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.orderlines.OrderLineGateway;
import uo.ri.cws.application.persistence.spares.orderlines.OrderLineGateway.OrderLineRecord;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway.SubstitutionRecord;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteSparePart implements Command<Void> {

    private SparePartGateway spg = Factories.persistence.forSparePart();
    private SupplyGateway supg = Factories.persistence.forSupply();
    private OrderLineGateway olg = Factories.persistence.forOrderLines();
    private SubstitutionGateway subg = Factories.persistence.forSubstitution();

    private String codeSparePart;

    public DeleteSparePart(String code) {
        ArgumentChecks.isNotNull(code, "Code cannot be null");
        ArgumentChecks.isNotBlank(code, "Invalid code");
        this.codeSparePart = code;
    }
    
    @Override
    public Void execute() throws BusinessException {
        checkSparePartExists();
        checkCanBeDeleted();
        deleteSparePart();
        return null;
    }
    
    private void checkSparePartExists() throws BusinessException {
        Optional<SparePartRecord> record = spg.findBySparePartCode(codeSparePart);
        BusinessChecks.exists(record, "The spare part does not exist");
    }

    private void checkCanBeDeleted() throws BusinessException {
        List<SupplyRecord> supplies = supg.findBySparePartCode(codeSparePart);
        BusinessChecks.isTrue(supplies.isEmpty(), "The spare part has supplies associated");
        
        Optional<SparePartRecord> record = spg.findBySparePartCode(codeSparePart);
        List<OrderLineRecord> orderLines = 
            olg.findBySparePartId(record.get().id);
        BusinessChecks.isTrue(orderLines.isEmpty(), "The spare part is involved in orders");
        
        List<SubstitutionRecord> substitutions = subg.findBySparePartId(codeSparePart);
        BusinessChecks.isTrue(substitutions.isEmpty(), "The spare part has substitutions associated");
        
    }

    private void deleteSparePart() {
        Optional<SparePartRecord> record = spg.findBySparePartCode(codeSparePart);
        spg.remove(record.get().id);
    }
}
