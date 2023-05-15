package com.github.creoii.hallow.mixin.block;

import com.github.creoii.hallow.block.Petrifiable;
import net.minecraft.block.BeetrootsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeetrootsBlock.class)
public class BeetrootsBlockMixin extends CropBlock implements Petrifiable {
    public BeetrootsBlockMixin(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(PETRIFIED, false));
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void hallow_injectPetrified(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(PETRIFIED);
    }
}
