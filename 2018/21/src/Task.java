public class Task {

    public void init() {
    }

    public void addLine(String input) {
    }

    public void afterParse() {
// siehe https://github.com/tmrd993/advent-of-code-solutions/blob/master/src/main/java/2k18/aoc18/Day21.java

        // Part 1
        // Die einzige Instruction die Register 0 verwendet
        // eqrr 4 0 2
        // > Inhalt Reg 4 muss = Reg 0 sein, damit in 2 eine 1 geschrieben wird.
        // >> Programm laufen lassen, bis diese Instruction ausgefuehrt wird - dann sieht man in Reg 4 die Zahl, die in Reg 0 eingetragen werden muss.

        //        15690445

        // Part 2
        // Das Programm loopt unendlich.
        // In Reg 4 landen immer wieder sich wiederholende Zahlen
        // Wenn alle Zahlen aus Reg 4 gesammelt sind + jeweils die Anzahl Instruktionen bis zu diesem Wert, abbrechen und Max Wert und entsprechende Zahl ist das Ergebnis

        // 936387
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
