public record Device(Elements element, Type type) {
    public String toString() {
        return "" + element() + "-" + type();
    }
}
