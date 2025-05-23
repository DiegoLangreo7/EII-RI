package uo.ri.cws.application.ui.manager.spares.provider.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListBySparePartsSuppliedAction implements Action {

	@Override
	public void execute() throws Exception {
		String code = Console.readString("Spare part code");
		
		ProvidersCrudService service = Factories.service.forProvidersService();
		List<ProviderDto> providers = service.findBySparePartCode(code);
		
		Console.println("There are " + providers.size() + " providers");
		for(ProviderDto p: providers) {
			Printer.print(p);
		}
	}

}
