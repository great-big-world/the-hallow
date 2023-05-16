package com.github.creoii.hallow.main.registry;

import com.github.creoii.creolib.api.util.block.BlockRegistryHelper;
import com.github.creoii.creolib.api.util.block.CBlockSettings;
import com.github.creoii.creolib.api.util.item.ItemRegistryHelper;
import com.github.creoii.hallow.block.AnointingTableBlock;
import com.github.creoii.hallow.main.TheHallow;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public final class HallowBlocks {
    //region terrain
    public static final Block HALLSTONE = new Block(CBlockSettings.copy(Blocks.STONE));
    public static final Block HALLOWED_DIRT = new Block(CBlockSettings.copy(Blocks.DIRT));
    public static final Block HALLOWED_GRASS_BLOCK = new Block(CBlockSettings.copy(Blocks.GRASS_BLOCK));
    //endregion

    //region functional
    public static final Block ANOINTING_TABLE = new AnointingTableBlock();
    //endregion

    public static void register() {
        BlockRegistryHelper.registerBlock(new Identifier(TheHallow.NAMESPACE, "hallstone"), HALLSTONE, new ItemRegistryHelper.ItemGroupSettings(ItemGroups.BUILDING_BLOCKS, null), new ItemRegistryHelper.ItemGroupSettings(ItemGroups.NATURAL, null));
        BlockRegistryHelper.registerBlock(new Identifier(TheHallow.NAMESPACE, "hallowed_dirt"), HALLOWED_DIRT, new ItemRegistryHelper.ItemGroupSettings(ItemGroups.BUILDING_BLOCKS, null), new ItemRegistryHelper.ItemGroupSettings(ItemGroups.NATURAL, null));
        BlockRegistryHelper.registerBlock(new Identifier(TheHallow.NAMESPACE, "hallowed_grass_block"), HALLOWED_GRASS_BLOCK, new ItemRegistryHelper.ItemGroupSettings(ItemGroups.BUILDING_BLOCKS, null), new ItemRegistryHelper.ItemGroupSettings(ItemGroups.NATURAL, null));
        BlockRegistryHelper.registerBlock(new Identifier(TheHallow.NAMESPACE, "anointing_table"), ANOINTING_TABLE, Items.ENCHANTING_TABLE, ItemGroups.FUNCTIONAL);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return GrassColors.getDefaultColor();
            }
            return BiomeColors.getGrassColor(world, pos);
        }, HALLOWED_GRASS_BLOCK);
    }
}
