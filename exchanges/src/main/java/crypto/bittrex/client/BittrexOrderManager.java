package crypto.bittrex.client;

import crypto.apikeys.ApiKeys;
import crypto.apikeys.ApiKeysRepository;
import crypto.bittrex.authentication.BittrexExchangeAuthentication;
import crypto.bittrex.domain.order.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class BittrexOrderManager {

    private final static String BUY_LIMIT = "buylimit";
    private final static String SELL_LIMIT = "selllimit";

    @Value("${bittrex.main.url}")
    private String bittrexMainUrl;

    @Value("${bittrex.order.history}")
    private String ordersHistoryUrl;

    @Value("${bittrex.order.by.id}")
    private String getOrderurl;

    @Value("${bittrex.order.buylimit}")
    private String buyLimitOrderUrl;

    @Value("${bittrex.order.selllimit}")
    private String sellLimitOrderUrl;

    @Value("${bittrex.order.cancel}")
    private String cancelOrderUrl;

    @Value("${bittrex.order.open.orders}")
    private String openOrdersUrl;

    @Value("${bittrex.apikey}")
    private String apiKeyUrl;

    private Long nonce = System.currentTimeMillis();

    private ApiKeysRepository apiKeysRepository;
    private BittrexExchangeAuthentication bittrexExchangeAuthentication;

    public BittrexOrderManager(ApiKeysRepository apiKeysRepository, BittrexExchangeAuthentication bittrexExchangeAuthentication) {
        this.apiKeysRepository = apiKeysRepository;
        this.bittrexExchangeAuthentication = bittrexExchangeAuthentication;
    }

    public BittrexOrderPlacedResponse placeOrder(BittrexOrderPlaced bittrexOrderPlaced) {
        ApiKeys apiKeys = apiKeysRepository.getByExchange("bittrex");
        String url = createUrlForOrderPlaceing(apiKeys, bittrexOrderPlaced);
        if (!url.isEmpty() || url != null) {
            HttpEntity entity = bittrexExchangeAuthentication.createHttpEntity(url.toString(), apiKeys.getApiSecretKey());
            Optional<BittrexOrderPlacedResponse> orderPlaced = bittrexExchangeAuthentication.getResponse(url.toString(), entity, BittrexOrderPlacedResponse.class, HttpMethod.POST);
            if (orderPlaced.isPresent() && orderPlaced.get().isSuccess()) {
                log.info("New order placed: " + orderPlaced);
            }else {
                log.error("Something went wrong with placing new order!" + orderPlaced.get().getMessage());
            }
            return orderPlaced.orElseGet(BittrexOrderPlacedResponse::new);
        }
        return new BittrexOrderPlacedResponse();
    }

    private String createUrlForOrderPlaceing(ApiKeys apiKeys, BittrexOrderPlaced bittrexOrderPlaced) {
        StringBuilder url = new StringBuilder();
        url.append(bittrexMainUrl);
        if (bittrexOrderPlaced.getOrderType().equals(BUY_LIMIT)) {
            url.append(buyLimitOrderUrl);
        }else if(bittrexOrderPlaced.getOrderType().equals(SELL_LIMIT)) {
            url.append(sellLimitOrderUrl);
        }else {
            log.error("Incorrect order type: " + bittrexOrderPlaced.getOrderType());
            return new String();
        }
        url.append(apiKeyUrl);
        url.append(apiKeys.getApiKey());
        url.append("&market=");
        url.append(bittrexOrderPlaced.getCurrencyPair());
        url.append("&quantity=");
        url.append(bittrexOrderPlaced.getQuantity());
        url.append("&rate=");
        url.append(bittrexOrderPlaced.getPriceLimit());
        url.append("&nonce=");
        url.append(nonce);
        return url.toString();
    }

   public BittrexOrderCancelResponse cancelOrder(String orderId) {
       ApiKeys apiKeys = apiKeysRepository.getByExchange("bittrex");
       String url = bittrexMainUrl + cancelOrderUrl + apiKeyUrl + apiKeys.getApiKey() + "&uuid=" + orderId + "&nonce=" + nonce;
       HttpEntity entity = bittrexExchangeAuthentication.createHttpEntity(url, apiKeys.getApiSecretKey());
       Optional<BittrexOrderCancelResponse> cancelOrderResponse = bittrexExchangeAuthentication.getResponse(url, entity, BittrexOrderCancelResponse.class, HttpMethod.POST);
       if (!cancelOrderResponse.get().isSucces()) {
           log.info("Order [id: " + orderId + "] was canceled!");
           return cancelOrderResponse.orElseGet(BittrexOrderCancelResponse::new);
       }else {
           log.error("Something wrong with order canceling process! " + cancelOrderResponse.get().getMessage());
           return cancelOrderResponse.orElseGet(BittrexOrderCancelResponse::new);
       }
   }

   public BittrexOrdersDto getOpenOrders(String currencyOrders) {
       ApiKeys apiKeys = apiKeysRepository.getByExchange("bittrex");
       String url = bittrexMainUrl + openOrdersUrl + apiKeyUrl + apiKeys.getApiKey() + "&nonce=" + nonce;
       if (!currencyOrders.equals("ALL OPEN ORDERS")) {
           url = "&market=" + currencyOrders;
       }
       System.out.println(url);
       HttpEntity entity = bittrexExchangeAuthentication.createHttpEntity(url, apiKeys.getApiSecretKey());
       Optional<BittrexOrdersDto> openOrders = bittrexExchangeAuthentication.getResponse(url, entity, BittrexOrdersDto.class, HttpMethod.POST);
       if (openOrders.get().isSuccess()) {
           log.info("Open orders was downloaded!" + openOrders.get());
       }else {
           log.error("Something went wrong with downloading open orders!");
       }
       return openOrders.orElseGet(BittrexOrdersDto::new);
   }


    public BittrexOrdersDto getOrdersHistory() {
        ApiKeys apiKeys = apiKeysRepository.getByExchange("bittrex");
        String url = bittrexMainUrl + ordersHistoryUrl + apiKeyUrl + apiKeys.getApiKey() + "&nonce=" + nonce;
        HttpEntity entity = bittrexExchangeAuthentication.createHttpEntity(url, apiKeys.getApiSecretKey());
        Optional<BittrexOrdersDto> ordersHistory = bittrexExchangeAuthentication.getResponse(url, entity, BittrexOrdersDto.class, HttpMethod.POST);
        if (ordersHistory.get().isSuccess()) {
            log.info("Orders history was downloaded!" + ordersHistory.get());
        }else {
            log.error("Something went wrong with downloading orders history!");
        }

        return ordersHistory.orElseGet(BittrexOrdersDto::new);
    }

    public BittrexOrderDto getOrder(String orderId) {
        ApiKeys apiKeys = apiKeysRepository.getByExchange("bittrex");
        String url = bittrexMainUrl + getOrderurl + orderId + apiKeyUrl + apiKeys.getApiKey() + "&nonce=" + nonce;
        HttpEntity entity = bittrexExchangeAuthentication.createHttpEntity(url, apiKeys.getApiSecretKey());
        Optional<BittrexOrderDto> order = bittrexExchangeAuthentication.getResponse(url, entity, BittrexOrderDto.class, HttpMethod.POST);
        return order.orElseGet(BittrexOrderDto::new);
    }
}
