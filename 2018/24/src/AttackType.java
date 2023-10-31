public enum AttackType {
    FIRE("FIRE"), COLD("COLD"), SLASHING("SLASHING"), BLUDGEONING("BLUDGEONING"), RADIATION("RADIATION");

    final String name;

    AttackType(String name) {
        this.name = name.toLowerCase();
    }

    static AttackType getAttackType(String input) {
        String lcInput = input.trim().toLowerCase();
        for (AttackType attackType : AttackType.values()) {
            if (attackType.name.equals(lcInput))
                return attackType;
        }
        throw new IllegalArgumentException("Unknown Attack Type >" + input + "<");
    }
}
