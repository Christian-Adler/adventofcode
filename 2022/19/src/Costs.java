public class Costs {
    int ore = 0;
    int clay = 0;
    int obsidian = 0;

    public Costs(String input) {
        String[] split = input.trim().split(" and "); // 3 ore and 14 clay
        for (int i = 0; i < split.length; i++) {
            String[] amountAndType = split[i].split(" ");
            int amount = Integer.parseInt(amountAndType[0]);
            String type = amountAndType[1];
            if (type.equalsIgnoreCase("ore"))
                ore = amount;
            else if (type.equalsIgnoreCase("clay"))
                clay = amount;
            else if (type.equalsIgnoreCase("obsidian"))
                obsidian = amount;
        }
    }

    @Override
    public String toString() {
        return "Costs{" +
                "ore=" + ore +
                ", clay=" + clay +
                ", obsidian=" + obsidian +
                '}';
    }
}
