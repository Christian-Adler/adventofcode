import java.util.ArrayList;

public class RLInstruction {
  private final ArrayList<String> instructionList;
  private int actIdx = -1;

  public RLInstruction(String instruction) {
    this.instructionList = Util.str2List(instruction);
  }

  public String getNext() {
    actIdx++;
    if (actIdx >= instructionList.size())
      actIdx = 0;
    return instructionList.get(actIdx);
  }

  public void reset() {
    actIdx = -1;
  }

  @Override
  public String toString() {
    return "RLInstruction{" +
        "instructionList=" + instructionList +
        ", actIdx=" + actIdx +
        '}';
  }
}
