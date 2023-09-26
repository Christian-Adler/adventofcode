public class Layer {
    final int layerIdx;
    final int range;
    final int severity;
    int actIdx = 0;
    boolean down = true;

    public Layer(int layerIdx, int range) {
        this.layerIdx = layerIdx;
        this.range = range;
        this.severity = layerIdx * range;
    }

    public void step() {
        if (down) {
            if (actIdx == range - 1) {
                down = false;
                actIdx--;
            } else
                actIdx++;
        } else {
            if (actIdx == 0) {
                down = true;
                actIdx++;
            } else
                actIdx--;
        }
    }

    public boolean isScannerAtTop() {
        return actIdx == 0;
    }

    public void reset() {
        down = true;
        actIdx = 0;
    }

    public boolean caughtAtDelay(int delay) {
        // % ( (depth -1)*2)

        return (delay + layerIdx) % ((range - 1) * 2) == 0;
    }
}
