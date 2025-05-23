package uo.ri.cws.application.service.spare.sparepart.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;

public class DtoAssembler {

    public static SparePartRecord toRecord(SparePartDto dto) {
        SparePartRecord s = new SparePartRecord();
        s.code = dto.code;
        s.price = dto.price;
        s.description = dto.description;
        s.stock = dto.stock;
        s.minStock = dto.minStock;
        s.maxStock = dto.maxStock;
        s.id = dto.id;
        s.version = dto.version;
        return s;
    }

    public static SparePartDto toDto(SparePartRecord m) {
        SparePartDto s = new SparePartDto();
        s.code = m.code;
        s.price = m.price;
        s.description = m.description;
        s.stock = m.stock;
        s.minStock = m.minStock;
        s.maxStock = m.maxStock;
        s.id = m.id;
        s.version = m.version;
        return s;
    }

    public static Optional<SparePartDto> toDtoOpt(SparePartRecord record) {
        if (record == null) {
            return Optional.empty();
        }

        SparePartDto s = new SparePartDto();
        s.code = record.code;
        s.price = record.price;
        s.description = record.description;
        s.stock = record.stock;
        s.minStock = record.minStock;
        s.maxStock = record.maxStock;
        s.id = record.id;
        s.version = record.version;
        return Optional.of(s);
    }

    public static List<SparePartDto> toDtoList(List<SparePartRecord> list) {
        return list.stream().map(m -> toDto(m)).toList();
    }

}
