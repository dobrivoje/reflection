package multithreading.threadexceptions;

import java.util.Arrays;
import java.util.concurrent.ThreadFactory;

/**
 * @author д06ри, dobri7@gmail.com
 */
public class DobriThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(final Runnable task) {
        Thread newThread = new Thread(task);
        newThread.setUncaughtExceptionHandler((thread, ex) -> {
            System.err.println("Thread [" + thread + "], uhvaćen exception : [" + ex + "]");
            if (ex instanceof DobriException)
                System.err.println("Thread [" + thread + "], parametri : " + Arrays.toString(((DobriException) ex).params));
        });

        return newThread;
    }
}
