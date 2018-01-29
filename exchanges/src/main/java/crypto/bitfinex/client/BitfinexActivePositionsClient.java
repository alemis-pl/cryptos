package crypto.bitfinex.client;

import com.google.gson.Gson;
import crypto.bitfinex.authentication.BitfinexExchangeAuthentication;
import crypto.bitfinex.authentication.BitfinexExchangeConnectionExceptions;
import crypto.bitfinex.authentication.BitfinexExchangeHttpResponse;
import crypto.bitfinex.domain.params.BitfinexParams;
import crypto.bitfinex.domain.params.BitfinexParamsModerator;
import crypto.bitfinex.domain.params.BitfinexParamsToSearch;
import crypto.bitfinex.domain.positions.BitfinexActivePositionsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BitfinexActivePositionsClient {

    @Value("${bitfinex.positions.active}")
    private String activePositions;

    @Autowired
    private BitfinexExchangeAuthentication exchangeAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(BitfinexOrderStatusClient.class);

    private static final String POST = "POST";

    public BitfinexActivePositionsDto[] getActivePositions() throws Exception {

        BitfinexParamsToSearch paramsToSearch = null;
        BitfinexParamsModerator paramsModerator = new BitfinexParamsModerator(BitfinexParams.WITHOUT_PARAMS.getParams(), paramsToSearch);

        try {
            BitfinexExchangeHttpResponse exchangeHttpResponse = exchangeAuthentication.sendExchangeRequest(activePositions, POST, paramsModerator);
            LOGGER.info("Active positions information: " + exchangeHttpResponse);

            Gson gson = new Gson();

            return gson.fromJson(exchangeHttpResponse.getContent(), BitfinexActivePositionsDto[].class);
        } catch (IOException e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.UNEXPECTED_IO_ERROR_MSG.getException(), e);
        } catch (NullPointerException e) {
            LOGGER.error(BitfinexExchangeConnectionExceptions.ACTIVE_POSITIONS_ERROR.getException(), e);
            throw new IOException(BitfinexExchangeConnectionExceptions.ACTIVE_POSITIONS_ERROR.getException(), e);
        }
    }
}
