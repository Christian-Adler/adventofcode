import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Task {

    public void addLine(String input, boolean task2) {

        Set<Integer> findings = new HashSet<>();
        Map<Integer, String> idx2Hash = new HashMap<>();
        ArrayList<Integer> sortedKeys = new ArrayList<>();

        int idx = 0;
        int hashedIdx = 0;
        while (findings.size() < 64) {
            if (idx % 1000 == 0)
                out("idx: ", idx);
            // create hashes
            while (hashedIdx < idx + 1000) {
                String hash = null;
                try {
                    if (task2)
                        hash = hash2017(input + hashedIdx);
                    else
                        hash = Util.md5(input + hashedIdx);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                String trippleLetter = firstTripple(hash);
                if (trippleLetter != null) {
                    idx2Hash.put(hashedIdx, hash);
                    sortedKeys.add(hashedIdx);
                }
                hashedIdx++;
            }

            // if tripple check next 1000 hashes
            String actHash = idx2Hash.get(idx);
            if (actHash != null) {
                String testLetter = firstTripple(actHash);
                if (testLetter != null) {
                    for (int testIdx : sortedKeys) {
                        if (testIdx > idx && testIdx < idx + 1000) {
                            String testHash = idx2Hash.get(testIdx);
                            boolean contains5 = contains5InARow(testHash, testLetter);
                            if (contains5) {
                                findings.add(idx);
                                out("found key ", testLetter, "at", idx);
                                if (findings.size() == 64) {
                                    out("64 found at:", testIdx, " by ", idx); // 17741 to low 20219, 20799 too high
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if (findings.size() < 64)
                idx++;
        }
    }

    String firstTripple(String input) {
        char c0 = input.charAt(0);
        char c1 = input.charAt(1);
        for (int ci = 2; ci < input.length(); ci++) {
            char c = input.charAt(ci);
            if (c == c0 && c == c1)
                return String.valueOf(c);
            c0 = c1;
            c1 = c;
        }
        return null;
    }

    String hash2017(String str) throws NoSuchAlgorithmException {
        String hash = Util.md5(str);
        for (int i = 0; i < 2016; i++) {
            hash = Util.md5(hash);
        }
        return hash;
    }

    boolean contains5InARow(String input, String letter) {
        String search = letter.repeat(5);
        return input.contains(search);
    }

    public void out(Object... str) {
        Util.out(str);
    }

}
