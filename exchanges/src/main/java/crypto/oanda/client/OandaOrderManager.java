package crypto.oanda.client;

import crypto.oanda.authentication.*;
import crypto.oanda.domain.order.OandaOrder;
import crypto.oanda.domain.order.OandaOrdersList;
import crypto.oanda.domain.trade.OandaTradesList;
import crypto.persistance.apikey.ApiKeys;
import crypto.persistance.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class OandaOrderManager {

    private OandaAuthentication authentication;
    private OandaUrlCreator urlCreation;
    private DbService dbService;

    @Autowired
    public OandaOrderManager(OandaAuthentication authentication, OandaUrlCreator urlCreation, DbService dbService) {
        this.authentication = authentication;
        this.urlCreation = urlCreation;
        this.dbService = dbService;
    }

    private ApiKeys getApiKeys() {
        return dbService.getApiKeysByExchange("oanda");
    }

    public OandaOrder placeOrder(OandaOrder oandaOrder) {
        String token = getApiKeys().getApiKey();
        String clientId = getApiKeys().getClientId();
        OandaHeadersParameters parameters = new OandaHeadersParameters(token, OandaRequestType.ORDER_REQUEST.getRequestType(), createOrder(oandaOrder));
        OandaUrlParameters urlParameters = new OandaUrlParameters(OandaUrlType.ORDERS.getUrlType(), clientId);
        HttpEntity entity = authentication.createHeaders(parameters);
        String url = urlCreation.createUrl(urlParameters).orElse(new String());
        Optional<OandaOrder> order = authentication.getResponse(url, entity,OandaOrder.class, HttpMethod.POST);
        return order.orElse(new OandaOrder());
    }

    private OandaOrder cancelPendingOrder(Long orderId) {
        String token = getApiKeys().getApiKey();
        String clientId = getApiKeys().getClientId();
        OandaHeadersParameters parameters = new OandaHeadersParameters(token, OandaRequestType.ORDER_CANCEL.getRequestType());
        OandaUrlParameters urlParameters = new OandaUrlParameters(OandaUrlType.ORDER_CANCEL.getUrlType(), clientId, orderId);
        HttpEntity entity = authentication.createHeaders(parameters);
        String url = urlCreation.createUrl(urlParameters).orElse(new String());
        Optional<OandaOrder> cancelOrder = authentication.getResponse(url, entity, OandaOrder.class, HttpMethod.PUT );
        return cancelOrder.orElse(new OandaOrder());
    }

    private OandaOrdersList getOrders(String instrument) {
        String token = getApiKeys().getApiKey();
        String clientId = getApiKeys().getClientId();
        OandaHeadersParameters parameters = new OandaHeadersParameters(token, OandaRequestType.STANDARD_REQUEST.getRequestType());
        OandaUrlParameters urlParameters = new OandaUrlParameters(OandaUrlType.GET_ORDERS.getUrlType(), clientId);
        HttpEntity entity = authentication.createHeaders(parameters);
        String url = urlCreation.createUrl(urlParameters).orElse(new String());
        Optional<OandaOrdersList> ordersList = authentication.getResponse(url,entity, OandaOrdersList.class, HttpMethod.GET);
        return ordersList.orElse(new OandaOrdersList());
    }

    private OandaOrdersList getPendingOrders() {
        String token = getApiKeys().getApiKey();
        String clientId = getApiKeys().getClientId();
        OandaHeadersParameters parameters = new OandaHeadersParameters(token, OandaRequestType.STANDARD_REQUEST.getRequestType());
        OandaUrlParameters urlParameters = new OandaUrlParameters(OandaUrlType.GET_PENDING_ORDERS.getUrlType(), clientId);
        HttpEntity entity = authentication.createHeaders(parameters);
        String url = urlCreation.createUrl(urlParameters).orElse(new String());
        Optional<OandaOrdersList> ordersList = authentication.getResponse(url,entity, OandaOrdersList.class, HttpMethod.GET);
        return ordersList.orElse(new OandaOrdersList());
    }

    private OandaTradesList getTrades(String instrument) {
        String token = getApiKeys().getApiKey();
        String clientId = getApiKeys().getClientId();
        OandaHeadersParameters parameters = new OandaHeadersParameters(token, OandaRequestType.STANDARD_REQUEST.getRequestType());
        OandaUrlParameters urlParameters = new OandaUrlParameters(OandaUrlType.TRADES.getUrlType(), clientId, instrument);
        HttpEntity entity = authentication.createHeaders(parameters);
        String url = urlCreation.createUrl(urlParameters).orElse(new String());
        Optional<OandaTradesList> tradesList = authentication.getResponse(url, entity, OandaTradesList.class, HttpMethod.GET);
        return tradesList.orElse(new OandaTradesList());
    }

    private OandaTradesList getOpenTrades() {
        String token = getApiKeys().getApiKey();
        String clientId = getApiKeys().getClientId();
        OandaHeadersParameters parameters = new OandaHeadersParameters(token, OandaRequestType.STANDARD_REQUEST.getRequestType());
        OandaUrlParameters urlParameters = new OandaUrlParameters(OandaUrlType.TRADES.getUrlType(), clientId);
        HttpEntity entity = authentication.createHeaders(parameters);
        String url = urlCreation.createUrl(urlParameters).orElse(new String());
        Optional<OandaTradesList> openTradesList = authentication.getResponse(url, entity, OandaTradesList.class, HttpMethod.GET);
        return openTradesList.orElse(new OandaTradesList());
    }

    private Map<String, Object> createOrder(OandaOrder oandaOrder) {
        Map<String, Object> orderParameters = new HashMap<>();

        orderParameters.put("type", oandaOrder.getType().toString());
        orderParameters.put("instrument", oandaOrder.getInstrument());
        orderParameters.put("units", oandaOrder.getUnits().toString());
        orderParameters.put("timeInForce", oandaOrder.getTimeInForce().toString());
        orderParameters.put("positionFill", oandaOrder.getPositionFill());
        orderParameters.put("gtdTime", oandaOrder.getGtdTime());

        if(oandaOrder.getPrice() != null) {
            orderParameters.put("price", String.format(Locale.ENGLISH, "%.5f", oandaOrder.getPrice()));
        }

        if(oandaOrder.getPriceBound() != null) {
            orderParameters.put("priceBound", String.format(Locale.ENGLISH, "%.5f", oandaOrder.getPriceBound()));
        }

        if(oandaOrder.getStopLossOnFill() != null) {
            Map<String, String> stopLossOnFill = new HashMap<>();
            stopLossOnFill.put("timeInForce", oandaOrder.getTimeInForce().toString());
            stopLossOnFill.put("price", String.format(Locale.ENGLISH, "%.5f", oandaOrder.getStopLossOnFill().getPrice()));
        }

        if(oandaOrder.getTakeProfitOnFill() != null) {
            Map<String, String> takeProfitOnFill = new HashMap<>();
            takeProfitOnFill.put("timeInForce", oandaOrder.getTimeInForce().toString());
            takeProfitOnFill.put("price", String.format(Locale.ENGLISH, "%.5f", oandaOrder.getTakeProfitOnFill().getPrice()));
        }

        log.info("New Order created [ " + orderParameters.toString() + " ]");

        Map<String, Object> map = new HashMap<>();
        map.put("order", orderParameters);
        return map;
    }




}
