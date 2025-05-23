package uo.ri.cws.application.ui.manager.spares.sparepart.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SparePartCrudService;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class AddAction implements Action {

    @Override
    public void execute() throws Exception {
        SparePartDto dto = new SparePartDto();

        Console.println("Please, provide the following data");
        dto.code = Console.readString("Code");
        dto.description = Console.readString("Description");
        dto.stock = Console.readInt("Current stock");
        dto.minStock = Console.readInt("Minimum stock");
        dto.maxStock = Console.readInt("Maximum stock");
        dto.price = Console.readDouble("Price");

        SparePartCrudService service = Factories.service.forSparePartCrudService();
        dto = service.add(dto);

        Console.println("The new spare part has been registered with id " + dto.id);
    }

}
