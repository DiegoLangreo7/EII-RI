package uo.ri.cws.application.ui.manager.spares.sparepart.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SparePartCrudService;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class DeleteAction implements Action {

    @Override
    public void execute() throws Exception {
        String code = Console.readString("Code");

        SparePartCrudService service = Factories.service.forSparePartCrudService();
        service.delete(code);

        Console.println("The spare part has been deleted");
    }

}
