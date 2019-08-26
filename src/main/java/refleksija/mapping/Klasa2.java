package refleksija.mapping;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import multithreading.callable.TaskWithResult;

import java.util.List;

/**
 * @author д06ри, dobri7@gmail.com
 */
@Data
@Builder
@EqualsAndHashCode
public class Klasa2 {

    private int idK;

    @EqualsAndHashCode.Exclude
    private String naziv;

    @EqualsAndHashCode.Exclude
    private int kolicina;

    @EqualsAndHashCode.Exclude
    private int vrednost;

    private List<String> listaSvojstava;
    private List<TaskWithResult> LK1;
}
