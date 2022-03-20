import java.util.List;

public class Classifier {

    public static void classifyTestCollection(List<Iris> irisTrainingList, List<Iris> irisTestList, int k) {
        //...
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
