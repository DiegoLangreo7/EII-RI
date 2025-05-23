package uo.ri.cws.application.ui.foreman;

import uo.ri.cws.application.ui.foreman.action.AddWorkOrderAction;
import uo.ri.cws.application.ui.foreman.action.UpdateWorkOrderAction;
import uo.ri.util.menu.BaseMenu;
import uo.ri.util.menu.NotYetImplementedAction;

public class ClientReceptionMenu extends BaseMenu {

	public ClientReceptionMenu() {
		menuOptions = new Object[][] { 
			{"Foreman > Check in menu", null},
			
			{"Add breakdown", 			AddWorkOrderAction.class }, 
			{"Update breakdown", 		UpdateWorkOrderAction.class },
			{"Delete breakdown", 		NotYetImplementedAction.class },
			{"", null},
			{"List breakdown", 			NotYetImplementedAction.class }, 
			{"Check a breakdown", 		NotYetImplementedAction.class },
			{"", null},
			{"List mechanics", 			NotYetImplementedAction.class }, 
			{"Assign a breakdown",  	NotYetImplementedAction.class },
		};
	}

}
