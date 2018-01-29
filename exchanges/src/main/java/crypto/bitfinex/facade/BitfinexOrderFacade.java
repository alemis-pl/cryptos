package crypto.bitfinex.facade;

import crypto.bitfinex.client.BitfinexNewOrderClient;
import crypto.bitfinex.domain.order.BitfinexCreatedOrderDto;
import crypto.bitfinex.domain.order.BitfinexOrderDto;
import crypto.bitfinex.domain.params.BitfinexParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BitfinexOrderFacade {

    @Autowired
    private BitfinexNewOrderClient newOrderClient;

    public BitfinexCreatedOrderDto createNewOrder(final BitfinexOrderDto orderDto) throws Exception {
        return newOrderClient.createNewOrder(orderDto, BitfinexParams.NEW_ORDER.getParams());
    }
}
