package uo.ri.cws.application.service.spare.provider.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.spares.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;

public class DtoAssembler {

    public static ProviderRecord toRecord(ProviderDto dto) {
        ProviderRecord m = new ProviderRecord();
        m.nif = dto.nif;
        m.name = dto.name;
        m.phone = dto.phone;
        m.email = dto.email;
        m.id = dto.id;
        m.version = dto.version;
        return m;
    }

    public static ProviderDto toDto(ProviderRecord record) {
        ProviderDto p = new ProviderDto();

        p.nif = record.nif;
        p.name = record.name;
        p.phone = record.phone;
        p.email = record.email;
        p.id = record.id;
        p.version = record.version;
        return p;
    }

    public static Optional<ProviderDto> toDtoOpt(ProviderRecord record) {
        if (record == null) {
            return Optional.empty();
        }

        ProviderDto p = new ProviderDto();

        p.nif = record.nif;
        p.name = record.name;
        p.phone = record.phone;
        p.email = record.email;
        p.id = record.id;
        p.version = record.version;
        return Optional.of(p);
    }

    public static List<ProviderDto> toDtoList(List<ProviderRecord> list) {
        return list.stream().map(m -> toDto(m)).toList();
    }

}
