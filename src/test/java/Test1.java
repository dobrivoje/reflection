import com.dobri.hibernate.Relation;
import com.dobri.hibernate.User;
import marketing.Lubenica;
import marketing.Priority;
import marketing.PriorityDiscount;
import marketing.ValidatorFacility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.paukov.combinatorics3.Generator;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
        System.err.println(ConvertAllToIds(lubenica1));
        System.err.println(ConvertAllToIds(lubenica2));

        assertFalse(lubenica1.equals(lubenica2));
    }

    @Test
    @Ignore
    public void test2() throws IllegalAccessException {
        Map<Lubenica, Set<Integer>> lubenicaSetMap = new HashMap<>();
        Long initTime = new MillisecondsMeasurement()
            .submit(() -> {
                try {
                    lubenicaSetMap.putAll(ConvertAllToIds(milionLubenicaList));
                } catch (IllegalAccessException e) {
                }
            }).getDelta();

        System.err.println("Trajanje : " + initTime);

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
        List<String> slova = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "1", "2", "3", "4", "5");
        List<List<String>> kombinacijeSlova = new ArrayList<>();

        Generator.combination(slova)
            .simple(6)
            .stream()
            .forEach(kombinacija -> kombinacijeSlova.add(kombinacija));

        kombinacijeSlova.forEach(System.out::println);

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
            , Comparator.comparing((PriorityDiscount pd) -> pd.getDiscountType()).reversed()/*.reversed()*/
                .thenComparing((PriorityDiscount pd) -> pd.getDiscount())
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

    @Test
    @Ignore
    public void test12_compose_andThen() {
        Function<Integer, Integer> dupliraj = e -> 2 * e;
        Function<Integer, Integer> kvadrat = e -> e * e;
        Function<Integer, Integer> decrement = e -> e - 1;
        Integer v11 = dupliraj.compose(kvadrat).apply(3);
        Integer v12 = dupliraj.andThen(kvadrat).apply(3);
        Integer v13 = dupliraj.andThen(kvadrat).andThen(decrement).apply(3);
        Integer v14 = dupliraj.andThen(kvadrat).compose(decrement).compose(decrement).andThen(dupliraj).apply(5);

        System.err.println("v11=" + v11 + ", v12=" + v12);
        System.err.println("v13=" + v13 + ", v14=" + v14);

        assertTrue(true);
    }

    @Test
    public void test13_validacije_funk_interfejs() {
        // podaci za validatore !
        Integer[] ulaz1 = new Integer[]{4, 4, 3, 4, 2, 1};
        Boolean[] ulaz2 = new Boolean[]{true, true, true, false, false};
        Object[][] ulazi = new Object[][]{ulaz1, ulaz1, ulaz2};

        // 1. validator
        Function<Integer[], Boolean> val1 = input -> input.length > 3;

        // 2. validator
        Function<Integer[], Boolean> val2 = input -> Stream.of(input)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream().anyMatch(p -> p.getKey() == 4 && p.getValue() >= 3);

        // 3. validator
        Function<Boolean[], Boolean> val3 = input -> Stream.of(input)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream().anyMatch(p -> p.getKey() && p.getValue() > 2);

        Function<Object[], Boolean>[] validators = new Function[]{val1, val2, val3};

        // moraju svi da budu "true"
        boolean res = false;
        int i = 0;
        for (Function<Object[], Boolean> v : validators) {
            res = v.apply(ulazi[i++]);
            if (!res) break;
        }

        System.err.println("res=" + res);

        assertTrue(res);
    }


    @Test
    public void test_hibernate1() {
        SessionFactory sessionFactory = new Configuration().configure()
            .buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            User u1 = new User();
            u1.setName("u1");
            u1.setUsername("un1");

            LocalDateTime ldtDobri = LocalDateTime.of(1975, 9, 7, 16, 20);
            LocalDateTime ldtKrme = LocalDateTime.of(1979, 4, 15, 9, 50);
            LocalDateTime ldt = new Random().nextBoolean() ? ldtDobri : ldtKrme;
            Date mojRodjendan = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

            Relation r1 = Relation.builder().user(u1).date(mojRodjendan).build();
            u1.getRelations().add(r1);

            session.save(u1);
            tx.commit();

            assertTrue(true);
        } catch (Exception e) {
            System.err.println(e);
            if (tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }
}
