package crypto.okex.domain.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkexCreatedOrderDto {

    private boolean result;
    private Long order_id;
}
