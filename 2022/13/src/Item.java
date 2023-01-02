import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Item {
    LinkedList<Item> list = null;

    Integer value = null;

    Item parent = null;

    public Item() {
        list = new LinkedList<>();
    }

    public Item(Integer value) {
        this.value = value;
    }

    void add(Item item) {
        list.add(item);
        item.parent = this;
    }

    boolean isInt() {
        return value != null;
    }

    boolean isList() {
        return list != null;
    }

    @Override
    public String toString() {
        if (value != null)
            return String.valueOf(value);
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(list.stream().map(i -> i.toString()).collect(Collectors.joining(",")));
        builder.append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return item.toString().equals(toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }
}
