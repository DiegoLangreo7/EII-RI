package uo.ri.cws.application.service.spare.sparepart.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;

public class DtoAssembler {

    public static SparePartReportDto toDto(SparePartRecord r) {
        SparePartReportDto dto = new SparePartReportDto();
        dto.id = r.id;
        dto.version = r.version;
        dto.code = r.code;
        dto.description = r.description;
        dto.price = r.price;
        dto.stock = r.stock;
        dto.minStock = r.minStock;
        dto.maxStock = r.maxStock;
        dto.totalUnitsSold = 0; // por ahora 0
        return dto;
    }

    public static Optional<SparePartReportDto> toDtoOpt(SparePartRecord r) {
        if (r == null) {
            return Optional.empty();
        }
        SparePartReportDto dto = new SparePartReportDto();
        dto.id = r.id;
        dto.version = r.version;
        dto.code = r.code;
        dto.description = r.description;
        dto.price = r.price;
        dto.stock = r.stock;
        dto.minStock = r.minStock;
        dto.maxStock = r.maxStock;
        dto.totalUnitsSold = 0;
        return Optional.of(dto);
    }

    public static List<SparePartReportDto> toDtoList(List<SparePartRecord> list) {
        List<SparePartReportDto> result = new ArrayList<>();
        for (SparePartRecord r : list) {
            SparePartReportDto dto = new SparePartReportDto();
            dto.id = r.id;
            dto.version = r.version;
            dto.code = r.code;
            dto.description = r.description;
            dto.price = r.price;
            dto.stock = r.stock;
            dto.minStock = r.minStock;
            dto.maxStock = r.maxStock;
            dto.totalUnitsSold = 0;
            result.add(dto);
        }
        return result;
    }
}
