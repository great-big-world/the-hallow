package com.github.creoii.hallow.recipe;

import com.github.creoii.creolib.api.tag.CItemTags;
import com.github.creoii.hallow.main.registry.HallowRecipes;
import com.google.gson.JsonObject;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class AnointingRecipe implements Recipe<Inventory> {
    private final Identifier id;
    private final EquipmentType equipmentType;
    private final Item anointment;
    private final EntityAttribute attribute;
    private final double amount;
    private ItemStack output;

    public AnointingRecipe(Identifier id, EquipmentType equipmentType, Item anointment, EntityAttribute attribute, double amount) {
        this.id = id;
        this.equipmentType = equipmentType;
        this.anointment = anointment;
        this.attribute = attribute;
        this.amount = amount;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager manager) {
        return output;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    public Item getAnointment() {
        return anointment;
    }

    public EntityAttribute getAttribute() {
        return attribute;
    }

    public double getAmount() {
        return amount;
    }

    public void setOutput(ItemStack output) {
        this.output = output;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        if (inventory.size() < 3) return false;
        return equipmentType.test(inventory.getStack(0)) && inventory.getStack(1).getItem() == anointment && inventory.getStack(2).isOf(Items.GLOWSTONE_DUST);
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager manager) {
        return inventory.getStack(0).copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HallowRecipes.ANOINTING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return HallowRecipes.ANOINTING_TYPE;
    }

    public static class Type implements RecipeType<AnointingRecipe> { }

    public static class Serializer implements RecipeSerializer<AnointingRecipe> {
        public AnointingRecipe read(Identifier identifier, JsonObject json) {
            double amount = JsonHelper.getDouble(json, "amount");
            EquipmentType base = EquipmentType.valueOf(JsonHelper.getString(json, "equipment_type").toUpperCase());
            Item anointment = Registries.ITEM.get(Identifier.tryParse(JsonHelper.getString(json, "anointment")));
            EntityAttribute attribute = Registries.ATTRIBUTE.get(Identifier.tryParse(JsonHelper.getString(json, "attribute")));
            return new AnointingRecipe(identifier, base, anointment, attribute, amount);
        }

        public AnointingRecipe read(Identifier identifier, PacketByteBuf buffer) {
            String equipmentTypeStr = buffer.readString().toUpperCase();
            String anointmentStr = buffer.readString();
            String attributeStr = buffer.readString();
            double amount = buffer.readDouble();
            EquipmentType equipmentType = EquipmentType.valueOf(equipmentTypeStr);
            Item anointment = Registries.ITEM.get(Identifier.tryParse(anointmentStr));
            EntityAttribute attribute = Registries.ATTRIBUTE.get(new Identifier(attributeStr));
            return new AnointingRecipe(identifier, equipmentType, anointment, attribute, amount);
        }

        public void write(PacketByteBuf packetByteBuf, AnointingRecipe recipe) {
            packetByteBuf.writeString(recipe.equipmentType.name());
            packetByteBuf.writeString(Registries.ITEM.getId(recipe.anointment).toString());
            packetByteBuf.writeDouble(recipe.amount);
        }
    }

    public enum EquipmentType {
        ALL(stack -> {
            return stack.getItem() instanceof Equipment;
        }),
        TOOL(stack -> {
            return stack.isIn(ItemTags.TOOLS);
        }),
        MELEE_WEAPON(stack -> {
            return stack.isIn(ItemTags.SWORDS) || stack.isOf(Items.TRIDENT);
        }),
        RANGED_WEAPON(stack -> {
            return stack.isOf(Items.BOW) || stack.isOf(Items.CROSSBOW);
        }),
        HELD(stack -> {
            return stack.isIn(ItemTags.TOOLS) || stack.isIn(ItemTags.SWORDS) || stack.isOf(Items.TRIDENT) || stack.isOf(Items.BOW) || stack.isOf(Items.CROSSBOW);
        }),
        ARMOR(stack -> {
            return stack.isIn(CItemTags.HELMETS) || stack.isIn(CItemTags.CHESTPLATES) || stack.isIn(CItemTags.LEGGINGS) || stack.isIn(CItemTags.BOOTS);
        });

        private final Predicate<ItemStack> test;

        EquipmentType(Predicate<ItemStack> test) {
            this.test = test;
        }

        public boolean test(ItemStack stack) {
            return test.test(stack);
        }
    }
}