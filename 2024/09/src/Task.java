import java.util.ArrayList;
import java.util.List;

public class Task {
  private final ArrayList<Block> blocks = new ArrayList<>();
  private final ArrayList<Block> blocks2 = new ArrayList<>();

  long maxId = -1;

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
        maxId = id;
      } else {
        blocks.add(new Block(val, -1));
        blocks2.add(new Block(val, -1));
      }
      isFile = !isFile;
    }
  }


  public void afterParse() {
    // out(blocks);
    rearange();
    rearange2();
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
        } else /*if (first.isSpace()) */ {
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
    long sum = buildChecksum(rearanged);
    out("part 1", "checksum", sum);
  }

  private static long buildChecksum(ArrayList<Block> rearanged) {
    long sum = 0;
    for (int i = 0; i < rearanged.size(); i++) {
      Block block = rearanged.get(i);
      if (block.isFile())
        sum += block.getFileId() * i;
    }
    return sum;
  }

  private void rearange2() {
    long actId = maxId;
    while (actId >= 0) {
      long finalActId = actId;
      Block fileBlock = blocks2.stream().filter(b -> b.isFile() && b.getFileId() == finalActId).findFirst().orElse(null);
      if (fileBlock == null) throw new IllegalStateException("Missing Block");
      int fileBlockIdx = blocks2.indexOf(fileBlock);

      int freeBlockIdx = findFreeBlockIdx(fileBlock.getSize());
      if (freeBlockIdx >= 0 && freeBlockIdx < fileBlockIdx) {
        Block spaceBlock = blocks2.get(freeBlockIdx);
        spaceBlock.reduceSize(fileBlock.getSize());
        Block replaceFileWithSpace = new Block(fileBlock.getSize(), -1);
        blocks2.add(fileBlockIdx, replaceFileWithSpace);
        blocks2.remove(fileBlock);
        blocks2.add(freeBlockIdx, fileBlock);
        if (spaceBlock.isEmpty())
          blocks2.remove(spaceBlock);
      }

      actId--;
    }

    // out(blocks2);

    ArrayList<Block> rearanged = new ArrayList<>();
    for (Block block : blocks2) {
      for (int i = 0; i < block.getSize(); i++) {
        rearanged.add(new Block(1, block.getFileId()));
      }
    }
    // out(rearanged);

    // checksum
    long sum = buildChecksum(rearanged);
    out("part 2", "checksum", sum);
  }

  private int findFreeBlockIdx(int freeSize) {
    for (int i = 0; i < blocks2.size(); i++) {
      Block block = blocks2.get(i);
      if (block.isSpace() && block.getSize() >= freeSize)
        return i;
    }
    return -1;
  }

  public void out(Object... str) {
    Util.out(str);
  }
}
