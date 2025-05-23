package uo.ri.cws.application.ui.manager.spares.order;

import uo.ri.cws.application.ui.manager.spares.order.action.GenerateOrdersAction;
import uo.ri.cws.application.ui.manager.spares.order.action.ListByProviderAction;
import uo.ri.cws.application.ui.manager.spares.order.action.ReceiveOrderAction;
import uo.ri.cws.application.ui.manager.spares.order.action.ViewOrderDetailAction;
import uo.ri.util.menu.BaseMenu;

public class OrdersMenu extends BaseMenu {

	public OrdersMenu() {
		menuOptions = new Object[][] {
			{"Manager > Parts management > Orders", null},

			{ "Generate", 			GenerateOrdersAction.class },
			{ "View order detail", 	ViewOrderDetailAction.class },
			{ "List by provider", 	ListByProviderAction.class },
			{ "Receive order", 		ReceiveOrderAction.class },
		};
	}

}
