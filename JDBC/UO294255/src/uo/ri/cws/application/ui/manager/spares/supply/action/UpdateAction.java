package uo.ri.cws.application.ui.manager.spares.supply.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class UpdateAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("Please, provide the following data");
		String nif = Console.readString("Supply nif");
		String code = Console.readString("Spare part code");

		SuppliesCrudService service = Factories.service.forSuppliesCrudService();
		Optional<SupplyDto> op = service.findByNifAndCode(nif, code);
		
		if ( op.isEmpty() ) {
			Console.println("There is no such supply.");
			Console.println("Please mind the nif/code and try again");
			return;
		}
		
		SupplyDto dto = op.get();
		Console.println("Current data for the supply");
		Printer.print( dto );
		
		dto.price = Console.readDouble("new price", dto.price);
		dto.deliveryTerm = Console.readInt("new delivery term", dto.deliveryTerm);
		
		service.update(dto); // dto keeps the id and version
		
		Console.println("The supply has been updated");
	}

}
