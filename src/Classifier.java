import java.util.*;
import java.util.stream.Collectors;

public class Classifier {

    private static int COUNTER_OF_PROPER_DIAGNOSED_IRISES = 0;

    public static void classifyTestCollection(List<Iris> irisTrainingList, List<Iris> irisTestList, int k) {
        for(Iris irisTest : irisTestList) { //Iterate through every test Iris...
            Map<Iris, Double> distancesToTrainingIrises = new HashMap<>();
            for(Iris irisTraining : irisTrainingList) { //...and check his distance to every training Iris
                distancesToTrainingIrises.put(irisTraining, getEuclideanDistance(irisTest, irisTraining));
            }

            //Sort map of distances by value (ascending) and get first k elements (== the smallest distances)
            List<Map.Entry<Iris, Double>> kNearestNeighbours = entriesSortedByValues(distancesToTrainingIrises).stream()
                    .limit(k)
                    .collect(Collectors.toList());

            //Increment counter if test iris diagnosed properly
            if(isDiagnosedProperly(irisTest, kNearestNeighbours, k)) {
                COUNTER_OF_PROPER_DIAGNOSED_IRISES++;
            }
        }

        //Summary
        System.out.println(COUNTER_OF_PROPER_DIAGNOSED_IRISES + "/" + irisTestList.size());
        System.out.println(((double) COUNTER_OF_PROPER_DIAGNOSED_IRISES / (double)irisTestList.size()) * 100 + "%");

        //Reset counter
        COUNTER_OF_PROPER_DIAGNOSED_IRISES = 0;
    }

    public static void classifyCustomConditionalAttributes(List<Iris> irisTrainingList, int k) {
        Map<Iris, Double> distancesToTrainingIrises = new HashMap<>();
        int dimension = irisTrainingList.get(0).getConditionalAttributes().size(); //to know how many number user have to input
        List<Double> conditionalAttributes = new ArrayList<>();
        Scanner conditionalScanner = new Scanner(System.in);

        System.out.println("Type " + dimension + " conditional attributes (e.g. 3,54)");
        for (int i = 0; i < dimension; i++) {
            conditionalAttributes.add(conditionalScanner.nextDouble());
        }

        for(Iris irisTraining : irisTrainingList) { //check distance to every training Iris
            distancesToTrainingIrises.put(irisTraining, getEuclideanDistance(new Iris(conditionalAttributes, null), irisTraining));
        }

        //Sort map of distances by value (ascending) and get List of first k elements (== the smallest distances)
        List<Map.Entry<Iris, Double>> kNearestNeighbours = entriesSortedByValues(distancesToTrainingIrises).stream()
                .limit(k)
                .collect(Collectors.toList());

        Map<String, Integer> counterMap = getEmptyIrisCounterMap();
        System.out.println("Your conditional attributes: " + conditionalAttributes);
        System.out.println(k + "NN:");
        for (int i = 0; i < kNearestNeighbours.size(); i++) {
            String decisionAttribute = kNearestNeighbours.get(i).getKey().getDecisionAttribute();
            System.out.println(decisionAttribute);
            counterMap.put(decisionAttribute, counterMap.get(decisionAttribute) + 1);
        }

        //Find key with max value
        System.out.print("Decision: ");
        System.out.println(Collections.max(counterMap.entrySet(), Map.Entry.comparingByValue()).getKey());

    }

    private static Map<String, Integer> getEmptyIrisCounterMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Iris-setosa", 0);
        map.put("Iris-versicolor", 0);
        map.put("Iris-virginica", 0);
        return map;
    }

    private static boolean isDiagnosedProperly(Iris irisTest, List<Map.Entry<Iris, Double>> kNearestNeighboursList, int k) {

        long counter = kNearestNeighboursList.stream()
                .filter(nn -> nn.toString().contains(irisTest.getDecisionAttribute()))
                .count();
        printPrettyInfo(irisTest, kNearestNeighboursList, counter, k);

        if(((double) counter / (double) k) > 0.5) {
            return true;
        } else {
            return false; //if 50% is correct also return false
        }
    }

    private static void printPrettyInfo(Iris irisTest, List<Map.Entry<Iris, Double>> kNearestNeighbours, long counter, int k) {
        System.out.println("K nearest neighbours for: " + irisTest.getDecisionAttribute() + ", " + irisTest.getConditionalAttributes());
        for (int i = 0; i < kNearestNeighbours.size(); i++) {
            System.out.println(kNearestNeighbours.get(i).getKey().getDecisionAttribute() + " - " + kNearestNeighbours.get(i).getValue());
        }
        System.out.println(String.valueOf(counter) + "/" + String.valueOf(k) + "\n");
    }

    private static Double getEuclideanDistance(Iris testIris, Iris trainIris) {
        int dimension = testIris.getConditionalAttributes().size(); //No matter if that's test or train Iris list, we assume it's the same size
        Double distance = 0.0;

        for (int i = 0; i < dimension; i++) {
            Double xa = testIris.getConditionalAttributes().get(i);
            Double xb = trainIris.getConditionalAttributes().get(i);
            distance += Math.pow(xa - xb, 2); //(xa-xb)^2
        }

        return Math.sqrt(distance); //We can bypass sqrt, but as method's name is called getEuclideanDistance I want to be precise
    }

    //https://stackoverflow.com/questions/2864840/treemap-sort-by-value
    static <K,V extends Comparable<? super V>>
    SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
