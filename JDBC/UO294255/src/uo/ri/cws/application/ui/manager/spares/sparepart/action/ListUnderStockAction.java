package uo.ri.cws.application.ui.manager.spares.sparepart.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SparePartReportService;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListUnderStockAction implements Action {

	@Override
	public void execute() throws Exception {
		SparePartReportService service = Factories.service.forSparePartReportService();
		List<SparePartReportDto> spares = service.findUnderStock();
		
		Console.println("There are " + spares.size() + " spares under stock");
		for(SparePartReportDto sp: spares) {
			Printer.print(sp);
		}
	}

}
