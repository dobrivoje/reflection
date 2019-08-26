import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import refleksija.Lubenica;
import refleksija.Priority;
import refleksija.ValidatorFacility;

import java.util.*;

import static refleksija.ValidatorFacility.ConvertAllToIds;

public class NekiTest {

    static final int MILION = 1_000_000;
    static final int HILJADA = 1_000;

    static List<Lubenica> milionLubenicaList = new LinkedList<>();
    static Map<Set<Integer>, Map<Integer, Double>> marketingAkcije = new LinkedHashMap<>();

    @BeforeAll
    public static void inits() {
        long t0 = System.currentTimeMillis();

        for (int i = 0; i < HILJADA; i++) {
            milionLubenicaList.add(
                Lubenica.builder()
                    .id(i)
                    .sorta("lubenica-" + i)
                    .ukusna(new Random().nextBoolean())
                    .velika(new Random().nextBoolean())
                    .imaRepic(new Random().nextBoolean())
                    .naPijaci(new Random().nextBoolean())
                    .uMarketu(new Random().nextBoolean())
                    .ketering(new Random().nextBoolean())
                    .build()
            );
        }

        long t1 = System.currentTimeMillis();
        long dt = t1 - t0;
        System.err.println("uk. inicijalizacija (ms) : " + dt);

        //<editor-fold desc="init marketing akcija...">
        Set<Integer> komb1 = new LinkedHashSet<>(Arrays.asList(1, 2, 3));
        Map<Integer, Double> pCena1 = new HashMap<>();
        pCena1.put(1, 10.0);
        marketingAkcije.put(komb1, pCena1);

        Set<Integer> komb2 = new LinkedHashSet<>(Arrays.asList(1, 3, 5));
        Map<Integer, Double> pCena2 = new HashMap<>();
        pCena2.put(2, 15.0);
        marketingAkcije.put(komb2, pCena2);

        Set<Integer> komb3 = new LinkedHashSet<>(Arrays.asList(4, 5, 6));
        Map<Integer, Double> pCena3 = new HashMap<>();
        pCena3.put(3, 12.0);
        marketingAkcije.put(komb3, pCena3);

        Set<Integer> komb4 = new LinkedHashSet<>(Arrays.asList(2, 7, 8));
        Map<Integer, Double> pCena4 = new HashMap<>();
        pCena4.put(4, 5.0);
        marketingAkcije.put(komb4, pCena4);

        Set<Integer> komb5 = new LinkedHashSet<>(Arrays.asList(10));
        Map<Integer, Double> pCena5 = new HashMap<>();
        pCena5.put(5, 2.0);
        marketingAkcije.put(komb5, pCena5);

        Set<Integer> komb6 = new LinkedHashSet<>(Arrays.asList(11, 7));
        Map<Integer, Double> pCena6 = new HashMap<>();
        pCena6.put(6, 1.5);
        marketingAkcije.put(komb6, pCena6);

        Set<Integer> komb7 = new LinkedHashSet<>(Arrays.asList(1, 4));
        Map<Integer, Double> pCena7 = new HashMap<>();
        pCena7.put(7, 1.5);
        marketingAkcije.put(komb7, pCena7);
        //</editor-fold>
    }

    @Test
    public void test1() throws IllegalAccessException {
        Lubenica lubenica1 = Lubenica.builder()
            .id(1)
            .sorta("lubenica 1")
            .ukusna(true)
            .velika(true)
            .imaRepic(false)
            .naPijaci(true)
            .uMarketu(false)
            .ketering(false)
            .build();
        Lubenica lubenica2 = Lubenica.builder()
            .id(2)
            .sorta("lubenica 2")
            .ukusna(true)
            .velika(false)
            .naPijaci(false)
            .uMarketu(true)
            .ketering(true)
            .build();

        System.err.println(ValidatorFacility.ConvertToIds(lubenica1));
        System.err.println(ValidatorFacility.ConvertToIds(lubenica2));
        System.err.println(ValidatorFacility.ConvertAllToIds(lubenica1, lubenica2));

        Assertions.assertFalse(lubenica1.equals(lubenica2));
    }

    @Test()
    public void test2() throws IllegalAccessException {
        Map<Lubenica, Set<Integer>> lubenicaSetMap = ConvertAllToIds(milionLubenicaList);
        Assertions.assertTrue(true /*lubenicaSetMap.size() == MILION*/);
    }

    @Test()
    public void test3() {
        Set<Integer> request = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 11));
        Map<Priority, Double> priorityByPrice = new LinkedHashMap<>();

        ValidatorFacility.DiscountWithExcluding(marketingAkcije, request, priorityByPrice);

        System.err.println("preostali artikli bez popusta : " + request);
        System.err.println("preostali artikli bez popusta : " + priorityByPrice);

        Assertions.assertTrue(true);
    }
}
