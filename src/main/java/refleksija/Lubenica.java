package refleksija;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author д06ри, dobri7@gmail.com
 */
@Builder
@Data
@EqualsAndHashCode
public class Lubenica {

    @EqualsAndHashCode.Include
    private int id;

    @EqualsAndHashCode.Include
    private String sorta;

    @Property(id = 0)
    @EqualsAndHashCode.Exclude
    private Boolean ukusna;

    @Property(id = 1)
    @EqualsAndHashCode.Exclude
    private Boolean velika;

    @Property(id = 2)
    @EqualsAndHashCode.Exclude
    private Boolean imaRepic;

    @Property(id = 3)
    @EqualsAndHashCode.Exclude
    private Boolean naPijaci;

    @Property(id = 4)
    @EqualsAndHashCode.Exclude
    private Boolean uMarketu;

    @Property(id = 5, include = false)
    @EqualsAndHashCode.Exclude
    private Boolean ketering;
}
