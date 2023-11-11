import computer.Computer;
import computer.IOutput;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    Map<Long, Long> programInitial = new HashMap<>();
    Map<Pos, Integer> gameArea = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        programInitial = Computer.parseProgram(input);
    }

    public void afterParse() throws InterruptedException, IOException {
        AtomicInteger instructionCounter = new AtomicInteger();
        Pos actPos = new Pos(0, 0);

        IOutput output = new IOutput() {
            @Override
            public void out(Long value) {
                int i = instructionCounter.get();
                if (i == 0)
                    actPos.x = value.intValue();
                else if (i == 1)
                    actPos.y = value.intValue();
                else {
                    if (value != 0)
                        gameArea.put(actPos.copy(), value.intValue());
                    else
                        gameArea.remove(actPos.copy());
                }
                if (instructionCounter.incrementAndGet() >= 3) instructionCounter.set(0);
            }
        };


        Computer computer = new Computer(programInitial, output);
        computer.exec();
        computer.join();

        int countBlockTiles = 0;

        SVG svg = new SVG();
        for (Map.Entry<Pos, Integer> entry : gameArea.entrySet()) {
            String color = "#ffffff"; // wall
            if (entry.getValue() == 2) {
                color = "#0000ff";
                countBlockTiles++;
            } else if (entry.getValue() == 3)
                color = "#aa0000";
            else if (entry.getValue() == 4)
                color = "#ff0000";
            svg.add(entry.getKey(), color);
        }

        Util.writeToAOCSvg(svg.toSVGString());

        out("Part 1", "Block tiles:", countBlockTiles);
    }


    public void out(Object... str) {
        Util.out(str);
    }

}
