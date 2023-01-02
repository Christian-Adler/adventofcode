import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Cave {
    private final Set<Octo> octos = new HashSet<>();
    private final Octo[][] grid = new Octo[10][10];


    public void setOcto(Octo octo) {
        grid[octo.pos.x][octo.pos.y] = octo;
        octos.add(octo);
    }

    public boolean step() {
//        System.out.println();

        octos.forEach(octo -> octo.incrementEngery());

        boolean foundFlash = true;
        while (foundFlash) {
            foundFlash = false;

            Optional<Octo> optionalOcto = octos.stream().filter(octo -> octo.willFlash()).findFirst();
            if (optionalOcto.isPresent()) {
                foundFlash = true;
                Octo octo = optionalOcto.get();
                octo.flash();

                // Engery drum herum steigern
                Position pos = octo.pos;
                Set<Position> neighbors = getNeighbors(pos);
                for (Position posN : neighbors) {
                    grid[posN.x][posN.y].incrementEngery();
                }
            }
        }

        octos.forEach(octo -> octo.checkFlashed());


        // Alle Ocots 0? Alle geflashed?
        return octos.stream().mapToInt(octo -> octo.energy).sum() == 0;
    }

    private Set<Position> getNeighbors(Position pos) {
        Set<Position> neighbors = new HashSet<>();

        for (int y = -1; y < 2; y++) {
            for (int x = -1; x < 2; x++) {
                neighbors.add(new Position(x + pos.x, y + pos.y));
            }
        }

        return neighbors.stream().filter(posN -> posN.y >= 0 && posN.y <= 9 && posN.x >= 0 && posN.x <= 9 && !posN.equals(pos)).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            int x = i % 10;
            int y = i / 10;

            Octo octo = grid[x][y];

            if (x == 0 && i != 0)
                builder.append("\r\n");
            builder.append(octo);
        }
        return builder.toString();
    }
}
