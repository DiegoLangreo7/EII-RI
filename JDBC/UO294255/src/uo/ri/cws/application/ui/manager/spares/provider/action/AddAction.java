package uo.ri.cws.application.ui.manager.spares.provider.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class AddAction implements Action {

    @Override
    public void execute() throws Exception {
        ProviderDto dto = new ProviderDto();

        Console.println("Please, provide the following data");
        dto.nif = Console.readString("Nif: ");
        dto.name = Console.readString("Name: ");
        dto.email = Console.readString("Email: ");
        dto.phone = Console.readString("Phone: ");

        ProvidersCrudService service = Factories.service.forProvidersService();
        dto = service.add(dto);

        Console.println("The new provider has been registered with id " + dto.id);
    }

}
