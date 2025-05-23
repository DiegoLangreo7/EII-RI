package uo.ri.cws.application.service.spare.sparepart.report.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway.SubstitutionRecord;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.cws.application.service.spare.sparepart.report.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByCode implements Command<Optional<SparePartReportDto>> {

    private SparePartGateway spg = Factories.persistence.forSparePart();
    private SubstitutionGateway subg = Factories.persistence.forSubstitution();
    private String sparePartCode;

    public FindByCode(String code) {
        ArgumentChecks.isNotNull(code, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(code, "Invalid argument description");
        this.sparePartCode = code;
    }

    @Override
    public Optional<SparePartReportDto> execute() throws BusinessException {
        Optional<SparePartRecord> recordOpt = spg.findBySparePartCode(sparePartCode);

        if (recordOpt.isEmpty()) {
            return Optional.empty();
        }

        SparePartRecord record = recordOpt.get();
        SparePartReportDto dto = DtoAssembler.toDto(record);

        List<SubstitutionRecord> substitutions = subg.findBySparePartId(record.id);
        int totalSales = 0;
        for (SubstitutionRecord sr : substitutions) {
            totalSales += sr.quantity;
        }
        dto.totalUnitsSold = totalSales;

        return Optional.of(dto);
    }
}
