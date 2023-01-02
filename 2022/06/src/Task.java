import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class Task {



    public void addLine(String input) {

        int markerLength = 14;

        for (int i = 0; i < input.length()-markerLength; i++) {
            String sub = input.substring(i, i + markerLength);
            HashSet<String> set = new HashSet<>(Arrays.stream(sub.split("")).toList());
            if(set.size()==markerLength){
                System.out.println(i+markerLength);
                break;
            }
        }


    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();


        return builder.toString();
    }
}
