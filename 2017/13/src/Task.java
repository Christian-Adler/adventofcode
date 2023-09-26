import java.util.HashMap;
import java.util.Map;

public class Task {

    Map<Integer, Layer> layers = new HashMap<>();


    public void init() {
    }

    public void addLine(String input) {
        String[] split = input.split(":\\s*");
        int layerIdx = Integer.parseInt(split[0]);
        Layer layer = new Layer(layerIdx, Integer.parseInt(split[1]));
        layers.put(layerIdx, layer);
    }

    public void afterParse() {
        int severity = evalSeverity(0);

        out("severity 0", severity);

        // Durch Mathe statt schrittweise
        severity = 0;
        for (Layer layer : layers.values()) {
            if (layer.caughtAtDelay(0)) {
                severity += layer.severity;
            }
        }
        out("severity 0", severity);

        int delay = 0;
        boolean caught = true;
        while (caught) {
            delay++;
            caught = false;

            for (Layer layer : layers.values()) {
                if (layer.caughtAtDelay(delay)) {
                    caught = true;
                    break;
                }
            }
        }

        out("Not caught with delay", delay);
    }

    private int evalSeverity(int delay) {
        int maxLayerIdx = layers.keySet().stream().max(Integer::compareTo).orElse(-1);
        int severity = 0;

        for (int stepidx = (delay * -1); stepidx <= maxLayerIdx; stepidx++) {
            Layer layer = layers.get(stepidx);
            if (layer != null) {
                if (layer.isScannerAtTop()) {
                    severity += layer.severity;
                }
            }

            // Scanner alle einen Schritt weiter
            layers.values().forEach(Layer::step);
        }
        return severity;
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
