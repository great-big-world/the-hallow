package com.github.creoii.hallow.item;

import com.github.creoii.creolib.api.util.item.CItemSettings;
import com.github.creoii.hallow.block.Petrifiable;
import com.github.creoii.hallow.main.registry.HallowStats;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class TestPetrifyItem extends Item {
    public TestPetrifyItem() {
        super(new CItemSettings());
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = context.getStack();
        if (state.getBlock() instanceof Petrifiable petrifiable && !petrifiable.isPetrified(state)) {
            if (!world.isClient) {
                PlayerEntity player = context.getPlayer();
                if (player != null) {
                    if (!player.getAbilities().creativeMode) stack.decrement(1);
                    player.incrementStat(HallowStats.BLOCKS_PETRIFIED);
                }
                world.setBlockState(pos, state.with(Petrifiable.PETRIFIED, true));
                petrifiable.onPetrify(world, state, pos, world.random);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }
}
