package multithreading.threadexceptions;

/**
 * @author д06ри, dobri7@gmail.com
 */
public class DobriException extends RuntimeException {

    final Object[] params;

    public DobriException(Object[] params) {
        super("Dobri - exception");
        this.params = params;
    }
}
