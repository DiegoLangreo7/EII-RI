package uo.ri.cws.application.ui.manager.spares.sparepart.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SparePartCrudService;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class UpdateAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("Please, provide the following data");
		String code = Console.readString("Code");
		
		SparePartCrudService service = Factories.service.forSparePartCrudService();
		Optional<SparePartDto> op = service.findByCode(code);
		
		if ( op.isEmpty() ) {
			Console.println("There is no such spare part. ");	
			Console.println("Please mind the code and try again.");
			return;
		}
		
		SparePartDto dto = op.get();
		Console.println("Current data for the spare part");
		Printer.print( dto );
		
		dto.description = Console.readString("new description", dto.description);
		dto.stock = Console.readInt("new stock", dto.stock);
		dto.minStock = Console.readInt("new minimum stock", dto.minStock);
		dto.maxStock = Console.readInt("new maximum stock", dto.maxStock);
		dto.price = Console.readDouble("new price", dto.price);
		
		service.update(dto); // dto keeps the id and version
		
		Console.println("The spare part has been updated");
	}

}
