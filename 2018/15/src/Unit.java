public class Unit {
    boolean isElfe;
    Pos p;
    int hitPoints = 200;
    int attackPower = 3;

    public Unit(String input, Pos p) {
        this.p = p;
        this.isElfe = input.equals("E");
    }

    public Unit copy() {
        return new Unit(toUnitString(), p.copy());
    }

    public String toUnitString() {
        return isElfe ? "E" : "G";
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Unit{");
        sb.append(toUnitString());
        sb.append(", ").append(p);
        sb.append(", healthPoints=").append(hitPoints);
        sb.append('}');
        return sb.toString();
    }

    public int getY() {
        return p.y;
    }

    public int getX() {
        return p.x;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * @return true if killed
     */
    public boolean attack(int attackP) {
        hitPoints -= attackP;
        return hitPoints <= 0;
    }
}
