import java.util.ArrayList;
import java.util.List;

public class Task {
  private final ArrayList<Block> blocks = new ArrayList<>();
  private final ArrayList<Block> blocks2 = new ArrayList<>();

  public void init() {
  }

  public void addLine(String input) {
    List<Integer> noList = Util.str2List(input).stream().mapToInt(Integer::parseInt).boxed().toList();
    boolean isFile = true;
    long id = -1;

    for (Integer val : noList) {
      if (isFile) {
        id++;
        blocks.add(new Block(val, id));
        blocks2.add(new Block(val, id));
      }
      else
        blocks.add(new Block(val, -1));
      isFile = !isFile;
    }
  }


  public void afterParse() {
    // out(blocks);
    rearange();

  }

  private void rearange() {
    ArrayList<Block> rearanged = new ArrayList<>();

    // first file stays at the front
    Block first = blocks.removeFirst();
    for (int i = 0; i < first.getSize(); i++) {
      rearanged.add(new Block(1, first.getFileId()));
    }

    Block actSpacesBlock = null;

    while (!blocks.isEmpty()) {
      // get next space block
      if (actSpacesBlock == null) {
        first = blocks.getFirst();
        if (first.isFile()) {
          // got file block
          first = blocks.removeFirst();
          for (int i = 0; i < first.getSize(); i++) {
            rearanged.add(new Block(1, first.getFileId()));
          }
        }
        else /*if (first.isSpace()) */ {
          actSpacesBlock = blocks.removeFirst();
        }
      }
      Block actFileBlock = null;
      if (!blocks.isEmpty()) {
        actFileBlock = blocks.getLast();
        while (actFileBlock.isSpace()) {
          blocks.removeLast();
          actFileBlock = blocks.getLast();
        }
      }

      if (actSpacesBlock != null && actFileBlock != null) {
        while (!actSpacesBlock.isEmpty()) {
          rearanged.add(new Block(1, actFileBlock.getFileId()));
          actFileBlock.reduceSize();
          actSpacesBlock.reduceSize();
          if (actFileBlock.isEmpty()) {
            blocks.removeLast();
            break;
          }
        }
      }

      if (actSpacesBlock != null && actSpacesBlock.isEmpty())
        actSpacesBlock = null;
    }

    // System.out.println(rearanged);

    // checksum
    long sum = 0;
    for (int i = 0; i < rearanged.size(); i++) {
      Block block = rearanged.get(i);
      sum += block.getFileId() * i;
    }
    out("part 1", "checksum", sum);
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
