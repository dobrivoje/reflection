package marketing;

import lombok.*;
import marketing.ValidatorFacility.DISCOUNT_TYPE;

import static marketing.ValidatorFacility.DISCOUNT_TYPE.*;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PriorityDiscount {

    Priority priority;
    double discount;

    @Builder.Default
    public DISCOUNT_TYPE discountType = PERCENT;
}