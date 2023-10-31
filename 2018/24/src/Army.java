import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Army {
    final ArmyType armyType;
    final List<Group> groups = new ArrayList<>();
    final Set<Group> removedGroups = new HashSet<>();

    public Army(ArmyType at) {
        this.armyType = at;
    }

    public Group addNewGroup(AttackType attackType, int units, int unitAttackDamage, int unitHitPoints, int initiative, Set<AttackType> weaknesses, Set<AttackType> immunities) {
        Group grp = new Group(groups.size() + 1, armyType, attackType, units, unitAttackDamage, unitHitPoints, initiative, weaknesses, immunities);
        groups.add(grp);
        return grp;
    }

    public void remove(Group group) {
        removedGroups.add(group);
        groups.remove(group);
    }

    public void reset() {
        groups.addAll(removedGroups);
        removedGroups.clear();
        groups.forEach(Group::reset);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Army{");
        sb.append("armyType='").append(armyType).append('\'');
        sb.append(", groups=");
        for (Group group : groups) {
            sb.append(group);
        }
        sb.append('}');
        return sb.toString();
    }
}
