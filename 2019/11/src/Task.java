import computer.Computer;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Task {
    Map<Long, Long> programInitial = new HashMap<>();

    Set<Pos> hullWhitePanels = new HashSet<>();
    Set<Pos> visitedPanels = new HashSet<>();

    public void init() {
    }

    public void addLine(String input) {
        programInitial = Computer.parseProgram(input);
    }

    public void afterParse() throws InterruptedException, IOException {
        Computer computer = new Computer(programInitial, value -> out("Part 1", "Output", value));

        Roboter roboter = new Roboter(hullWhitePanels, visitedPanels, computer::addInput);
        computer.setOutput(roboter::addInput);

        computer.addInput(0L);
        computer.exec();
        roboter.start();
        computer.join();
        roboter.addInput(-1); // exit
        roboter.join();

        out("Part 1", visitedPanels.size());
//        out(toStringConsole());
//        Util.writeToAOCSvg(toStringSVG());

        // Part 2
        hullWhitePanels.clear();
        hullWhitePanels.add(new Pos(0, 0));
        visitedPanels.clear();

        computer = new Computer(programInitial, value -> out("Part 1", "Output", value));

        roboter = new Roboter(hullWhitePanels, visitedPanels, computer::addInput);
        computer.setOutput(roboter::addInput);

        computer.addInput(1L);
        computer.exec();
        roboter.start();
        computer.join();
        roboter.addInput(-1); // exit
        roboter.join();

        out("Part 2");
        out(toStringConsole());
        Util.writeToAOCSvg(toStringSVG());
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public String toStringSVG() {
        SVG svg = new SVG();
        hullWhitePanels.forEach(svg::add);
        return svg.toSVGStringAged();
    }


    public String toStringConsole() {
        SVG svg = new SVG();
        hullWhitePanels.forEach(svg::add);
        return svg.toConsoleString();
    }
}
