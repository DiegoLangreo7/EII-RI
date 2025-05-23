package uo.ri.cws.application.service.spare.provider.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.provider.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindBySparePartCode implements Command<List<ProviderDto>> {

    private ProviderGateway pg = Factories.persistence.forProvider();
    private String code;

    public FindBySparePartCode(String argCode) {
        ArgumentChecks.isNotNull(argCode,
            "Invalid argument name, cannot be null");
        ArgumentChecks.isNotBlank(argCode, "Invalid argument name");
        this.code = argCode;
    }

    @Override
    public List<ProviderDto> execute() {
        return DtoAssembler.toDtoList(pg.findBySparePartCode(code));

    }

}
