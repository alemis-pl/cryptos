package crypto.okcoin.domain.order;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OkcoinOrderHistoryRequest {

    private String symbol;
    private String status;
    private String currentPage;
    private String pageLength;
}
