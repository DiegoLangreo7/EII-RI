package uo.ri.cws.application.service.spare.sparepart.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateSparePart implements Command<Void> {

    private SparePartGateway spg = Factories.persistence.forSparePart();
    private SparePartDto dto;

    public UpdateSparePart(SparePartDto arg) {
        ArgumentChecks.isNotNull(arg, "Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(arg.id, "Invalid argument, id cannot be null");
        ArgumentChecks.isNotBlank(arg.id, "Invalid argument, id cannot be blank");
        ArgumentChecks.isNotNull(arg.description, "Invalid argument, description cannot be null");
        ArgumentChecks.isNotBlank(arg.description, "Invalid argument, description cannot be blank");

        this.dto = arg;
    }

    @Override
    public Void execute() throws BusinessException {
        checkValues();
        SparePartRecord existing = checkSparePartExists();
        checkVersion(existing);
        update(existing);
        return null;
    }

    private void checkValues() throws BusinessException {
        BusinessChecks.isTrue(dto.stock >= 0, "Stock cannot be negative");
        BusinessChecks.isTrue(dto.minStock >= 0, "Min stock cannot be negative");
        BusinessChecks.isTrue(dto.maxStock >= 0, "Max stock cannot be negative");
        BusinessChecks.isTrue(dto.price >= 0, "Price cannot be negative");
    }

    private SparePartRecord checkSparePartExists() throws BusinessException {
        Optional<SparePartRecord> recordOpt = spg.findBySparePartCode(dto.code);
        BusinessChecks.exists(recordOpt, "The spare part does not exist");
        return recordOpt.get();
    }

    private void checkVersion(SparePartRecord record) throws BusinessException {
        BusinessChecks.isTrue(dto.version == record.version, "The spare part is stale");
    }

    private void update(SparePartRecord record) {
        record.description = dto.description;
        record.minStock = dto.minStock;
        record.maxStock = dto.maxStock;
        record.stock = dto.stock;
        record.price = dto.price;
        spg.update(record);
    }
}
