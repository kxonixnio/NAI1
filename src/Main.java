import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<String> trainingList = Reading.readFileInList("iris_training.txt");
        List<Iris> irisTrainingList = Iris.getIrisList(trainingList);

        List<String> testList = Reading.readFileInList("iris_test.txt");
        List<Iris> irisTestList = Iris.getIrisList(testList);

        //Enter k
        Scanner kScanner = new Scanner(System.in);
        System.out.print("Enter k: ");
        int k = kScanner.nextInt();

        Classifier.classifyTestCollection(irisTrainingList, irisTestList, k);
    }
}
