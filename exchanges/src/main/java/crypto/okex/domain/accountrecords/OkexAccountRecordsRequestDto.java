package crypto.okex.domain.accountrecords;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkexAccountRecordsRequestDto {

    private String symbol;
    private String type;
    private String currentPage;
    private String pageLength;
}
