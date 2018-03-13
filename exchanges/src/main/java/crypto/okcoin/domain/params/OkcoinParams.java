package crypto.okcoin.domain.params;

import lombok.Getter;

@Getter
public enum OkcoinParams {

    WITHOUT_PARAMS("WITHOUT_PARAMS"),
    NEW_ORDER("NEW_ORDER"),
    ORDER_BY_ID("ORDER_BY_ID"),
    ORDERS_HISTORY("ORDERS_HISTORY"),
    ACCOUNT_RECORDS("ACCOUNT_RECORDS");

    private String params;

    OkcoinParams(final String params) {
        this.params = params;
    }
}
