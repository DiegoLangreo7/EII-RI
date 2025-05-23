package uo.ri.cws.application.ui.manager.spares.provider.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class DeleteAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("Please, provide the following data");
		String nif = Console.readString("Nif");
		
		ProvidersCrudService service = Factories.service.forProvidersService();
		service.delete(nif);


		Console.println("The provider has been deleted");
	}

}
