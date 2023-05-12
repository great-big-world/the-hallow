package com.github.creoii.hallow.main.registry;

import com.github.creoii.creolib.api.util.block.BlockRegistryHelper;
import com.github.creoii.hallow.block.AnointingTableBlock;
import com.github.creoii.hallow.main.TheHallow;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public final class HallowBlocks {
    public static final Block ANOINTING_TABLE = new AnointingTableBlock();

    public static void register() {
        BlockRegistryHelper.registerBlock(new Identifier(TheHallow.NAMESPACE, "anointing_table"), ANOINTING_TABLE, Items.ENCHANTING_TABLE, ItemGroups.FUNCTIONAL);
    }
}
