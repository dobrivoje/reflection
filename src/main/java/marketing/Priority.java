package marketing;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode
public class Priority {

    @EqualsAndHashCode.Exclude
    public static int counter = 0;

    @EqualsAndHashCode.Include
    public final int id = counter++;

    @EqualsAndHashCode.Include
    public int cardinality;

    @EqualsAndHashCode.Include
    public int priority;
}
