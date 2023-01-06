import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Person {
    final String name;

    Map<Person, Integer> neighbor2Happiness = new HashMap<>();

    public Person(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("name='").append(name);
        for (Map.Entry<Person, Integer> nEntry : neighbor2Happiness.entrySet()) {
            sb.append(" # ");
            sb.append(nEntry.getKey().name).append("->").append(nEntry.getValue());
        }
        sb.append('}');
        return sb.toString();
    }
}
