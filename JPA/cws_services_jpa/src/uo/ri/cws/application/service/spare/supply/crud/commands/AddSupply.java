package uo.ri.cws.application.service.spare.supply.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Provider;
import uo.ri.cws.domain.SparePart;
import uo.ri.cws.domain.Supply;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddSupply implements Command<SupplyDto> {

    private SupplyDto dto;

    public AddSupply(SupplyDto arg) {
        ArgumentChecks.isNotNull(arg, "Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(arg.provider,
            "Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(arg.sparePart,
            "Invalid argument, cannot be null");

        this.dto = arg;
    }
    
    @Override
    public SupplyDto execute() throws BusinessException {
        SupplyRepository repo = Factories.repository.forSupply();
        ProviderRepository repoProvider = Factories.repository.forProvider();
        SparePartRepository repoSparePart = Factories.repository.forSparePart();
        
        BusinessChecks.isTrue(dto.price >= 0.0, "Invalid argument price");
        BusinessChecks.isTrue((dto.deliveryTerm >= 0),
            "Invalid argument deliveryTerm");

        String nif = dto.provider.nif;
        String code = dto.sparePart.code;
        
        Optional<Provider> op = repoProvider.findByNif(nif);
        BusinessChecks.exists(op, "Provider doesnt exists");
        
        Optional<SparePart> osp = repoSparePart.findByCode(code);
        BusinessChecks.exists(osp, "SparePart doesnt exists");
        
        Optional<Supply> os = repo.findByNifAndCode(nif,code);
        BusinessChecks.doesNotExist(os, "SparePart doesnt exists");

        Supply s = new Supply(op.get(),osp.get(), dto.price, dto.deliveryTerm);

        repo.add(s);

        dto.id = s.getId();
        return dto;
    }

}
