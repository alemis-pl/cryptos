package crypto.okex.domain.params;

import lombok.Getter;

@Getter
public enum OkexParams {

    WITHOUT_PARAMS("WITHOUT_PARAMS"),
    NEW_ORDER("NEW_ORDER"),
    ORDER_BY_ID("ORDER_BY_ID");

    private String params;

    OkexParams(final String params) {
        this.params = params;
    }
}
