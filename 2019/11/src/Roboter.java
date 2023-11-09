import computer.IOutput;

import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Roboter extends Thread {

    private final LinkedBlockingQueue<Long> input = new LinkedBlockingQueue<>();
    private final Set<Pos> hullWhitePanels;
    private final Set<Pos> visitedPanels;
    private final IOutput output;

    private Pos actPos = new Pos(0, 0);
    private Pos dir = new Pos(0, -1);

    public Roboter(Set<Pos> hullWhitePanels, Set<Pos> visitedPanels, IOutput output) {
        this.hullWhitePanels = hullWhitePanels;
        this.visitedPanels = visitedPanels;
        this.output = output;
    }

    public void addInput(long value) {
        input.add(value);
    }

    public void run() {
        boolean paint = true;

        Long nextInstruction = null;

        while (true) {
            try {
                nextInstruction = input.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (nextInstruction == -1)
                break;
            if (paint) {
                if (nextInstruction == 1) { // Paint white
                    hullWhitePanels.add(actPos.copy());
                } else if (nextInstruction == 0) { // paint black
                    hullWhitePanels.remove(actPos);
                }
                visitedPanels.add(actPos);
            } else {
                if (nextInstruction == 1) { // Paint white
                    dir = dir.rotate90DegToNew(true);
                } else { // paint black
                    dir = dir.rotate90DegToNew(false);
                }
                actPos = actPos.addToNew(dir);
                output.out(hullWhitePanels.contains(actPos) ? 1L : 0L);
            }
            paint = !paint;
        }
    }
}
