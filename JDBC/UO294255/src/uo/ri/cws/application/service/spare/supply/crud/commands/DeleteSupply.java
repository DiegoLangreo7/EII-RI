package uo.ri.cws.application.service.spare.supply.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteSupply implements Command<Void> {

    private SupplyGateway sg = Factories.persistence.forSupply();
    private String id;
    private String nif;
    private String code;

    public DeleteSupply(String nif, String code) {
        ArgumentChecks.isNotNull(nif, "Nif cant be null");
        ArgumentChecks.isNotBlank(nif, "Invalid nif");
        ArgumentChecks.isNotNull(code, "Code cant be null");
        ArgumentChecks.isNotBlank(code, "Code nif");
        this.nif = nif;
        this.code = code;
    }

    @Override
    public Void execute() throws BusinessException {
        checkSupplyExists();
        deleteSupply();
        return null;
    }

    private void deleteSupply() {
        sg.remove(this.id);
    }

    private void checkSupplyExists() throws BusinessException {
        Optional<SupplyRecord> sr = sg.findByNifAndCode(nif, code);
        BusinessChecks.exists(sr, "The Supply doesnt exists");
        this.id = sr.get().id;

    }

}
