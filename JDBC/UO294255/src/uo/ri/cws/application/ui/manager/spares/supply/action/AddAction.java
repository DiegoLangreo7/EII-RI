package uo.ri.cws.application.ui.manager.spares.supply.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class AddAction implements Action {

	@Override
	public void execute() throws Exception {
		SupplyDto dto = new SupplyDto();
		
		Console.println("Please, provide the following data:");
		dto.provider.nif = Console.readString("Provider nif");
		dto.sparePart.code = Console.readString("Spare part code");
		dto.price = Console.readDouble("Price");
		dto.deliveryTerm = Console.readInt("Delivery term");
		
		SuppliesCrudService service = Factories.service.forSuppliesCrudService();
		dto = service.add(dto);
		
		Console.println("The new supply has been registered with id " +  dto.id);
	}

}
