import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Task {
  private boolean parseWorkflows = true;
  private final Map<String, Workflow> workflows = new HashMap<>();
  private final ArrayList<Rating> ratings = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    if (input.trim().isEmpty()) {
      parseWorkflows = false;
      return;
    }
    if (parseWorkflows) {
      Workflow workflow = Workflow.from(input);
      workflows.put(workflow.name, workflow);
    } else { // parse ratings
      ratings.add(Rating.from(input));
    }
  }

  public void afterParse() {
//    out(workflows);
//    out(ratings);

    long sumAllParts = 0;

    for (Rating rating : ratings) {
      boolean check = checkRating(rating);
      if (check) {
        for (RatingItem ratingItem : rating.ratingItems) {
          sumAllParts += ratingItem.value();
        }
      }
    }

    out("Part 1", "sum all A items", sumAllParts);

    // Part 2

    Range range = new Range();
    for (PartCategory partCategory : PartCategory.values()) {
      range.put(partCategory, new CategoryRange(1, 4000));
    }
//    out("Max possible combinations:", range.combinations());
    long combis = calcCombinations("in", range);
    out("Part 2", "acceptable combinations", combis);
  }

  private long calcCombinations(String workflowName, Range range) {
    if (workflowName.equals("R"))
      return 0;
    long combinations = range.combinations();
    if (combinations < 0)
      return 0;
    if (workflowName.equals("A"))
      return combinations;


    Workflow workflow = workflows.get(workflowName);
    long sumCombinations = 0;
    Range actRange = range.copy();
    for (Rule rule : workflow.rules) {
      PartCategory partCategory = rule.partCategory();
      if (partCategory == null) {
        long combis = calcCombinations(rule.targetWorkflow(), actRange);
        sumCombinations += combis;
        continue;
      }

      Operator operator = rule.operator();
      int compareValue = rule.compareValue();

      CategoryRange categoryRange = actRange.get(partCategory);
      Range rangeCopy = actRange.copy();
      if (operator.equals(Operator.LT)) {
        rangeCopy.put(partCategory, new CategoryRange(categoryRange.min(), Math.min(categoryRange.max(), compareValue - 1)));
        actRange.put(partCategory, new CategoryRange(Math.max(categoryRange.min(), compareValue), categoryRange.max()));
      } else {
        rangeCopy.put(partCategory, new CategoryRange(Math.max(categoryRange.min(), compareValue + 1), categoryRange.max()));
        actRange.put(partCategory, new CategoryRange(categoryRange.min(), Math.min(categoryRange.max(), compareValue)));
      }

      // rekursion
      long combis = calcCombinations(rule.targetWorkflow(), rangeCopy);
      sumCombinations += combis;
    }
    return sumCombinations;
  }

  private boolean checkRating(Rating rating) {
    Workflow actWorkflow = workflows.get("in");
    while (actWorkflow != null) {
      String targetWorkflowName = actWorkflow.calcTargetWorkflow(rating);
      if (targetWorkflowName.equals("A")) return true;
      if (targetWorkflowName.equals("R")) return false;
      actWorkflow = workflows.get(targetWorkflowName);
    }
    return false;
  }

  public void out(Object... str) {
    Util.out(str);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    return builder.toString();
  }

  public String toStringSVG() {
    SVG svg = new SVG();
    return svg.toSVGStringAged();
  }


  public String toStringConsole() {
    SVG svg = new SVG();
    return svg.toConsoleString();
  }
}
