import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {

    Map<Integer, Bot> bots = new HashMap<>();
    Map<Integer, Output> outputs = new HashMap<>();
    List<IAdd> workList = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        if (input.startsWith("value")) {
            String[] parts = Util.cleanFrom(input, "value ", " goes to bot").split(" ");
            int val = Integer.parseInt(parts[0]);
            int botNo = Integer.parseInt(parts[1]);

            Bot bot = getOrCreateBot(botNo);
            if (bot.add(val))
                workList.add(bot);
        } else if (input.startsWith("bot")) {
            String[] parts = Util.cleanFrom(input, " gives low to", " and high to").split(" ");
            int botNo = Integer.parseInt(parts[1]);
            String lowTarget = parts[2];
            int lowNo = Integer.parseInt(parts[3]);
            String highTarget = parts[4];
            int highNo = Integer.parseInt(parts[5]);

            Bot bot = getOrCreateBot(botNo);

            if (lowTarget.equals("bot"))
                bot.low = getOrCreateBot(lowNo);
            else if (lowTarget.equals("output"))
                bot.low = getOrCreateOutput(lowNo);

            if (highTarget.equals("bot"))
                bot.high = getOrCreateBot(highNo);
            else if (highTarget.equals("output"))
                bot.high = getOrCreateOutput(highNo);

        } else
            throw new IllegalArgumentException("unknown input");
    }

    private Bot getOrCreateBot(int no) {
        Bot b = bots.get(no);
        if (b == null) {
            b = new Bot(no);
            bots.put(no, b);
        }
        return b;
    }

    private Output getOrCreateOutput(int no) {
        Output o = outputs.get(no);
        if (o == null) {
            o = new Output(no);
            outputs.put(no, o);
        }
        return o;
    }

    public void afterParse() {
//        out(bots);
        while (!workList.isEmpty()) {
            IAdd workBot = workList.remove(0);
            List<IAdd> next = workBot.give();
            workList.addAll(next);
//            out(bots);
        }

//        out(outputs);
        // Part 2
        long multiply = 1;
        if (outputs.size() > 2) {
            multiply *= outputs.get(0).output.get(0);
            multiply *= outputs.get(1).output.get(0);
            multiply *= outputs.get(2).output.get(0);
            out("part 2:", multiply);
        }
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        return "";
    }

}
