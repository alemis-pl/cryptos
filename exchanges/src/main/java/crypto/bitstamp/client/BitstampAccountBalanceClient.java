package crypto.bitstamp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BitstampAccountBalanceClient {

    @Value("${bitstamp.accountbalance}")
    private String accountBalance;

    private static final Logger LOGGER = LoggerFactory.getLogger(BitstampAccountBalanceClient.class);

    private static final String POST = "POST";


}
