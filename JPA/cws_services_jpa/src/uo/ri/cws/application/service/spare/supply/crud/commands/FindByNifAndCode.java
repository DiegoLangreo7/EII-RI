package uo.ri.cws.application.service.spare.supply.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supply.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByNifAndCode implements Command<Optional<SupplyDto>> {

    SupplyRepository repo = Factories.repository.forSupply();
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
        return repo.findByNifAndCode(nif,code).map(m -> DtoAssembler.toDto(m));
    }

}
