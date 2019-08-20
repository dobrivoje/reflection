package multithreading.callable;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author д06ри, dobri7@gmail.com
 */
public class CallableDemo {

    public static void main(String[] args) {
        int thNo = 10;

        List<Future<String>> resList = new LinkedList<>();
        ExecutorService E = Executors.newFixedThreadPool(thNo);

        for (int i = 0; i < thNo; i++)
            resList.add(E.submit(new TaskWithResult(i)));

        for (Future<String> res : resList) {
            try {
                System.err.println(res.get());
            } catch (Exception e) {
                System.err.println("greška : " + e);
            }
            finally {
                E.shutdown();
            }
        }

        E.shutdownNow();
    }
}
