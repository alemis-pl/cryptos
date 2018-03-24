package crypto.bittrex.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BittrexOrderInfo {

    @JsonProperty(value = "Uuid")
    private String uuid;

    @JsonProperty(value = "OrderUuid")
    private String orderUuid;

    @JsonProperty(value = "Exchange")
    private String currencyPair;

    @JsonProperty(value = "OrderType")
    private String orderType;

    @JsonProperty(value = "Quantity")
    private BigDecimal quantity;

    @JsonProperty(value = "QuantityRemaining")
    private BigDecimal quantityRemaining;

    @JsonProperty(value = "Limit")
    private BigDecimal limit;

    @JsonProperty(value = "ComissionPaid")
    private BigDecimal commissionPaid;

    @JsonProperty(value = "Price")
    private BigDecimal price;

    @JsonProperty(value = "PricePerUnit")
    private BigDecimal pricePerUnit;

    @JsonProperty(value = "Opened")
    @JsonDeserialize(using = BittrexStringDateTimeDeserializer.class)
    private DateTime opened;

    @JsonProperty(value = "Closed")
    private boolean closed;

    @JsonProperty(value = "CancelInitiated")
    private boolean cancelIntitiated;

    @JsonProperty(value = "ImmediateOrcancel")
    private boolean immediateOrCancel;

    @JsonProperty(value = "IsConditional")
    private boolean isConditional;

    @JsonProperty(value = "Condition")
    private String condition;

    @JsonProperty(value = "ConditionTarget")
    private String conditionTarget;
}
