package uo.ri.cws.application.ui.manager.spares.sparepart;

import uo.ri.cws.application.ui.manager.spares.sparepart.action.AddAction;
import uo.ri.cws.application.ui.manager.spares.sparepart.action.DeleteAction;
import uo.ri.cws.application.ui.manager.spares.sparepart.action.ListByDescriptionAction;
import uo.ri.cws.application.ui.manager.spares.sparepart.action.ListUnderStockAction;
import uo.ri.cws.application.ui.manager.spares.sparepart.action.UpdateAction;
import uo.ri.cws.application.ui.manager.spares.sparepart.action.ViewDetailAction;
import uo.ri.util.menu.BaseMenu;

public class SparePartsMenu extends BaseMenu {

	public SparePartsMenu() {
		menuOptions = new Object[][] {
			{"Manager > Parts management > Spare parts", null},

			{ "Add", 		AddAction.class },
			{ "Update", 	UpdateAction.class },
			{ "Remove", 	DeleteAction.class },
			{ "View detail", 			ViewDetailAction.class },
			{ "List by description", 	ListByDescriptionAction.class },
			{ "List under stock", 		ListUnderStockAction.class },
		};
	}

}
