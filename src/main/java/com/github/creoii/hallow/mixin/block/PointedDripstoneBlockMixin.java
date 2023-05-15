package com.github.creoii.hallow.mixin.block;

import com.github.creoii.hallow.block.Petrifiable;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin extends Block implements LandingBlock, Waterloggable, Petrifiable {
    public PointedDripstoneBlockMixin(Settings settings) { super(settings); }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hallow_defaultPetrified(Settings settings, CallbackInfo ci) {
        setDefaultState(getDefaultState().with(PETRIFIED, false));
    }

    @Inject(method = "dripTick", at = @At("HEAD"), cancellable = true)
    private static void hallow_petrifyDripping(BlockState state, ServerWorld world, BlockPos pos, float dripChance, CallbackInfo ci) {
        if (state.get(PETRIFIED)) ci.cancel();
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void hallow_injectPetrified(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(PETRIFIED);
    }
}
