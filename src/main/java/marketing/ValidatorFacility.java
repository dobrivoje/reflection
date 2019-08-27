package marketing;

import lombok.NonNull;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.paukov.combinatorics3.Generator;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ValidatorFacility {

    /**
     * Discount type
     */
    public enum DISCOUNT {
        ABSOLUTE, PERCENT;
    }

    public static final Map<Priority, Double> EMPTY_PRIORITY = new HashMap<>();

    //<editor-fold desc="PriorityPrice">
    public class PriorityPrice {

        public Priority priority;
        public double price;
        public DISCOUNT discount;

        public PriorityPrice(Priority priority, double price) {
            this.priority = priority;
            this.price = price;
            this.discount = DISCOUNT.ABSOLUTE;
        }

        public PriorityPrice(Priority priority, double price, DISCOUNT discount) {
            this(priority, price);
            this.discount = discount;
        }
    }
    //</editor-fold>

    /**
     * Vrati skup indeksa sa deklarisnih polja klase Lubenica
     * ako postoji anotracija Svojstva nad tim poljem
     *
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public static <T> Set<Integer> ConvertToIds(T object) throws IllegalAccessException {
        Set<Integer> res = new LinkedHashSet<>();

        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getType().equals(Boolean.class)) {
                field.setAccessible(true);

                Object av = field.get(object);
                if (av != null) {
                    boolean actualValue = (boolean) av;

                    if (actualValue && field.isAnnotationPresent(Property.class)) {
                        Property declaredAnnotation = field.getDeclaredAnnotation(Property.class);
                        if (declaredAnnotation.include())
                            res.add(declaredAnnotation.id());
                    }
                }
            }
        }

        return res;
    }

    public static <T> Map<T, Set<Integer>> ConvertAllToIds(T... genericObjects) throws IllegalAccessException {
        return ConvertAllToIds(Arrays.asList(genericObjects));
    }

    public static <T> Map<T, Set<Integer>> ConvertAllToIds(@NonNull Collection<T> genericObjects) throws IllegalAccessException {
        Map<T, Set<Integer>> ms = new HashMap<>();
        if (!genericObjects.isEmpty())
            for (T obj : genericObjects)
                ms.put(obj, ConvertToIds(obj));

        return ms;
    }

    // TODO implementirati..
    public <T> boolean valid(@NonNull Set<Integer> request, @NonNull T key, @NonNull Map<T, Set<Integer>> actual) {
        return true;
    }

    /**
     * Prva nađena kombinacija se oduzima od skupa zahteva i ide se dalje<br>
     * sa umanjenim zahtevom za tu nađenu kombinacuju.<br>
     * Napomena : ovaj način nije idealan jer isključivanjem<br>
     * prve kombinacije, isključujemo potencijalne podskup(e) drugih kombinacija.
     *
     * @param marketingActions Prethodno definisan spisak kombinacija int skupova<br>
     *                         Ključ je kombinacija (skup) int-ova, a vrednost je cena<br>
     *                         <br>
     * @param request          Zahtev koji dobijamo, pa za njega računamo krajnji DiscountWithExcluding<br>
     * @return
     */
    public static void DiscountWithExcluding(@NonNull Map<Set<Integer>, Map<Integer, Double>> marketingActions
        , @NonNull Set<Integer> request, Map<Priority, Double> rezultat) {
        for (Map.Entry<Set<Integer>, Map<Integer, Double>> E : marketingActions.entrySet()) {
            if (request.size() < E.getKey().size()) {
                rezultat.putAll(EMPTY_PRIORITY);
            } else {
                Map<Integer, Double> prioDiscMap = E.getValue();
                Map.Entry<Integer, Double> pdMap = prioDiscMap.entrySet().iterator().next();

                Integer priority = pdMap.getKey();
                Double discount = pdMap.getValue();
                Set<Integer> actionSet = E.getKey();

                if (request.containsAll(actionSet)) {
                    request.removeAll(actionSet);

                    Priority prio = Priority.builder()
                        .priority(priority)
                        .cardinality(actionSet.size())
                        .build();

                    rezultat.put(prio, discount);
                    System.err.println("akcija : " + actionSet + ", prio : " + priority
                        + ", cardinality : " + prio.getCardinality() + ", DiscountWithExcluding : " + discount);

                    DiscountWithExcluding(marketingActions, request, rezultat);
                }
            }
        }
    }

    /**
     * Za razliku od DiscountWithExcluding, ovde pronalazimo sve moguće kombinacije<br>
     *
     * @param marketingActions Prethodno definisan spisak kombinacija int skupova<br>
     *                         Ključ je kombinacija (skup) int-ova, a vrednost je cena<br>
     *                         <br>
     * @param request          Zahtev koji dobijamo, pa za njega računamo krajnji DiscountWithExcluding<br>
     * @return
     */
    public static List<PriorityPrice> DiscountForAll(@NonNull Map<List<Integer>, Map<Integer, Double>> marketingActions
        , @NonNull List<Integer> request) {

        List<PriorityPrice> result = new LinkedList<>();

        List<List<Integer>> allCombinations = Generator.subset(request).simple().stream().collect(Collectors.toList());
        for (List<Integer> comb : allCombinations) {
            Map<Integer, Double> v = marketingActions.get(comb);
            if (v != null && !v.isEmpty()) {
                Map.Entry<Integer, Double> E = v.entrySet().iterator().next();
                int marketingActionPriority = E.getKey();
                double marketingActionPrice = E.getValue();

                Priority priority = Priority.builder()
                    .cardinality(comb.size())
                    .priority(marketingActionPriority)
                    .build();

                result.add(new ValidatorFacility().new PriorityPrice(priority, marketingActionPrice));
            }
        }

        Collections.sort(result, (pp1, pp2) -> pp2.priority.getCardinality() - pp1.priority.getCardinality());
        return result;
    }
}
