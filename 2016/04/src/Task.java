import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task {
    long sumOfSectorIds = 0;
    final String regex = "([a-z-]+)(\\d+)\\[(\\w+)\\]";
    final Pattern pattern = Pattern.compile(regex);

    public void init() {
    }

    public void addLine(String input) {
        final Matcher matcher = pattern.matcher(input);

        String encryptedName = null;
        long sectorId = -1;
        String checksum = null;
        while (matcher.find()) {
//            System.out.println("Full match: " + matcher.group(0));

            if (matcher.groupCount() != 3)
                throw new IllegalArgumentException("invalid input");

            encryptedName = matcher.group(1);
            sectorId = Long.parseLong(matcher.group(2));
            checksum = matcher.group(3);
        }

        if (encryptedName == null || checksum == null)
            throw new IllegalArgumentException("invalid input 2");

        ArrayList<String> charList = Util.str2List(encryptedName);
        Map<String, Integer> charMap = new HashMap<>();
        for (String s : charList) {
            if (s.equals("-")) continue;
            charMap.compute(s, (k, v) -> v == null ? 1 : v + 1);
        }
//        out("charMap", charMap);
        List<String> sortedCharList = charMap.entrySet().stream().sorted((e1, e2) -> {
            int cmp = Long.compare(e2.getValue(), e1.getValue());
            if (cmp == 0) // Anzahl gleich > nach Str sortieren
                cmp = e1.getKey().compareTo(e2.getKey());
            return cmp;
        }).map(Map.Entry::getKey).collect(Collectors.toList());
        sortedCharList = sortedCharList.subList(0, 5);
//        out(sortedCharList);

        String checksumShouldBe = String.join("", sortedCharList);
//        out("checksumShouldBe", checksumShouldBe);

        if (checksumShouldBe.equals(checksum)) {
            sumOfSectorIds += sectorId;

            // Decrypt
            String decrypt = encryptedName.replaceAll("-", " ");
            ArrayList<String> decryptList = Util.str2List(decrypt);
            for (int i = 0; i < decryptList.size(); i++) {
                String c = decryptList.get(i);
                if (c.equals(" ")) continue;

                decryptList.set(i, decrypt(c, sectorId));
            }
            String decrypted = String.join("", decryptList);
//            out(decrypted);
            if (decrypted.contains("north"))
                out("SectorId for north:", sectorId);
        }
    }

    String decrypt(String s, long num) {
        char c = s.charAt(0);
        int change = (int) c - 97;
        int changed = (int) (change + num) % 26;
        int back2char = changed + 97;
        char cDecrypted = (char) back2char;
        return String.valueOf(cDecrypted);
    }

    public void afterParse() {
        out("sumOfSectorIds", sumOfSectorIds);
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
