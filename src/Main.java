import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> trainingList = Reading.readFileInList("iris_training.txt");
        List<Iris> irisTrainingList = Iris.getIrisList(trainingList);

        System.out.println("TRAINING COLLECTION");
        for(Iris iris : irisTrainingList) {
            System.out.println(iris.toString());
        }

        List<String> testList = Reading.readFileInList("iris_test.txt");
        List<Iris> irisTestList = Iris.getIrisList(testList);

        System.out.println("\nTEST COLLECTION");
        for(Iris iris : irisTestList) {
            System.out.println(iris.toString());
        }
    }
}
