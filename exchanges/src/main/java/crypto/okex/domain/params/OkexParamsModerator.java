package crypto.okex.domain.params;

import crypto.okex.domain.accountrecords.OkexAccountRecordsRequestDto;
import crypto.okex.domain.order.OkexOrderById;
import crypto.okex.domain.order.OkexOrderDto;
import crypto.okex.domain.order.OkexOrderHistoryRequest;
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
    private OkexOrderHistoryRequest okexOrderHistoryRequest;
    private OkexAccountRecordsRequestDto okexAccountRecordsRequestDto;

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

    public OkexParamsModerator(String paramsType, OkexOrderHistoryRequest okexOrderHistoryRequest) {
        this.paramsType = paramsType;
        this.okexOrderHistoryRequest = okexOrderHistoryRequest;
    }

    public OkexParamsModerator(String paramsType, OkexAccountRecordsRequestDto okexAccountRecordsRequestDto) {
        this.paramsType = paramsType;
        this.okexAccountRecordsRequestDto = okexAccountRecordsRequestDto;
    }
}
