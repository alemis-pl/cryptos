package crypto.okex.domain.order;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OkexOrderHistoryRequest {

    private String symbol;
    private String status;
    private String currentPage;
    private String pageLength;
}
