import java.util.Objects;

public class Ingredient {
    final String name;
    final int capacity;
    final int durability;
    final int flavor;
    final int texture;
    final int calories;

    public Ingredient(String input) {
        String[] parts = input.split(" ");
        name = parts[0];
        capacity = Integer.parseInt(parts[1]);
        durability = Integer.parseInt(parts[2]);
        flavor = Integer.parseInt(parts[3]);
        texture = Integer.parseInt(parts[4]);
        calories = Integer.parseInt(parts[5]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ingredient{");
        sb.append("name='").append(name).append('\'');
        sb.append(", capacity=").append(capacity);
        sb.append(", durability=").append(durability);
        sb.append(", flavor=").append(flavor);
        sb.append(", texture=").append(texture);
        sb.append(", calories=").append(calories);
        sb.append('}');
        return sb.toString();
    }
}
