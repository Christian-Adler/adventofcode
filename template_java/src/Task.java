public class Task {

    public void init() {
    }

    public void addLine(String input) {
    }

    public void afterParse() {
    }

    public void out(Object... str) {
        Util.out(str);
    }
	
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public String toStringSVG() {
           SVG svg = new SVG();
		   return svg.toSVGStringAged();
    }
	
	
    public String toStringConsole() {
        SVG svg = new SVG();
        return svg.toConsoleString();
    }
}
