package uo.ri.cws.application.service.spare.provider.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.order.OrderGateway;
import uo.ri.cws.application.persistence.spares.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteProvider implements Command<Void> {

    private ProviderGateway pg = Factories.persistence.forProvider();
    private SupplyGateway sg = Factories.persistence.forSupply();
    private OrderGateway og = Factories.persistence.forOrder();
    private String providerNif;

    public DeleteProvider(String nif) {
        ArgumentChecks.isNotNull(nif, "Nif cant be null");
        ArgumentChecks.isNotBlank(nif, "Invalid nif");
        this.providerNif = nif;
    }

    @Override
    public Void execute() throws BusinessException {
        checkCanBeDeleted();
        checkProviderExist();
        deleteProvider();
        return null;
    }

    private void deleteProvider() {
        pg.remove(providerNif);
    }

    private void checkProviderExist() throws BusinessException {
        Optional<ProviderRecord> omr = pg.findByNif(providerNif);
        BusinessChecks.exists(omr, "The provider doesnt exists");
    }

    private void checkCanBeDeleted() throws BusinessException {
        List<SupplyRecord> supplies = sg.findByProviderNif(providerNif);
        Optional<SupplyRecord> optionalSupply = supplies.stream().findFirst();
        BusinessChecks.doesNotExist(optionalSupply,
            "It has a supply asociated");

        List<OrderRecord> orders = og.findByProviderNif(providerNif);
        Optional<OrderRecord> optionalOrders = orders.stream().findFirst();
        BusinessChecks.doesNotExist(optionalOrders,
            "It has a order associated");
    }

}
