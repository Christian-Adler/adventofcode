import java.util.*;

public class Game {
static Map<String,Integer> mapping = new HashMap<>();
static {
    // A X Stein : 1 ,  B Y Papier : 2, C Z Schere : 3
    // Verloren 0, Unentschieden 3 Gewinn 6
    mapping.put("A X",1+3);
    mapping.put("A Y",2+6);
    mapping.put("A Z",3+0);
    mapping.put("B X",1+0);
    mapping.put("B Y",2+3);
    mapping.put("B Z",3+6);
    mapping.put("C X",1+6);
    mapping.put("C Y",2+0);
    mapping.put("C Z",3+3);
}

private int score = 0;

    public void addLine(String input) {
        score+= mapping.get(input);
    }

    public void printScore(){
        System.out.println("Score:"+score);
    }

}
