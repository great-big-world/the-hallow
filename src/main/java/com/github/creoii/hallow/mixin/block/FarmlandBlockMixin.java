package com.github.creoii.hallow.mixin.block;

import com.github.creoii.hallow.block.Petrifiable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin extends Block implements Petrifiable {
    public FarmlandBlockMixin(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(PETRIFIED, false));
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void hallow_petrifyFarmland(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (state.get(PETRIFIED)) ci.cancel();
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void injectPetrified(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(PETRIFIED);
    }
}
