import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> linesFromFile = Reading.readFileInList("iris_training.txt");
        List<Iris> irisList = Iris.getTrainingList(linesFromFile);

        for(Iris iris : irisList) {
            System.out.println(iris.toString());
        }
    }
}
