package uo.ri.cws.application.service.spare.sparepart.report.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.cws.application.service.spare.sparepart.report.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class FindUnderStock implements Command<List<SparePartReportDto>> {

    private SparePartGateway spg = Factories.persistence.forSparePart();

    @Override
    public List<SparePartReportDto> execute() throws BusinessException {
        List<SparePartReportDto> allList = DtoAssembler.toDtoList(spg.findAll());
        List<SparePartReportDto> selectedList = new ArrayList<>();

        for (SparePartReportDto spare : allList) {
            if (spare.stock < spare.minStock) {
                selectedList.add(spare);
            }
        }

        return selectedList;
    }

}
