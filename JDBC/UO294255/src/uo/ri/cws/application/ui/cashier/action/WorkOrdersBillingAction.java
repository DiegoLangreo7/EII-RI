package uo.ri.cws.application.ui.cashier.action;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class WorkOrdersBillingAction implements Action {

    @Override
    public void execute() throws BusinessException {
        List<String> workOrderIds = new ArrayList<String>();

        // type work order ids to be invoiced in the invoice
        do {
            String id = Console.readString("Type work order ids:  ");
            workOrderIds.add(id);
        } while (nextWorkorder());

        InvoicingService service = Factories.service.forInvoicingService();
        InvoiceDto invoice = service.createInvoiceFor(workOrderIds);
        Printer.printInvoice(invoice);
    }

    private boolean nextWorkorder() {
        return Console.readString(" Any other workorder? (y/n) ")
                      .equalsIgnoreCase("y");
    }

}
