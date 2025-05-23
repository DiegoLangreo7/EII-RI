package uo.ri.cws.application.service.spare.sparepart.crud;

import java.util.Optional;

import uo.ri.cws.application.service.spare.SparePartCrudService;
import uo.ri.cws.application.service.spare.sparepart.crud.commands.AddSparePart;
import uo.ri.cws.application.service.spare.sparepart.crud.commands.DeleteSparePart;
import uo.ri.cws.application.service.spare.sparepart.crud.commands.FindByCode;
import uo.ri.cws.application.service.spare.sparepart.crud.commands.UpdateSparePart;
import uo.ri.cws.application.service.util.command.executor.JdbcCommandExecutor;
import uo.ri.util.exception.BusinessException;

public class SparePartCrudServiceImpl implements SparePartCrudService {

    private JdbcCommandExecutor executor = new JdbcCommandExecutor();

    @Override
    public SparePartDto add(SparePartDto dto) throws BusinessException {
        return executor.execute(new AddSparePart(dto));

    }

    @Override
    public void delete(String code) throws BusinessException {
        executor.execute(new DeleteSparePart(code));
    }

    @Override
    public void update(SparePartDto dto) throws BusinessException {
        executor.execute(new UpdateSparePart(dto));
    }

    @Override
    public Optional<SparePartDto> findByCode(String code) 
        throws BusinessException {
        return executor.execute(new FindByCode(code));
    }

}
