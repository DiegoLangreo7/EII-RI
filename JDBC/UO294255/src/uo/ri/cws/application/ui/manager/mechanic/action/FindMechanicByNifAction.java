package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class FindMechanicByNifAction implements Action{

    @Override
    public void execute() throws Exception {
        String nifMechanic = Console.readString("Type mechanic nif");

        MechanicCrudService ms = Factories.service.forMechanicService();
        Optional<MechanicDto> mechanic = ms.findMechanicByNif(nifMechanic);
        
        if (mechanic.isPresent()) {
            Printer.printMechanic(mechanic.get());
        } else {
            System.out.println("Mechanic not found with nif: " + nifMechanic);
        }
        
    }

}
