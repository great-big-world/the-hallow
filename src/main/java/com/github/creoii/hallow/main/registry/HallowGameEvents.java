package com.github.creoii.hallow.main.registry;

import com.github.creoii.hallow.main.TheHallow;
import net.fabricmc.fabric.api.registry.SculkSensorFrequencyRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.event.GameEvent;

public class HallowGameEvents {
    public static GameEvent BLOCK_PETRIFIED;
    
    public static void register() {
        BLOCK_PETRIFIED = registerGameEvent(new Identifier(TheHallow.NAMESPACE, "block_petrified"), 16, 7);
    }

    private static GameEvent registerGameEvent(Identifier id, int range) {
        return Registry.register(Registries.GAME_EVENT, id, new GameEvent(id.getPath(), range));
    }

    private static GameEvent registerGameEvent(Identifier id, int range, int frequency) {
        GameEvent gameEvent = registerGameEvent(id, range);
        SculkSensorFrequencyRegistry.register(gameEvent, frequency);
        return gameEvent;
    }
}
