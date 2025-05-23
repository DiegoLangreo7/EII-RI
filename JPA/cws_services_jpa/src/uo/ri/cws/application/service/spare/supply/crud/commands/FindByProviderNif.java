package uo.ri.cws.application.service.spare.supply.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supply.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByProviderNif implements Command<List<SupplyDto>> {

    SupplyRepository repo = Factories.repository.forSupply();
    private String nifProvider;

    public FindByProviderNif(String nif) {
        ArgumentChecks.isNotNull(nif, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(nif, "Invalid argument nif");
        this.nifProvider = nif;
    }

    @Override
    public List<SupplyDto> execute() throws BusinessException {
        return DtoAssembler.toSupplyDtoList(repo.findByProviderNif(nifProvider));
    }

}
