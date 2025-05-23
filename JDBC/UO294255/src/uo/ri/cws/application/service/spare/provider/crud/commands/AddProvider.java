package uo.ri.cws.application.service.spare.provider.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.provider.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddProvider implements Command<ProviderDto> {

    private ProviderGateway pg = Factories.persistence.forProvider();
    private ProviderDto dto;

    public AddProvider(ProviderDto arg) {
        ArgumentChecks.isNotNull(arg, "Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(arg.nif, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(arg.nif, "Invalid argument id");
        ArgumentChecks.isNotNull(arg.name, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(arg.name, "Invalid argument name");
        ArgumentChecks.isNotNull(arg.email, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(arg.email, "Invalid argument surname");
        ArgumentChecks.isTrue(arg.email.contains("@"), "Invalid email");
        ArgumentChecks.isNotNull(arg.phone, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(arg.phone, "Invalid argument surname");

        this.dto = new ProviderDto();
        dto.id = UUID.randomUUID().toString();
        dto.version = 1L;

        dto.nif = arg.nif;
        dto.name = arg.name;
        dto.email = arg.email;
        dto.phone = arg.phone;
    }

    @Override
    public ProviderDto execute() throws BusinessException {
        checkProviderDoesNotExist();
        checkProviderRepeatedValuesDoesNotExist();
        insertProvider();
        return this.dto;
    }

    private void checkProviderRepeatedValuesDoesNotExist()
        throws BusinessException {
        Optional<ProviderRecord> omr = pg.findByValues(dto.name, dto.email,
            dto.phone);
        BusinessChecks.doesNotExist(omr, "A providers with same values exists");

    }

    public void insertProvider() {
        ProviderRecord mr = DtoAssembler.toRecord(dto);
        pg.add(mr);
    }

    private void checkProviderDoesNotExist() throws BusinessException {
        Optional<ProviderRecord> omr = pg.findByNif(dto.nif);
        BusinessChecks.doesNotExist(omr, "The provider already exists");
    }

}
