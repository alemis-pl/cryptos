package crypto.okcoin.domain.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkcoinOrderById {

    private String symbol;
    private Long orderId;
}
