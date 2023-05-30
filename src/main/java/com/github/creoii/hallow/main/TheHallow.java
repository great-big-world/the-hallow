package com.github.creoii.hallow.main;

import com.github.creoii.hallow.effect.hex.Hexes;
import com.github.creoii.hallow.main.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.dimension.DimensionType;

public class TheHallow implements ModInitializer {
    public static final String NAMESPACE = "hallow";
    public static final Random RANDOM = Random.create();
    public static final RegistryKey<DimensionType> DIMENSION_KEY = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, new Identifier(NAMESPACE, "the_hallow"));

    @Override
    public void onInitialize() {
        HallowBlocks.register();
        HallowItems.register();
        HallowConfiguredFeatures.register();
        HallowPlacedFeatures.register();
        HallowBiomes.register();
        HallowRecipeTypes.register();
        Hexes.register();
        HallowStatusEffects.register();
        HallowSoundEvents.register();
        HallowGameEvents.register();
        HallowStats.register();
        HallowEvents.register();
    }
}
