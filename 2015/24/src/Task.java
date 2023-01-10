import java.util.*;

public class Task {
    List<Integer> packages = new ArrayList<>();

    Set<ArrayList<Integer>> weightGroups = new HashSet<>();

    public void init() {
    }

    public void addLine(String input) {
        packages.add(Integer.parseInt(input));
    }

    public void afterParse(int numGroups) {
        Collections.sort(packages);
        Collections.reverse(packages);

        out(packages);
        int sum = packages.stream().mapToInt(i -> i).sum();
        out("sum", sum);
        int weightPerGroup = sum / numGroups;
        out("weight per group:", weightPerGroup);

        int weightSum = 0;
        int lastIdx = 0;
        for (int i = packages.size() - 1; i >= 0; i--) {

            Integer weight = packages.get(i);
            if (weightSum + weight > weightPerGroup) break;
            lastIdx = i;
            weightSum += weight;
        }
        out("lastIdx for round 0:", lastIdx);

        buildAllCombinations(weightPerGroup, new ArrayList<>(), 0, lastIdx);

//        out("Weightgroups:", weightGroups.size());
//        out(weightGroups);

        Map<Integer, Integer> sizeMap = new HashMap<>();
        for (ArrayList<Integer> weightGroup : weightGroups) {
            int size = weightGroup.size();
            sizeMap.compute(size, (k, v) -> v == null ? 1 : v + 1);
        }
//        out(sizeMap);
        int minGroupSize = sizeMap.keySet().stream().mapToInt(i -> i).min().orElse(0);
        out("min group size", minGroupSize);

        long minQuantumEntanglement = Long.MAX_VALUE;
        for (ArrayList<Integer> weightGroup : weightGroups) {
            if (weightGroup.size() != minGroupSize) continue;

            long quantumEntanglement = 1;
            for (Integer integer : weightGroup) {
                quantumEntanglement *= integer;
            }

            minQuantumEntanglement = Math.min(minQuantumEntanglement, quantumEntanglement);
        }

        out("minQuantumEntanglement", minQuantumEntanglement);
    }

    int minSize = Integer.MAX_VALUE;

    void buildAllCombinations(int targetWeight, ArrayList<Integer> soFar, int startIdx, int lastInitialCombinationIdx) {
        if (soFar.size() > minSize)
            return;
        int soFarSum = sum(soFar);
//        out("soFarSum", soFarSum, soFar);
        if (soFarSum > targetWeight)
            return;
        if (soFarSum == targetWeight) {
            weightGroups.add(soFar);
            minSize = Math.min(minSize, soFar.size());
//            out(soFar);
            return;
        }

        int endIdx = startIdx == 0 ? lastInitialCombinationIdx : packages.size();
        for (int i = startIdx; i < endIdx; i++) {
            Integer weight = packages.get(i);
            ArrayList<Integer> nextSet = new ArrayList<>(soFar);
            nextSet.add(weight);
            buildAllCombinations(targetWeight, nextSet, i + 1, lastInitialCombinationIdx);
        }
    }

    int sum(ArrayList<Integer> set) {
        return set.stream().mapToInt(i -> i).sum();
    }

    public void out(Object... str) {
        StringBuilder out = new StringBuilder();
        for (Object o : str) {
            if (out.length() > 0)
                out.append(" ");
            out.append(o);
        }
        System.out.println(out);
    }

}
