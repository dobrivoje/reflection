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
public class Klasa1 {

    private int id;

    @EqualsAndHashCode.Exclude
    private String naziv;

    @EqualsAndHashCode.Exclude
    private int kol;

    @EqualsAndHashCode.Exclude
    private int vred;

    private List<String> list;

    private List<TaskWithResult> taskWithResultList;
}
