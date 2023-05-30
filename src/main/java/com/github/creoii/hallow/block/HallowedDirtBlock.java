package com.github.creoii.hallow.block;

import com.github.creoii.creolib.api.util.block.CBlockSettings;
import com.github.creoii.hallow.main.registry.HallowPlacedFeatures;
import net.minecraft.block.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class HallowedDirtBlock extends Block implements Fertilizable {
    public HallowedDirtBlock() {
        super(CBlockSettings.copy(Blocks.DIRT).mapColor(MapColor.TERRACOTTA_BROWN));
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.up()).isReplaceable();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.up()).isReplaceable();
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.getRegistryManager().get(RegistryKeys.PLACED_FEATURE).getEntry(HallowPlacedFeatures.PATCH_DEADROOT).ifPresent(placedFeatureReference -> {
            placedFeatureReference.value().generateUnregistered(world, world.getChunkManager().getChunkGenerator(), random, pos.up());
        });
    }
}
