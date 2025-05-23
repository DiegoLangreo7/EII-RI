package uo.ri.cws.application.service.invoice.create;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.commands.FindNotInvoicedWorkOrdersByClientNif;
import uo.ri.cws.application.service.invoice.create.commands.CreateInvoiceFor;
import uo.ri.cws.application.service.util.command.executor.JdbcCommandExecutor;
import uo.ri.util.exception.BusinessException;

public class InvoicingServiceImpl implements InvoicingService {

    private JdbcCommandExecutor executor = new JdbcCommandExecutor();

    @Override
    public InvoiceDto createInvoiceFor(List<String> workOrderIds)
        throws BusinessException {
        return executor.execute(new CreateInvoiceFor(workOrderIds));
    }

    @Override
    public List<InvoicingWorkOrderDto> findWorkOrdersByClientNif(String nif)
        throws BusinessException {
        return null;
    }

    @Override
    public List<InvoicingWorkOrderDto> findNotInvoicedWorkOrdersByClientNif(
        String nif) throws BusinessException {
        return executor.execute(new FindNotInvoicedWorkOrdersByClientNif(nif));
    }

    @Override
    public List<InvoicingWorkOrderDto> findWorkOrdersByPlateNumber(String plate)
        throws BusinessException {
        return null;
    }

    @Override
    public Optional<InvoiceDto> findInvoiceByNumber(Long number)
        throws BusinessException {
        return null;
    }

    @Override
    public List<PaymentMeanDto> findPayMeansByClientNif(String nif)
        throws BusinessException {
        return null;
    }

    @Override
    public void settleInvoice(String invoiceId, Map<String, Double> charges)
        throws BusinessException {

    }

}
