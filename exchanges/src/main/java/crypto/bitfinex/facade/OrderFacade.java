package crypto.bitfinex.facade;

import crypto.bitfinex.client.NewOrderClient;
import crypto.bitfinex.domain.order.CreatedOrderDto;
import crypto.bitfinex.domain.order.OrderDto;
import crypto.bitfinex.domain.params.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderFacade {

    @Autowired
    private NewOrderClient newOrderClient;

    public CreatedOrderDto createNewOrder(final OrderDto orderDto) throws Exception {
        return newOrderClient.createNewOrder(orderDto, Params.NEW_ORDER.getParams());
    }
}
