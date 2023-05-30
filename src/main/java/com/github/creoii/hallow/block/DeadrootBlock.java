package com.github.creoii.hallow.block;

import com.github.creoii.creolib.api.util.block.CBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class DeadrootBlock extends PlantBlock implements Waterloggable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2d, 0d, 2d, 14d, 4d, 14d);
    protected static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public DeadrootBlock() {
        super(CBlockSettings.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.TERRACOTTA_BROWN).offset(OffsetType.XYZ));
        setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState().with(WATERLOGGED, context.getWorld().getFluidState(context.getBlockPos()).isIn(FluidTags.WATER));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
}