package crypto.okex.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class OkexCanceldOrderResponseDto {

    private boolean result;
    private Long order_id;

}
