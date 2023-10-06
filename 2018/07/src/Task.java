import java.util.*;
import java.util.stream.Collectors;

public class Task {
    private int numWorkers = 0;
    private int basicStepDuration = 0;

    Map<String, List<String>> step2successors = new HashMap<>();
    Map<String, List<String>> step2predecessors = new HashMap<>();

    public void init(int numWorkers, int basicStepDuration) {
        this.numWorkers = numWorkers;
        this.basicStepDuration = basicStepDuration;
    }

    public void addLine(String input) {
        String[] split = Util.cleanFrom(input, "Step", "must be finished before step", "can begin.").trim().split("\\s+");
//        out(split);
        String step = split[0];
        String successor = split[1];

        List<String> successors = step2successors.computeIfAbsent(step, k -> new ArrayList<>());
        successors.add(successor);
        List<String> predecessors = step2predecessors.computeIfAbsent(successor, k -> new ArrayList<>());
        predecessors.add(step);
    }

    public void afterParse() {
        out(step2successors);
        Set<String> allSuccessors = step2successors.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        HashSet<String> startSteps = new HashSet<>(step2successors.keySet());
        startSteps.removeAll(allSuccessors);
        out("StartStep(s)", startSteps);

        LinkedList<String> workList = new LinkedList<>();
        workList.addAll(startSteps);
        Set<String> completedSteps = new HashSet<>();

        List<String> stepOrder = new ArrayList<>();
        while (!workList.isEmpty()) {
            Collections.sort(workList);
            String step = workList.remove(0);

            stepOrder.add(step);
            completedSteps.add(step);

            // Alle moeglichen Nachfolger durchgehen
            List<String> successors = step2successors.get(step);
            if (successors != null) {
                // Die Nachfolger in WorkList aufnehmen, von denen ALLE! Vorgaenger erledigt sind.
                for (String successor : successors) {
                    List<String> successorsPredecessors = step2predecessors.getOrDefault(successor, new ArrayList<>());
                    if (completedSteps.containsAll(successorsPredecessors))
                        workList.add(successor);
                }
            }
        }

        out("Part1", "stepOrder", String.join("", stepOrder));

        // Part 2
        // ##################################################
        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < numWorkers; i++) {
            workers.add(new Worker());
        }

        workList = new LinkedList<>();
        workList.addAll(startSteps);
        completedSteps = new HashSet<>();
        stepOrder = new ArrayList<>();
        int second = 0;

        while (true) {
            boolean hasActiveWorker = false;
            for (Worker worker : workers) {
                hasActiveWorker |= worker.isActive();
            }
            if (workList.isEmpty() && !hasActiveWorker)
                break;

            Collections.sort(workList);

            for (Worker worker : workers) {
                if (worker.isFree() && !workList.isEmpty()) {
                    String step = workList.remove(0);
                    worker.set(step, getSecondsForStep(step));
                }
            }

//            out("second", second);
//            out("workers", workers);
//            out("completedSteps", completedSteps);
//            out("stepOrder", stepOrder);

            for (Worker worker : workers) {
                if (worker.isActive() && worker.nextSecond()) {
                    String step = worker.getActStep();
                    completedSteps.add(step);
                    stepOrder.add(step);

                    // Alle moeglichen Nachfolger durchgehen
                    List<String> successors = step2successors.get(step);
                    if (successors != null) {
                        // Die Nachfolger in WorkList aufnehmen, von denen ALLE! Vorgaenger erledigt sind.
                        for (String successor : successors) {
                            List<String> successorsPredecessors = step2predecessors.getOrDefault(successor, new ArrayList<>());
                            if (completedSteps.containsAll(successorsPredecessors))
                                workList.add(successor);
                        }
                    }
                    worker.free();
                }
            }
            second++;
        }

        out("Part 2", "second", second);
//        out("workers", workers);
//        out("completedSteps", completedSteps);
        out("stepOrder", stepOrder);
    }

    public int getSecondsForStep(String step) {
        return basicStepDuration + getLetterAmount(step);
    }

    private static int getLetterAmount(String l) {
        int c = (int) l.toLowerCase().charAt(0);
        return c - 96;
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

}
