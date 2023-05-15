package com.github.creoii.hallow.mixin.block;

import com.github.creoii.hallow.block.Petrifiable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneTorchBlock.class)
public class RedstoneTorchBlockMixin extends TorchBlock implements Petrifiable {
    public RedstoneTorchBlockMixin(Settings settings, ParticleEffect particle) {
        super(settings, particle);
        setDefaultState(getDefaultState().with(PETRIFIED, false));
    }

    @Inject(method = "shouldUnpower", at = @At("HEAD"), cancellable = true)
    private void hallow_petrifyRedstoneTorchUnpower(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.get(PETRIFIED)) cir.setReturnValue(false);
    }

    @Inject(method = "neighborUpdate", at = @At("HEAD"), cancellable = true)
    private void hallow_petrifyRedstoneWireNeighbor(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify, CallbackInfo ci) {
        if (state.get(PETRIFIED)) ci.cancel();
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void hallow_injectPetrified(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(PETRIFIED);
    }
}
