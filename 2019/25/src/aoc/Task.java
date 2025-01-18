package aoc;

import aoc.computer.Computer;
import aoc.computer.IInput;
import aoc.util.Util;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Task extends TaskBase {
  public static void main(String[] args) throws Exception {
    runForInput("./input.txt");
  }

  public String part1(List<String> lines) throws Exception {
    AtomicReference<String> password = new AtomicReference<>();

    Map<Long, Long> program = Computer.parseProgram(lines.getFirst());
    List<String> outputCollect = new ArrayList<>();
    Input input = new Input();

    // got through rooms/doors by manual input collecting everything and drawing a map
    fillInputWithCollectAllItemsAndGoToPressureSensitiveFloor(input);

    List<String> availableItems = new ArrayList<>(List.of("fuel cell", "manifold", "candy cane", "mutex", "coin", "dehydrated water", "prime number", "cake"));

    Set<Set<String>> cache = new HashSet<>();

    List<WorkItem> workList = new ArrayList<>();
    for (String availableItem : availableItems) {
      WorkItem workItem = new WorkItem(List.of(availableItem));
      if (cache.add(new HashSet<>(workItem.items)))
        workList.add(workItem);
    }

    AtomicReference<WorkItem> actWorkItem = new AtomicReference<>();
    AtomicBoolean testMode = new AtomicBoolean(false);

    Computer computer = new Computer(program, value -> {
      // System.out.print((char) value.intValue());
      if (value == 10) {
        outputCollect.add("\n");
        String joined = String.join("", outputCollect);


        if (joined.contains("Command?")) {

          System.out.println(joined);
          outputCollect.clear();

          if (joined.contains("you are ejected back to the checkpoint"))
            testMode.set(true);

          if (input.isEmpty()) {
            if (testMode.get()) { // TEST MODE
              if (actWorkItem.get() == null && !workList.isEmpty())
                actWorkItem.set(workList.removeFirst());

              if (actWorkItem.get() != null) {
// Phase 1: take items
                WorkItem workItem = actWorkItem.get();
                if (workItem.phase == WorkItem.TAKE) {
                  String takeNext = workItem.toTakeNext();
                  if (takeNext != null)
                    input.add("take " + takeNext);
                  else {
                    workItem.phase = WorkItem.TEST;
                    input.add("west");
                  }
                }
// Phase 2: test
                else if (workItem.phase == WorkItem.TEST) {
                  // successful?

                  // if (joined.contains("Alert! Droids on this ship are lighter than the detected value!")) {
                  //   // to heavy -> abort
                  // } else
                  if (joined.contains("Alert! Droids on this ship are heavier than the detected value!")) {
                    // to light -> build next work items
                    HashSet<String> tmp = new HashSet<>(availableItems);
                    tmp.removeAll(workItem.items);

                    for (String item : tmp) {
                      WorkItem nextWorkItem = new WorkItem(workItem.items, item);
                      if (cache.add(new HashSet<>(nextWorkItem.items)))
                        workList.add(nextWorkItem);
                    }
                  }

                  workItem.phase = WorkItem.DROP;
                  input.add("drop " + workItem.toDropNext());
                }
// Phase 3: drop items
                else if (workItem.phase == WorkItem.DROP) {
                  String takeNext = workItem.toDropNext();
                  if (takeNext != null)
                    input.add("drop " + takeNext);
                  else {
                    actWorkItem.set(null);
                    input.add("next work item...");
                  }
                } else {
                  out("unexpected phase");
                  queryInput(input);
                }
              } else {
                out("Unexpected state - actWorkItem content is null");
                queryInput(input);
              }
            } else {
              // out("Not in test mode...!?");
              queryInput(input);
            }
          }
        } else if (joined.contains("You should be able to get in by typing")) {
          // out(joined);
          String res = joined.substring(joined.indexOf("typing") + 7);
          res = res.substring(0, res.indexOf("on the"));
          password.set(res);
        }
      } else
        outputCollect.add(String.valueOf((char) value.intValue()));
    }, input);

    computer.exec();
    computer.join();

    return password.get();
  }

  private static class WorkItem {
    public final Set<String> items;

    private final List<String> pickedUp = new ArrayList<>();
    public static final int TAKE = 0;
    public static final int TEST = 1;
    public static final int DROP = 2;
    public int phase = 0;

    WorkItem(Collection<String> items) {
      this.items = new HashSet<>(items);
    }

    WorkItem(Collection<String> items, String item) {
      this.items = new HashSet<>(items);
      this.items.add(item);
    }

    public String toTakeNext() {
      ArrayList<String> tmp = new ArrayList<>(items);
      tmp.removeAll(pickedUp);
      if (tmp.isEmpty()) return null;
      String takeNext = tmp.getFirst();
      pickedUp.add(takeNext);
      return takeNext;
    }

    public String toDropNext() {
      if (pickedUp.isEmpty()) return null;
      return pickedUp.removeFirst();
    }
  }


  private static void fillInputWithCollectAllItemsAndGoToPressureSensitiveFloor(Input input) {
    input.add("south");
    input.add("take fuel cell");
    input.add("south");
    input.add("take manifold");
    input.add("north");
    input.add("north");
    input.add("north");
    input.add("take candy cane");
    input.add("south");
    input.add("west");
    input.add("take mutex");
    input.add("south");
    input.add("south");
    input.add("take coin");
    input.add("west");
    input.add("take dehydrated water");
    input.add("south");
    input.add("take prime number");
    input.add("north");
    input.add("east");
    input.add("north");
    input.add("east");
    input.add("take cake");
    input.add("north");
    input.add("west");
    input.add("south");

    // drop all items
    input.add("drop fuel cell");
    input.add("drop manifold");
    input.add("drop candy cane");
    input.add("drop mutex");
    input.add("drop coin");
    input.add("drop dehydrated water");
    input.add("drop prime number");
    input.add("drop cake");

    input.add("west");
  }

  private static void queryInput(Input input) {
    // Using Scanner for Getting Input from User
    Scanner scanner = new Scanner(System.in);

    String s = "";
    while (s.trim().isEmpty()) {
      System.out.println("Enter a string:");
      s = scanner.nextLine().trim();
    }

    // System.out.println("You entered string " + s);
    input.add(s);
  }


  private static class Input implements IInput {
    private final LinkedBlockingQueue<Long> input = new LinkedBlockingQueue<>();

    @Override
    public void add(Long... values) {
      for (Long value : values) {
        input.offer(value);
      }
    }

    public void add(String command) {
      for (String s : Util.str2List(command)) {
        add((long) mapStr2intCode(s));
      }
      add((long) mapStr2intCode("\n"));
    }

    private int mapStr2intCode(String s) {
      return s.codePointAt(0);
    }

    public boolean isEmpty() {
      return input.isEmpty();
    }

    @Override
    public long get() throws InterruptedException {
      return input.take();
    }
  }
}
