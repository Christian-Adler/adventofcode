import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Task2 {

    private int prio = 0;

    List<String> three = new LinkedList<>();

    public void addLine(String input) {
        String in = input.trim();

        three.add(in);

        if (three.size() < 3) return;

        List<Character> chars = three.get(0).chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        chars.retainAll(three.get(1).chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
        chars.retainAll(three.get(2).chars().mapToObj(c -> (char) c).collect(Collectors.toList()));

        Character inAll = chars.get(0);
//        System.out.println(inAll);
        int pri = (int) inAll;
//        System.out.println(pri);
//        System.out.println((int) 'a');
//        System.out.println((int) 'A');

        if (pri >= 97)
            pri = pri - 96;
        else
            pri = pri - 64 + 26;
//        System.out.println(pri);

        prio += pri;

        three.clear();
    }

    public void printPrioSum() {
        System.out.println(prio);
    }

}
