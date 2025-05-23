package uo.ri.cws.application.service.spare.provider.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.provider.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindBySparePartCode implements Command<List<ProviderDto>> {

    private ProviderRepository repo = Factories.repository.forProvider();
    private String code;

    public FindBySparePartCode(String argCode) {
        ArgumentChecks.isNotNull(argCode,
            "Invalid argument name, cannot be null");
        ArgumentChecks.isNotBlank(argCode, "Invalid argument name");
        this.code = argCode;
    }

    @Override
    public List<ProviderDto> execute() {
        return DtoAssembler.toProvidersDtoList(repo.findBySparePartCode(code));
    }

}
