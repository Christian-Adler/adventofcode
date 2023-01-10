import java.util.ArrayList;
import java.util.Arrays;

public class Util {
    public static ArrayList<String> str2List(String input) {
        return new ArrayList<String>(Arrays.asList(input.split("")));
    }

    public static String cleanFrom(String input, String... strings) {
        String result = input;
        for (String string : strings) {
            result = result.replace(string, "");
        }
        return result;
    }

    public static void out(Object... str) {
        StringBuilder out = new StringBuilder();
        for (Object o : str) {
            if (out.length() > 0)
                out.append(" ");
            out.append(o);
        }
        System.out.println(out);
    }
}
