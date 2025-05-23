package uo.ri.cws.application.service.spare.order;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.order.command.FindByCode;
import uo.ri.cws.application.service.spare.order.command.FindByProviderNif;
import uo.ri.cws.application.service.spare.order.command.Receive;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.NotYetImplementedException;

public class OrdersCrudServiceImpl implements OrdersService {
    
    private CommandExecutor executor = Factories.executor.forExecutor();

    @Override
    public List<OrderDto> generateOrders() throws BusinessException {
    	throw new NotYetImplementedException();    
    }

    @Override
    public List<OrderDto> findByProviderNif(String nif)
        throws BusinessException {
        return executor.execute(new FindByProviderNif(nif));

    }

    @Override
    public Optional<OrderDto> findByCode(String code) throws BusinessException {
        return executor.execute(new FindByCode(code));
    } 

    @Override
    public OrderDto receive(String code) throws BusinessException {
        return executor.execute(new Receive(code));

    }

}
