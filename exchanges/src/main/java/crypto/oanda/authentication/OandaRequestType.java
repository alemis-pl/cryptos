package crypto.oanda.authentication;

import lombok.Getter;

@Getter
public enum OandaRequestType {

    STANDARD_REQUEST("STANDARD_REQUEST"),
    ORDER_REQUEST("ORDER_REQUEST");

    private String requestType;

    OandaRequestType(String request) {
        this.requestType = request;
    }
}
