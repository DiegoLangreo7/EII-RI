package uo.ri.cws.application.ui.manager.spares.supply.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListBySparePartAction implements Action {

	@Override
	public void execute() throws Exception {
		String code = Console.readString("Spare part code");
		
		SuppliesCrudService service = Factories.service.forSuppliesCrudService();
		List<SupplyDto> supplies = service.findBySparePartCode(code);
		
		Console.println("There are " + supplies.size() + " supplies for the spare part");
		for(SupplyDto p: supplies) {
			Printer.print(p);
		}
	}

}
