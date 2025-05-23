package uo.ri.cws.application.service.spare.provider.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.provider.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateProvider implements Command<Void> {

    private ProviderGateway pg = Factories.persistence.forProvider();
    private ProviderDto dto;

    public UpdateProvider(ProviderDto arg) {
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
        dto.nif = arg.nif;
        dto.name = arg.name;
        dto.email = arg.email;
        dto.phone = arg.phone;
        dto.version = arg.version + 1;
    }

    @Override
    public Void execute() throws BusinessException {
        checkProviderExist();
        checkProviderRepeatedValuesDoesNotExist();
        checkVersion();
        update();
        return null;
    }

    private void checkProviderExist() throws BusinessException {
        Optional<ProviderRecord> omr = pg.findByNif(dto.nif);
        BusinessChecks.exists(omr, "The provider doesnt exists");
    }

    private void checkProviderRepeatedValuesDoesNotExist()
        throws BusinessException {
        Optional<ProviderRecord> omr = pg.findByValues(dto.name, dto.email,
            dto.phone);
        BusinessChecks.doesNotExist(omr, "A providers with same values exists");

    }

    private void checkVersion() throws BusinessException {
        Optional<ProviderRecord> existingRecord = pg.findByNif(dto.nif);
        if (existingRecord.isPresent()) {
            ProviderRecord record = existingRecord.get();
            if (dto.version != record.version + 1) {
                throw new BusinessException("The provider is stale");
            }
        }
    }

    private void update() {
        ProviderRecord record = DtoAssembler.toRecord(dto);
        pg.update(record);
    }

}
