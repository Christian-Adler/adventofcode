import java.util.ArrayList;
import java.util.List;

public class Output implements IAdd {
    final int no;
    List<Integer> output = new ArrayList<>();

    public Output(int no) {
        this.no = no;
    }

    public boolean add(int i) {
        output.add(i);
        return false;
    }

    @Override
    public List<IAdd> give() {
        // nothing to do
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Output{" + "no=" + no +
                ", output=" + output +
                '}';
    }
}
