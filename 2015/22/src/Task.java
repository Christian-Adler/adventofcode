import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Task {
    boolean useExample = false;
    Gear bossGear;
    List<Spell> spells = new ArrayList<>();

    int soFarMinSpendMana = Integer.MAX_VALUE;

    void minSoFar(int minSpendMana) {
        if (minSpendMana < soFarMinSpendMana) {
            soFarMinSpendMana = minSpendMana;
            out("SoFarMinSpendMana", soFarMinSpendMana);
        }
    }

    public void init() {
        bossGear = new Gear(55, 8, 0, 0, 0);
        if (useExample)
//            bossGear = new Gear(13, 8, 0, 0, 0);
            bossGear = new Gear(14, 8, 0, 0, 0);

        // (String name, int costs, int damagePerRound, int healsPerRound, int manaPerRound, int turns)
        spells.add(new Spell("Recharge", 229, 0, 0, 0, 101, 5));
        spells.add(new Spell("Poison", 173, 3, 0, 0, 0, 6));
        spells.add(new Spell("Shield", 113, 0, 0, 7, 0, 6));
        spells.add(new Spell("Drain", 73, 2, 2, 0, 0, 1));
        spells.add(new Spell("Magic Missile", 53, 4, 0, 0, 0, 1));
    }

    public void afterParse() {
        Gear me = new Gear(50, 0, 0, 500, 0);

        if (useExample)
            me = new Gear(10, 0, 0, 250, 0);

        HashMap<String, Spell> activeSpells = new HashMap<>();

        int minMana = minMana(me, activeSpells, "", bossGear, 1);
        out("minSpendMana:", minMana);
    }

    int minMana(Gear me, HashMap<String, Spell> activeSpells, String soFarSpells, Gear boss, int turn) {
        if (me.spendMana > soFarMinSpendMana)
            return Integer.MAX_VALUE;

        Gear b = boss.clone();
        Gear m = me.clone();

        // Player Turn Spells
        HashMap<String, Spell> afterPlayerExecActiveSpells = execActiveSpells(activeSpells, b, m);

        // buy one spell
        List<Spell> buyAbleSpells = new ArrayList<>();
        for (Spell spell : spells) {
            Spell alreadyActive = activeSpells.get(spell.name);
            if (alreadyActive != null && alreadyActive.round > 1)
                continue;
            if (spell.costs > m.mana)
                continue;

            buyAbleSpells.add(spell.clone());
        }

        if (buyAbleSpells.isEmpty())
            return Integer.MAX_VALUE; // Verloren

        int minSpendMana = Integer.MAX_VALUE;

        for (Spell buySpell : buyAbleSpells) {
            Gear mG = m.clone();
            Gear bG = b.clone();
            mG.mana -= buySpell.costs;
            mG.spendMana += buySpell.costs;
            buySpell.round = buySpell.turns;

            HashMap<String, Spell> beforeBossExecActiveSpells = new HashMap<>();
            for (Spell spell : afterPlayerExecActiveSpells.values()) {
                beforeBossExecActiveSpells.put(spell.name, spell.clone());
            }

            // Turn player
            // sofort wirkende
            if (buySpell.name.equals("Magic Missile") || buySpell.name.equals("Drain")) {
                bG.hitPoints -= buySpell.damagePerRound;
                mG.hitPoints += buySpell.healsPerRound;
            } else
                beforeBossExecActiveSpells.put(buySpell.name, buySpell);

            if (bG.hitPoints <= 0) { // Boss erledigt?
                minSpendMana = Math.min(minSpendMana, mG.spendMana);
                minSoFar(minSpendMana);
//                out(minSpendMana, soFarSpells, " - ", buySpell.name);
                continue;
            }

            // Turn boss
            // exec Spells
            HashMap<String, Spell> afterBossTurnActiveSpells = execActiveSpells(beforeBossExecActiveSpells, bG, mG);

            if (bG.hitPoints <= 0) { // Boss erledigt?
                minSpendMana = Math.min(minSpendMana, mG.spendMana);
                minSoFar(minSpendMana);
//                out(minSpendMana, soFarSpells, buySpell.name);
                continue;
            }

            // Boss damages Player
            mG.hitPoints -= Math.max(1, bG.damage - mG.armor);
            if (mG.hitPoints <= 0) {
//                out("Verloren ", soFarSpells, " - ", buySpell.name);
                continue; // verloren
            }

            //naechste Runde

            int subMin = minMana(mG, afterBossTurnActiveSpells, soFarSpells + ", " + buySpell.name, bG, turn + 1);
            minSpendMana = Math.min(minSpendMana, subMin);
        }

        return minSpendMana;
    }

    private static HashMap<String, Spell> execActiveSpells(HashMap<String, Spell> activeSpells, Gear boss, Gear me) {
        HashMap<String, Spell> afterTurnActiveSpells = new HashMap<>();
        for (Spell activeSpell : activeSpells.values()) {
            Spell spell = activeSpell.clone();

            boss.hitPoints -= spell.damagePerRound;
            me.hitPoints += spell.healsPerRound;
            me.mana += spell.manaPerRound;

            // Armor nur aktiv - nicht jedes Mal dazu
            if (spell.armorWhileActive > 0 && spell.round == spell.turns)
                me.armor += spell.armorWhileActive;

            spell.round--;

            // Armor entfernen sobald nicht mehr aktiv
            if (spell.armorWhileActive > 0 && spell.round == 0)
                me.armor -= spell.armorWhileActive;

            if (spell.round > 0) // noch aktiv?
                afterTurnActiveSpells.put(spell.name, spell);
        }
        return afterTurnActiveSpells;
    }

    public void out(Object... str) {
        StringBuilder out = new StringBuilder();
        for (Object o : str) {
            if (out.length() > 0)
                out.append(" ");
            out.append(o);
        }
        System.out.println(out);
    }

    @Override
    public String toString() {
        return "-";
    }

}
