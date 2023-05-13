package com.github.creoii.hallow.screen;

import com.github.creoii.creolib.api.tag.CItemTags;
import com.github.creoii.hallow.block.AnointingTableBlock;
import com.github.creoii.hallow.main.registry.HallowBlocks;
import com.github.creoii.hallow.main.registry.HallowRecipes;
import com.github.creoii.hallow.main.registry.HallowSoundEvents;
import com.github.creoii.hallow.main.registry.tag.HallowItemTags;
import com.github.creoii.hallow.recipe.AnointingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class AnointingScreenHandler extends ScreenHandler {
    private final World world;
    private AnointingRecipe recipe;
    protected final ScreenHandlerContext context;
    protected final CraftingResultInventory output = new CraftingResultInventory();
    protected final Inventory input = new SimpleInventory(3) {
        public void markDirty() {
            super.markDirty();
            onContentChanged(this);
        }
    };

    public AnointingScreenHandler(int id, PlayerInventory inventory) {
        this(id, inventory, ScreenHandlerContext.EMPTY);
    }

    public AnointingScreenHandler(int id, PlayerInventory inventory, ScreenHandlerContext context) {
        super(HallowRecipes.ANOINTING_SCREEN, id);
        world = inventory.player.world;
        this.context = context;
        addSlot(new Slot(input, 0, 27, 31) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(CItemTags.EQUIPMENT);
            }

            @Override
            public ItemStack insertStack(ItemStack stack, int count) {
                recipe.setOutput(stack);
                return super.insertStack(stack, count);
            }
        });
        addSlot(new Slot(input, 1, 76, 31) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(HallowItemTags.ANOINTING_CRYSTALS) && !input.getStack(0).isEmpty();
            }

            @Override
            public ItemStack insertStack(ItemStack stack, int count) {
                recipe.setOutput(input.getStack(0));
                return super.insertStack(stack, count);
            }
        });
        addSlot(new Slot(input, 2, 76, 51) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == Items.GLOWSTONE_DUST && !input.getStack(0).isEmpty();
            }

            @Override
            public ItemStack insertStack(ItemStack stack, int count) {
                recipe.setOutput(input.getStack(0));
                return super.insertStack(stack, count);
            }
        });
        addSlot(new Slot(output, 3, 134, 31) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            public boolean canTakeItems(PlayerEntity playerEntity) {
                return canTakeOutput();
            }

            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                onTakeOutput(player, stack);
            }
        });

        int k;
        for (k = 0; k < 3; ++k) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(inventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for (k = 0; k < 9; ++k) {
            addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory == input) updateResult();
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        context.run((world, blockPos) -> dropInventory(player, input));
    }

    public boolean canUse(PlayerEntity player) {
        return context.get((world, pos) -> world.getBlockState(pos).isOf(HallowBlocks.ANOINTING_TABLE) && player.squaredDistanceTo((double) pos.getX() + .5d, (double) pos.getY() + 0d, (double) pos.getZ() + .5d) <= 64d, true);
    }

    protected boolean canTakeOutput() {
        return recipe != null && recipe.matches(input, world);
    }

    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        stack.onCraft(player.world, player, stack.getCount());
        output.unlockLastRecipe(player);
        decrease(0);
        decrease(1);
        decrease(2);
        context.run((world, pos) -> {
            BlockState state = world.getBlockState(pos);
            if (state.isOf(HallowBlocks.ANOINTING_TABLE)) {
                world.setBlockState(pos, state.with(AnointingTableBlock.ACTIVATED, true));
                world.playSound(null, pos, HallowSoundEvents.BLOCK_ANOINTING_TABLE_USE, SoundCategory.BLOCKS, .75f, world.random.nextFloat() * .1f + .3f);
                world.scheduleBlockTick(pos, state.getBlock(), world.random.nextInt(51) + 50);
            }
        });
    }

    private void decrease(int index) {
        ItemStack itemstack = input.getStack(index);
        itemstack.decrement(1);
        input.setStack(index, itemstack);
    }

    public void updateResult() {
        List<AnointingRecipe> list = world.getRecipeManager().getAllMatches(HallowRecipes.ANOINTING_TYPE, input, world);
        if (list.isEmpty()) output.setStack(0, ItemStack.EMPTY);
        else {
            recipe = list.get(0);
            ItemStack stack = recipe.craft(input, world.getRegistryManager());
            recipe.setOutput(stack);
            stack.getOrCreateNbt().putBoolean("Anointed", true);
            output.setLastRecipe(recipe);
            output.setStack(0, stack);
        }
    }

    private static Optional<Integer> getSlotFor(ItemStack stack) {
        if (stack.isIn(CItemTags.EQUIPMENT)) {
            return Optional.of(0);
        }
        if (stack.isIn(HallowItemTags.ANOINTING_CRYSTALS)) {
            return Optional.of(1);
        }
        if (stack.isOf(Items.GLOWSTONE_DUST)) {
            return Optional.of(2);
        }
        return Optional.empty();
    }

    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 3) {
                if (!insertItem(itemStack2, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index != 0 && index != 1 && index != 2) {
                if (index >= 4 && index < 40) {
                    Optional<Integer> slotFor = getSlotFor(itemStack);
                    if (slotFor.isPresent() && !insertItem(itemStack2, slotFor.get(), 3, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!insertItem(itemStack2, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) slot.setStack(ItemStack.EMPTY);
            else slot.markDirty();

            if (itemStack2.getCount() == itemStack.getCount()) return ItemStack.EMPTY;

            slot.onTakeItem(player, itemStack2);
        }
        return itemStack;
    }

    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != output && super.canInsertIntoSlot(stack, slot);
    }
}