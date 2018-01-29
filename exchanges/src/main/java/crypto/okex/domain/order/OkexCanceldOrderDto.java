package crypto.okex.domain.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkexCanceldOrderDto {

    private String symbol;
    private Long orderId;
}
