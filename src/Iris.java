import java.util.*;

public class Iris {
    private List<Double> conditionalAttributes;
    private String decisionAttribute;

    public Iris(List<Double> conditionalAttributes, String decisionAttribute) {
        this.conditionalAttributes = conditionalAttributes;
        this.decisionAttribute = decisionAttribute;
    }

    public static List<Iris> getIrisList(List<String> list) {
        List<Iris> outputList = new ArrayList<>();

        for(String line : list) { //for each line...
            List<Double> attributes = new ArrayList<>();
            String decisionAtt = "";

            line = line.replace(',', '.'); //e.g. 3,7 -> 3.7
            Scanner doubleScanner = new Scanner(line);

            try {
                while (!doubleScanner.hasNextDouble()) {
                    attributes.add(Double.parseDouble(doubleScanner.next())); //read all numbers...
                }
            } catch (NumberFormatException e) { //... if it is not a number, it is a decision attribute
                line = line.trim();
                decisionAtt = line.substring(line.lastIndexOf(" ") + 1);
                decisionAtt = decisionAtt.replaceAll("\\s+",""); //remove all whitespaces and non-visible characters
            }

            outputList.add(new Iris(attributes, decisionAtt));
        }
        return outputList;
    }

    public List<Double> getConditionalAttributes() {
        return conditionalAttributes;
    }

    public String getDecisionAttribute() {
        return decisionAttribute;
    }

    @Override
    public String toString() {
        return decisionAttribute;
    }
}
