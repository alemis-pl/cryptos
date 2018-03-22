package crypto.strategy.manager;

import crypto.bitfinex.client.BitfinexMarketDataManager;
import crypto.bitfinex.domain.tickers.BitfinexTicker;
import crypto.mail.EmailService;
import crypto.mail.Mail;
import crypto.oanda.client.OandaPriceManager;
import crypto.oanda.domain.price.OandaPrice;
import crypto.repository.DbStrategiesService;
import crypto.strategy.domain.simplestrategy.SimpleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@EnableScheduling
@Service
public class SimpleStrategyManager {

    private static final String BITFINEX = "bitfinex";
    private static final String OANDA = "oanda";

    private static final String HIGHER_THAN_STRATEGY_PRICE = "higher";
    private static final String LOWER_THAN_STRATEGY_PRICE = "lower";

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

//    @Scheduled(fixedDelay = 1000)
    private void createSignal() {
        List<SimpleStrategy> strategiesList = dbStrategiesService.getAllSimpleStrategiesByIsAchieved(false);
        if (!strategiesList.isEmpty()) {
            strategiesList.forEach(strategy -> { {
                if (strategy.getExchange().equals(BITFINEX)) {
                    Optional<BitfinexTicker> ticker = bitfinexMarketDataManager.downloadTickersData(strategy.getInstrument());
                    ticker.ifPresent(bitfinexTicker -> verifyPrice(strategy, bitfinexTicker.getLast_price()));
                }else if(strategy.getExchange().equals(OANDA)) {
                    Optional<OandaPrice> price = oandaPriceManager.getPrice(strategy.getInstrument());
                    price.ifPresent(oandaPrice -> verifyPrice(strategy, oandaPrice.getCloseoutAsk()));
                }
            }});
        }
    }

    private void verifyPrice(SimpleStrategy strategy, BigDecimal marketPrice) {
        if (!strategy.isAchieved()) {
            if (strategy.getMarketPriceIs().equals(HIGHER_THAN_STRATEGY_PRICE)) {
                if (marketPrice.compareTo(strategy.getPrice()) <= 0) {
                    updateStrategyAndsendSignal(strategy);
                }
            } else if (strategy.getMarketPriceIs().equals(LOWER_THAN_STRATEGY_PRICE)) {
                if (marketPrice.compareTo(strategy.getPrice()) >= 0) {
                    updateStrategyAndsendSignal(strategy);
                }
            }
        }
    }

    private void updateStrategyAndsendSignal(SimpleStrategy strategy) {
        strategy.setAchieved(true);
        dbStrategiesService.saveSimpleStrategy(strategy);
        final Mail mail = createMailMessage(strategy);
        emailService.send(mail);
        log.info("Price " + strategy.getPrice() + "  of " + strategy.getInstrument() + " was achieved!");
    }

    private Mail createMailMessage(SimpleStrategy strategy) {
        String actualTime = getTime();
        String subject = "Price achieved estimated level";
        String message = "Instrument: " + strategy.getInstrument() + "; exchange: " + strategy.getExchange()
                + " achieved price: " + strategy.getPrice() + " at time: " + actualTime;
        return new Mail(adminMail, subject, message);
    }

    private String getTime() {
        return LocalDateTime.now().toString();
    }
}
