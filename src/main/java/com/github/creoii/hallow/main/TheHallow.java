package com.github.creoii.hallow.main;

import com.github.creoii.hallow.effect.hex.Hexes;
import com.github.creoii.hallow.main.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.math.random.Random;

public class TheHallow implements ModInitializer {
    public static final String NAMESPACE = "hallow";
    public static final Random RANDOM = Random.create();

    @Override
    public void onInitialize() {
        HallowBlocks.register();
        HallowItems.register();
        HallowRecipeTypes.register();
        Hexes.register();
        HallowStatusEffects.register();
        HallowSoundEvents.register();
        HallowGameEvents.register();
        HallowStats.register();
        HallowEvents.register();
    }
}
