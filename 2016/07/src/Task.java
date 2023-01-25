import java.util.ArrayList;
import java.util.List;

public class Task {

    long abbaCounter = 0;
    long sslCounter = 0;

    public void init() {
    }

    public void addLine(String input) {
        String[] parts = input.split("[\\[\\]]");
        boolean isAbbaEven = false;
        boolean isAbbaOdd = false;
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            boolean isPartAbba = isAbba(part);
            if (i % 2 == 0) {
                isAbbaEven |= isPartAbba;
            } else {
                isAbbaOdd |= isPartAbba;
            }
        }
        if (isAbbaEven && !isAbbaOdd) {
            abbaCounter++;
        }

        // Part 2 ssl
        ArrayList<SSLPair> sslPairs = new ArrayList<>();
        for (int i = 0; i < parts.length; i++) {
            if (i % 2 != 0) continue;
            String part = parts[i];
            sslPairs.addAll(collectABA(part));
        }
        out(sslPairs);
        for (int i = 0; i < parts.length; i++) {
            if (i % 2 == 0) continue;
            String part = parts[i];
            for (SSLPair sslPair : sslPairs) {
                if (part.contains(sslPair.bab)) {
                    sslCounter++;
                    return; // in dieser IP nicht weiter suchen - es gibt bei einigen noch weitere Treffer - aber jede IP zaehlt ja nur einmal
                }
            }
        }
    }

    public void afterParse() {
        out("abbaCount:", abbaCounter);
        out("sslCount:", sslCounter);
    }


    private boolean isAbba(String input) {
        if (input.length() < 4) {
            return false;
        }

        char c0 = input.charAt(0);
        char c1 = input.charAt(1);
        char c2 = input.charAt(2);

        for (int i = 3; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c0 == c && c1 == c2 && c0 != c1) {
                return true;
            }

            c0 = c1;
            c1 = c2;
            c2 = c;
        }
        return false;
    }

    private List<SSLPair> collectABA(String input) {
        List<SSLPair> res = new ArrayList<>();
        if (input.length() < 3) {
            return res;
        }

        char c0 = input.charAt(0);
        char c1 = input.charAt(1);

        for (int i = 2; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c0 == c && c0 != c1) {
                res.add(new SSLPair("" + c + c1 + c, "" + c1 + c + c1));
            }

            c0 = c1;
            c1 = c;
        }
        return res;
    }

    public void out(Object... str) {
        Util.out(str);
    }

    private record SSLPair(String aba, String bab) {
    }

}
