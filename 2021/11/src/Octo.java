public class Octo {
    public static int flashes = 0;
    public Position pos;
    public int energy = 0;
    public boolean flasched = false;

    public Octo(int engery, Position pos) {
        this.energy = engery;
        this.pos = pos;
    }

    public void incrementEngery() {
        energy++;
    }

    public boolean willFlash() {
        return energy > 9 && !flasched;
    }

    public void flash() {
        flasched = true;
    }

    public void checkFlashed() {
        if (energy > 9) {
            flashes++;
            energy = 0;
            flasched = false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(energy);
    }
}
