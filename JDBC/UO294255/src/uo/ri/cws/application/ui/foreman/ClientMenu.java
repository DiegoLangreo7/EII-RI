package uo.ri.cws.application.ui.foreman;

import uo.ri.util.menu.BaseMenu;
import uo.ri.util.menu.NotYetImplementedAction;

public class ClientMenu extends BaseMenu {

	public ClientMenu() {
		menuOptions = new Object[][] { 
			{ "Foreman > Customer management", null },

			{ "Add customer", NotYetImplementedAction.class }, 
			{ "Update customer", NotYetImplementedAction.class }, 
			{ "Delete customer", NotYetImplementedAction.class }, 
			{ "List customers", NotYetImplementedAction.class }, 
		};
	}

}
