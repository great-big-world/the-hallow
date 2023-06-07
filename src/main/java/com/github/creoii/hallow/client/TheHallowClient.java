package com.github.creoii.hallow.client;

import com.github.creoii.hallow.main.registry.HallowBlocks;
import com.github.creoii.hallow.main.registry.HallowParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class TheHallowClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HallowBlocks.registerClient();
        HallowParticleTypes.registerClient();
    }
}
