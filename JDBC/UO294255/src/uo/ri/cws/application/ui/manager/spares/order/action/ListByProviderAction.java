package uo.ri.cws.application.ui.manager.spares.order.action;

import java.util.Comparator;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListByProviderAction implements Action {

	@Override
	public void execute() throws Exception {
		String nif = Console.readString("Please, type the provider nif");
		
		OrdersService service = Factories.service.forOrdersService();
		List<OrderDto> orders = service.findByProviderNif(nif);
		
		orders.sort( new OrdersComparator() ); 
		
		for(OrderDto o: orders) {
			Printer.printSummary(o);
		}
		

	}

	public class OrdersComparator implements Comparator<OrderDto> {
		@Override
		public int compare(OrderDto o1, OrderDto o2) {
			int diff = o1.state.compareTo( o2.state );
			if ( diff == 0) {
				diff = o1.orderedDate.compareTo( o2.orderedDate );
			}
			return diff;
		}
	}

}
