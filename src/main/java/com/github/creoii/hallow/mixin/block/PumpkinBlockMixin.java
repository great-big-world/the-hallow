package com.github.creoii.hallow.mixin.block;

import com.github.creoii.hallow.block.Petrifiable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GourdBlock;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PumpkinBlock.class)
public abstract class PumpkinBlockMixin extends GourdBlock implements Petrifiable {
    public PumpkinBlockMixin(Settings settings) { super(settings); }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hallow_defaultPetrified(Settings settings, CallbackInfo ci) {
        setDefaultState(getDefaultState().with(PETRIFIED, false));
    }

    @SuppressWarnings("deprecation")
    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void hallow_petrifyPumpkins(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (state.get(PETRIFIED)) cir.setReturnValue(super.onUse(state, world, pos, player, hand, hit));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PETRIFIED);
    }
}
