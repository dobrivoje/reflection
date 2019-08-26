package refleksija;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Data
@EqualsAndHashCode
public class Priority {

    @EqualsAndHashCode.Exclude
    private static int counter = 0;

    @EqualsAndHashCode.Include
    private final int id = counter++;

    /**
     * ovo je "cardinality" kombinacije, odn. kardinalnost skupa,<br>
     * da bi smo mogli da pratimo prioritete po kardinalnosti.
     */
    @EqualsAndHashCode.Include
    private Integer cardinality;

    @EqualsAndHashCode.Include
    private Integer priority;

    public static void main(String[] args) {
        System.err.println("EMPTY_PRIORITY : " + ValidatorFacility.EMPTY_PRIORITY);

        List<Priority> lp = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Priority p = Priority.builder()
                .cardinality(Objects.hashCode(String.valueOf("17-" + i + "&.!.&-17")))
                .build();

            lp.add(p);
            System.err.println(p);
        }
    }
}
