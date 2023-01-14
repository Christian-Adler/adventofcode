import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Task2 {

    final String regex = "\\(\\d+x\\d+\\)";

    final Pattern pattern = Pattern.compile(regex);


    public void init() {
    }

    public void addLine(String input) {
        LinkedList<Item> list = new LinkedList<>();

        int idx = 0;
        int idxBracket = input.indexOf("(");
        while (idxBracket >= 0) {
            if (idxBracket > idx)
                list.add(new Item(input.substring(idx, idxBracket)));

            int idxBracketClose = input.indexOf(")", idxBracket);

            list.add(new Item(input.substring(idxBracket, idxBracketClose + 1)));

            idx = idxBracketClose + 1;
            idxBracket = input.indexOf("(", idxBracketClose);

        }
        list.add(new Item(input.substring(idx)));

//        out(list);

        long decompressed = 0;

        long count = 0;
        while (!list.isEmpty()) {
            count++;
            if (count % 10000000 == 0)
                out(list.size(), " ", decompressed);
            Item item = list.removeFirst();
            if (!item.isMarker) {
                decompressed += item.length;
                continue;
            }

            List<Item> toRepeat = collectToNum(list, item.numLetters);

            for (int i = 0; i < item.numRepeats; i++) {
                list.addAll(0, toRepeat);
            }
        }

        out("decompressed:", decompressed);
    }

    List<Item> collectToNum(LinkedList<Item> list, int num) {
        List<Item> result = new LinkedList<>();
        int numCounter = 0;
        while (numCounter < num) {
            Item item = list.removeFirst();
            if (item.isMarker) {
                result.add(item);
                numCounter += item.length;
            } else {
                if (numCounter + item.length < num) {
                    result.add(item);
                    numCounter += item.length;
                } else {
                    int missingLength = num - numCounter;
                    result.add(new Item(missingLength));
                    numCounter += missingLength;

                    // Bei list den Rest aber wieder einhaengen!
                    if (item.length > missingLength)
                        list.add(0, new Item(item.length - missingLength));
                }
            }
        }
        return result;
    }

    public void afterParse() {
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {

        return "";
    }
}
