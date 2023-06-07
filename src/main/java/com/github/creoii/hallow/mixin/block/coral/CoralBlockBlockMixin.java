package com.github.creoii.hallow.mixin.block.coral;

import com.github.creoii.hallow.block.Petrifiable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CoralBlockBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CoralBlockBlock.class)
public class CoralBlockBlockMixin extends Block implements Petrifiable {
    public CoralBlockBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hallow_defaultPetrified(Block deadCoralBlock, Settings settings, CallbackInfo ci) {
        setDefaultState(getDefaultState().with(PETRIFIED, false));
    }

    @SuppressWarnings("deprecation")
    @Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"), cancellable = true)
    private void hallow_petrifyCoral(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        if (state.get(PETRIFIED)) cir.setReturnValue(super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos));
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"), cancellable = true)
    private void hallow_petrifyTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (state.get(PETRIFIED)) ci.cancel();
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PETRIFIED);
    }
}
