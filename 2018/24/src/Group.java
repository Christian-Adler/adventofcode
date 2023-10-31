import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Group {
    final int groupId;
    final ArmyType armyType;
    final int unitHitPoints;
    int unitAttackDamage;
    final AttackType attackType;
    final int initiative;
    final Set<AttackType> weaknesses;
    final Set<AttackType> immunities;

    int units = 0;
    final int unitsStartValue;
    final int attackStartValue;

    boolean isSelectedTarget = false;

    public Group(int groupId, ArmyType armyType, AttackType attackType, int units, int unitAttackDamage, int unitHitPoints, int initiative, Set<AttackType> weaknesses, Set<AttackType> immunities) {
        this.groupId = groupId;
        this.armyType = armyType;
        this.attackType = attackType;
        this.unitAttackDamage = unitAttackDamage;
        this.unitHitPoints = unitHitPoints;
        this.initiative = initiative;
        this.weaknesses = weaknesses;
        this.immunities = immunities;
        this.units = units;
        this.unitsStartValue = units;
        this.attackStartValue = unitAttackDamage;
    }

    void reset() {
        units = unitsStartValue;
    }

    void boost(int boost) {
        unitAttackDamage = attackStartValue + boost;
    }

    int effectivePower() {
        return units * unitAttackDamage;
    }

    boolean attacked(int damage) {
        int killsUnits = damage / unitHitPoints;
        units -= killsUnits;
        if (units < 0)
            units = 0;
        return units == 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\r\nGroup{");
        sb.append("armyType=").append(armyType);
        sb.append(", groupId=").append(groupId);
        sb.append(", units=").append(units);
        sb.append(", unitHitPoints=").append(unitHitPoints);
        sb.append(", attackType=").append(attackType);
        sb.append(", unitAttackDamage=").append(unitAttackDamage);
        sb.append(", effectivePower=").append(effectivePower());
        sb.append(", initiative=").append(initiative);
        sb.append(", weaknesses=").append(weaknesses);
        sb.append(", immunities=").append(immunities);
        sb.append(", isSelectedTarget=").append(isSelectedTarget);
        sb.append('}');
        return sb.toString();
    }

    public static void parseGroupAndAddToArmy(String input, Army actArmy) {
        int numUnits = Integer.parseInt(input.substring(0, input.indexOf("units")).trim());
        int hitPoints = Integer.parseInt(input.substring(input.indexOf("each with") + 9, input.indexOf("hit points")).trim());
        int initiative = Integer.parseInt(input.substring(input.indexOf("initiative") + 10).trim());

        int idxBraceOpen = input.indexOf('(');
        int idxBraceClose = input.indexOf(')');

        Set<AttackType> weaknesses = new HashSet<>();
        Set<AttackType> immunities = new HashSet<>();

        String[] split;
        if (idxBraceClose >= 0) {
            split = input.substring(idxBraceOpen + 1, idxBraceClose).split("; ");
            for (String s : split) {
                if (s.startsWith("weak"))
                    weaknesses = new HashSet<>(Arrays.stream(Util.cleanFrom(s, "weak to").split(",")).map(AttackType::getAttackType).toList());
                else if (s.startsWith("immune"))
                    immunities = new HashSet<>(Arrays.stream(Util.cleanFrom(s, "immune to").split(",")).map(AttackType::getAttackType).toList());
            }
        } else { // no weakness or immunes
            idxBraceClose = input.indexOf("points") + 6;
        }

        split = input.substring(idxBraceClose).split("\\s+");
        int attackDamage = Integer.parseInt(split[6].trim());
        AttackType attackType = AttackType.getAttackType(split[7].trim());

        actArmy.addNewGroup(attackType, numUnits, attackDamage, hitPoints, initiative, weaknesses, immunities);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId == group.groupId && armyType == group.armyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, armyType);
    }
}
