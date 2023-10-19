import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

public class FlowingWater {
    Pos pos;
    Pos direction;
    private boolean settled = false;
    private boolean settledLeft = false;
    private boolean settledRight = false;
    Set<FlowingWater> prev = new LinkedHashSet<>();
    Set<FlowingWater> next = new LinkedHashSet<>();

    public FlowingWater(Pos pos, Pos direction) {
        this.pos = pos;
        this.direction = direction;
    }

    public FlowingWater copy() {
        return new FlowingWater(pos.copy(), direction.copy());
    }


    public boolean setSettled() {
        settledLeft = true;
        settledRight = true;
        boolean result = !settled;
        settled = true;
        pos.color = "~";
        return result;
    }

    public boolean isSettled() {
        return settled;
    }

    public boolean isSettledLeft() {
        return settled || settledLeft;
    }

    public boolean setSettledLeft() {
        boolean result;
        if (settledRight)
            result = setSettled();
        else {
            result = !settledLeft;
            settledLeft = true;
            pos.color = "/";
        }
        return result;
    }

    public boolean isSettledRight() {
        return settled || settledRight;
    }

    public boolean setSettledRight() {
        boolean result;
        if (settledLeft)
            result = setSettled();
        else {
            result = !settledRight;
            settledRight = true;
            pos.color = "\\";
        }
        return result;
    }

    public boolean isDirectionDown() {
        return direction.equals(Pos.DOWN);
    }

    public boolean isDirectionLeft() {
        return direction.equals(Pos.LEFT);
    }

    public boolean isDirectionRight() {
        return direction.equals(Pos.RIGHT);
    }

    public Pos nextStepWouldBe() {
        return pos.addToNew(direction);
    }

    public Pos stepDownWouldBe() {
        return pos.addToNew(Pos.DOWN);
    }

    public Pos stepRightWouldBe() {
        return pos.addToNew(Pos.RIGHT);
    }

    public Pos stepLeftWouldBe() {
        return pos.addToNew(Pos.LEFT);
    }

    public FlowingWater step() {
        return step(direction);
    }

    public FlowingWater step(Pos dir) {
        Pos nextStepPos = pos.copy();
        nextStepPos.add(dir);
        nextStepPos.color = dir.color;
        FlowingWater fw = new FlowingWater(nextStepPos, dir.copy());
        fw.prev.add(this);
        this.next.add(fw);
        if (next.size() > 1)
            pos.color = "+";
        return fw;
    }


    public FlowingWater stepDown() {
        return step(Pos.DOWN);
    }

    public FlowingWater stepLeft() {
        return step(Pos.LEFT);
    }

    public FlowingWater stepRight() {
        return step(Pos.RIGHT);
    }

    public void treeWalk(Consumer<Pos> consumer) {
        HashSet<Pos> visited = new HashSet<>();
        treeWalk(consumer, visited);
    }

    private void treeWalk(Consumer<Pos> consumer, HashSet<Pos> visited) {
        if (!visited.add(pos))
            return;

        consumer.accept(pos);
        for (FlowingWater child : next) {
            child.treeWalk(consumer, visited);
        }
    }
}
