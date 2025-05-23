package uo.ri.cws.application.service.spare.supply.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supply.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateSupply implements Command<Void> {

    private SupplyGateway sg = Factories.persistence.forSupply();
    private SupplyDto dto;

    public UpdateSupply(SupplyDto arg) {
        ArgumentChecks.isNotNull(arg,"Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(arg.provider,"Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(arg.sparePart,"Invalid argument, cannot be null");

        this.dto = new SupplyDto();
        dto.id = arg.id;
        dto.provider.nif = arg.provider.nif;
        dto.sparePart.code = arg.sparePart.code;
        dto.price = arg.price;
        dto.deliveryTerm = arg.deliveryTerm;
        dto.version = arg.version + 1;
    }

    @Override
    public Void execute() throws BusinessException {
        checkValue();
        checkSupplyExists();
        checkVersion();
        updateSupply();
        return null;
    }

    private void updateSupply() {
        SupplyRecord record = DtoAssembler.toRecord(dto);
        sg.update(record);
    }

    private void checkVersion() throws BusinessException {
        Optional<SupplyRecord> existingRecord = sg.findById(dto.id);

        if (existingRecord.isPresent()) {
            SupplyRecord record = existingRecord.get();
            if (dto.version != record.version + 1) {
                throw new BusinessException("The supply is stale");
            }
        }
    }

    private void checkValue() throws BusinessException {
        BusinessChecks.isTrue(dto.price >= 0.0, "Invalid argument price");
        BusinessChecks.isTrue((dto.deliveryTerm >= 0), "Invalid argument deliveryTerm");
    }

    private void checkSupplyExists() throws BusinessException {
        Optional<SupplyRecord> sr = sg.findByNifAndCode(dto.provider.nif, dto.sparePart.code);
        BusinessChecks.exists(sr, "The supply doesnt exists");
    }

}
