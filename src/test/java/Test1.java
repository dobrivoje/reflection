import marketing.Lubenica;
import marketing.Priority;
import marketing.ValidatorFacility;
import marketing.ValidatorFacility.PriorityPrice;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.paukov.combinatorics3.Generator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static marketing.ValidatorFacility.ConvertAllToIds;
import static org.junit.Assert.*;

/**
 * @author д06ри, dobri7@gmail.com
 */
public class Test1 extends MyTestInfra {

    //<editor-fold desc="init values">
    static final int MILION = 1_000_000;
    static final int HILJADA = 1_000;

    static List<Lubenica> milionLubenicaList = new LinkedList<>();
    static Map<List<Integer>, Map<Integer, Double>> marketingActions = new HashMap<>();
    //</editor-fold>

    @Before
    @Ignore
    public void inits() {
        Long initTime = new MillisecondsMeasurement()
            .submit(() -> {
                for (int i = 0; i < MILION; i++)
                    milionLubenicaList.add(napraviLubenicu(i, "lubenica #" + i, nextRandomBoolean()));
            }).getDelta();

        System.err.println("uk. inicijalizacija milion lubenica u 1 thread-u u (ms) : " + initTime);

        //<editor-fold desc="init mark. akcija">
        List<Integer> komb1 = new LinkedList<>(Arrays.asList(1, 2, 3));
        Map<Integer, Double> pCena1 = new HashMap<>();
        pCena1.put(1, 10.0);
        marketingActions.put(komb1, pCena1);

        List<Integer> komb2 = new LinkedList<>(Arrays.asList(1, 3, 5));
        Map<Integer, Double> pCena2 = new HashMap<>();
        pCena2.put(2, 15.0);
        marketingActions.put(komb2, pCena2);

        List<Integer> komb3 = new LinkedList<>(Arrays.asList(4, 5, 6));
        Map<Integer, Double> pCena3 = new HashMap<>();
        pCena3.put(3, 12.0);
        marketingActions.put(komb3, pCena3);

        List<Integer> komb4 = new LinkedList<>(Arrays.asList(2, 7, 8));
        Map<Integer, Double> pCena4 = new HashMap<>();
        pCena4.put(4, 5.0);
        marketingActions.put(komb4, pCena4);

        List<Integer> komb5 = new LinkedList<>(Arrays.asList(10));
        Map<Integer, Double> pCena5 = new HashMap<>();
        pCena5.put(5, 2.0);
        marketingActions.put(komb5, pCena5);

        List<Integer> komb6 = new LinkedList<>(Arrays.asList(1, 2));
        Map<Integer, Double> pCena6 = new HashMap<>();
        pCena6.put(6, 1.5);
        marketingActions.put(komb6, pCena6);

        List<Integer> komb7 = new LinkedList<>(Arrays.asList(1, 3));
        Map<Integer, Double> pCena7 = new HashMap<>();
        pCena7.put(7, 1.1);
        marketingActions.put(komb7, pCena7);

        List<Integer> komb8 = new LinkedList<>(Arrays.asList(1, 3));
        Map<Integer, Double> pCena8 = new HashMap<>();
        pCena8.put(8, 1.15);
        marketingActions.put(komb8, pCena8);
        //</editor-fold>
    }

    @Test
    @Ignore
    public void test9_rnd_interval() {
        int low = -3, up = 3;

        for (int i = 0; i < 20; i++) {
            int rnd = nextRandomInt(low, up);
            System.err.println("rnd : " + rnd);

            assertTrue(rnd >= low && rnd <= up);
        }
    }

    private Lubenica napraviLubenicu(int i, String s, boolean yesNo) {
        return Lubenica.builder()
            .id(i)
            .sorta(s)
            .ukusna(yesNo)
            .velika(yesNo)
            .imaRepic(yesNo)
            .naPijaci(yesNo)
            .uMarketu(yesNo)
            .ketering(yesNo)
            .build();
    }

    @Test
    @Ignore
    public void test_Prioriteti() {
        System.err.println("EMPTY_PRIORITY : " + ValidatorFacility.EMPTY_PRIORITY);

        List<Priority> priorityList = Stream.generate(() ->
            Priority.builder()
                .cardinality(nextRandomInt(10, false))
                .priority(nextRandomInt(10, false))
                .build())
            .limit(10)
            .collect(Collectors.toList());

        priorityList.forEach(System.out::println);

        assertTrue(priorityList.size() == 10);
    }

    @Test
    @Ignore
    public void test1() throws IllegalAccessException {
        Lubenica lubenica1 = Lubenica.builder()
            .id(1)
            .sorta("lubenica-1")
            .ukusna(true)
            .velika(true)
            .naPijaci(true)
            .uMarketu(true)
            .ketering(true)
            .build();

        Lubenica lubenica2 = Lubenica.builder()
            .id(2)
            .sorta("lubenica-2")
            .ukusna(true)
            .velika(false)
            .naPijaci(false)
            .uMarketu(true)
            .ketering(true)
            .build();

        System.err.println(ValidatorFacility.ConvertToIds(lubenica1));
        System.err.println(ValidatorFacility.ConvertToIds(lubenica2));
        System.err.println(ConvertAllToIds(lubenica1, lubenica2));

        assertFalse(lubenica1.equals(lubenica2));
    }

    @Test
    @Ignore
    public void test2() throws IllegalAccessException {
        Map<Lubenica, Set<Integer>> lubenicaSetMap = ConvertAllToIds(milionLubenicaList);
        assertTrue(true /*lubenicaSetMap.size() == MILION*/);
    }

    @Test
    @Ignore
    public void test3() {
        Set<Integer> request = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 11));
        Map<Priority, Double> priorityByPrice = new LinkedHashMap<>();

        // ValidatorFacility.DiscountWithExcluding(marketingActions, request, priorityByPrice);

        System.err.println("preostali artikli bez popusta : " + request);
        System.err.println("preostali artikli bez popusta : " + priorityByPrice);

        assertTrue(true);
    }

    @Test
    @Ignore
    public void test4_kombinacijebezPonavljanja() {
        Set<String> slova = new HashSet<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        Set<List<String>> kombinacijeSlova = new LinkedHashSet<>();

        Generator.combination(slova)
            .simple(3)
            .stream()
            .forEach(kombinacija -> kombinacijeSlova.add(kombinacija));

        assertTrue(true);
    }

    private MeasureTime.Measurement izmeriPerformanseKodKreiranjaLubenica(int brojLubenica) {
        return new MillisecondsMeasurement()
            .submit(() ->
                Stream.generate(() ->
                    Lubenica.builder()
                        .id(nextRandomInt(false))
                        .sorta("sorta-" + nextRandomBoolean())
                        .ukusna(nextRandomBoolean())
                        .velika(nextRandomBoolean())
                        .imaRepic(nextRandomBoolean())
                        .naPijaci(nextRandomBoolean())
                        .uMarketu(nextRandomBoolean())
                        .ketering(nextRandomBoolean())
                        .build()
                ).limit(brojLubenica)
            );
    }

    private long podskupoviZaBrojElemenata(int brojElemenataSkupa) {
        return new MillisecondsMeasurement().submit(() -> {
                int dvaNaEn = (int) Math.pow(2, brojElemenataSkupa - 1);

                List<Integer> numbersPart1 = IntStream.range(1, brojElemenataSkupa)
                    .boxed().collect(Collectors.toList());

                List<List<Integer>> sviPodskupovi =
                    Generator
                        .subset(numbersPart1)
                        .simple()
                        .stream().collect(Collectors.toList());

                System.err.println("ukupno podskupova 2^n : " + dvaNaEn);
                System.err.println("ukupno podskupova     : " + sviPodskupovi.size());

                assertEquals(sviPodskupovi.size(), dvaNaEn);
            }
        ).getDelta();
    }

    @Test
    @Ignore
    public void test6_sviPodskupoviMnogoElemenata() {
        podskupoviZaBrojElemenata(24);
    }

    @Test
    @Ignore
    public void test6_performansePodskupovaMnogoElemenata() {
        for (int i = 1; i <= 25; i++) {
            long delta = podskupoviZaBrojElemenata(i);
            System.err.println(i + ". elemenata. ukupno vreme (ms)     : " + delta);
            System.err.println("____________________________________");
            System.err.println();
        }
    }

    @Test
    @Ignore
    public void test7_performanse_1000_Lubenica() {
        MeasureTime.Measurement m = izmeriPerformanseKodKreiranjaLubenica(HILJADA);
        System.err.println("trajanje operacije : " + m.getDelta() + ", rezultat : " + m.getResult());
    }

    @Test
    @Ignore
    public void test8_performanse_1000_000_Lubenica() {
        MeasureTime.Measurement m = izmeriPerformanseKodKreiranjaLubenica(MILION);
        System.err.println("trajanje operacije : " + m + ", rezultat : " + m.getResult());
    }

    @Test
    @Ignore
    public void test9_DiscountsExist() {
        List<Integer> korisnikIzbor = Arrays.asList(1, 2, 3, 5, 6, 7, 8, 10, 12);
        List<List<Integer>> allPossibleCombinations =
            Generator.subset(korisnikIzbor).simple().stream().collect(Collectors.toList());

        List<Map<Integer, Double>> result = new LinkedList<>();
        for (List<Integer> comb : allPossibleCombinations) {
            Map<Integer, Double> prioPriceMap = marketingActions.get(comb);
            if (prioPriceMap != null && !prioPriceMap.isEmpty())
                result.add(prioPriceMap);
        }

        Collections.sort(result, Comparator.comparingInt(m -> m.entrySet().iterator().next().getKey()));
        result.forEach(System.out::println);

        assertTrue(!result.isEmpty());
    }

    @Test
    @Ignore
    public void test10_() {
        List<Integer> userChoice = Arrays.asList(1, 2, 3, 5, 6, 7, 8, 10, 12);
        List<PriorityPrice> result = ValidatorFacility.DiscountForAll(marketingActions, userChoice);

        for (PriorityPrice pp : result) {
            System.err.println("cardinality:" + pp.priority.getCardinality()
                + ", priority:" + pp.priority.getPriority() + " -> price=" + pp.price);
        }

        assertTrue(true);
    }
}
