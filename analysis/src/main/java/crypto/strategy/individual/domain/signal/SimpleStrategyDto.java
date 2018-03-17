package crypto.strategy.individual.domain.signal;


import lombok.*;

import java.math.BigDecimal;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleStrategyDto {

    private Long id;
    private String instrumentType;
    private String instrument;
    private String position;
    private BigDecimal price;
    private boolean achieved;
    private Long timestamp;
    private String exchange;

}
