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

        for(String line : list) {
            List<Double> attributes = new ArrayList<>();
            String decisionAtt = "";

            line = line.replace(',', '.');
            Scanner doubleScanner = new Scanner(line);

            try {
                while (!doubleScanner.hasNextDouble()) {
                    attributes.add(Double.parseDouble(doubleScanner.next()));
                }
            } catch (NumberFormatException e) {
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
