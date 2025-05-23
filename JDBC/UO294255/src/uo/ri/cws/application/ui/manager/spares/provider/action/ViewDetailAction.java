package uo.ri.cws.application.ui.manager.spares.provider.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ViewDetailAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("Please, provide the following data");
		String nif = Console.readString("Nif: ");
		
		ProvidersCrudService service = Factories.service.forProvidersService();
		Optional<ProviderDto> op = service.findByNif(nif);
		
		if ( op.isEmpty() ) {
			Console.println("There is no such provider.");
			Console.println("Please mind the nif and try again.");
			return;
		}
		
		Printer.print( op.get() );
	}

}
