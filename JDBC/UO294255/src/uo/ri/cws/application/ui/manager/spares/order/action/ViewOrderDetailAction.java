package uo.ri.cws.application.ui.manager.spares.order.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ViewOrderDetailAction implements Action {

	@Override
	public void execute() throws Exception {
		String orderCode = Console.readString("Order code");
		
		OrdersService service = Factories.service.forOrdersService();
		Optional<OrderDto> oo = service.findByCode(orderCode);
		
		if ( oo.isEmpty()) {
			Console.println("There is no order with such code");
			return;
		}
			
		Printer.printDetail( oo.get() );
	}

}
