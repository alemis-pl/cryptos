package crypto.okcoin.domain.accountrecords;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OkcoinAccountRecordDto {

    private String addr;
    private Long account;
    private BigDecimal amount;
    private String bank;
    private String beneficiary_addr;
    private BigDecimal transaction_value;
    private BigDecimal fee;
    private Long date;
    private String status;

}
