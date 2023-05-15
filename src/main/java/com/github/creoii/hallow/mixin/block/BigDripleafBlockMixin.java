package com.github.creoii.hallow.mixin.block;

import com.github.creoii.hallow.block.Petrifiable;
import net.minecraft.block.*;
import net.minecraft.block.enums.Tilt;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BigDripleafBlock.class)
public abstract class BigDripleafBlockMixin extends HorizontalFacingBlock implements Fertilizable, Waterloggable, Petrifiable {
    protected BigDripleafBlockMixin(Settings settings) { super(settings); }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hallow_defaultPetrified(Settings settings, CallbackInfo ci) {
        setDefaultState(getDefaultState().with(PETRIFIED, false));
    }

    @Inject(method = "changeTilt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/enums/Tilt;Lnet/minecraft/sound/SoundEvent;)V", at = @At("HEAD"), cancellable = true)
    private void hallow_petrifyTilt(BlockState state, World world, BlockPos pos, Tilt tilt, SoundEvent sound, CallbackInfo ci) {
        if (state.get(PETRIFIED)) ci.cancel();
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void hallow_appendPetrified(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(PETRIFIED);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return !state.get(PETRIFIED);
    }
}
