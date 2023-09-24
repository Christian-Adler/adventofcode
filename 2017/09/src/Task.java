public class Task {
    String input = "";

    public void init() {
    }

    public void addLine(String input) {
        this.input = input;
    }

    public void afterParse() {
        int groupsOpened = 0;
        int groupsScore = 0;
        int groupDepth = 0;
        boolean garbage = false;
        int garbageCount = 0;

        for (int i = 0; i < input.length(); i++) {
            String c = input.substring(i, i + 1);
//            out(c);

            if (garbage) {
                if (c.equals("!")) {
                    i++; // skip next
                } else if (c.equals(">")) {
                    garbage = false;
                } else
                    garbageCount++;
            } else {
                switch (c) {
                    case "<" -> garbage = true;
                    case "{" -> {
                        groupsOpened++;
                        groupDepth++;
                        groupsScore += groupDepth;
                    }
                    case "}" -> {
                        groupDepth--;
                    }
                }
            }
        }

        out("score", groupsScore, "groups", groupsOpened);
        out("garbageCount", garbageCount);
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
