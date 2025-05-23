package uo.ri.cws.application.ui.manager.spares.order.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ReceiveOrderAction implements Action {

	@Override
	public void execute() throws Exception {
		String code = Console.readString("Please, type the order code");
		
		OrdersService service = Factories.service.forOrdersService();
		Optional<OrderDto> order = Optional.empty();

		if ( order.isEmpty()) {
			Console.println("There is no order with such code");
			return;
		}

		Console.println("Please, review the content of the order received");
		Printer.printDetail( order.get() );
		
		String yesNo = Console.readString("Is all right? (y/n)");
		if ( yesNo.equalsIgnoreCase( "y" ) ) {
			service.receive(code);
			Console.println("The reception has been registered and the prices updated");
		} else {
			Console.println("Please, contact the provider then try again.");
			Console.println("The operation has been aborted.");
		}

	}

}
