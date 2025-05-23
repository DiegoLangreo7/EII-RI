package uo.ri.cws.application.ui.manager.spares.supply.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class DeleteAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("Please, provide the following data");
		String nif = Console.readString("Provider nif");
		String code = Console.readString("Spare part code");

		SuppliesCrudService service = Factories.service.forSuppliesCrudService();
		service.delete(nif, code);

		Console.println("The supply has been deleted");
	}

}
