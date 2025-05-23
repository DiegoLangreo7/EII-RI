package uo.ri.cws.application.ui.manager.spares.supply.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListByProviderAction implements Action {

    @Override
    public void execute() throws Exception {
        String nif = Console.readString("Please, type Supply nif");

        SuppliesCrudService service = Factories.service.forSuppliesCrudService();
        List<SupplyDto> supplies = service.findByProviderNif(nif);

        Console.println("There are " + supplies.size() + " supplies from the provider");
        for (SupplyDto p : supplies) {
            Printer.print(p);
        }
    }

}
