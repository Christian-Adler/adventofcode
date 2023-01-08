import java.util.Objects;

public class Spell {
    final String name;
    final int costs;
    final int damagePerRound;
    final int healsPerRound;
    final int armorWhileActive;
    final int manaPerRound;
    final int turns;
    int round = -1;

    public Spell(String name, int costs, int damagePerRound, int healsPerRound, int armorWhileActive, int manaPerRound, int turns) {
        this.name = name;
        this.costs = costs;
        this.damagePerRound = damagePerRound;
        this.healsPerRound = healsPerRound;
        this.armorWhileActive = armorWhileActive;
        this.manaPerRound = manaPerRound;
        this.turns = turns;
    }

    Spell(Spell other, int round) {
        this(other.name, other.costs, other.damagePerRound, other.healsPerRound, other.armorWhileActive, other.manaPerRound, other.turns);
        this.round = round;
    }

    public Spell clone() {
        return new Spell(this, this.round);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Spell{");
        sb.append("name='").append(name).append('\'');
        sb.append(", costs=").append(costs);
        sb.append(", damagePerRound=").append(damagePerRound);
        sb.append(", healsPerRound=").append(healsPerRound);
        sb.append(", armorWhileActive=").append(armorWhileActive);
        sb.append(", manaPerRound=").append(manaPerRound);
        sb.append(", turns=").append(turns);
        sb.append(", round=").append(round);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spell spell = (Spell) o;
        return Objects.equals(name, spell.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
