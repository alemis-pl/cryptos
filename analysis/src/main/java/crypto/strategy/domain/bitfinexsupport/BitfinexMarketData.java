package crypto.strategy.domain.bitfinexsupport;

import crypto.bitfinex.domain.tickers.BitfinexTicker;

import java.util.HashMap;

public class BitfinexMarketData {

    private static HashMap<String, BitfinexTicker> bitfinexMarketData;

    static {
        if (bitfinexMarketData == null) {
            bitfinexMarketData = new HashMap<>();
        }
    }

    public static HashMap<String, BitfinexTicker> getBitfinexMarketData(){
        return bitfinexMarketData;
    }
}
