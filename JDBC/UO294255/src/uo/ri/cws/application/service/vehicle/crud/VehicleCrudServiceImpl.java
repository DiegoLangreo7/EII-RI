package uo.ri.cws.application.service.vehicle.crud;

import java.util.Optional;

import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.util.exception.BusinessException;

public class VehicleCrudServiceImpl implements VehicleCrudService{

    @Override
    public Optional<VehicleDto> findVehicleByPlate(String plate)
        throws BusinessException {
        return null;
    }

}
