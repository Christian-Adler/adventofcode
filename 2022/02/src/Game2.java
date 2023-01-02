import java.util.HashMap;
import java.util.Map;

public class Game2 {
static Map<String,Integer> mapping = new HashMap<>();
static {
    // A  Stein : 1 ,  B  Papier : 2, C  Schere : 3
    // X Verloren 0, Y Unentschieden 3 Z Gewinn 6
    mapping.put("A X",3+0);
    mapping.put("A Y",1+3);
    mapping.put("A Z",2+6);
    mapping.put("B X",1+0);
    mapping.put("B Y",2+3);
    mapping.put("B Z",3+6);
    mapping.put("C X",2+0);
    mapping.put("C Y",3+3);
    mapping.put("C Z",1+6);
}

private int score = 0;

    public void addLine(String input) {
        score+= mapping.get(input);
    }

    public void printScore(){
        System.out.println("Score:"+score);
    }

}
