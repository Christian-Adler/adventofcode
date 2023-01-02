import java.util.List;
import java.util.stream.Collectors;

public class Task {

    private int prio = 0;

    public void addLine(String input) {
        String in = input.trim();
        List<Character> first = in.substring(0, in.length() / 2).chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        List<Character> second = in.substring(in.length() / 2).chars().mapToObj(c -> (char) c).collect(Collectors.toList());

//        System.out.println(first);
//        System.out.println(second);

        first.retainAll(second);

        Character inBoth = first.get(0);
//        System.out.println(inBoth);
        int pri = (int) inBoth;
//        System.out.println(pri);
//        System.out.println((int) 'a');
//        System.out.println((int) 'A');

        if (pri >= 97)
            pri = pri - 96;
        else
            pri = pri - 64 + 26;
//        System.out.println(pri);

        prio += pri;
    }

    public void printPrioSum() {
        System.out.println(prio);
    }

}
