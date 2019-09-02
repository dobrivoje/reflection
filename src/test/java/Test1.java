import marketing.Lubenica;
import marketing.Priority;
import marketing.PriorityDiscount;
import marketing.ValidatorFacility;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.paukov.combinatorics3.Generator;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static marketing.ValidatorFacility.ConvertAllToIds;
import static marketing.ValidatorFacility.DISCOUNT_TYPE.*;
import static org.junit.Assert.*;

/**
 * @author д06ри, dobri7@gmail.com
 */
public class Test1 extends MyTestInfra {

    //<editor-fold desc="init values">
    static final int MILION = 1_000_000;
    static final int HILJADA = 1_000;

    static List<Lubenica> milionLubenicaList = new LinkedList<>();
    static Map<List<Integer>, PriorityDiscount> marketingActions = new HashMap<>();
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
        List<Integer> komb1 = Arrays.asList(1, 2, 3);
        PriorityDiscount pCena1 = PriorityDiscount.builder().discount(10).discountType(PERCENT).priority(
            Priority.builder().priority(1).build()
        ).build();
        marketingActions.put(komb1, pCena1);

        List<Integer> komb2 = Arrays.asList(1, 3, 5);
        PriorityDiscount pCena2 = PriorityDiscount.builder().discount(15).discountType(ABSOLUTE).priority(
            Priority.builder().priority(2).build()
        ).build();
        marketingActions.put(komb2, pCena2);

        List<Integer> komb3 = Arrays.asList(4, 5, 6);
        PriorityDiscount pCena3 = PriorityDiscount.builder().discount(12).discountType(ABSOLUTE).priority(
            Priority.builder().priority(3).build()
        ).build();
        marketingActions.put(komb3, pCena3);

        List<Integer> komb4 = Arrays.asList(2, 7, 8);
        PriorityDiscount pCena4 = PriorityDiscount.builder().discount(5).discountType(DIRECT).priority(
            Priority.builder().priority(4).build()
        ).build();
        marketingActions.put(komb4, pCena4);

        List<Integer> komb5 = Arrays.asList(10);
        PriorityDiscount pCena5 = PriorityDiscount.builder().discount(2).discountType(DIRECT).priority(
            Priority.builder().priority(5).build()
        ).build();
        marketingActions.put(komb5, pCena5);

        List<Integer> komb6 = Arrays.asList(1, 2);
        PriorityDiscount pCena6 = PriorityDiscount.builder().discount(1.5).discountType(DIRECT).priority(
            Priority.builder().priority(6).build()
        ).build();
        marketingActions.put(komb6, pCena6);

        List<Integer> komb7 = Arrays.asList(1, 3);
        PriorityDiscount pCena7 = PriorityDiscount.builder().discount(1.1).priority(
            Priority.builder().priority(7).build()
        ).build();
        marketingActions.put(komb7, pCena7);

        List<Integer> komb8 = Arrays.asList(1, 3);
        PriorityDiscount pCena8 = PriorityDiscount.builder().discount(1.15).priority(
            Priority.builder().priority(8).build()
        ).build();
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
        List<Integer> request = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 11);
        Map<Priority, Double> priorityByPrice = new LinkedHashMap<>();

        // ValidatorFacility.DiscountWithExcluding(marketingActions, request, priorityByPrice);

        System.err.println("preostali artikli bez popusta : " + request);
        System.err.println("preostali artikli bez popusta : " + priorityByPrice);

        assertTrue(true);
    }

    @Test
    @Ignore
    public void test4_kombinacijebezPonavljanja() {
        List<String> slova = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");
        List<List<String>> kombinacijeSlova = new ArrayList<>();

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
    public void test91_DiscountsExist() {
        List<Integer> izborKorisnika = Arrays.asList(1, 2, 3, 5, 6, 7, 8, 10, 12);

        List<PriorityDiscount> result = ValidatorFacility.DiscountForAllIds(marketingActions, izborKorisnika);
        Collections.sort(result
            , Comparator.comparing((PriorityDiscount pd) -> pd.getPriority().cardinality)/*.reversed()*/
                .thenComparing(
                    Comparator.comparing((PriorityDiscount pd) -> pd.getDiscountType().ordinal()).reversed()
                )
        );
        result.forEach(System.out::println);

        assertTrue(!result.isEmpty());
    }

    @Test
    @Ignore
    public void test10_() {
        List<Integer> userChoice = Arrays.asList(1, 2, 3, 5, 6, 7, 8, 10, 12);
        List<PriorityDiscount> result = ValidatorFacility.DiscountForAllIds(marketingActions, userChoice);

        Collections.sort(result, (p1, p2) -> p2.getPriority().priority - p1.getPriority().priority);
        result.forEach(System.out::println);

        assertTrue(true);
    }

    @Test
    @Ignore
    public void test11_() {
        //<editor-fold desc="Izbori korisnika init">
        List<Lubenica> izborKorisnika1 = Arrays.asList(
            Lubenica.builder().id(1).ketering(true).sorta("ls-1").build()
            , Lubenica.builder().id(7).ketering(false).sorta("ls-7").build()
        );

        List<Lubenica> izborKorisnika2 = Arrays.asList(
            Lubenica.builder().id(1).ketering(true).sorta("ls-1").build()
            , Lubenica.builder().id(2).ketering(false).sorta("ls-2").build()
            , Lubenica.builder().id(7).ketering(false).sorta("ls-7").build()
        );

        List<Lubenica> izborKorisnika3 = Arrays.asList(
            Lubenica.builder().id(1).ketering(nextRandomBoolean()).sorta("sorta-1").build()
            , Lubenica.builder().id(2).ketering(nextRandomBoolean()).sorta("sorta-2").build()
            , Lubenica.builder().id(3).ketering(nextRandomBoolean()).sorta("sorta-3").build()
            , Lubenica.builder().id(4).ketering(nextRandomBoolean()).sorta("sorta-4").build()
            , Lubenica.builder().id(5).ketering(nextRandomBoolean()).sorta("sorta-5").build()
        );
        //</editor-fold>

        List<List<Lubenica>> izbori = new ArrayList<>();
        izbori.addAll(Arrays.asList(izborKorisnika1, izborKorisnika2, izborKorisnika3));

        //<editor-fold desc="marketingAkcijeLubenice init">
        Map<List<Lubenica>, PriorityDiscount> marketingAkcijeLubenice = new HashMap<>();
        marketingAkcijeLubenice.put(
            Arrays.asList(
                Lubenica.builder().id(1).ketering(nextRandomBoolean()).sorta("sorta-1").build()
                , Lubenica.builder().id(2).ketering(nextRandomBoolean()).sorta("sorta-2").build()
                , Lubenica.builder().id(3).ketering(nextRandomBoolean()).sorta("sorta-7").build()
            )
            , PriorityDiscount.builder().discount(8).discountType(PERCENT)
                .priority(Priority.builder().priority(5).build()).build()
        );
        marketingAkcijeLubenice.put(
            Arrays.asList(
                Lubenica.builder().id(1).ketering(nextRandomBoolean()).sorta("sorta-1").build()
                , Lubenica.builder().id(2).ketering(nextRandomBoolean()).sorta("sorta-2").build()
                , Lubenica.builder().id(3).ketering(nextRandomBoolean()).sorta("sorta-3").build()
                , Lubenica.builder().id(4).ketering(nextRandomBoolean()).sorta("sorta-4").build()
                , Lubenica.builder().id(5).ketering(nextRandomBoolean()).sorta("sorta-5").build()
            )
            , PriorityDiscount.builder().discount(20).discountType(PERCENT)
                .priority(Priority.builder().priority(1).build())
                .build()
        );
        marketingAkcijeLubenice.put(
            Arrays.asList(
                Lubenica.builder().id(1).ketering(nextRandomBoolean()).sorta("sorta-1").build()
                , Lubenica.builder().id(7).ketering(nextRandomBoolean()).sorta("sorta-7").build()
            )
            , PriorityDiscount.builder().discount(5).discountType(ABSOLUTE)
                .priority(Priority.builder().priority(4).build()).build()
        );
        marketingAkcijeLubenice.put(
            Arrays.asList(
                Lubenica.builder().id(3).ketering(nextRandomBoolean()).sorta("sorta-1").build()
                , Lubenica.builder().id(5).ketering(nextRandomBoolean()).sorta("sorta-7").build()
            )
            , PriorityDiscount.builder().discount(5).discountType(DIRECT)
                .priority(Priority.builder().priority(3).build()).build()
        );
        //</editor-fold>

        List<PriorityDiscount> result = ValidatorFacility.DiscountForAll(marketingAkcijeLubenice, izborKorisnika1);
        Collections.sort(result, (p1, p2) -> (int) (p2.getPriority().priority - p1.getPriority().priority));

        System.err.println("*****************************************************************");
//        result.forEach(System.out::println);

        System.err.println();

        for (List<Lubenica> pojednacniIzbor : izbori) {
            List<PriorityDiscount> pojPrioDisc = ValidatorFacility.DiscountForAll(marketingAkcijeLubenice, pojednacniIzbor);
            Collections.sort(pojPrioDisc, (p1, p2) -> (int) (p2.getPriority().priority - p1.getPriority().priority));

            System.err.println("----------------------------------------------");
            System.err.println(pojednacniIzbor);
            System.err.println("----------------------------------------------");
            pojPrioDisc.forEach(System.out::println);
        }

        assertTrue(true);
    }
}
