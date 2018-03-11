package crypto.oanda.authentication;

import lombok.Getter;

@Getter
public enum OandaUrlType {

    PRICES("PRICES"),
    POSITIONS("POSITIONS"),
    POSITIONS_OPEN("POSITIONS_OPEN"),
    ORDERS("ORDERS"),
    ORDER_CANCEL("ORDER_CANCEL"),
    GET_ORDERS("GET_ORDERS"),
    GET_PENDING_ORDERS("GET_PENDING_ORDERS"),
    TRADES("TRADES"),
    OPEN_TRADES("OPEN_TRADES"),
    ACCOUNT("ACCOUNT"),
    ACCOUNT_SUMMARY("ACCOUNT_SUMMARY"),
    TRADEABLE_INSTRUMENTS("TRADEABLE INSTRUMENTS");

    private String urlType;

    OandaUrlType(String urlType) {
        this.urlType = urlType;
    }
}
