package crypto.bitfinex.authentication;

import lombok.Getter;

@Getter
public enum BitfinexExchangeConnectionExceptions {

    UNEXPECTED_IO_ERROR_MSG("Failed to connect to Exchange due to unexpected IO error."),
    IO_SOCKET_TIMEOUT_ERROR_MSG("Failed to connect to Exchange due to socket timeout."),
    CONNECTION_ERROR("Failed to connect to Exchange. Something dead!"),
    SSL_CONNECTION_REFUSED("Failed to connect to Exchange. SSL Connection was refused or reset by the server."),
    IO_5XX_TIMEOUT_ERROR_MSG("Failed to connect to Exchange due to 5xx timeout."),
    AUTHENTICATED_ACCESS_NOT_POSSIBLE("Authenticated access not possible, because key and secret was not initialized: use right constructor."),
    ENCODING_ERROR("Failed to encode secret key. The Character Encoding is not supported."),
    INVALID_KEY_ERROR("Something wrong with keys - invalid encoding, wrod length etc."),
    LACK_OF_ALGORITHM_ERROR("HmaxSHA384 not available in the environment"),
    RETURN_NULL_AFTER_PLACING_ORDER("Something wrong with the process of placing the new order."),
    CANCEL_ORDER_ERROR("Failed to cancel order. Something wrong with Order id"),
    ORDER_STATUS_ERROR("Something wrong with downloading order status"),
    ACTIVE_POSITIONS_ERROR("Something wrong with downloading of active positions information.");


    private String exception;

    BitfinexExchangeConnectionExceptions(final String exception) {
        this.exception = exception;
    }
}
