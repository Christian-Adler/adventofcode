public class Gear {
    int hitPoints = 0;
    int damage = 0;
    int armor = 0;
    int mana = 0;
    int spendMana = 0;

    public Gear() {

    }

    public Gear(int hitPoints, int damage, int armor, int mana, int spendMana) {
        this.hitPoints = hitPoints;
        this.damage = damage;
        this.armor = armor;
        this.mana = mana;
        this.spendMana = spendMana;
    }

    public Gear(Gear copyFrom) {
        this(copyFrom.hitPoints, copyFrom.damage, copyFrom.armor, copyFrom.mana, copyFrom.spendMana);
    }

    public Gear clone() {
        return new Gear(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Gear{");
        sb.append("hitPoints=").append(hitPoints);
        sb.append(", damage=").append(damage);
        sb.append(", armor=").append(armor);
        sb.append(", mana=").append(mana);
        sb.append(", spendMana=").append(spendMana);
        sb.append('}');
        return sb.toString();
    }
}
