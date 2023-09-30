import java.util.ArrayList;
import java.util.List;

public class Task {

    List<Component> components = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        components.add(new Component(input));
    }

    public void afterParse() {
        out(components);
        List<Component> starters = components.stream().filter(Component::isStarter).toList();

        List<List<Component>> allPossibleBridges = new ArrayList<>();

        for (Component starter : starters) {
            List<Component> onlyStarterList = new ArrayList<>();
            onlyStarterList.add(starter);
            allPossibleBridges.add(onlyStarterList);

            ArrayList<Component> availableComponents = new ArrayList<>(components);
            availableComponents.remove(starter);

            ArrayList<Component> bridge = new ArrayList<>();
            bridge.add(starter);

            findAllBridges(starter.getOtherPort(0), bridge, availableComponents, allPossibleBridges);
        }

        int strongestBridge = 0;
        int longestBridge = 0;
        int longestBridgeStrength = 0;
        for (List<Component> bridge : allPossibleBridges) {
            int length = bridge.size();
            int strength = calcStrength(bridge);
            strongestBridge = Math.max(strongestBridge, strength);
//            out(strength + " : " + toString(bridge));

            if (length > longestBridge || length == longestBridge && strength > longestBridgeStrength) {
                longestBridge = length;
                longestBridgeStrength = strength;
            }
        }
        out("strongestBridge", strongestBridge);
        out("longestBridge", longestBridge);
        out("longestBridgeStrength", longestBridgeStrength);
    }

    private void findAllBridges(int connectionPort, ArrayList<Component> soFarBridge, ArrayList<Component> availableComponents, List<List<Component>> allPossibleBridges) {
        List<Component> connectAbles = availableComponents.stream().filter(c -> c.hasPort(connectionPort)).toList();

        for (Component connectAble : connectAbles) {
            ArrayList<Component> availComponents = new ArrayList<>(availableComponents);
            availComponents.remove(connectAble);
            ArrayList<Component> bridge = new ArrayList<>(soFarBridge);
            bridge.add(connectAble);
            allPossibleBridges.add(bridge);

            findAllBridges(connectAble.getOtherPort(connectionPort), bridge, availComponents, allPossibleBridges);
        }
    }

    private String toString(List<Component> components) {
        StringBuilder builder = new StringBuilder();

        for (Component component : components) {
            if (!builder.isEmpty()) builder.append("--");
            builder.append(component);
        }

        return builder.toString();
    }

    private int calcStrength(List<Component> components) {
        int strength = 0;
        for (Component component : components) {
            strength += component.port1;
            strength += component.port2;
        }

        return strength;
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
