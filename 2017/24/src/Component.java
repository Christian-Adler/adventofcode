public class Component {
    final int port1;
    final int port2;

    public Component(String input) {
        String[] split = input.split("/");
        port1 = Integer.parseInt(split[0]);
        port2 = Integer.parseInt(split[1]);
    }

    @Override
    public String toString() {
        return "{" + port1 + "/" + port2 + '}';
    }

    public boolean isStarter() {
        return port1 == 0 || port2 == 0;
    }

    public boolean hasPort(int port) {
        return port == port1 || port == port2;
    }

    public int getOtherPort(int usedPort) {
        if (port1 == usedPort) return port2;
        return port1;
    }
}
