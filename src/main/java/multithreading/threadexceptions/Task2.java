package multithreading.threadexceptions;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author д06ри, dobri7@gmail.com
 */
public class Task2 implements Runnable {

    private final long threadId;
    private final int maxTrajanje = 300;
    private final String thName;
    private final int trajanje;

    public Task2(long tid) {
        threadId = tid;
        thName = "Thread #[" + threadId + "]";
        trajanje = new Random().nextInt(maxTrajanje);

        try {
            TimeUnit.MILLISECONDS.sleep(trajanje);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void run() {
        boolean slucajno = new Random().nextBoolean();
        if (slucajno) throw new DobriException(getParameters());
    }

    private Object[] getParameters() {
        return new Object[]{threadId, thName, trajanje};
    }
}
