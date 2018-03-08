package crypto.oanda.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OandaUrlCreator {

    private static final String PRICES = "PRICES";
    private static final String POSITIONS = "POSITIONS";
    private static final String POSITIONS_OPEN = "POSITIONS_OPEN";

    @Value("${oanda.main.url}")
    private String oandaMainUrl;

    @Value("${oanda.prices}")
    private String pricesUrl;

    @Value("${oanda.positions.all}")
    private String positinsUrl;

    @Value("&{oanda.positions.open}")
    private String openPositionsUrl;


    public String createUrl(String urlType, String accountId, String instrument) {
        String url = null;
        switch (urlType) {
            case PRICES:
                url = createUrlForPrices(accountId, instrument);
                log.info("URL = ", url," created!" );
                break;
            case POSITIONS:
                url = createUrlForPositions(accountId, instrument);
                log.info("URL = ", url," created!" );
                break;
            case POSITIONS_OPEN:
                url = createUrlForOpenPositions(accountId);
                log.info("URL = ", url," created!" );
                break;
            default:
                log.error("Incorrect url type!");
        }
        return url;
    }

    private String createMainUrlPart(String accountId) {
        StringBuilder sb = new StringBuilder();
        sb.append(oandaMainUrl);
        sb.append(accountId);
        sb.append("/");
        return sb.toString();
    }

    private String createUrlForPrices(String accountId, String instrument) {
        StringBuilder sb = new StringBuilder();
        sb.append(createMainUrlPart(accountId));
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
}
