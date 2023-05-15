package com.github.creoii.hallow.mixin.block;

import com.github.creoii.hallow.block.Petrifiable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.TorchflowerBlock;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TorchflowerBlock.class)
public class TorchflowerBlockMixin extends CropBlock implements Petrifiable {
    public TorchflowerBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hallow_defaultPetrified(Settings settings, CallbackInfo ci) {
        setDefaultState(getDefaultState().with(PETRIFIED, false));
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void hallow_injectPetrified(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(PETRIFIED);
    }
}
