package crypto.okcoin.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class OkcoinOrdersHistoryDto {

    private Long current_page;
    private OkcoinOrderHistoryDto[] orders;
    private Long page_length;
    private boolean result;
    private Long total;
}
