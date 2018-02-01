package crypto.okex.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class OkexOrdersHistoryDto {

    private Long current_page;
    private OkexOrderHistoryDto[] orders;
    private Long page_length;
    private boolean result;
    private Long total;
}
