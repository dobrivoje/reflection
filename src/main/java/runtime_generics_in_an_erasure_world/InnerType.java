package runtime_generics_in_an_erasure_world;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

/**
 * @author д06ри, dobri7@gmail.com
 */
public class InnerType {

    public static class Internal<T> {

    }

    public static void main(String[] args) {
        InnerType.Internal<String> internal = new Internal<>();
        Class<?> classType = internal.getClass();
        System.out.println(classType + ", " + classType.getGenericSuperclass());

        ParameterizedType t =
            (ParameterizedType) classType.getGenericSuperclass();
        System.out.println(t.getOwnerType() + ", " + t.getRawType() + ", " +
            Arrays.toString(t.getActualTypeArguments()));
    }
}
