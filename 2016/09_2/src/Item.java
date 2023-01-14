public class Item {
    String str;
    int length = 0;

    boolean isMarker = false;
    int numLetters = 0;
    int numRepeats = 0;

    public Item(int length) {
        str = "generated";
        this.length = length;
    }

    public Item(String str) {
        this.str = str;
        this.length = str.length();
        isMarker = str.startsWith("(");
        if (isMarker) {
            String[] parts = str.replace("(", "").replace(")", "").split("x");
            numLetters = Integer.parseInt(parts[0]);
            numRepeats = Integer.parseInt(parts[1]);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("str='").append(str).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
