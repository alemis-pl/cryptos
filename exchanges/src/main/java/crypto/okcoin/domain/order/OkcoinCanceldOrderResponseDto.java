package crypto.okcoin.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class OkcoinCanceldOrderResponseDto {

    private boolean result;
    private Long order_id;

}
