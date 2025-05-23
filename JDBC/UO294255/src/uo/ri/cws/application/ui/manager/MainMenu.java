package uo.ri.cws.application.ui.manager;

import uo.ri.cws.application.ui.manager.mechanic.MechanicMenu;
import uo.ri.cws.application.ui.manager.spares.sparepart.SparePartsMenu;
import uo.ri.cws.application.ui.manager.vehicletype.VehicleTypeMenu;
import uo.ri.util.menu.BaseMenu;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { 
			{ "Administrator", null },
			{ "Mechanics management", 			MechanicMenu.class }, 
			{ "Spare parts management", 			SparePartsMenu.class },
			{ "Vehicle types management", 	VehicleTypeMenu.class },
		};
	}

	public static void main(String[] args) {
		new MainMenu().execute();
	}

}
