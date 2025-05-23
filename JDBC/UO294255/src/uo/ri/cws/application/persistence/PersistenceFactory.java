package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.impl.InterventionGatewayImpl;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.impl.InvoiceGatewayImpl;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.persistence.spares.order.OrderGateway;
import uo.ri.cws.application.persistence.spares.order.impl.OrderGatewayImpl;
import uo.ri.cws.application.persistence.spares.orderlines.OrderLineGateway;
import uo.ri.cws.application.persistence.spares.orderlines.impl.OrderLineGatewayImpl;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway;
import uo.ri.cws.application.persistence.spares.provider.impl.ProviderGatewayImpl;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.spares.sparepart.impl.SparePartGatewayImpl;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway;
import uo.ri.cws.application.persistence.spares.supply.impl.SupplyGatewayImpl;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway;
import uo.ri.cws.application.persistence.substitution.impl.SubstitutionGatewayImpl;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.impl.WorkOrderGatewayImpl;

public class PersistenceFactory {

    public MechanicGateway forMechanic() {
        return new MechanicGatewayImpl();
    }

    public InvoiceGateway forInvoice() {
        return new InvoiceGatewayImpl();
    }

    public WorkOrderGateway forWorkOrder() {
        return new WorkOrderGatewayImpl();
    }

    public InterventionGateway forIntervention() {
        return new InterventionGatewayImpl();
    }

    public ProviderGateway forProvider() {
        return new ProviderGatewayImpl();
    }

    public SupplyGateway forSupply() {
        return new SupplyGatewayImpl();
    }

    public SparePartGateway forSparePart() {
        return new SparePartGatewayImpl();
    }

    public OrderGateway forOrder() {
        return new OrderGatewayImpl();
    }
    
    public OrderLineGateway forOrderLines() {
        return new OrderLineGatewayImpl();
    }

    
    public SubstitutionGateway forSubstitution() {
        return new SubstitutionGatewayImpl();
    }
   
}
