package uo.ri.cws.application.service.spare.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.spares.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.persistence.spares.orderlines.OrderLineGateway.OrderLineRecord;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderLineDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderedSpareDto;

public class DtoAssembler {

    public static OrderRecord toRecord(OrderDto dto) {
        OrderRecord record = new OrderRecord();
        record.id = dto.id;
        record.version = dto.version;
        record.code = dto.code;
        record.orderedDate = dto.orderedDate;
        record.receptionDate = dto.receptionDate;
        record.amount = dto.amount;
        record.state = dto.state;
        record.providerId = dto.provider.id;
        record.providerNif = dto.provider.nif;
        record.providerName = dto.provider.name;
        return record;
    }

    public static List<OrderLineRecord> toOrderLineRecordList(OrderDto dto) {
        List<OrderLineRecord> orderLineRecords = new ArrayList<>();
        for (OrderLineDto line : dto.lines) {
            OrderLineRecord record = new OrderLineRecord();
            record.sparePartId = line.sparePart.id;
            record.sparePartCode = line.sparePart.code;
            record.sparePartDescription = line.sparePart.description;
            record.price = line.price;
            record.quantity = line.quantity;
            orderLineRecords.add(record);
        }
        return orderLineRecords;
    }

    public static OrderDto toDto(OrderRecord record) {
        OrderDto dto = new OrderDto();
        dto.id = record.id;
        dto.version = record.version;
        dto.code = record.code;
        dto.orderedDate = record.orderedDate;
        dto.receptionDate = record.receptionDate;
        dto.amount = record.amount;
        dto.state = record.state;
        dto.provider.id = record.providerId;
        dto.provider.nif = record.providerNif;
        dto.provider.name = record.providerName;
        return dto;
    }

    public static OrderDto toDtoWithLines(OrderRecord record, List<OrderLineRecord> lines) {
        OrderDto dto = toDto(record);
        for (OrderLineRecord r : lines) {
            OrderLineDto line = new OrderLineDto();
            line.price = r.price;
            line.quantity = r.quantity;

            OrderedSpareDto sp = new OrderedSpareDto();
            sp.id = r.sparePartId;
            sp.code = r.sparePartCode;
            sp.description = r.sparePartDescription;

            line.sparePart = sp;
            dto.lines.add(line);
        }
        return dto;
    }

    public static Optional<OrderDto> toDtoOpt(OrderRecord record, List<OrderLineRecord> lines) {
        if (record == null) {
            return Optional.empty();
        }
        return Optional.of(toDtoWithLines(record, lines));
    }

    public static List<OrderDto> toDtoList(List<OrderRecord> list) {
        List<OrderDto> dtos = new ArrayList<>();
        for (OrderRecord record : list) {
            dtos.add(toDto(record));
        }
        return dtos;
    }
}
