package com.github.creoii.hallow.main.registry;

import com.github.creoii.hallow.main.TheHallow;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class HallowSoundEvents {
    public static final SoundEvent BLOCK_ANOINTING_TABLE_ACTIVATE = SoundEvent.of(new Identifier(TheHallow.NAMESPACE, "block.anointing_table.activate"));

    public static void register() {
        registerSoundEvent(BLOCK_ANOINTING_TABLE_ACTIVATE);
    }

    private static void registerSoundEvent(SoundEvent soundEvent) {
        Registry.register(Registries.SOUND_EVENT, soundEvent.getId(), soundEvent);
    }
}
