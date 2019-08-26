package refleksija.mapping;

import multithreading.callable.TaskWithResult;

import java.util.Arrays;

/**
 * @author д06ри, dobri7@gmail.com
 */
public class TesterMapiranje {

    public static void main(String[] args) {
        Klasa1 k1 = Klasa1.builder()
            .id(1)
            .kol(11)
            .naziv("k1 naziv")
            .vred(111)
            .list(Arrays.asList("a", "b", "c"))
            .taskWithResultList(Arrays.asList(new TaskWithResult(1), new TaskWithResult(22)))
            .build();

        Klasa2 k2Preslikan = K1K2Maper.INSTANCE.mapiraj(k1);

        System.err.println("k1 : " + k1);
        System.err.println("k2 preslikani : " + k2Preslikan);
    }
}
