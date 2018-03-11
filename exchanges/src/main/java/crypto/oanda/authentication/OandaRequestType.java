package crypto.oanda.authentication;

import lombok.Getter;

@Getter
public enum OandaRequestType {

    STANDARD_REQUEST("STANDARD_REQUEST"),
    ORDER_REQUEST("ORDER_REQUEST"),
    ORDER_CANCEL("ORDER_CANCEL");

    private String requestType;

    OandaRequestType(String request) {
        this.requestType = request;
    }
}
