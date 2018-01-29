package crypto.okex.domain.params;

import crypto.okex.domain.order.OkexCanceldOrderDto;
import crypto.okex.domain.order.OkexCanceldOrderResponseDto;
import crypto.okex.domain.order.OkexOrderDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OkexParamsModerator {

    private String paramsType;
    private OkexOrderDto orderDto;
    private OkexCanceldOrderDto okexCanceldOrderDto;

    public OkexParamsModerator(String paramsType, OkexOrderDto orderDto) {
        this.paramsType = paramsType;
        this.orderDto = orderDto;
    }

    public OkexParamsModerator(String paramsType, OkexCanceldOrderDto okexCanceldOrderDto) {
        this.paramsType = paramsType;
        this.okexCanceldOrderDto = okexCanceldOrderDto;
    }
}
