import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Task {
    ArrayList<String> password = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        if (input.startsWith("password")) {
            password = Util.str2List(input.replace("password:", ""));
            out(password);
            return;
        }
        String[] parts = input.split(" ");

        if (input.startsWith("swap position")) {
            int x = Integer.parseInt(parts[2]);
            int y = Integer.parseInt(parts[5]);

            String atX = password.get(x);
            String atY = password.get(y);
            password.set(x, atY);
            password.set(y, atX);
        } else if (input.startsWith("swap letter")) {
            String replace = parts[2];
            String by = parts[5];
            password.replaceAll(s -> s.equals(replace) ? "_" : s);
            password.replaceAll(s -> s.equals(by) ? replace : s);
            password.replaceAll(s -> s.equals("_") ? by : s);
        } else if (input.startsWith("rotate left")) {
            int steps = Integer.parseInt(parts[2]) % password.size();
            ArrayList<String> tmp1 = new ArrayList<>(password.subList(0, steps));
            ArrayList<String> tmp2 = new ArrayList<>(password.subList(steps, password.size()));
            tmp2.addAll(tmp1);
            password = tmp2;
        } else if (input.startsWith("rotate right")) {
            int steps = Integer.parseInt(parts[2]) % password.size();
            ArrayList<String> tmp1 = new ArrayList<>(password.subList(0, password.size() - steps));
            ArrayList<String> tmp2 = new ArrayList<>(password.subList(password.size() - steps, password.size()));
            tmp2.addAll(tmp1);
            password = tmp2;
        } else if (input.startsWith("rotate based on position of letter")) {
            String letterX = parts[6];
            int steps = password.indexOf(letterX);
            if (steps >= 4)
                steps++;
            steps++;
            steps = steps % password.size();
            ArrayList<String> tmp1 = new ArrayList<>(password.subList(0, password.size() - steps));
            ArrayList<String> tmp2 = new ArrayList<>(password.subList(password.size() - steps, password.size()));
            tmp2.addAll(tmp1);
            password = tmp2;
        } else if (input.startsWith("reverse positions")) {
            int x = Integer.parseInt(parts[2]);
            int y = Integer.parseInt(parts[4]);
            ArrayList<String> tmp1 = new ArrayList<>(password.subList(0, x));
            ArrayList<String> tmp2 = new ArrayList<>(password.subList(x, y + 1));
            ArrayList<String> tmp3 = new ArrayList<>(password.subList(y + 1, password.size()));
            Collections.reverse(tmp2);
            tmp1.addAll(tmp2);
            tmp1.addAll(tmp3);
            password = tmp1;
        } else if (input.startsWith("move position")) {
            int x = Integer.parseInt(parts[2]);
            int y = Integer.parseInt(parts[5]);
            String move = password.remove(x);
            password.add(y, move);
        }

//        out(password);
    }

    public void afterParse() {
        String joined = password.stream().collect(Collectors.joining());
        out("Scrambled:", joined);
    }

    public void out(Object... str) {
        Util.out(str);
    }

}
