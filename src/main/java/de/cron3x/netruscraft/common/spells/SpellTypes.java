package de.cron3x.netruscraft.common.spells;

import java.util.Optional;

public class SpellTypes {
    public static Optional<Class<Spell>> StringToSpell(String str) {
        return switch (str) {
            case "adw" -> Optional.of(Spell.class);
            default -> Optional.empty();
        };
    }
}
