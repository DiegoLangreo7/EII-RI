package uo.ri.cws.application.ui.manager.spares.order.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class GenerateOrdersAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("New orders are about to be generated");
		
		OrdersService service = Factories.service.forOrdersService();
		
		List<OrderDto> orders = service.generateOrders();

		Console.println( orders.size() + " have been generated.");
		for(OrderDto order: orders) {
			Printer.printSummary(order);
		}

	}

}
