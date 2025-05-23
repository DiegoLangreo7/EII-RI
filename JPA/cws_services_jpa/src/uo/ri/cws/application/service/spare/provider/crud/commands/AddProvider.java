package uo.ri.cws.application.service.spare.provider.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Provider;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddProvider implements Command<ProviderDto> {

    private ProviderDto dto;

    public AddProvider(ProviderDto arg) {
        ArgumentChecks.isNotNull(arg, "Invalid argument, cannot be null");
        ArgumentChecks.isNotNull(arg.nif, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(arg.nif, "Invalid argument id");
        ArgumentChecks.isNotNull(arg.name, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(arg.name, "Invalid argument name");
        ArgumentChecks.isNotNull(arg.email, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(arg.email, "Invalid argument surname");
        ArgumentChecks.isTrue(arg.email.contains("@"), "Invalid email");
        ArgumentChecks.isNotNull(arg.phone, "Invalid argument, cannot be null");
        ArgumentChecks.isNotBlank(arg.phone, "Invalid argument surname");

        this.dto = arg;
    }

    @Override
    public ProviderDto execute() throws BusinessException {
        ProviderRepository repo = Factories.repository.forProvider();

        Optional<Provider> op = repo.findByNif(dto.nif);
        BusinessChecks.doesNotExist(op, "Provider already exists");

        List<Provider> pl = repo.findByNameMailPhone(dto.name, dto.email,
            dto.phone);
        BusinessChecks.isTrue(pl.size() == 0, "Provider already exists");

        Provider p = new Provider(dto.nif, dto.name, dto.email, dto.phone);

        repo.add(p);

        dto.id = p.getId();
        return dto;
    }

}
