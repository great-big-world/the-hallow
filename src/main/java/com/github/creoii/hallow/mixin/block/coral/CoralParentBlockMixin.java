package com.github.creoii.hallow.mixin.block.coral;

import com.github.creoii.hallow.block.Petrifiable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CoralParentBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CoralParentBlock.class)
public class CoralParentBlockMixin extends Block implements Waterloggable, Petrifiable {
    public CoralParentBlockMixin(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(PETRIFIED, false));
    }

    @Inject(method = "checkLivingConditions", at = @At("HEAD"), cancellable = true)
    private void hallow_petrifyCoral(BlockState state, WorldAccess world, BlockPos pos, CallbackInfo ci) {
        if (state.get(PETRIFIED)) ci.cancel();
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void hallow_injectPetrified(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(PETRIFIED);
    }
}
