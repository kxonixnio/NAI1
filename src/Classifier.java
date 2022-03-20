import java.util.*;
import java.util.stream.Collectors;

public class Classifier {
    /**
     * Classifies test list based on training list.
     * Provide basic console GUI - shows which attribute is considered and it's k nearest neighbours.
     * Prints how many decision attributes were correct (also % of it).
     * @param irisTrainingList - list of Irises with correct decision attributes
     * @param irisTestList - list of Irises with decision attributes to check
     * @param k - amount of nearest neighbours to check
     */
    public static void classifyTestCollection(List<Iris> irisTrainingList, List<Iris> irisTestList, int k) {
        int counterOfProperDiagnosedIrises = 0;

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
                counterOfProperDiagnosedIrises++;
            }
        }

        //Summary
        System.out.println(counterOfProperDiagnosedIrises + "/" + irisTestList.size());
        System.out.println(((double) counterOfProperDiagnosedIrises / (double)irisTestList.size()) * 100 + "%");
    }

    /**
     * Classifies custom Iris provided by user.
     * Checks dimension based on data from the file and asks user to input right amount of attributes.
     * @param irisTrainingList - list of Irises with decision attributes to check
     * @param k - amount of nearest neighbours to check
     */
    public static void classifyCustomConditionalAttributes(List<Iris> irisTrainingList, int k) {
        List<Double> conditionalAttributes = new ArrayList<>();
        Scanner conditionalScanner = new Scanner(System.in);
        int dimension = irisTrainingList.get(0).getConditionalAttributes().size(); //to know how many numbers user have to input

        //User input - values of conditional attributes
        System.out.println("Type " + dimension + " conditional attributes (e.g. 3,54)");
        for (int i = 0; i < dimension; i++) {
            conditionalAttributes.add(conditionalScanner.nextDouble());
        }

        //check distance of user's Iris to each training Iris
        Map<Iris, Double> distancesToTrainingIrises = new HashMap<>();
        for(Iris irisTraining : irisTrainingList) {
            distancesToTrainingIrises.put(irisTraining, getEuclideanDistance(new Iris(conditionalAttributes, null), irisTraining));
        }

        //Sort map of distances by value (ascending) and get List of first k elements (== the smallest distances)
        List<Map.Entry<Iris, Double>> kNearestNeighbours = entriesSortedByValues(distancesToTrainingIrises).stream()
                .limit(k)
                .collect(Collectors.toList());

        //Print some info
        Map<String, Integer> counterMap = getEmptyIrisCounterMap();
        System.out.println("Your conditional attributes: " + conditionalAttributes); //print info about user input
        System.out.println(k + "NN:");
        for (int i = 0; i < kNearestNeighbours.size(); i++) {
            String decisionAttribute = kNearestNeighbours.get(i).getKey().getDecisionAttribute();
            System.out.println(decisionAttribute); //print info about kNN
            counterMap.put(decisionAttribute, counterMap.get(decisionAttribute) + 1);
        }

        //Find key with max value
        System.out.print("Decision: ");
        System.out.println(Collections.max(counterMap.entrySet(), Map.Entry.comparingByValue()).getKey()); //if there's a tie decision is based on what Collections.max return
    }

    /**
     * Get counter map
     * @return map <Iris-type, counter>, where counter is set to 0
     */
    private static Map<String, Integer> getEmptyIrisCounterMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Iris-setosa", 0);
        map.put("Iris-versicolor", 0);
        map.put("Iris-virginica", 0);
        return map;
    }

    /**
     * Returns true if Iris test decision attribute is correct (more than 50% of kNN have the same decision attribute).
     * Returns false otherwise.
     * @param irisTest - test Iris with decision attribute to check
     * @param kNearestNeighboursList - list of irisTest's kNN
     * @param k - amount of nearest neighbours to check
     * @return decision if Iris test decision attribute is correct.
     */
    private static boolean isDiagnosedProperly(Iris irisTest, List<Map.Entry<Iris, Double>> kNearestNeighboursList, int k) {
        //Count how many nearest neighbours have the same decision attribute as test Iris
        long counter = kNearestNeighboursList.stream()
                .filter(nn -> nn.toString().contains(irisTest.getDecisionAttribute()))
                .count();

        //Print info
        System.out.println("K nearest neighbours for: " + irisTest.getDecisionAttribute() + ", " + irisTest.getConditionalAttributes());
        for (int i = 0; i < kNearestNeighboursList.size(); i++) {
            System.out.println(kNearestNeighboursList.get(i).getKey().getDecisionAttribute() + " - " + kNearestNeighboursList.get(i).getValue());
        }
        System.out.println(String.valueOf(counter) + "/" + String.valueOf(k) + "\n");

        //Check if more than 50% cases "passed through"
        //if exactly 50% is correct also return false
        return ((double) counter / (double) k) > 0.5;
    }

    /**
     * Returns a distance between two Irises
     * @param testIris - test Iris with decision attribute to check
     * @param trainIris - tran Iris with correct decision attribute
     * @return distance between two Irises
     */
    private static Double getEuclideanDistance(Iris testIris, Iris trainIris) {
        int dimension = testIris.getConditionalAttributes().size(); //No matter if that's test or train Iris list, we assume it's the same size
        Double distance = 0.0;

        for (int i = 0; i < dimension; i++) {
            Double xa = testIris.getConditionalAttributes().get(i);
            Double xb = trainIris.getConditionalAttributes().get(i);
            distance += Math.pow(xa - xb, 2); //(xa-xb)^2
        }

        return Math.sqrt(distance); //We can bypass sqrt, but as method's name is called getEuclideanDistance I want to be as precise as possible
    }

    //https://stackoverflow.com/questions/2864840/treemap-sort-by-value
    private static <K,V extends Comparable<? super V>>
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
