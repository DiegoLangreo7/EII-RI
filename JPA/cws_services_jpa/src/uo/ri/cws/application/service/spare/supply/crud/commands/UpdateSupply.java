package uo.ri.cws.application.service.spare.supply.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Supply;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateSupply implements Command<Void> {

    private SupplyDto dto;

    public UpdateSupply(SupplyDto arg) {
        ArgumentChecks.isNotNull(arg, "Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(arg.provider,
            "Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(arg.sparePart,
            "Invalid argument, cannot be null");

        this.dto = arg;
    }
    
    @Override
    public Void execute() throws BusinessException {
        BusinessChecks.isTrue(dto.price >= 0.0, "Invalid argument price");
        BusinessChecks.isTrue((dto.deliveryTerm >= 0),
            "Invalid argument deliveryTerm");

        SupplyRepository repo = Factories.repository.forSupply();

        String nif = dto.provider.nif;
        String code = dto.sparePart.code;
        
        Optional<Supply> sp = repo.findByNifAndCode(nif,code);
        BusinessChecks.exists(sp, "Supply does not exists");

        Supply s = sp.get();

        BusinessChecks.hasVersion(dto.version, s.getVersion());

        s.setPrice(dto.price);
        s.setDeliveryTerm(dto.deliveryTerm);

        return null;
    }

}
