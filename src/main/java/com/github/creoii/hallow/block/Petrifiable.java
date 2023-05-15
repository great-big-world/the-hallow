package com.github.creoii.hallow.block;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public interface Petrifiable {
    BooleanProperty PETRIFIED = BooleanProperty.of("petrified");

    default void onPetrify(World world, BlockState state, BlockPos pos, Random random) {
    }

    default boolean isPetrified(BlockState state) {
        return state.get(PETRIFIED);
    }
}
