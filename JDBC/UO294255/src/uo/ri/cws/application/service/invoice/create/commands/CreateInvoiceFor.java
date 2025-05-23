package uo.ri.cws.application.service.invoice.create.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Round;

public class CreateInvoiceFor implements Command<InvoiceDto> {

    private InvoiceGateway ig = Factories.persistence.forInvoice();
    private WorkOrderGateway wog = Factories.persistence.forWorkOrder();
    private List<String> ids;
    private List<WorkOrderRecord> workOrders;

    public CreateInvoiceFor(List<String> workOrderIds) {
        ArgumentChecks.isNotNull(workOrderIds, "Invalid list argument");
        ArgumentChecks.isTrue(workOrderIds.size() != 0, "List is empty");
        for (String id : workOrderIds) {
            ArgumentChecks.isNotNull(id, "Invalid id, is cant be null");
        }
        this.ids = workOrderIds;
    }

    @Override
    public InvoiceDto execute() throws BusinessException {
        checkWorkOrders();
        InvoiceDto dto = createInvoice();
        linkWorkOrders(dto);
        return dto;
    }

    private void linkWorkOrders(InvoiceDto dto) {
        for (WorkOrderRecord workOrder : this.workOrders) {
            workOrder.state = "INVOICED";
            workOrder.invoiceId = dto.id;
            wog.setInvoiced(workOrder.id, workOrder.invoiceId);
        }

    }

    private InvoiceDto createInvoice() {
        InvoiceDto dto = new InvoiceDto();
        dto.id = UUID.randomUUID().toString();
        dto.version = 1L;
        dto.date = LocalDate.now();
        double total = calculateTotalInvoice();
        dto.amount = Round.twoCents(total);
        dto.vat = Round.twoCents(total * 0.21 / 1.21);
        dto.amount = Round.twoCents(total);
        dto.state = "NOT_YET_PAID";
        ig.add(DtoAssembler.toRecord(dto));
        return dto;
    }

    private double calculateTotalInvoice() {
        double VAT = 0.21;
        double total = 0.0;
        for (WorkOrderRecord workOrder : this.workOrders) {
            total += workOrder.amount;
        }
        return total *= (1 + VAT);
    }

    private void checkWorkOrders() throws BusinessException {
        List<WorkOrderRecord> worList = new ArrayList<WorkOrderRecord>();
        for (String workOrderID : ids) {
            Optional<WorkOrderRecord> owr = wog.findById(workOrderID);
            BusinessChecks.exists(owr, "WorkOrder doesnt exists");
            String workOrderState = owr.get().state;
            BusinessChecks.isTrue(workOrderState.equals("FINISHED"),
                "WorkOrder isnt finished");
            worList.add(owr.get());
        }
        this.workOrders = worList;

    }

}
