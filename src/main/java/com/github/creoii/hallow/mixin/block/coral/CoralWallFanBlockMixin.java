package com.github.creoii.hallow.mixin.block.coral;

import com.github.creoii.hallow.block.Petrifiable;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CoralWallFanBlock.class)
public class CoralWallFanBlockMixin extends DeadCoralWallFanBlock implements Petrifiable {
    public CoralWallFanBlockMixin(AbstractBlock.Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(PETRIFIED, false));
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"), cancellable = true)
    private void hallow_petrifyCoral(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (state.get(PETRIFIED)) ci.cancel();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(PETRIFIED));
    }
}
