package uo.ri.cws.application.service.spare.sparepart.report.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.cws.application.service.spare.sparepart.report.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByDescription implements Command<List<SparePartReportDto>> {

    private SparePartGateway spg = Factories.persistence.forSparePart();
    private String description;

    public FindByDescription(String description) {
        ArgumentChecks.isNotNull(description, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(description, "Invalid argument description");
        this.description = description;
    }

    @Override
    public List<SparePartReportDto> execute() throws BusinessException {
        return DtoAssembler.toDtoList(spg.findByDescription(description));
    }

}
