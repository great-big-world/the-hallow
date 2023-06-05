package com.github.creoii.hallow.main.registry;

import com.github.creoii.creolib.api.util.block.BlockRegistryHelper;
import com.github.creoii.creolib.api.util.block.CBlockSettings;
import com.github.creoii.creolib.api.util.item.ItemRegistryHelper;
import com.github.creoii.creolib.api.util.registry.RegistrySets;
import com.github.creoii.hallow.block.AnointingTableBlock;
import com.github.creoii.hallow.block.DeadrootBlock;
import com.github.creoii.hallow.block.HallowedDirtBlock;
import com.github.creoii.hallow.main.TheHallow;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.MapColor;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public final class HallowBlocks {
    //region terrain
    public static final Block HALLSTONE = new Block(CBlockSettings.copy(Blocks.TUFF).mapColor(MapColor.DIRT_BROWN));
    public static final Block HALLOWED_DIRT = new HallowedDirtBlock();
    public static final Block HALLOWED_GRASS_BLOCK = new Block(CBlockSettings.copy(Blocks.GRASS_BLOCK));
    //endregion

    //region vegetation
    public static final Block DEADROOT = new DeadrootBlock();
    public static final Block POTTED_DEADROOT = new FlowerPotBlock(DEADROOT, CBlockSettings.copy(Blocks.FLOWER_POT));
    //endregion

    //region wood
    public static final RegistrySets.WoodSet EBONY = RegistrySets.createWoodSet(TheHallow.NAMESPACE, "ebony", MapColor.TERRACOTTA_BLACK, MapColor.BLACK, Items.WARPED_BUTTON, Items.WARPED_STEM, Items.WARPED_HANGING_SIGN);
    //endregion

    //region functional
    public static final Block ANOINTING_TABLE = new AnointingTableBlock();
    //endregion

    public static void register() {
        BlockRegistryHelper.registerBlock(new Identifier(TheHallow.NAMESPACE, "hallstone"), HALLSTONE, new ItemRegistryHelper.ItemGroupSettings(ItemGroups.BUILDING_BLOCKS, null), new ItemRegistryHelper.ItemGroupSettings(ItemGroups.NATURAL, null));
        BlockRegistryHelper.registerBlock(new Identifier(TheHallow.NAMESPACE, "hallowed_dirt"), HALLOWED_DIRT, new ItemRegistryHelper.ItemGroupSettings(ItemGroups.BUILDING_BLOCKS, null), new ItemRegistryHelper.ItemGroupSettings(ItemGroups.NATURAL, null));
        BlockRegistryHelper.registerBlock(new Identifier(TheHallow.NAMESPACE, "hallowed_grass_block"), HALLOWED_GRASS_BLOCK, new ItemRegistryHelper.ItemGroupSettings(ItemGroups.BUILDING_BLOCKS, null), new ItemRegistryHelper.ItemGroupSettings(ItemGroups.NATURAL, null));
        BlockRegistryHelper.registerBlock(new Identifier(TheHallow.NAMESPACE, "deadroot"), DEADROOT, Items.NETHER_SPROUTS, ItemGroups.NATURAL);
        BlockRegistryHelper.registerBlock(new Identifier(TheHallow.NAMESPACE, "potted_deadroot"), POTTED_DEADROOT);
        EBONY.register();
        BlockRegistryHelper.registerBlock(new Identifier(TheHallow.NAMESPACE, "anointing_table"), ANOINTING_TABLE, Items.ENCHANTING_TABLE, ItemGroups.FUNCTIONAL);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                DEADROOT,
                EBONY.door()
        );

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return GrassColors.getDefaultColor();
            }
            return BiomeColors.getGrassColor(world, pos);
        }, HALLOWED_GRASS_BLOCK);
    }
}
