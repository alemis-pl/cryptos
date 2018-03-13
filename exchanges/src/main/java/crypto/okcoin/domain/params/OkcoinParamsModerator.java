package crypto.okcoin.domain.params;

import crypto.okcoin.domain.accountrecords.OkcoinAccountRecordsRequestDto;
import crypto.okcoin.domain.order.OkcoinOrderById;
import crypto.okcoin.domain.order.OkcoinOrderDto;
import crypto.okcoin.domain.order.OkcoinOrderHistoryRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OkcoinParamsModerator {

    private String paramsType;
    private OkcoinOrderDto orderDto;
    private OkcoinOrderById okexCanceldOrderDto;
    private OkcoinOrderHistoryRequest okexOrderHistoryRequest;
    private OkcoinAccountRecordsRequestDto okexAccountRecordsRequestDto;

    public OkcoinParamsModerator(String paramsType) {
        this.paramsType = paramsType;
    }

    public OkcoinParamsModerator(String paramsType, OkcoinOrderDto orderDto) {
        this.paramsType = paramsType;
        this.orderDto = orderDto;
    }

    public OkcoinParamsModerator(String paramsType, OkcoinOrderById okexCanceldOrderDto) {
        this.paramsType = paramsType;
        this.okexCanceldOrderDto = okexCanceldOrderDto;
    }

    public OkcoinParamsModerator(String paramsType, OkcoinOrderHistoryRequest okexOrderHistoryRequest) {
        this.paramsType = paramsType;
        this.okexOrderHistoryRequest = okexOrderHistoryRequest;
    }

    public OkcoinParamsModerator(String paramsType, OkcoinAccountRecordsRequestDto okexAccountRecordsRequestDto) {
        this.paramsType = paramsType;
        this.okexAccountRecordsRequestDto = okexAccountRecordsRequestDto;
    }
}
