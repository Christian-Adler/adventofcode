public class Task {
    int xMin = 0;
    int xMax = 10;
    int yMin = 0;
    int yMax = 10;

    public void init() {
    }

    public void addLine(String input) {
    }

    public void afterParse() {
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
            input = input.replace(string, "");
        }
        return input;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public String toStringSVG() {
        int startL = 20;
        int endL = 50;
        int startS = 20;
        int endS = 100;

        int startH = 260;
        int endH = 70;

        int steps = 100; // Anzahl schritte aus Task...

        float stepL = (endL - startL) / (float) steps;
        float stepS = (endS - startS) / (float) steps;
        float stepH = (endH - startH) / (float) steps;


        StringBuilder builder = new StringBuilder();

        builder.append("\r\n");
        builder.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + (xMax + 3) + "\" height=\"" + (yMax + 3) + "\">");
        builder.append("\r\n");
        builder.append("<rect style=\"fill:#000000;\" width=\"" + (xMax + 3) + "\" height=\"" + (yMax + 3) + "\" x=\"0\" y=\"0\" />");
        builder.append("\r\n");
        builder.append("<g transform=\"translate(1,1)\">");
        for (int y = yMin; y <= yMax; y++) {
            builder.append("\r\n");
            for (int x = xMin; x <= xMax; x++) {
                Pos pos = new Pos(x, y);

                // Standard
                if ((x == xMin || x == xMax) && (y == yMin || y == yMax))
                    builder.append("<rect style=\"fill:#d0d0d0;\" width=\"1\" height=\"1\" x=\"" + pos.x + "\" y=\"" + pos.y + "\" />");

                else {
                    int numVisits = x * y;
                    if (numVisits % 10 == 0) {
                        float h = startH + numVisits * stepH;
                        float s = startS + numVisits * stepS;
                        float l = startL + numVisits * stepL;
                        String rgb = Util.HSLtoRGB(h, s, l);
                        builder.append("<rect style=\"fill:" + rgb + ";\" width=\"1\" height=\"1\" x=\"" + pos.x + "\" y=\"" + pos.y + "\" />");
                    }
                }
            }
        }
        builder.append("\r\n");
        builder.append("</g>");
        builder.append("\r\n");
        builder.append("</svg>");
        builder.append("\r\n");

        return builder.toString();
    }
}
