package crypto.strategy.domain.simplestrategy;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;

@Slf4j
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleStrategyDto {

    private Long id;
    private String instrumentType;
    private String instrument;
    private String position;
    private BigDecimal price;
    private boolean achieved;
    private Long timestamp;
    private String exchange;
    private String marketPriceIs;

}
