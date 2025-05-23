package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindNotInvoicedWorkOrdersByClientNif
    implements Command<List<InvoicingWorkOrderDto>> {

    private WorkOrderGateway wog = Factories.persistence.forWorkOrder();
    private String nif;

    public FindNotInvoicedWorkOrdersByClientNif(String nif) {
        ArgumentChecks.isNotNull(nif, "Invalid nif, cannot be null");
        this.nif = nif;
    }

    @Override
    public List<InvoicingWorkOrderDto> execute() {
        List<WorkOrderRecord> list = wog
            .findNotInvoicedWorkOrdersByClientNif(this.nif);
        List<InvoicingWorkOrderDto> result = DtoAssembler
            .toInvoicingWorkOrderList(list);
        return result;
    }

}
