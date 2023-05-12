package com.github.creoii.hallow.main;

import com.github.creoii.hallow.main.registry.*;
import net.fabricmc.api.ModInitializer;

public class TheHallow implements ModInitializer {
    public static final String NAMESPACE = "hallow";

    @Override
    public void onInitialize() {
        HallowBlocks.register();
        HallowRecipes.register();
        HallowStatusEffects.register();
        HallowSoundEvents.register();
        HallowStats.register();
        HallowEvents.register();
    }
}
