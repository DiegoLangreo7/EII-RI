package uo.ri.cws.application.ui.util;

import java.util.List;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderLineDto;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.vehicletype.VehicleTypeCrudService.VehicleTypeDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;
import uo.ri.util.console.Console;

public class Printer {

	public static void printInvoice(InvoiceDto invoice) {

		double importeConIVa = invoice.amount;
		double iva =  invoice.vat;
		double importeSinIva = importeConIVa / (1 + iva / 100);

		Console.printf("Invoice #: %d\n", 				invoice.number );
		Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", 	invoice.date);
		Console.printf("\tTotal: %.2f €\n", 			importeSinIva);
		Console.printf("\tTax: %.1f %% \n", 			invoice.vat );
		Console.printf("\tTotal, tax inc.: %.2f €\n", 	invoice.amount );
		Console.printf("\tState: %s\n", 				invoice.state );
	}

	public static void printPaymentMeans(List<PaymentMeanDto> medios) {
		Console.println();
		Console.println("Available payment means");

		Console.printf("\t%s \t%-8.8s \t%s \n", "Id", "Type", "Acummulated");
		for (PaymentMeanDto medio : medios) {
			printPaymentMean( medio );
		}
	}

	private static void printPaymentMean(PaymentMeanDto medio) {
		Console.printf("\t%s \t%-8.8s \t%s \n"
				, medio.id
				, medio.getClass().getName()  // not the best...
				, medio.accumulated
		);
	}

	public static void printWorkOrder(WorkOrderDto rep) {

		Console.printf("\t%s \t%-40.40s \t%td/%<tm/%<tY \t%-12.12s \t%.2f\n"
				, rep.id
				, rep.description
				, rep.date
				, rep.state
				, rep.amount
		);
	}

	public static void printMechanic(MechanicDto m) {

		Console.printf("\t%s %-10.10s %-15.15s %-25.25s\n"
				, m.id
				, m.nif
				, m.name
				, m.surname
			);
	}

    public static void printMechanics(List<MechanicDto> list) {

        Console.printf("\t%-36s %-9s %-10s %-25s %-10s\n",
            "Mechanic identifier"
            , "NIF"
            , "Name"
            , "Surname"
            , "Version");
        for (MechanicDto m : list) {
            printMechanic(m);
        }
    }
    
	public static void printVehicleType(VehicleTypeDto vt) {

		Console.printf("\t%s %-10.10s %5.2f €/hour %d\n"
				, vt.id
				, vt.name
				, vt.pricePerHour
				, vt.minTrainigHours
			);
	}

	public static void printSummary(OrderDto o) {
		Console.printf("\t%s %-10.10s %td/%<tm/%<tY %-8.8s %9.2f €\n"
				, o.code
				, o.provider.nif
				, o.orderedDate
				, o.state
				, o.amount
			);
	}

	public static void printDetail(OrderDto o) {
		printSummary( o );
		Console.printf("\t%-10.10s %td/%<tm/%<tY %td/%<tm/%<tY %6.2f € %s\n"
				, o.provider.name
				, o.orderedDate
				, o.receptionDate
				, o.amount
				, o.state
			);
		for(OrderLineDto l : o.lines) {
			Console.printf("\t\t%-10.10s %-20.20s %3d %6.2f €\n"
				, l.sparePart.code
				, l.sparePart.description
				, l.quantity
				, l.price
			);
		}
	}

	public static void print(ProviderDto p) {
		Console.printf("\t%-10.10s %-20.20s %-20.20s %-11.11s\n"
				, p.nif
				, p.name
				, p.email
				, p.phone
			);
	}

	public static void print(SparePartDto sp) {
		Console.printf("\t%-10.10s %-30.30s %4d %4d %4d %6.2f €\n"
				, sp.code
				, sp.description
				, sp.stock
				, sp.minStock
				, sp.maxStock
				, sp.price
			);
	}

	public static void print(SparePartReportDto sp) {
		Console.printf("\t%-10.10s %-30.30s %4d %4d %4d %6.2f € %5d\n"
				, sp.code
				, sp.description
				, sp.stock
				, sp.minStock
				, sp.maxStock
				, sp.price
				, sp.totalUnitsSold
			);
	}

	public static void print(SupplyDto s) {
		Console.printf("\t%-10.10s %-20.20s %-10.10s %-30.30s %6.2f € %2d\n"
				, s.provider.nif
				, s.provider.name
				, s.sparePart.code
				, s.sparePart.description
				, s.price
				, s.deliveryTerm
			);
	}

    public static void printInvoicingWorkOrder(InvoicingWorkOrderDto arg) {
        Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n"
            , arg.id
            , arg.description
            , arg.date
            , arg.state
            , arg.amount
            );
    }

    public static void printInvoicingWorkOrders(
        List<InvoicingWorkOrderDto> arg) {
        Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n"
            , "Identifier"
            , "description"
            , "status"
            , "total");
        for (InvoicingWorkOrderDto inv : arg) {
            printInvoicingWorkOrder(inv);
        }
    }
}
