package crypto.oanda.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class OandaUrlCreator {

    private static final String PRICES = "PRICES";
    private static final String POSITIONS = "POSITIONS";
    private static final String POSITIONS_OPEN = "POSITIONS_OPEN";
    private static final String ORDERS = "ORDERS";
    private static final String ORDER_CANCEL = "ORDER_CANCEL";
    private static final String GET_ORDERS = "GET_ORDERS";
    private static final String GET_PENDING_ORDERS = "GET_PENDING_ORDERS";
    private static final String TRADES = "TRADES";
    private static final String OPEN_TRADES = "OPEN_TRADES";
    private static final String ACCOUNT = "ACCOUNT";
    private static final String ACCOUNT_SUMMARY = "ACCOUNT_SUMMARY";
    private static final String TRADEABLE_INSTRUMENTS = "TRADEABLE INSTRUMENTS";

    @Value("${oanda.main.url}")
    private String oandaMainUrl;

    @Value("${oanda.prices}")
    private String pricesUrl;

    @Value("${oanda.positions.all}")
    private String positinsUrl;

    @Value("${oanda.positions.open}")
    private String openPositionsUrl;

    @Value("${oanda.orders}")
    private String ordersUrl;

    @Value("${oanda.cancel}")
    private String cancelOrderUrl;

    @Value("${oanda.get.orders}")
    private String getOrdersUrl;

    @Value("${oanda.get.pending.orders}")
    private String getPendingOrdersUrl;

    @Value("${oanda.trades}")
    private String tradesUrl;

    @Value("${oanda.trades.open}")
    private String opentTradesUrl;

    @Value("${oanda.account.summary}")
    private String accountSummaryUrl;

    @Value("${onada.tradeable.instruments}")
    private String tradeableInstrumentsUrl;


    public Optional<String> createUrl(OandaUrlParameters parameters) {
        String url = null;
        switch (parameters.getUrlType()) {
            case ACCOUNT:
                url = createMainUrlPart(parameters.getAccountId());
                log.info("URL for account info  [ ", url, "] created!" );
                break;
            case ACCOUNT_SUMMARY:
                url = createUrlForAccountSummery(parameters.getAccountId());
                log.info("URL for account summary info  [ ", url, "] created!" );
                break;
            case TRADEABLE_INSTRUMENTS:
                url = createUrlForTradeableInstruments(parameters.getAccountId());
                log.info("URL for account tradeable instruments  [ ", url, "] created!" );
                break;
            case PRICES:
                url = createUrlForPrices(parameters.getAccountId(), parameters.getInstrument());
                log.info("URL for prices  [ ", url, "] created!" );
                break;
            case POSITIONS:
                url = createUrlForPositions(parameters.getAccountId(), parameters.getInstrument());
                log.info("URL for positions [ ", url,"] created!" );
                break;
            case POSITIONS_OPEN:
                url = createUrlForOpenPositions(parameters.getAccountId());
                log.info("URL for open positions [ ", url," ] created!" );
                break;
            case ORDERS:
                url = createUrlForOrders(parameters.getAccountId());
                log.info("URL for orders [ ", url," ] created!" );
                break;
            case ORDER_CANCEL:
                url = createUrlForCancelOrder(parameters.getAccountId(), parameters.getOrderId());
                log.info("URL for cancel order [ ", url," ] created!" );
                break;
            case GET_ORDERS:
                url = createUrlForGetOrders(parameters.getAccountId(), parameters.getInstrument());
                log.info("URL for get all orders [ ", url," ] created!" );
                break;
            case GET_PENDING_ORDERS:
                url = createUrlForGetPendingOrders(parameters.getAccountId());
                log.info("URL for get all pending orders [ ", url," ] created!" );
                break;
            case TRADES:
                url = createUrlForTrades(parameters.getAccountId(), parameters.getInstrument());
                log.info("URL for trades [ ", url," ] created!" );
                break;
            case OPEN_TRADES:
                url = createUrlForOpenTrades(parameters.getAccountId());
                log.info("URL for open trades [ ", url," ] created!" );
                break;
            default:
                log.error("Incorrect url type! ", parameters.getUrlType());
        }
        return Optional.ofNullable(url);
    }

    private String createMainUrlPart(String accountId) {
        StringBuilder sb = new StringBuilder();
        sb.append(oandaMainUrl);
        sb.append(accountId);
        return sb.toString();
    }

    private String createUrlForAccountSummery(String accountId ) {
        StringBuilder sb = new StringBuilder();
        sb.append(createMainUrlPart(accountId));
        sb.append("/");
        sb.append(accountSummaryUrl);
        return sb.toString();
    }

    private String createUrlForTradeableInstruments(String accountId) {
        StringBuilder sb = new StringBuilder();
        sb.append(createMainUrlPart(accountId));
        sb.append("/");
        sb.append(tradeableInstrumentsUrl);
        return sb.toString();
    }

    private String createUrlForPrices(String accountId, String instrument) {
        StringBuilder sb = new StringBuilder();
        sb.append(createMainUrlPart(accountId));
        sb.append("/");
        sb.append(pricesUrl);
        sb.append(instrument);
        return sb.toString();
    }

    private String createUrlForPositions(String accountId, String instrument) {
        StringBuilder sb = new StringBuilder();
        sb.append(createMainUrlPart(accountId));
        sb.append("/");
        sb.append(positinsUrl);
        if(instrument != null) {
            sb.append("/");
            sb.append(instrument);
        }
        return sb.toString();
    }

    private String createUrlForOpenPositions(String accountId) {
        StringBuilder sb = new StringBuilder();
        sb.append(createMainUrlPart(accountId));
        sb.append("/");
        sb.append(openPositionsUrl);
        return sb.toString();
    }

    private String createUrlForOrders(String accountId) {
        StringBuilder sb = new StringBuilder();
        sb.append(createMainUrlPart(accountId));
        sb.append("/");
        sb.append(ordersUrl);
        return sb.toString();
    }

    private String createUrlForGetOrders(String accountId, String instrument) {
        StringBuilder sb = new StringBuilder();
        sb.append(createMainUrlPart(accountId));
        sb.append("/");
        sb.append(getOrdersUrl);
        return sb.toString();
    }

    private String createUrlForCancelOrder(String accountId, Long orderId) {
        StringBuilder sb = new StringBuilder();
        sb.append(createUrlForOrders(accountId));
        sb.append("/");
        sb.append(String.valueOf(orderId));
        sb.append("/");
        sb.append(cancelOrderUrl);
        return sb.toString();
    }

    private String createUrlForGetPendingOrders(String accountId) {
        StringBuilder sb = new StringBuilder();
        sb.append(createMainUrlPart(accountId));
        sb.append("/");
        sb.append(getPendingOrdersUrl);
        return sb.toString();
    }

    private String createUrlForTrades(String accountId, String instrument) {
        StringBuilder sb = new StringBuilder();
        sb.append(createMainUrlPart(accountId));
        sb.append("/");
        sb.append(tradesUrl);
        sb.append("?instrument=");
        sb.append(instrument);
        return sb.toString();
    }

    private String createUrlForOpenTrades(String accountId){
        StringBuilder sb = new StringBuilder();
        sb.append(createMainUrlPart(accountId));
        sb.append("/");
        sb.append(opentTradesUrl);
        return sb.toString();
    }
}
