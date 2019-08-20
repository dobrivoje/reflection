package multithreading.threadexceptions;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author д06ри, dobri7@gmail.com
 */
public class Demo {

    public static void main(String[] args) {
        ExecutorService E = Executors.newCachedThreadPool(new DobriThreadFactory());

        List<Runnable> runList = Arrays.asList(
            new Task2(12), new Task2(7), new Task2(33), new Task2(7), new Task2(1), new Task2(19)
        );
        for (Runnable r : runList) E.execute(r);

        E.shutdown();
    }
}
