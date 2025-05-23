package uo.ri.cws.application.service.spare.provider.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Provider;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteProvider implements Command<Void> {

    private String providerNif;

    public DeleteProvider(String nif) {
        ArgumentChecks.isNotNull(nif, "Nif cant be null");
        ArgumentChecks.isNotBlank(nif, "Invalid nif");
        this.providerNif = nif;
    }

    @Override
    public Void execute() throws BusinessException {

        ProviderRepository repo = Factories.repository.forProvider();

        Optional<Provider> op = repo.findByNif(providerNif);
        BusinessChecks.exists(op, "Provider does not exists");

        Provider p = op.get();
        BusinessChecks.isTrue(p.getSupplies().isEmpty(),
            "The provider has supplies");
        BusinessChecks.isTrue(p.getOrders().isEmpty(),
            "The provider has orders");
        repo.remove(p);

        return null;
    }

}
