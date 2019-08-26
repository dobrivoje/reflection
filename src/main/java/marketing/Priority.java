package marketing;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode
public class Priority {

    @EqualsAndHashCode.Exclude
    private static int counter = 0;

    @EqualsAndHashCode.Include
    private final int id = counter++;

    /**
     * kardinalnost skupa,<br>
     * po kom grupišemo prioritete.
     */
    @EqualsAndHashCode.Include
    private Integer cardinality;

    @EqualsAndHashCode.Include
    private Integer priority;
}
