package crypto.okcoin.domain.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkcoinCreatedOrderDto {

    private boolean result;
    private Long order_id;
}
