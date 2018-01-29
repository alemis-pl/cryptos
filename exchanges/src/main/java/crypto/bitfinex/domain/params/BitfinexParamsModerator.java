package crypto.bitfinex.domain.params;

import crypto.bitfinex.domain.order.BitfinexOrderDto;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BitfinexParamsModerator {

    private String paramType;
    private BitfinexParamsToSearch paramsToSearch;
    private BitfinexOrderDto orderDto;

    public BitfinexParamsModerator(String paramType, BitfinexParamsToSearch paramsToSearch) {
        this.paramType = paramType;
        this.paramsToSearch = paramsToSearch;
    }

    public BitfinexParamsModerator(String paramType, BitfinexOrderDto orderDto) {
        this.paramType = paramType;
        this.orderDto = orderDto;
    }
}
