public class Task {
    long targetValue = 0;

    public void init() {
    }

    public void addLine(String input) {
        targetValue = Long.parseLong(input);
    }

    public void afterParse() {
        out("targetValue", targetValue);

        long maxPresents = 0;

        // to high houseNo 829920
        int houseNo = (int) targetValue / 45;
        boolean foundHouse = false;
        while (!foundHouse) {
            long numPresents = 0;
            if (houseNo % 1000 == 0) out("where:", houseNo);

            for (int i = houseNo; i >= 1; i--) {
                if (houseNo % i == 0) {
                    numPresents += i * 10;
                }


                if (numPresents > targetValue)
                    break;
            }

            if (numPresents > maxPresents) {
                maxPresents = numPresents;
                out("max presents:", maxPresents, "at house", houseNo);
            }

            if (numPresents >= targetValue)
                foundHouse = true;
            else
                houseNo++;
        }
        out("houseNo", houseNo);
    }

    public void out(Object... str) {
        String out = "";
        for (Object o : str) {
            if (out.length() > 0)
                out += " ";
            out += o;
        }
        System.out.println(out);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            result = result.replace(string, "");
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

}
