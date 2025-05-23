package uo.ri.cws.application.service.spare.supply.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.spares.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;

public class DtoAssembler {

    public static SupplyRecord toRecord(SupplyDto dto) {
        SupplyRecord s = new SupplyRecord();
        s.provider.nif = dto.provider.nif;
        s.sparePart.code = dto.sparePart.code;
        s.price = dto.price;
        s.deliveryTerm = dto.deliveryTerm;
        s.id = dto.id;
        s.version = dto.version;
        return s;
    }

    public static SupplyDto toDto(SupplyRecord record) {
        SupplyDto s = new SupplyDto();

        s.provider.nif = record.provider.nif;
        s.sparePart.code = record.sparePart.code;
        s.price = record.price;
        s.deliveryTerm = record.deliveryTerm;
        s.id = record.id;
        s.version = record.version;
        return s;
    }

    public static Optional<SupplyDto> toDtoOpt(SupplyRecord record) {
        if (record == null) {
            return Optional.empty();
        }

        SupplyDto s = new SupplyDto();
        s.provider.nif = record.provider.nif;
        s.sparePart.code = record.sparePart.code;
        s.price = record.price;
        s.deliveryTerm = record.deliveryTerm;
        s.id = record.id;
        s.version = record.version;
        return Optional.of(s);
    }

    public static List<SupplyDto> toDtoList(List<SupplyRecord> list) {
        return list.stream().map(m -> toDto(m)).toList();
    }

}
