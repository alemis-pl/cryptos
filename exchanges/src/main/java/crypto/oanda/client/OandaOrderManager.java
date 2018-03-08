package crypto.oanda.client;

import crypto.oanda.authentication.OandaAuthentication;
import crypto.oanda.authentication.OandaUrlCreator;
import crypto.persistance.apikey.ApiKeys;
import crypto.persistance.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OandaOrderManager {

    private OandaAuthentication authentication;
    private OandaUrlCreator urlCreation;
    private DbService dbService;

    @Autowired
    public OandaOrderManager(OandaAuthentication authentication, OandaUrlCreator urlCreation, DbService dbService) {
        this.authentication = authentication;
        this.urlCreation = urlCreation;
        this.dbService = dbService;
    }

    private ApiKeys getApiKeys() {
        return dbService.getApiKeysByExchange("oanda");
    }

}
