package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.FindAllMechanics;
import uo.ri.cws.application.service.mechanic.crud.commands.FindMechanicById;
import uo.ri.cws.application.service.mechanic.crud.commands.FindMechanicByNif;
import uo.ri.cws.application.service.mechanic.crud.commands.UpdateMechanic;
import uo.ri.cws.application.service.util.command.executor.JdbcCommandExecutor;
import uo.ri.util.exception.BusinessException;

public class MechanicCrudServiceImpl implements MechanicCrudService {

    private JdbcCommandExecutor executor = new JdbcCommandExecutor();

    @Override
    public MechanicDto addMechanic(MechanicDto dto) throws BusinessException {
        return executor.execute(new AddMechanic(dto));
    }

    @Override
    public void deleteMechanic(String mechanicId) throws BusinessException {
        executor.execute(new DeleteMechanic(mechanicId));
    }

    @Override
    public void updateMechanic(MechanicDto dto) throws BusinessException {
        executor.execute(new UpdateMechanic(dto));
    }

    @Override
    public Optional<MechanicDto> findMechanicById(String id)
        throws BusinessException {
        return new FindMechanicById(id).execute();
    }

    @Override
    public Optional<MechanicDto> findMechanicByNif(String nif)
        throws BusinessException {
        return new FindMechanicByNif(nif).execute();
    }

    @Override
    public List<MechanicDto> findAllMechanics() throws BusinessException {
        return new FindAllMechanics().execute();
    }

}
