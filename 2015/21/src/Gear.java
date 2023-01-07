import java.util.Objects;

public class Gear {
    int hitPoints = 0;
    int damage = 0;
    int armor = 0;

    public Gear() {

    }

    public Gear(int hitPoints, int damage, int armor) {
        this.hitPoints = hitPoints;
        this.damage = damage;
        this.armor = armor;
    }

    public Gear(Gear copyFrom) {
        this(copyFrom.hitPoints, copyFrom.damage, copyFrom.armor);
    }

    public Gear clone() {
        return new Gear(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gear gear = (Gear) o;
        return hitPoints == gear.hitPoints && damage == gear.damage && armor == gear.armor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hitPoints, damage, armor);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Gear{");
        sb.append("hitPoints=").append(hitPoints);
        sb.append(", damage=").append(damage);
        sb.append(", armor=").append(armor);
        sb.append('}');
        return sb.toString();
    }
}
