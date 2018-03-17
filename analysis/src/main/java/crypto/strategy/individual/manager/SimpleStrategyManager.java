package crypto.strategy.individual.manager;

import crypto.bitfinex.client.BitfinexMarketDataManager;
import crypto.bitfinex.domain.tickers.BitfinexTicker;
import crypto.mail.EmailService;
import crypto.mail.Mail;
import crypto.oanda.client.OandaPriceManager;
import crypto.oanda.domain.price.OandaPrice;
import crypto.repository.DbStrategiesService;
import crypto.strategy.individual.domain.signal.SimpleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Slf4j
@EnableScheduling
@Service
public class SimpleStrategyManager {

    private static final String BITFINEX = "bitfinex";
    private static final String OANDA = "oanda";

    @Value("${admin.mail}")
    private String adminMail;


    private EmailService emailService;
    private DbStrategiesService dbStrategiesService;
    private BitfinexMarketDataManager bitfinexMarketDataManager;
    private OandaPriceManager oandaPriceManager;

    public SimpleStrategyManager(EmailService emailService, DbStrategiesService dbStrategiesService, BitfinexMarketDataManager bitfinexMarketDataManager, OandaPriceManager oandaPriceManager) {
        this.emailService = emailService;
        this.dbStrategiesService = dbStrategiesService;
        this.bitfinexMarketDataManager = bitfinexMarketDataManager;
        this.oandaPriceManager = oandaPriceManager;
    }

    @Scheduled(fixedDelay = 5000)
    private void downloadMarketData() {
        List<SimpleStrategy> strategiesInfo = dbStrategiesService.getAllIndividualStrategies();
        strategiesInfo.stream().forEach(strategy -> {
            if(strategy.getExchange().equals(BITFINEX)) {
                downloadBitfinexTickersData(strategy);
            }else if(strategy.getExchange().equals(OANDA)) {
                downloadOandaTickersData(strategy);
            }else {
                log.warn("No exchange found!");
            }
        });
    }

    private void downloadBitfinexTickersData(SimpleStrategy strategy) {
        if(!strategy.isAchieved()) {
            Optional<BitfinexTicker> ticker = bitfinexMarketDataManager.downloadTickersData(strategy.getInstrument());
            if (ticker.isPresent()) {
                log.info("Ticker for " + strategy.getInstrument() + " from exchange " + strategy.getExchange() + " downloaded");
                compareBitfinexPrice(ticker.get(), strategy);
            } else {
                log.info("Something went wrong with downloading ticker for" + strategy.getInstrument() + " from exchange " + strategy.getExchange() + " downloaded");
            }
        }
    }
    private void compareBitfinexPrice(BitfinexTicker ticker, SimpleStrategy strategy) {
        if(ticker.getLast_price().compareTo(strategy.getPrice()) == 0 || ticker.getLast_price().compareTo(strategy.getPrice()) < 0) {
            strategy.setAchieved(true);
            dbStrategiesService.saveIndividualStrategy(strategy);
            final Mail mail = createMailMessage(strategy);
            emailService.send(mail);
            log.info("Price " + strategy.getPrice() + "  of " + strategy.getInstrument() + " was achieved!");
        }
    }

    private void downloadOandaTickersData(SimpleStrategy strategy) {
        if(!strategy.isAchieved()) {
            OandaPrice price = oandaPriceManager.getPrice(strategy.getInstrument());
            if(price != null) {
                log.info("Ticker for " + strategy.getInstrument() + " from exchange " + strategy.getExchange() + " downloaded");
                compareOandaPrice(price, strategy);
            }
        }
    }

    private void compareOandaPrice(OandaPrice price, SimpleStrategy strategy) {
        if(price.getCloseoutAsk().compareTo(strategy.getPrice()) == 0 ||  price.getCloseoutAsk().compareTo(strategy.getPrice()) < 0) {
            strategy.setAchieved(true);
            dbStrategiesService.saveIndividualStrategy(strategy);
            final Mail mail = createMailMessage(strategy);
            emailService.send(mail);
            log.info("Price " + strategy.getPrice() + "  of " + strategy.getInstrument() + " was achieved!");
        }
    }

    private Mail createMailMessage(SimpleStrategy strategy) {
        String actualTime = getTime();
        String subject = "Price achieved estimated level";
        String message = "Instrument: " + strategy.getInstrument() + "; exchange: " + strategy.getExchange()
                + " achieved price: " + strategy.getPrice() + " at time: " + actualTime;
        System.out.println(adminMail);
        System.out.println(subject);
        System.out.println(message);
        return new Mail(adminMail, subject, message);
    }

    private String getTime() {
        return LocalDateTime.now().toString();
    }
}
