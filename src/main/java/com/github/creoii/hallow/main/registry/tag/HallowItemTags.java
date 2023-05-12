package com.github.creoii.hallow.main.registry.tag;

import com.github.creoii.hallow.main.TheHallow;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class HallowItemTags {
    public static final TagKey<Item> ANOINTING_CRYSTALS = TagKey.of(RegistryKeys.ITEM, new Identifier(TheHallow.NAMESPACE, "anointing_crystals"));
}
