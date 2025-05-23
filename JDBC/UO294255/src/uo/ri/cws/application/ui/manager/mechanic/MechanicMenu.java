package uo.ri.cws.application.ui.manager.mechanic;

import uo.ri.cws.application.ui.manager.mechanic.action.AddMechanicAction;
import uo.ri.cws.application.ui.manager.mechanic.action.DeleteMechanicAction;
import uo.ri.cws.application.ui.manager.mechanic.action.FindAllMechanicsAction;
import uo.ri.cws.application.ui.manager.mechanic.action.UpdateMechanicAction;
import uo.ri.util.menu.BaseMenu;
import uo.ri.util.menu.NotYetImplementedAction;

public class MechanicMenu extends BaseMenu {

	public MechanicMenu() {
		menuOptions = new Object[][] { 
			{"Manager > Mechanics management", null},
			
			{ "Add mechanic", 				AddMechanicAction.class }, 
			{ "Update mechanic", 	UpdateMechanicAction.class }, 
			{ "Delete mechanic", 				DeleteMechanicAction.class }, 
			{ "List mechanic", 				NotYetImplementedAction.class },
			{ "List mechanics", 				FindAllMechanicsAction.class },
		};
	}

}
