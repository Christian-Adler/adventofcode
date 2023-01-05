public class Task {

    long sumCharactersOfCode = 0;
    long sumCharactersData = 0;

    public void init() {
    }

    public void addLine(String input) {
        int charactersOfCode = input.length();

        String work = input;

        int idx = -1;
        // \\
        idx = work.indexOf("\\\\");
        while (idx >= 0) {
            work = work.substring(0, idx) + "_" + work.substring(idx + 2);
            idx = work.indexOf("\\\\");
        }  // \"
        idx = work.indexOf("\\\"");
        while (idx >= 0) {
            work = work.substring(0, idx) + "~" + work.substring(idx + 2);
            idx = work.indexOf("\\\"");
        }

        work = work.replaceAll("\\\\x[a-f\\d][a-f\\d]", "#");

        int charactersOfData = work.length() - 2; //-2 wg umschliessenden Anfuehrungszeichen

        sumCharactersOfCode += charactersOfCode;
        sumCharactersData += charactersOfData;
    }

    public void afterParse() {
        out("sumCharactersOfCode:", sumCharactersOfCode);
        out("sumCharactersData:", sumCharactersData);

        long diff = sumCharactersOfCode - sumCharactersData;
        out("diff", diff);
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

    public void ou(Object str) {
        System.out.print(str);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            input = input.replace(string, "");
        }
        return input;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

}
