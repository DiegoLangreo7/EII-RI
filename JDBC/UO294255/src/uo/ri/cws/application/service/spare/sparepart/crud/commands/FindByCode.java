package uo.ri.cws.application.service.spare.sparepart.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.cws.application.service.spare.sparepart.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByCode implements Command<Optional<SparePartDto>> {

    private SparePartGateway spg = Factories.persistence.forSparePart();
    private String codeSparePart;

    public FindByCode(String code) {
        ArgumentChecks.isNotNull(code, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(code, "Invalid argument code");
        this.codeSparePart = code;
    }

    @Override
    public Optional<SparePartDto> execute() throws BusinessException {
        Optional<SparePartRecord> recordOpt = spg
            .findBySparePartCode(codeSparePart);

        if (recordOpt.isPresent()) {
            return DtoAssembler.toDtoOpt(recordOpt.get());
        } else {
            return Optional.empty();
        }
    }

}
