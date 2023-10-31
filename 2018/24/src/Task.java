import java.util.*;

public class Task {

    Army immune = null;
    Army infection = null;

    public void init() {
    }

    public void addLine(String input) {
        if (input.trim().isEmpty())
            return;

        if (input.startsWith("Immune")) {
            immune = new Army(ArmyType.IMMUNE);
            return;
        } else if (input.startsWith("Infection")) {
            infection = new Army(ArmyType.INFECTION);
            return;
        }

        Army actArmy = infection != null ? infection : immune;

        Group.parseGroupAndAddToArmy(input, actArmy);
    }


    public void afterParse() {
//        out(immune);
//        out(infection);

        part1();
        part2();
    }

    private void part1() {
        while (!immune.groups.isEmpty() && !infection.groups.isEmpty()) {
            // targetSelection
            ArrayList<Group> allGroups = new ArrayList<>();
            allGroups.addAll(immune.groups);
            allGroups.addAll(infection.groups);

            allGroups.forEach(g -> g.isSelectedTarget = false);

            allGroups.sort(Comparator.comparingInt(Group::effectivePower).thenComparingInt(g -> g.initiative).reversed());
//            out();
//            out("target selection");
//            out(allGroups);

            // Choose target
            Map<Group, Group> attacker2target = new HashMap<>();

            for (Group group : allGroups) {
                if (group.units == 0)
                    continue;

                Army targetArmy = infection.armyType.equals(group.armyType) ? immune : infection;
                List<Group> possibleTargetGroups = targetArmy.groups.stream().filter(g -> !g.isSelectedTarget).filter(g -> g.units > 0).toList();

                int maxDealableDamage = 0;
                Group targetGroup = null;
                for (Group possibleTargetGroup : possibleTargetGroups) {
                    // calc damage
                    int damageWouldBe = calcDamage(group, possibleTargetGroup);

                    if (damageWouldBe > maxDealableDamage) {
                        maxDealableDamage = damageWouldBe;
                        targetGroup = possibleTargetGroup;
                    } else if (damageWouldBe == maxDealableDamage && targetGroup != null
                            && (possibleTargetGroup.effectivePower() > targetGroup.effectivePower()
                            || possibleTargetGroup.effectivePower() == targetGroup.effectivePower() && possibleTargetGroup.initiative > targetGroup.initiative))
                        targetGroup = possibleTargetGroup;
                }

                if (targetGroup != null) {
                    targetGroup.isSelectedTarget = true;
                    attacker2target.put(group, targetGroup);
                }
            }

//            out();
//            out("Selected targets");
//            out(attacker2target);

            // attacking
            allGroups.sort(Comparator.comparingInt(g -> g.initiative));
            Collections.reverse(allGroups);

//            out();
//            out("Attack");
//            out(allGroups);

            for (Group group : allGroups) {
                if (group.units == 0) continue;

                Group target = attacker2target.get(group);
                if (target == null) continue;

                int damage = calcDamage(group, target);
                boolean killedGroup = target.attacked(damage);

                if (killedGroup) {
                    Army targetArmy = infection.armyType.equals(target.armyType) ? infection : immune;
                    targetArmy.remove(target);
                }
            }
        }

        int winningArmyUnits = 0;

        if (!immune.groups.isEmpty()) {
            out("winning army", immune.armyType);
            winningArmyUnits = immune.groups.stream().mapToInt(g -> g.units).sum();
        } else {
            out("winning army", infection.armyType);
            winningArmyUnits = infection.groups.stream().mapToInt(g -> g.units).sum();
        }

        out("Part 1", "winningArmyUnits", winningArmyUnits);
    }

    private void part2() {
        int addBoost = 1;

        int winningArmyUnits = -1;
        while (winningArmyUnits < 0) {
            addBoost *= 2;

            winningArmyUnits = runWar(addBoost);

//            out("winning army", winningArmyType);
        }
        int toSmall = addBoost / 2;
        int toLarge = addBoost;
        out("boost between", toSmall, toLarge);

        while (Math.abs(toSmall - toLarge) > 1) { // 1 nicht mehr aufteilbar -> man landet in Endloschleife
            addBoost = toSmall + (toLarge - toSmall) / 2;
            winningArmyUnits = runWar(addBoost);
            if (winningArmyUnits < 0)
                toSmall = addBoost;
            else
                toLarge = addBoost;
        }

        // reicht samll? Wenn nicht, dann large
        int runWarResult = runWar(toSmall);
        if (runWarResult < 0) {
            out("Part 2", "required boost", toLarge);
            runWarResult = runWar(toLarge);
        } else
            out("Part 2", "required boost", toSmall);
        out("Part 2", "left immune units", runWarResult);

    }

    private int runWar(int boost) {
        immune.reset();
        infection.reset();
        for (Group group : immune.groups) {
            group.boost(boost);
        }

        while (!immune.groups.isEmpty() && !infection.groups.isEmpty()) {
            // targetSelection
            ArrayList<Group> allGroups = new ArrayList<>();
            allGroups.addAll(immune.groups);
            allGroups.addAll(infection.groups);

            allGroups.forEach(g -> g.isSelectedTarget = false);

            allGroups.sort(Comparator.comparingInt(Group::effectivePower).thenComparingInt(g -> g.initiative).reversed());
//            out();
//            out("target selection");
//            out(allGroups);

            // Choose target
            Map<Group, Group> attacker2target = new HashMap<>();

            for (Group group : allGroups) {
                if (group.units == 0)
                    continue;

                Army targetArmy = infection.armyType.equals(group.armyType) ? immune : infection;
                List<Group> possibleTargetGroups = targetArmy.groups.stream().filter(g -> !g.isSelectedTarget).filter(g -> g.units > 0).toList();

                int maxDealableDamage = 0;
                Group targetGroup = null;
                for (Group possibleTargetGroup : possibleTargetGroups) {
                    // calc damage
                    int damageWouldBe = calcDamage(group, possibleTargetGroup);

                    if (damageWouldBe > maxDealableDamage) {
                        maxDealableDamage = damageWouldBe;
                        targetGroup = possibleTargetGroup;
                    } else if (damageWouldBe == maxDealableDamage && targetGroup != null
                            && (possibleTargetGroup.effectivePower() > targetGroup.effectivePower()
                            || possibleTargetGroup.effectivePower() == targetGroup.effectivePower() && possibleTargetGroup.initiative > targetGroup.initiative))
                        targetGroup = possibleTargetGroup;
                }

                if (targetGroup != null) {
                    targetGroup.isSelectedTarget = true;
                    attacker2target.put(group, targetGroup);
                }
            }

//            out();
//            out("Selected targets");
//            out(attacker2target);

            // attacking
            allGroups.sort(Comparator.comparingInt(g -> g.initiative));
            Collections.reverse(allGroups);

//            out();
//            out("Attack");
//            out(allGroups);

            int unitsAllGroups = allGroups.stream().mapToInt(g -> g.units).sum();

            for (Group group : allGroups) {
                if (group.units == 0) continue;

                Group target = attacker2target.get(group);
                if (target == null) continue;

                int damage = calcDamage(group, target);
                boolean killedGroup = target.attacked(damage);

                if (killedGroup) {
                    Army targetArmy = infection.armyType.equals(target.armyType) ? infection : immune;
                    targetArmy.remove(target);
                }
            }

            int unitsAllGroupsAfterAttack = allGroups.stream().mapToInt(g -> g.units).sum();
            // Gleichstand?
            if (unitsAllGroupsAfterAttack == unitsAllGroups) {
                out("Unentschieden bei boost ", boost);
                return -1;
            }
        }

        int winningArmyUnits;
        if (!immune.groups.isEmpty()) {
            winningArmyUnits = immune.groups.stream().mapToInt(g -> g.units).sum();
        } else {
            winningArmyUnits = -1;
        }
        return winningArmyUnits;
    }

    private static int calcDamage(Group attackingGroup, Group targetGroup) {
        AttackType attackType = attackingGroup.attackType;
        int damageWouldBe = attackingGroup.effectivePower();
        if (targetGroup.immunities.contains(attackType))
            damageWouldBe = 0;
        else if (targetGroup.weaknesses.contains(attackType))
            damageWouldBe *= 2;
        return damageWouldBe;
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
