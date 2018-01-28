package crypto.bitfinex.domain.params;

import lombok.Getter;

@Getter
public enum Params {


    BALANCE_HISTORY("BALANCE_HISTORY"),
    BALANCE_HISTORY_SINCE("BALANCE_HISTORY_SINCE"),
    BALANCE_HISTORY_SINCE_WALLET("BALANCE_HISTORY_SINCE_WALLET"),
    BALANCE_HISTORY_SINCE_UNTIL_WALLET("BALANCE_HISTORY_SINCE_UNTIL_WALLET"),
    PAST_TRADES("PAST_TRADES"),
    WITHOUT_PARAMS("WITHOUT_PARAMS"),
    NEW_ORDER("NEW_ORDER"),
    ORDER_BY_ID("ORDER_BY_ID");

    private String params;

    Params(final String params) {
        this.params = params;
    }
}
