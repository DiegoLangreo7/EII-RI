package uo.ri.cws.application.service.spare.orders;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.orders.commands.FindByCode;
import uo.ri.cws.application.service.spare.orders.commands.FindByProviderNif;
import uo.ri.cws.application.service.spare.orders.commands.GenerateOrders;
import uo.ri.cws.application.service.spare.orders.commands.Receive;
import uo.ri.cws.application.service.util.command.executor.JdbcCommandExecutor;
import uo.ri.util.exception.BusinessException;

public class OrdersCrudServiceImpl implements OrdersService {
    private JdbcCommandExecutor executor = new JdbcCommandExecutor();

    @Override
    public List<OrderDto> generateOrders() throws BusinessException {
        return executor.execute(new GenerateOrders());
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
