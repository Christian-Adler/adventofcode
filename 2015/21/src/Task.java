import java.util.HashSet;
import java.util.Set;

public class Task {

    Gear bossGear = new Gear();

    Set<ShopItem> shopWeapons = new HashSet<>();
    Set<ShopItem> shopArmor = new HashSet<>();
    Set<ShopItem> shopRings = new HashSet<>();

    public void init() {
    }

    public void addLine(String input) {
        if (input.startsWith("Hit"))
            bossGear.hitPoints = Integer.parseInt(cleanFrom(input, "Hit Points:").trim());
        else if (input.startsWith("Damage"))
            bossGear.damage = Integer.parseInt(cleanFrom(input, "Damage:").trim());
        else if (input.startsWith("Armor"))
            bossGear.armor = Integer.parseInt(cleanFrom(input, "Armor:").trim());
    }

    void addToShop(String type, String name, int cost, int damage, int armor) {
        Set<ShopItem> shop = null;
        if (type.startsWith("Wea"))
            shop = shopWeapons;
        else if (type.startsWith("Ar"))
            shop = shopArmor;
        else shop = shopRings;

        shop.add(new ShopItem(type, name, cost, damage, armor));
    }

    public void afterParse() {
        // Weapons: Cost  Damage  Armor - genau 1
        addToShop("Weapon", "Dagger", 8, 4, 0);
        addToShop("Weapon", "Shortsword", 10, 5, 0);
        addToShop("Weapon", "Warhammer", 25, 6, 0);
        addToShop("Weapon", "Longsword", 40, 7, 0);
        addToShop("Weapon", "Greataxe", 74, 8, 0);

        // Armor: Cost  Damage  Armor - max 1
        addToShop("Armor", "Leather", 13, 0, 1);
        addToShop("Armor", "Chainmail", 31, 0, 2);
        addToShop("Armor", "Splintmail", 53, 0, 3);
        addToShop("Armor", "Bandedmail", 75, 0, 4);
        addToShop("Armor", "Platemail", 102, 0, 5);

        // "Rings: Cost  Damage  Armor - 0-2
        addToShop("Rings", "Damage +1", 25, 1, 0);
        addToShop("Rings", "Damage +2", 50, 2, 0);
        addToShop("Rings", "Damage +3", 100, 3, 0);
        addToShop("Rings", "Defense +1", 20, 0, 1);
        addToShop("Rings", "Defense +2", 40, 0, 2);
        addToShop("Rings", "Defense +3", 80, 0, 3);

        out("boss", bossGear);

        int minGold = Integer.MAX_VALUE;
        int maxGoldAndStillLose = Integer.MIN_VALUE;

        for (ShopItem weapon : shopWeapons) {
            int costsWeapon = weapon.cost();
            Gear playerWithWeapon = new Gear(100, weapon.damage(), weapon.armor());
            if (winable(playerWithWeapon, bossGear))
                minGold = Math.min(minGold, costsWeapon);
            else {
                maxGoldAndStillLose = Math.max(maxGoldAndStillLose, costsWeapon);
                for (int a = 0; a <= 1; a++) {
                    if (a == 1) {
                        for (ShopItem armor : shopArmor) {
                            int costsWithArmor = costsWeapon + armor.cost();
                            Gear playerWithArmor = playerWithWeapon.clone();
                            playerWithArmor.armor += armor.armor();

                            if (winable(playerWithArmor, bossGear))
                                minGold = Math.min(minGold, costsWithArmor);
                            else {
                                maxGoldAndStillLose = Math.max(maxGoldAndStillLose, costsWithArmor);
// >>
                                for (ShopItem ring : shopRings) {
                                    int costsWithRing = costsWithArmor + ring.cost();
                                    Gear playerWith1Ring = playerWithArmor.clone();
                                    playerWith1Ring.armor += ring.armor();
                                    playerWith1Ring.damage += ring.damage();

                                    if (winable(playerWith1Ring, bossGear))
                                        minGold = Math.min(minGold, costsWithRing);
                                    else {
                                        maxGoldAndStillLose = Math.max(maxGoldAndStillLose, costsWithRing);
                                        for (ShopItem ring2 : shopRings) {
                                            if (ring.equals(ring2)) continue;
                                            int costsWithRing2 = costsWithRing + ring2.cost();
                                            Gear playerWith2Ring = playerWith1Ring.clone();
                                            playerWith2Ring.armor += ring2.armor();
                                            playerWith2Ring.damage += ring2.damage();

                                            if (winable(playerWith2Ring, bossGear))
                                                minGold = Math.min(minGold, costsWithRing2);
                                            else
                                                maxGoldAndStillLose = Math.max(maxGoldAndStillLose, costsWithRing2);
                                        }
                                    }
                                }
// <<
                            }
                        }
                    } else {
                        for (ShopItem ring : shopRings) {
                            int costsWithRing = costsWeapon + ring.cost();
                            Gear playerWith1Ring = playerWithWeapon.clone();
                            playerWith1Ring.armor += ring.armor();
                            playerWith1Ring.damage += ring.damage();

                            if (winable(playerWith1Ring, bossGear))
                                minGold = Math.min(minGold, costsWithRing);
                            else {
                                maxGoldAndStillLose = Math.max(maxGoldAndStillLose, costsWithRing);
                                for (ShopItem ring2 : shopRings) {
                                    if (ring.equals(ring2)) continue;
                                    int costsWithRing2 = costsWithRing + ring2.cost();
                                    Gear playerWith2Ring = playerWith1Ring.clone();
                                    playerWith2Ring.armor += ring2.armor();
                                    playerWith2Ring.damage += ring2.damage();

                                    if (winable(playerWith2Ring, bossGear))
                                        minGold = Math.min(minGold, costsWithRing2);
                                    else
                                        maxGoldAndStillLose = Math.max(maxGoldAndStillLose, costsWithRing2);
                                }
                            }
                        }
                    }
                }
            }
        }

        out("minGold:", minGold);
        out("maxGoldAndStillLose:", maxGoldAndStillLose);
    }

    boolean winable(Gear p, Gear b) {
        Gear player = p.clone();
        Gear boss = b.clone();

        int count = 0;

        while (true) {
            count++;

            if (count > 2 && player.equals(p) && boss.equals(b))
                throw new IllegalArgumentException("there is never a winner");

            boss.hitPoints -= Math.max(1, player.damage - boss.armor);
            if (boss.hitPoints <= 0)
                return true;
            player.hitPoints -= Math.max(1, boss.damage - player.armor);
            if (player.hitPoints <= 0)
                return false;
        }
    }

    public void out(Object... str) {
        String out = "";
        for (Object o : str) {
            if (out.length() > 0)
                out += " ";
            out += o;
        }
        System.out.println(out);
    }

    String cleanFrom(String input, String... strings) {
        String result = input;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            result = result.replace(string, "");
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

}
