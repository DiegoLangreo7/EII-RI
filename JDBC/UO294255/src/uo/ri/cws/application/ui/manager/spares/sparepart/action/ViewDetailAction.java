package uo.ri.cws.application.ui.manager.spares.sparepart.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SparePartReportService;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ViewDetailAction implements Action {

	@Override
	public void execute() throws Exception {
		String code = Console.readString("Code");
		
		SparePartReportService service = Factories.service.forSparePartReportService();
		Optional<SparePartReportDto> op = service.findByCode(code);
		
		if ( op.isEmpty() ) {
			Console.println("There is no such spare part.");
			Console.println("Please mind the code and try again.");
			return;
		}
		
		Printer.print( op.get() );
	}

}
