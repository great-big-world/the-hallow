package com.github.creoii.hallow.main.registry;

import com.github.creoii.hallow.main.TheHallow;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public final class HallowStats {
    public static final Identifier INTERACT_WITH_ANOINTING_TABLE = new Identifier(TheHallow.NAMESPACE, "interact_with_anointing_table");
    public static final Identifier BLOCKS_PETRIFIED = new Identifier(TheHallow.NAMESPACE, "blocks_petrified");

    public static void register() {
        registerStat(INTERACT_WITH_ANOINTING_TABLE);
        registerStat(BLOCKS_PETRIFIED);
    }

    private static Identifier registerStat(Identifier id) {
        return registerStat(id, StatFormatter.DEFAULT);
    }

    private static Identifier registerStat(Identifier id, StatFormatter formatter) {
        Registry.register(Registries.CUSTOM_STAT, id, id);
        Stats.CUSTOM.getOrCreateStat(id, formatter);
        return id;
    }
}