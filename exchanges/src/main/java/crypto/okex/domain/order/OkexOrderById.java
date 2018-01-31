package crypto.okex.domain.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkexOrderById {

    private String symbol;
    private Long orderId;
}
