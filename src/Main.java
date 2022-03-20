import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<String> trainingList = Reading.readFileInList("iris_training.txt");
        List<Iris> irisTrainingList = Iris.getIrisList(trainingList);

        List<String> testList = Reading.readFileInList("iris_test.txt");
        List<Iris> irisTestList = Iris.getIrisList(testList);

        while(true) {
            System.out.println("Welcome to s22472 kNN!");
            System.out.println("Please type your choose:");
            System.out.println("0 - Exit");
            System.out.println("1 - Qualify examples from txt file");
            System.out.println("2 - Qualify your own example");

            Scanner chooseScanner = new Scanner(System.in);
            Scanner kScanner = new Scanner(System.in);
            int choose = chooseScanner.nextInt();
            if(choose == 1) {
                System.out.print("Enter k: ");
                Classifier.classifyTestCollection(irisTrainingList, irisTestList, kScanner.nextInt());
            } else if(choose == 2) {
                System.out.println("Enter k: ");
                Classifier.classifyCustomConditionalAttributes(irisTrainingList, kScanner.nextInt());
            } else if(choose == 0) {
                break;
            } else {
                System.out.println("Please type number 0 or 1 or 2");
            }
            System.out.println();
        }

    }
}
