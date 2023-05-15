package com.github.creoii.hallow.mixin.block;

import com.github.creoii.hallow.block.Petrifiable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConcretePowderBlock.class)
public class ConcretePowderBlockMixin extends FallingBlock implements Petrifiable {
    public ConcretePowderBlockMixin(Settings settings) { super(settings); }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hallow_defaultPetrified(Block hardened, Settings settings, CallbackInfo ci) {
        setDefaultState(getDefaultState().with(PETRIFIED, false));
    }

    @Inject(method = "shouldHarden", at = @At("HEAD"), cancellable = true)
    private static void hallow_petrifyConcretePowder(BlockView world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.get(PETRIFIED)) cir.setReturnValue(false);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PETRIFIED);
    }
}
