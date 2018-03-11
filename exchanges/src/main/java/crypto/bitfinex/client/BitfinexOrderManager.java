package crypto.bitfinex.client;

import crypto.bitfinex.authentication.BitfinexExchangeAuthenticationSpring;
import crypto.bitfinex.domain.order.BitfinexCancelAllOrders;
import crypto.bitfinex.domain.order.BitfinexCreatedOrderDto;
import crypto.bitfinex.domain.order.BitfinexOrderDto;
import crypto.bitfinex.domain.params.BitfinexParams;
import crypto.bitfinex.domain.params.BitfinexParamsModerator;
import crypto.bitfinex.domain.params.BitfinexParamsToSearch;
import crypto.bitfinex.domain.positions.BitfinexActivePositionsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class BitfinexOrderManager {

    @Value("${bitfinex.main.url}")
    private String bitfinexMainUrl;

    @Value("${bitfinex.order.new}")
    private String newOrder;

    @Value("${bitfinex.order.cancel}")
    private String cancelOrder;

    @Value("${bitfinex.order.cancel.all}")
    private String cancelAllOrder;

    @Value("${bitfinex.order.active}")
    private String activeOrders;

    @Value("${bitfinex.order.history}")
    private String ordersHistory;

    @Value("${bitfinex.order.status}")
    private String orderStatus;

    @Value("${bitfinex.positions.active}")
    private String activePositions;


    private BitfinexExchangeAuthenticationSpring exchangeAuthenticationSpring;

    @Autowired
    public BitfinexOrderManager(BitfinexExchangeAuthenticationSpring exchangeAuthenticationSpring) {
        this.exchangeAuthenticationSpring = exchangeAuthenticationSpring;
    }

    public BitfinexCreatedOrderDto createNewOrder(BitfinexOrderDto orderDto, String params) {
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(params, orderDto);
        String url = bitfinexMainUrl + newOrder;
        HttpEntity entity = exchangeAuthenticationSpring.createHttpEntity(paramsModerator, newOrder);
        Optional<BitfinexCreatedOrderDto> order = exchangeAuthenticationSpring.getResponse(url, entity, BitfinexCreatedOrderDto.class, HttpMethod.POST);
        if (order.isPresent()) {
            log.info("New order placed!");
        }
        return order.orElse(new BitfinexCreatedOrderDto());
    }

    public boolean cancelOrder(String orderId) {
        BitfinexParamsToSearch paramsToSearch = new BitfinexParamsToSearch(orderId);
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.ORDER_BY_ID.getParams(), paramsToSearch);
        String url = bitfinexMainUrl + cancelOrder;
        HttpEntity entity = exchangeAuthenticationSpring.createHttpEntity(paramsModerator, cancelOrder);
        Optional<BitfinexCreatedOrderDto> canceledOrder = exchangeAuthenticationSpring.getResponse(url, entity, BitfinexCreatedOrderDto.class, HttpMethod.POST);
        boolean result = false;
        if (canceledOrder.isPresent()) {
            log.info("Order canceled!");
            result = true;
        }
        return result;
    }

    public BitfinexCancelAllOrders cancelAllOrders() {
        BitfinexParamsToSearch paramsToSearch = null;
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.WITHOUT_PARAMS.getParams(), paramsToSearch);
        String url = bitfinexMainUrl + cancelAllOrder;
        HttpEntity entity = exchangeAuthenticationSpring.createHttpEntity(paramsModerator, cancelAllOrder);
        Optional<BitfinexCancelAllOrders> canceledAllOrders = exchangeAuthenticationSpring.getResponse(url, entity, BitfinexCancelAllOrders.class, HttpMethod.POST);
        if (canceledAllOrders.isPresent()) {
            log.info("All orders cancelled!");
        }
        return canceledAllOrders.orElse(new BitfinexCancelAllOrders());
    }

    public BitfinexCreatedOrderDto[] getActiveOrders() {
        BitfinexParamsToSearch paramsToSearch = null;
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.WITHOUT_PARAMS.getParams(), paramsToSearch);
        String url = bitfinexMainUrl + activeOrders;
        HttpEntity entity = exchangeAuthenticationSpring.createHttpEntity(paramsModerator, activeOrders);
        Optional<BitfinexCreatedOrderDto[]> activeOrders = exchangeAuthenticationSpring.getResponse(url, entity, BitfinexCreatedOrderDto[].class, HttpMethod.POST);
        if (activeOrders.isPresent()) {
            log.info("Active orders information downloaded!");
        }
        BitfinexCreatedOrderDto[] bitfinexCreatedOrderDtos = new BitfinexCreatedOrderDto[1];
        return activeOrders.orElse(bitfinexCreatedOrderDtos);
    }

    public BitfinexCreatedOrderDto[] getOrdersHistory() {
        BitfinexParamsToSearch paramsToSearch = null;
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.WITHOUT_PARAMS.getParams(), paramsToSearch);
        String url = bitfinexMainUrl + ordersHistory;
        HttpEntity entity = exchangeAuthenticationSpring.createHttpEntity(paramsModerator, ordersHistory);
        Optional<BitfinexCreatedOrderDto[]> historyOrders = exchangeAuthenticationSpring.getResponse(url, entity, BitfinexCreatedOrderDto[].class, HttpMethod.POST);
        if (historyOrders.isPresent()) {
            log.info("Orders history information downloaded!");
        }
        BitfinexCreatedOrderDto[] bitfinexCreatedOrderDtos = new BitfinexCreatedOrderDto[1];
        return historyOrders.orElse(bitfinexCreatedOrderDtos);
    }

    public BitfinexCreatedOrderDto getOrdersStatus(String orderId) {
        BitfinexParamsToSearch paramsToSearch = new BitfinexParamsToSearch(orderId);
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.ORDER_BY_ID.getParams(), paramsToSearch);
        String url = bitfinexMainUrl + orderStatus;
        HttpEntity entity = exchangeAuthenticationSpring.createHttpEntity(paramsModerator, orderStatus);
        Optional<BitfinexCreatedOrderDto> orderStatus = exchangeAuthenticationSpring.getResponse(url, entity, BitfinexCreatedOrderDto.class, HttpMethod.POST);
        if (orderStatus.isPresent()) {
            log.info("Order status downloaded!");
        }
        return orderStatus.orElse(new BitfinexCreatedOrderDto());
    }

    public BitfinexActivePositionsDto[] getActivePositions() {
        BitfinexParamsToSearch paramsToSearch = null;
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.WITHOUT_PARAMS.getParams(), paramsToSearch);
        String url = bitfinexMainUrl + activePositions;
        HttpEntity entity = exchangeAuthenticationSpring.createHttpEntity(paramsModerator, activePositions);
        Optional<BitfinexActivePositionsDto[]> activePositions = exchangeAuthenticationSpring.getResponse(url, entity, BitfinexActivePositionsDto[].class, HttpMethod.POST);
        if (activePositions.isPresent()) {
            log.info("Active positions information downloaded!");
        }
        BitfinexActivePositionsDto[] activePositionsDtos = new BitfinexActivePositionsDto[1];
        return activePositions.orElse(activePositionsDtos);
    }
}