import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Bot implements IAdd {

    public static List<Integer> compares = new ArrayList<>();

    final int botNo;
    List<Integer> chips = new ArrayList<>();
    IAdd low = null;
    IAdd high = null;

    public Bot(int botNo) {
        this.botNo = botNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bot bot = (Bot) o;
        return botNo == bot.botNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(botNo);
    }

    @Override
    public String toString() {
        return "Bot{" + "botNo=" + botNo +
                ", chips=" + chips +
                '}';
    }

    public boolean add(int i) {
        chips.add(i);
        return chips.size() > 1;
    }

    public List<IAdd> give() {
        if (chips.size() != 2) throw new IllegalStateException("Give called but chips are not 2");

        if (chips.containsAll(compares))
            System.out.println("Comparing bot: " + botNo);

        Collections.sort(chips);

        List<IAdd> next = new ArrayList<>();
        if (low.add(chips.get(0)))
            next.add(low);

        if (high.add(chips.get(1)))
            next.add(high);

        chips.clear();

        return next;
    }
}
