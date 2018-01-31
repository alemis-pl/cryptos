package crypto.okex.domain.params;

import crypto.okex.domain.order.OkexOrderById;
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
    private OkexOrderById okexCanceldOrderDto;

    public OkexParamsModerator(String paramsType) {
        this.paramsType = paramsType;
    }

    public OkexParamsModerator(String paramsType, OkexOrderDto orderDto) {
        this.paramsType = paramsType;
        this.orderDto = orderDto;
    }

    public OkexParamsModerator(String paramsType, OkexOrderById okexCanceldOrderDto) {
        this.paramsType = paramsType;
        this.okexCanceldOrderDto = okexCanceldOrderDto;
    }
}
