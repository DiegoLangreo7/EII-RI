package uo.ri.cws.application.ui.manager.spares.provider.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListByNameAction implements Action {

	@Override
	public void execute() throws Exception {
		String name = Console.readString("Please, type the name of the part");
		
		ProvidersCrudService service = Factories.service.forProvidersService();
		List<ProviderDto> providers = service.findByName(name);
		
		Console.println("There are " + providers.size() + " providers");
		for(ProviderDto p: providers) {
			Printer.print(p);
		}
	}

}
