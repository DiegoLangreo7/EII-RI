package uo.ri.cws.application.service;

import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.InvoicingServiceImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.SparePartCrudService;
import uo.ri.cws.application.service.spare.SparePartReportService;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.orders.OrdersCrudServiceImpl;
import uo.ri.cws.application.service.spare.provider.crud.ProvidersCrudServiceImpl;
import uo.ri.cws.application.service.spare.sparepart.crud.SparePartCrudServiceImpl;
import uo.ri.cws.application.service.spare.sparepart.report.SparePartReportServiceImpl;
import uo.ri.cws.application.service.spare.supply.crud.SuppliesCrudServiceImpl;

public class ServiceFactory {

    public MechanicCrudService forMechanicService() {
        return new MechanicCrudServiceImpl();
    }

    public InvoicingService forInvoicingService() {
        return new InvoicingServiceImpl();
    }

//    public static WorkOrderCrudService forWorkOrderService() {
//        return new WorkOrderServiceImpl();
//    }
//
//    public static VehicleTypeCrudService forVehicleTypeService() {
//        return new VehicleTypeServiceImpl();
//    }
//
//    public static VehicleCrudService forVehicleService() {
//        return new VehicleCrudServiceImpl(); 
//    }

    public OrdersService forOrdersService() {
        return new OrdersCrudServiceImpl();
    }

    public SuppliesCrudService forSuppliesCrudService() {
        return new SuppliesCrudServiceImpl();
    }

    public ProvidersCrudService forProvidersService() {
        return new ProvidersCrudServiceImpl();
    }

    public SparePartReportService forSparePartReportService() {
        return new SparePartReportServiceImpl();
    }

    public SparePartCrudService forSparePartCrudService() {
        return new SparePartCrudServiceImpl();
    }

}
