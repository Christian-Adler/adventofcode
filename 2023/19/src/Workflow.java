import java.util.ArrayList;

public class Workflow {
  public final String name;
  public final ArrayList<Rule> rules;


  private Workflow(String name, ArrayList<Rule> rules) {
    this.name = name;
    this.rules = rules;
  }

  public static Workflow from(String input) {
    String[] split = Util.cleanFrom(input, "}").split("\\{");
    String name = split[0];
    String[] ruleStrs = split[1].split(",");
    ArrayList<Rule> rules = new ArrayList<>();

    for (String ruleStr : ruleStrs) {
      String[] ruleSplit = ruleStr.split(":");
      if (ruleSplit.length == 1)// last rule without condition
        rules.add(new Rule(null, null, 0, ruleSplit[0]));
      else {
        String condition = ruleSplit[0];
        String targetWorkflow = ruleSplit[1];

        PartCategory partCategory = PartCategory.from(condition.substring(0, 1));
        Operator operator = Operator.from(condition.substring(1, 2));
        int compareValue = Integer.parseInt(condition.substring(2));

        rules.add(new Rule(partCategory, operator, compareValue, targetWorkflow));
      }
    }

    return new Workflow(name, rules);
  }

  public String calcTargetWorkflow(Rating rating) {
    for (Rule rule : rules) {
      if (rule.matches(rating))
        return rule.targetWorkflow();
    }
    return "INVALID";
  }

  @Override
  public String toString() {
    return "Workflow{" +
        "name='" + name + '\'' +
        ", rules=" + rules +
        '}';
  }
}
