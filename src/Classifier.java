import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Classifier {

    public static void classifyTestCollection(List<Iris> irisTrainingList, List<Iris> irisTestList, int k) {
        for(Iris irisTest : irisTestList) { //Iterate through every test Iris...
            Map<Iris, Double> distancesToTrainingIrises = new HashMap<>();
            for(Iris irisTraining : irisTrainingList) { //...and check his distance to every training Iris
                distancesToTrainingIrises.put(irisTraining, getEuclideanDistance(irisTest, irisTraining));
            }

            System.out.println(entriesSortedByValues(distancesToTrainingIrises));

            List<?> list = entriesSortedByValues(distancesToTrainingIrises).stream().limit(k).collect(Collectors.toList());
            System.out.println(Arrays.asList(list));

            return;
        }
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

    private static void printCollections(List<Iris> irisTrainingList, List<Iris> irisTestList) {
        System.out.println("TRAINING COLLECTION");
        for(Iris iris : irisTrainingList) {
            System.out.println(iris.toString());
        }

        System.out.println("\nTEST COLLECTION");
        for(Iris iris : irisTestList) {
            System.out.println(iris.toString());
        }
    }
}
