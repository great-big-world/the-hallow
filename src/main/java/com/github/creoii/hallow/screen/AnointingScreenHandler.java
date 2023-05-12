package com.github.creoii.hallow.screen;

import com.github.creoii.hallow.block.AnointingTableBlock;
import com.github.creoii.hallow.main.registry.HallowBlocks;
import com.github.creoii.hallow.main.registry.HallowRecipes;
import com.github.creoii.hallow.main.registry.HallowSoundEvents;
import com.github.creoii.hallow.main.registry.tag.HallowItemTags;
import com.github.creoii.hallow.recipe.AnointingRecipe;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.*;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

import java.util.*;

public class AnointingScreenHandler extends ScreenHandler {
    private final World world;
    private AnointingRecipe recipe;
    private final List<AnointingRecipe> recipes;
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
        recipes = world.getRecipeManager().listAllOfType(HallowRecipes.ANOINTING_TYPE);
        addSlot(new Slot(input, 0, 27, 31) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof Equipment;
            }
        });
        addSlot(new Slot(input, 1, 76, 31) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(HallowItemTags.ANOINTING_CRYSTALS);
            }
        });
        addSlot(new Slot(input, 2, 76, 51) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == Items.GLOWSTONE_DUST;
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
            if (stack.getOrCreateNbt().getBoolean("Anointed")) return;
            EnchantmentHelper.set(EnchantmentHelper.get(input.getStack(0)), stack);

            EntityAttribute attribute = recipe.getAttribute();
            double amount = recipe.getAmount();

            EquipmentSlot slot = MobEntity.getPreferredEquipmentSlot(stack);
            Multimap<EntityAttribute, EntityAttributeModifier> newAttributes = HashMultimap.create();
            for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : stack.getAttributeModifiers(slot).entries()) {
                EntityAttributeModifier modifier = entry.getValue();
                if (entry.getKey() == attribute && modifier.getOperation() == EntityAttributeModifier.Operation.ADDITION) {
                    amount += modifier.getValue();
                } else newAttributes.put(entry.getKey(), modifier);
            }
            newAttributes.put(attribute, new EntityAttributeModifier("Anointment bonus", amount, EntityAttributeModifier.Operation.ADDITION));

            for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : newAttributes.entries()) {
                for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry1 : stack.getAttributeModifiers(slot).entries()) {
                    if (!entry.getKey().getTranslationKey().equals(entry1.getKey().getTranslationKey())) stack.addAttributeModifier(entry.getKey(), entry.getValue(), slot);
                }
            }

            recipe.setOutput(stack);
            stack.getOrCreateNbt().putBoolean("Anointed", true);
            output.setLastRecipe(recipe);
            output.setStack(0, stack);
        }
    }

    protected boolean method_30025(ItemStack stack) {
        return recipes.stream().anyMatch(recipe -> recipe.getAnointment() == stack.getItem()) || stack.getItem() == Items.GLOWSTONE_DUST;
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
                    int i = method_30025(itemStack) ? 1 : 0;
                    if (!insertItem(itemStack2, i, 3, false)) {
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