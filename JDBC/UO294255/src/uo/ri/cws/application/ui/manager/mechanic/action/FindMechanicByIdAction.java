package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class FindMechanicByIdAction implements Action {

    @Override
    public void execute() throws Exception {
        String idMechanic = Console.readString("Type mechanic id ");

        MechanicCrudService ms = Factories.service.forMechanicService();
        Optional<MechanicDto> mechanic = ms.findMechanicById(idMechanic);

        if (mechanic.isPresent()) {
            Printer.printMechanic(mechanic.get());
        } else {
            System.out.println("Mechanic not found with id: " + idMechanic);
        }
    }

}
