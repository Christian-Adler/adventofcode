import java.util.ArrayList;

public class Task {

  long sum = 0;

  ArrayList<ArrayList<Lens>> boxes = new ArrayList<>();

  public void init() {
    for (int i = 0; i < 256; i++) {
      boxes.add(new ArrayList<>());
    }
  }

  public void addLine(String input) {
    String[] split = input.split(",");
    for (String s : split) {
      int h = hash(s);
//      out(h);
      sum += h;

      // Part 2
      if (s.endsWith("-")) {
        String label = s.substring(0, s.length() - 1);
        int boxIdx = hash(label);
        ArrayList<Lens> box = boxes.get(boxIdx);
        box.removeIf(l -> l.label.equals(label));
      } else {
        String[] split2 = s.split("=");
        String label = split2[0];
        int lensFocalLength = Integer.parseInt(split2[1]);
        int boxIdx = hash(label);
        ArrayList<Lens> box = boxes.get(boxIdx);
        Lens lens = box.stream().filter(l -> l.label.equals(label)).findFirst().orElse(null);
        if (lens != null)
          lens.focalLength = lensFocalLength;
        else
          box.add(new Lens(label, lensFocalLength));
      }

//      out(s);
//      out(boxes);
    }
  }

  public void afterParse() {
    out("Part 1", sum);

    // Part 2 calculation
    long focusingPowerSum = 0;
    int actBox = 0;
    for (ArrayList<Lens> box : boxes) {

      int actLens = 0;
      for (Lens lens : box) {
        int focusingPower = (1 + actBox) * (1 + actLens) * lens.focalLength;
//        out(focusingPower);
        focusingPowerSum += focusingPower;
        actLens++;
      }

      actBox++;
    }

    out("Part 2", focusingPowerSum);
  }

  private int hash(String toHash) {
    int currentValue = 0;

    for (int c = 0; c < toHash.length(); c++) {
      char ch = toHash.charAt(c);
      currentValue += (int) ch;
      currentValue *= 17;
      currentValue = currentValue % 256;
    }

    return currentValue;
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
