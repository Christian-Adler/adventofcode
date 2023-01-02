import java.util.*;

public class Line {
    private static final Set<String> openers = new HashSet<>(Arrays.asList("(", "[", "{", "<"));

    private static final Map<String, Long> errScoreMap = new HashMap<>();
    private static final Map<String, Long> closingScoreMap = new HashMap<>();
    private static final Map<String, String> match = new HashMap<>();

    static {
        errScoreMap.put(")", 3L);
        errScoreMap.put("]", 57L);
        errScoreMap.put("}", 1197L);
        errScoreMap.put(">", 25137L);

        closingScoreMap.put(")", 1L);
        closingScoreMap.put("]", 2L);
        closingScoreMap.put("}", 3L);
        closingScoreMap.put(">", 4L);

        match.put("(", ")");
        match.put("[", "]");
        match.put("{", "}");
        match.put("<", ">");
    }

    public List<String> list = new LinkedList<>();
    public Stack<String> stack = new Stack<>();
    public boolean isCorrupt = false;
    public long syntaxErrScore = 0;
    public boolean isIncomplete = false;
    public long completeScore = 0;

    public Line(String line) {
        list.addAll(Arrays.asList(line.split("")));

        checkLine();
    }

    public void checkLine() {
        for (String c :
                list) {
            if (stack.isEmpty())
                stack.add(c);
            else {
                // Oeffnend - dann einfach anhaengen
                if (openers.contains(c)) {
                    stack.push(c);
                } else {
                    String peeked = stack.peek();

                    if (c.equals(match.get(peeked))) // richtig
                        stack.pop();
                    else {
                        isCorrupt = true;
                        syntaxErrScore = errScoreMap.get(c);
                        break;
                    }
                }
            }
        }
        if (!stack.isEmpty() && !isCorrupt) {
            isIncomplete = true;
            completeScore = 0;

            while (!stack.isEmpty()) {
                completeScore = completeScore * 5 + closingScoreMap.get(match.get(stack.pop()));
            }
        }
    }

    @Override
    public String toString() {
        return String.join("", list) + " isCorrupt:" + isCorrupt + (isCorrupt ? " " + syntaxErrScore : " isIncomplete:" + isIncomplete + " " + completeScore);
    }
}
