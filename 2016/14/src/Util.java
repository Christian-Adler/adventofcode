import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public static String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte[] digest = md.digest();
        return bytesToHex(digest).toLowerCase();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
