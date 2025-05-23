package uo.ri.cws.application.service.spare.supply.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supply.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
 
public class FindBySparePartCode implements Command<List<SupplyDto>> {

    SupplyRepository repo = Factories.repository.forSupply();
    private String codeSparePart;

    public FindBySparePartCode(String code) {
        ArgumentChecks.isNotNull(code, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(code, "Invalid argument code");
        this.codeSparePart = code;
    }

    @Override
    public List<SupplyDto> execute() throws BusinessException {
        return DtoAssembler.toSupplyDtoList(repo.findBySparePartCode(codeSparePart));

    }
}
