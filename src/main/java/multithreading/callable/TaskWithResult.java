package multithreading.callable;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author д06ри, dobri7@gmail.com
 */
public class TaskWithResult implements Callable<String> {

    private final long threadId;
    private final String thName;
    private int trajanje;

    public TaskWithResult(long tid) {
        threadId = tid;
        thName = "Thread #[" + threadId + "]";
        trajanje = 10_000;
    }

    @Override
    public String call() {
        System.err.println(thName + " is beginning ...");

        int kolikoJeTrajao = new Random().nextInt(trajanje);
        try {
            TimeUnit.MILLISECONDS.sleep(kolikoJeTrajao);
        } catch (InterruptedException e) {
        }

        System.err.println(thName + " - DONE");
        return thName + " - trajao : " + kolikoJeTrajao;
    }

    @Override
    public String toString() {
        return threadId + " - " + thName;
    }
}
