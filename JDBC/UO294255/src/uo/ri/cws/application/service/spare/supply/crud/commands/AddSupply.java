package uo.ri.cws.application.service.spare.supply.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supply.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddSupply implements Command<SupplyDto> {

    private SupplyGateway sg = Factories.persistence.forSupply();
    private SparePartGateway spg = Factories.persistence.forSparePart();
    private ProviderGateway pg = Factories.persistence.forProvider();
    private SupplyDto dto;

    public AddSupply(SupplyDto arg) {
        ArgumentChecks.isNotNull(arg, "Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(arg.provider,
            "Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(arg.sparePart,
            "Invalid argument, cannot be null");

        this.dto = new SupplyDto();
        dto.id = UUID.randomUUID().toString();
        dto.version = 1L;
        dto.provider.nif = arg.provider.nif;
        dto.sparePart.code = arg.sparePart.code;
        dto.price = arg.price;
        dto.deliveryTerm = arg.deliveryTerm;
    }

    @Override
    public SupplyDto execute() throws BusinessException {
        checkValue();
        checkProviderExists();
        checkSparePartExists();
        checkSupplyDontIsRepeated();
        insertProvider();
        return this.dto;
    }

    private void checkValue() throws BusinessException {
        BusinessChecks.isTrue(dto.price >= 0.0, "Invalid argument price");
        BusinessChecks.isTrue((dto.deliveryTerm >= 0),
            "Invalid argument deliveryTerm");
    }

    private void checkSparePartExists() throws BusinessException {
        Optional<SparePartRecord> omr = spg
            .findBySparePartCode(dto.sparePart.code);
        BusinessChecks.exists(omr, "The spare part doesnt exists");

    }

    private void checkProviderExists() throws BusinessException {
        Optional<ProviderRecord> omr = pg.findByNif(dto.provider.nif);
        BusinessChecks.exists(omr, "The provider doesnt exists");
    }

    public void insertProvider() {
        SupplyRecord mr = DtoAssembler.toRecord(dto);
        sg.add(mr);
    }

    private void checkSupplyDontIsRepeated() throws BusinessException {
        Optional<SupplyRecord> omr = sg.findByNifAndCode(dto.provider.nif,
            dto.sparePart.code);
        BusinessChecks.doesNotExist(omr,
            "A supply with the same provider and spare part already exists");
    }
}
