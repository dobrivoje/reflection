package refleksija;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author д06ри, dobri7@gmail.com
 */
@Builder
@Data
@EqualsAndHashCode
public class Lubenica {

    private int id;
    private String sorta;

    @Svojstva(id = 0, yes = true)
    private Boolean ukusna;
    @Svojstva(id = 1, yes = true)
    private Boolean velika;
    @Svojstva(id = 2, yes = false)
    private Boolean imaRepic;
    @Svojstva(id = 3, yes = false)
    private Boolean naPijaci;
    @Svojstva(id = 4, yes = false)
    private Boolean uMarketu;
    @Svojstva(id = 5, yes = false)
    private Boolean ketering;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Lubenica l1 = Lubenica.builder()
            .id(1).sorta("lubenica 1").ukusna(true).velika(true).imaRepic(false)
            .naPijaci(true).uMarketu(false).ketering(false)
            .build();
        Lubenica l2 = Lubenica.builder()
            .id(2).sorta("lubenica 2").ukusna(true).velika(false)
            .naPijaci(false).uMarketu(true).ketering(true)
            .build();


        System.err.println(print2(l1));

        System.err.println();
        System.err.println("-----------------------------------------------");
        System.err.println();

        System.err.println(print2(l2));
    }

    /**
     * Vrati skup indeksa sa deklarisnih polja klase Lubenica
     * ako postoji anotracija Svojstva nad tim poljem
     *
     * @param lubenica
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Set<Integer> print2(Lubenica lubenica) throws NoSuchFieldException, IllegalAccessException {
        Set<Integer> res = new LinkedHashSet<>();

        for (Field f : lubenica.getClass().getDeclaredFields()) {
            if (f.getType().equals(Boolean.class)) {
                String fName = f.getName();
                Field declaredFieldFromActualValue = lubenica.getClass().getDeclaredField(fName);

                if (declaredFieldFromActualValue != null) {
                    Object av = declaredFieldFromActualValue.get(lubenica);
                    if (av != null) {
                        boolean actualValue = (boolean) av;

                        if (actualValue && f.isAnnotationPresent(Svojstva.class)) {
                            System.err.println("Lubenica [" + lubenica.getId() + "] : " + fName + " -> " + actualValue);

                            Svojstva declaredAnnotation = f.getDeclaredAnnotation(Svojstva.class);
                            res.add(declaredAnnotation.id());
                        }
                    }
                }
            }
        }

        return res;
    }
}
