package crypto.okcoin.domain.accountrecords;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkcoinAccountRecordsRequestDto {

    private String symbol;
    private String type;
    private String currentPage;
    private String pageLength;
}
