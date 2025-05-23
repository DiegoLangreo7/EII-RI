package uo.ri.cws.application.service.spare.supply.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supply.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByNifAndCode implements Command<Optional<SupplyDto>> {

    private SupplyGateway sg = Factories.persistence.forSupply();
    private String nif;
    private String code;

    public FindByNifAndCode(String nif, String code) {
        ArgumentChecks.isNotNull(code, "Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(nif, "Invalid argument, cannot be null");
        this.nif = nif;
        this.code = code;
    }

    @Override
    public Optional<SupplyDto> execute() throws BusinessException {
        return getSupply(nif, code);
    }

    private Optional<SupplyDto> getSupply(String provider_ID, String sparepart_ID) {

        Optional<SupplyRecord> recordOpt = sg.findByNifAndCode(nif, code);

        if (recordOpt.isPresent()) {
            return DtoAssembler.toDtoOpt(recordOpt.get());
        } else {
            return Optional.empty();
        }

    }
}
