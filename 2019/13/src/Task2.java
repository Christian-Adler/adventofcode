import computer.Computer;
import computer.IOutput;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Task2 {
    Map<Long, Long> programInitial = new HashMap<>();
    Map<Pos, Integer> gameArea = new HashMap<>();

    public void init() {
    }

    public void addLine(String input) {
        programInitial = Computer.parseProgram(input);
    }

    public void afterParse() throws InterruptedException, IOException {
        Computer computer = new Computer(programInitial, value -> {
        });
        computer.setProgramValue(0, 2);

        AtomicInteger instructionCounter = new AtomicInteger();
        AtomicBoolean isShowScore = new AtomicBoolean(false);
        AtomicLong score = new AtomicLong();
        Pos actPos = new Pos(0, 0);
        Pos ball = new Pos(0, 0);
        Pos paddle = new Pos(0, 0);

        IOutput output = new IOutput() {
            @Override
            public void out(Long value) {
                int i = instructionCounter.get();
                int iVal = value.intValue();
                if (i == 0) {
                    actPos.x = iVal;

                    if (iVal == -1)
                        isShowScore.set(true);
                } else if (i == 1)
                    actPos.y = iVal;
                else {
                    if (isShowScore.get()) {
                        isShowScore.set(false);
                        score.set(value);
//                        Task2.this.out("act score", value);
                    } else {
                        if (value != 0) {
                            gameArea.put(actPos.copy(), iVal);
                            if (iVal == 4) {
                                ball.x = actPos.x;
                                ball.y = actPos.y;
//                                Task2.this.out("ball pos", ball);

                                if (ball.x > paddle.x)
                                    computer.addInput(1);
                                else if (ball.x < paddle.x)
                                    computer.addInput(-1);
                                else
                                    computer.addInput(0);

                                Task2.this.saveSVG();
                                // Sleep to
//                                try {
//                                    Thread.sleep(10);
//                                } catch (InterruptedException e) {
//                                    throw new RuntimeException(e);
//                                }
                            } else if (iVal == 3) {
                                paddle.x = actPos.x;
                                paddle.y = actPos.y;
//                                Task2.this.out("paddle pos", paddle);
                            }
                        } else
                            gameArea.remove(actPos.copy());
                    }
                }
                if (instructionCounter.incrementAndGet() >= 3)
                    instructionCounter.set(0);
            }
        };
        computer.setOutput(output);


        computer.exec();
        computer.join();

        out("Part 2", "score", score);

        saveSVG();
    }

    private void saveSVG() {
        SVG svg = new SVG();
        for (Map.Entry<Pos, Integer> entry : gameArea.entrySet()) {
            String color = "#ffffff"; // wall
            if (entry.getValue() == 2) {
                color = "#0000ff";
            } else if (entry.getValue() == 3)
                color = "#aa0000";
            else if (entry.getValue() == 4)
                color = "#ff0000";
            svg.add(entry.getKey(), color);
        }

        try {
            Util.writeToAOCSvg(svg.toSVGString());
        } catch (IOException e) {
            out(e);
        }
    }


    public void out(Object... str) {
        Util.out(str);
    }

}
