package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class DtoAssembler {

    public static List<MechanicDto> toDtoList(List<MechanicRecord> list) {
        return list.stream().map(m -> toDto(m)).toList();
    }

    public static MechanicDto toDto(MechanicRecord record) {
        MechanicDto res = new MechanicDto();
        res.id = record.id;
        res.version = record.version;

        res.nif = record.nif;
        res.name = record.name;
        res.surname = record.surname;
        return res;
    }

    public static Optional<MechanicDto> toDtoOpt(MechanicRecord record) {
        if (record == null) {
            return Optional.empty();
        }

        MechanicDto res = new MechanicDto();
        res.id = record.id;
        res.version = record.version;
        res.nif = record.nif;
        res.name = record.name;
        res.surname = record.surname;

        return Optional.of(res);
    }

    public static MechanicRecord toRecord(MechanicDto dto) {
        MechanicRecord m = new MechanicRecord();
        m.nif = dto.nif;
        m.name = dto.name;
        m.surname = dto.surname;
        m.id = dto.id;
        m.version = dto.version;
        return m;
    }
}
