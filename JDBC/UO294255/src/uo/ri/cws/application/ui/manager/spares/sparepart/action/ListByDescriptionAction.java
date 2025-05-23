package uo.ri.cws.application.ui.manager.spares.sparepart.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SparePartReportService;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListByDescriptionAction implements Action {

	@Override
	public void execute() throws Exception {
		String desc = Console.readString("Spare part description (may be partial)");
		
		SparePartReportService service = Factories.service.forSparePartReportService();
		List<SparePartReportDto> spares = service.findByDescription(desc);
		
		Console.println("There are " + spares.size() + " spare parts");
		for(SparePartReportDto p: spares) {
			Printer.print(p);
		}
	}

}
