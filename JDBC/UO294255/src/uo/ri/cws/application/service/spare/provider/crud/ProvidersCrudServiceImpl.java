package uo.ri.cws.application.service.spare.provider.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.provider.crud.commands.AddProvider;
import uo.ri.cws.application.service.spare.provider.crud.commands.DeleteProvider;
import uo.ri.cws.application.service.spare.provider.crud.commands.FindBySparePartCode;
import uo.ri.cws.application.service.spare.provider.crud.commands.FindProviderByName;
import uo.ri.cws.application.service.spare.provider.crud.commands.FindProviderByNif;
import uo.ri.cws.application.service.spare.provider.crud.commands.UpdateProvider;
import uo.ri.cws.application.service.util.command.executor.JdbcCommandExecutor;
import uo.ri.util.exception.BusinessException;

public class ProvidersCrudServiceImpl implements ProvidersCrudService {

    private JdbcCommandExecutor executor = new JdbcCommandExecutor();

    @Override
    public ProviderDto add(ProviderDto dto) throws BusinessException {
        return executor.execute(new AddProvider(dto));
    }

    @Override
    public void delete(String nif) throws BusinessException {
        executor.execute(new DeleteProvider(nif));

    }

    @Override
    public void update(ProviderDto dto) throws BusinessException {
        executor.execute(new UpdateProvider(dto));

    }

    @Override
    public Optional<ProviderDto> findByNif(String nif)
        throws BusinessException {
        return executor.execute(new FindProviderByNif(nif));
    }

    @Override
    public List<ProviderDto> findByName(String name) throws BusinessException {
        return executor.execute(new FindProviderByName(name));
    }

    @Override
    public List<ProviderDto> findBySparePartCode(String code)
        throws BusinessException {
        return executor.execute(new FindBySparePartCode(code));
    }

}
