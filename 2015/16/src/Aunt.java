import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Aunt {
    final int no;
    Map<String, Integer> properties = new HashMap<>();

    public Aunt(String input) {
        // Sue 471: goldfish: 10, samoyeds: 10, pomeranians: 5
        String in = input.replace("Sue ", "");
        int firstSplitIdx = in.indexOf(":");
        no = Integer.parseInt(in.substring(0, firstSplitIdx));
        String[] parts = in.substring(firstSplitIdx + 1).split(",");
        for (String part : parts) {
            String[] partItems = part.split(":");
            properties.put(partItems[0].trim(), Integer.parseInt(partItems[1].trim()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aunt aunt = (Aunt) o;
        return no == aunt.no;
    }

    @Override
    public int hashCode() {
        return Objects.hash(no);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Aunt{");
        sb.append("no=").append(no);
        sb.append(", properties=").append(properties);
        sb.append('}');
        return sb.toString();
    }
}
