package crypto.bitfinex.domain.params;

import crypto.bitfinex.domain.order.OrderDto;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ParamsModerator {

    private String paramType;
    private ParamsToSearch paramsToSearch;
    private OrderDto orderDto;

    public ParamsModerator(String paramType, ParamsToSearch paramsToSearch) {
        this.paramType = paramType;
        this.paramsToSearch = paramsToSearch;
    }

    public ParamsModerator(String paramType, OrderDto orderDto) {
        this.paramType = paramType;
        this.orderDto = orderDto;
    }
}
