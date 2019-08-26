import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyTestInfra extends Stopwatch {

    //<editor-fold desc="Random facility">
    protected final Random random = new Random();

    protected boolean nextRandomBoolean() {
        return random.nextBoolean();
    }

    protected int nextRandomInt(int limit, boolean withZero) {
        return (withZero ? 0 : 1) + random.nextInt(limit);
    }

    protected int nextRandomInt(boolean withZero) {
        return (withZero ? 0 : 1) + random.nextInt();
    }
    //</editor-fold>

    //<editor-fold desc="stop watch">
    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        System.out.println(String.format("Test %s %s, spent %d microseconds",
            testName, status, TimeUnit.NANOSECONDS.toMicros(nanos)));
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "finished", nanos);
    }
    //</editor-fold>

    //<editor-fold desc="MeasureTime">
    public interface MeasureTime {

        //<editor-fold desc="Measurement tuple">
        @Data
        @AllArgsConstructor
        class Measurement<T> {

            // delta time
            Long delta;

            // sumbit result
            T result;
        }
        //</editor-fold>

        Measurement submit(@NonNull Runnable task);
    }

    public class MillisecondsMeasurement implements MeasureTime {

        @Override
        public Measurement submit(Runnable task) {
            long t0 = System.currentTimeMillis();

            task.run();

            long t1 = System.currentTimeMillis();
            return new Measurement(t1 - t0, true /* 2. parametar  = rezultat  */);
        }
    }
    //</editor-fold>
}
