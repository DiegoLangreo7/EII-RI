package uo.ri.cws.application.service.spare.supply.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Supply;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteSupply implements Command<Void> {

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

        SupplyRepository repo = Factories.repository.forSupply();

        Optional<Supply> os = repo.findByNifAndCode(nif,code);
        BusinessChecks.exists(os, "Supply does not exists");

        Supply s = os.get();
        
        repo.remove(s);

        return null;
    }
}
