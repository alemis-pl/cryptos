package crypto.strategy.mapper;

import crypto.bitfinex.client.BitfinexMarketDataManager;
import crypto.bitfinex.domain.tickers.BitfinexTicker;
import crypto.oanda.client.OandaPriceManager;
import crypto.oanda.domain.price.OandaPrice;
import crypto.strategy.domain.bitfinexsupport.BitfinexMarketData;
import crypto.strategy.domain.simplestrategy.SimpleStrategyDto;
import crypto.strategy.domain.simplestrategy.SimpleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Configuration
public class SimpleStrategyMapper {

    private static final String BITFINEX = "bitfinex";
    private static final String OANDA = "oanda";

    private static final String HIGHER_THAN_STRATEGY_PRICE = "higher";
    private static final String LOWER_THAN_STRATEGY_PRICE = "lower";

    @Autowired
    private BitfinexMarketDataManager bitfinexMarketDataManager;

    @Autowired
    private OandaPriceManager oandaPriceManager;

    public SimpleStrategy convertToSimpleStrategy(SimpleStrategyDto simpleStrategyDto) {
        return new SimpleStrategy(simpleStrategyDto.getId(), simpleStrategyDto.getInstrumentType(), simpleStrategyDto.getInstrument(), simpleStrategyDto.getPosition(), simpleStrategyDto.getPrice(), simpleStrategyDto.isAchieved(), setTimestamp(), simpleStrategyDto.getExchange(), setMarketPriceIs(simpleStrategyDto.getInstrument(), simpleStrategyDto.getPrice(), simpleStrategyDto.getExchange()));
    }

    public SimpleStrategyDto convertToSimpleStrategyDto(SimpleStrategy simpleStrategy) {
        return new SimpleStrategyDto(simpleStrategy.getId(), simpleStrategy.getInstrumentType(), simpleStrategy.getInstrument(), simpleStrategy.getPosition(), simpleStrategy.getPrice(), simpleStrategy.isAchieved(), simpleStrategy.getTimestamp(), simpleStrategy.getExchange(), simpleStrategy.getMarketPriceIs());
    }

    private Long setTimestamp() {
        return System.currentTimeMillis();
    }

    private String setMarketPriceIs(String instrument, BigDecimal price, String exchange) {
        String marketPriceIs = "";
        switch (exchange) {
            case BITFINEX:
                Optional<BitfinexTicker> ticker = bitfinexMarketDataManager.downloadTickersData(instrument);
                if (ticker.isPresent()) {
                    marketPriceIs = comparePrice(price, ticker.get().getLast_price());
                }
                break;
            case OANDA:
                Optional<OandaPrice> oandaPrice = oandaPriceManager.getPrice(instrument);
                if (oandaPrice.isPresent()) {
                    System.out.println(oandaPrice);
                    marketPriceIs = comparePrice(price, oandaPrice.get().getCloseoutAsk());
                }
            default:
                log.error("No exchange found! Verification whether market price is lower or higher then strategy price was not possible!");
        }
        return marketPriceIs;
    }

    private String comparePrice(BigDecimal strategyPrice, BigDecimal marketPrice) {
        System.out.println(strategyPrice);
        System.out.println(marketPrice);
        if (strategyPrice.compareTo(marketPrice) < 0) {
            return HIGHER_THAN_STRATEGY_PRICE;
        }else if (strategyPrice.compareTo(marketPrice) > 0) {
            return LOWER_THAN_STRATEGY_PRICE;
        }else {
            return "not defined!";
        }
    }
}
