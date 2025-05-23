package uo.ri.cws.application.service.spare.sparepart.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.cws.application.service.spare.sparepart.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddSparePart implements Command<SparePartDto> {

    private SparePartGateway spg = Factories.persistence.forSparePart();
    private SparePartDto dto;

    public AddSparePart(SparePartDto arg) {
        ArgumentChecks.isNotNull(arg, "Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(arg.code, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(arg.code,
            "Invalid argument, cannot be blank");
        ArgumentChecks.isNotNull(arg.description,
            "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(arg.description,
            "Invalid argument, cannot be blank");
        this.dto = new SparePartDto();
        dto.id = UUID.randomUUID().toString();
        dto.version = 1L;

        dto.code = arg.code;
        dto.description = arg.description;
        dto.maxStock = arg.maxStock;
        dto.minStock = arg.minStock;
        dto.stock = arg.stock;
        dto.price = arg.price;
    }

    @Override
    public SparePartDto execute() throws BusinessException {
        checkSparePartDoesNotExist();
        checkValues();
        insertSparePart();
        return this.dto;
    }

    private void insertSparePart() {
        SparePartRecord spr = DtoAssembler.toRecord(dto);
        spg.add(spr);
    }

    private void checkValues() throws BusinessException {
        BusinessChecks.isTrue(dto.price >= 0, "Invalid value");
        BusinessChecks.isTrue(dto.stock >= 0, "Invalid value");
        BusinessChecks.isTrue(dto.minStock >= 0, "Invalid value");
        BusinessChecks.isTrue(dto.maxStock >= 0, "Invalid value");

    }

    private void checkSparePartDoesNotExist() throws BusinessException {
        Optional<SparePartRecord> omr = spg.findBySparePartCode(dto.code);
        BusinessChecks.doesNotExist(omr, "The spare part already exists");
    }

}
