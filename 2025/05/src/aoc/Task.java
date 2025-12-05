package aoc;

import aoc.util.Img;
import aoc.util.Range;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "RedundantThrows"})
public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
//    runForInput("./input_example_1.txt");
     runForInput("./input.txt");
  }

  private final List<Range> ranges = new ArrayList<>();
  private final List<Long> ids =new ArrayList<>();
private boolean parseRanges =true;
  public void exec(List<String> lines, Object... params) throws Exception {
      for (String line : lines) {
          if(line.trim().isEmpty()){
              parseRanges=false;continue;
          }
          if(parseRanges)
              ranges.add(Range.parse(line));
          else ids.add(Long.parseLong(line));
      }

//      out(ids,ranges);
      long countFresh = 0;
      for (Long id : ids) {
          for (Range range : ranges) {
              if(range.contains(id)){countFresh++;break;
              }
          }
      }
    out("part 1: ", countFresh);

      Set<Long> allPossible = new HashSet<>();
      for (Range range : ranges) {
          allPossible.addAll(new ArrayList<>(range.asStream().boxed().collect(Collectors.toList())));
      }

    out("part 2: ", allPossible.size());
  }

  @Override
  public String toString() {
    return toStringConsole();
  }

  public void toBmp() throws Exception {
    Img img = new Img();
    img.writeBitmapAged();
  }

  public String toStringConsole() {
    Img img = new Img();
    return img.toConsoleString();
  }
}
