package com.github.creoii.hallow.recipe;

import com.github.creoii.creolib.api.tag.CItemTags;
import com.github.creoii.hallow.main.registry.HallowRecipeTypes;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.util.Map;
import java.util.function.Predicate;

public class AnointingRecipe implements Recipe<Inventory> {
    public static final String ANOINTMENT_BONUS_KEY = "Anointment bonus";
    private final Identifier id;
    private final EquipmentType equipmentType;
    private final Item anointment;
    private final EntityAttribute attribute;
    private final double amount;
    private ItemStack output;
    private PlayerEntity player;

    public AnointingRecipe(Identifier id, EquipmentType equipmentType, Item anointment, EntityAttribute attribute, double amount) {
        this.id = id;
        this.equipmentType = equipmentType;
        this.anointment = anointment;
        this.attribute = attribute;
        this.amount = amount;
    }

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

    public AnointingRecipe withPlayer(PlayerEntity player) {
        this.player = player;
        return this;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        if (inventory.size() < 3 || inventory.size() > 3) return false;
        return equipmentType.test(inventory.getStack(0)) && inventory.getStack(1).getItem() == anointment && inventory.getStack(2).isOf(Items.GLOWSTONE_DUST);
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager manager) {
        ItemStack stack = inventory.getStack(0).copy();
        if (stack.getOrCreateNbt().getBoolean("Anointed")) return ItemStack.EMPTY;

        double amount = getAmount();
        EquipmentSlot slot;
        if (stack.getItem() instanceof Equipment equipment) {
            slot = equipment.getSlotType();
        } else slot = EquipmentSlot.MAINHAND;

        Multimap<EntityAttribute, EntityAttributeModifier> oldAttributesMap = reverse(stack.getAttributeModifiers(slot));
        oldAttributesMap.put(attribute, new EntityAttributeModifier(ANOINTMENT_BONUS_KEY, amount, EntityAttributeModifier.Operation.ADDITION));
        Multimap<EntityAttribute, EntityAttributeModifier> newModifiers = LinkedListMultimap.create();
        Multimap<EntityAttribute, EntityAttributeModifier> addModifiers = LinkedListMultimap.create();

        oldAttributesMap.forEach((entityAttribute, entityAttributeModifier) -> {
            if (entityAttributeModifier.getName().equals("Anointment bonus")) {
                addModifiers.put(entityAttribute, entityAttributeModifier);
            } else {
                double value = entityAttributeModifier.getValue() + player.getAttributeBaseValue(entityAttribute);
                if (entityAttribute == EntityAttributes.GENERIC_ATTACK_DAMAGE) {
                    value += EnchantmentHelper.getAttackDamage(stack, EntityGroup.DEFAULT);
                }
                newModifiers.put(entityAttribute, new EntityAttributeModifier(entityAttributeModifier.getId(), entityAttributeModifier.getName(), value, entityAttributeModifier.getOperation()));
            }
        });
        newModifiers.putAll(addModifiers);

        newModifiers.forEach((attribute1, entityAttributeModifier) -> stack.addAttributeModifier(attribute1, entityAttributeModifier, slot));
        EnchantmentHelper.set(EnchantmentHelper.get(inventory.getStack(0)), stack);
        return stack;
    }

    @SuppressWarnings("unchecked")
    private static Multimap<EntityAttribute, EntityAttributeModifier> reverse(Multimap<EntityAttribute, EntityAttributeModifier> multimap) {
        Multimap<EntityAttribute, EntityAttributeModifier> reversed = LinkedListMultimap.create();
        Map.Entry<EntityAttribute, EntityAttributeModifier>[] array = multimap.entries().toArray(new Map.Entry[]{});
        for (int i = array.length - 1; i >= 0; --i) {
            Map.Entry<EntityAttribute, EntityAttributeModifier> entry = array[i];
            reversed.put(entry.getKey(), entry.getValue());
        }
        return reversed;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HallowRecipeTypes.ANOINTING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return HallowRecipeTypes.ANOINTING_TYPE;
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
            return stack.isIn(CItemTags.EQUIPMENT);
        }),
        TOOL(stack -> {
            return stack.isIn(ItemTags.TOOLS);
        }),
        MELEE_WEAPON(stack -> {
            return stack.isIn(CItemTags.MELEE_WEAPONS);
        }),
        HELD(stack -> {
            return stack.isIn(ItemTags.TOOLS) || stack.isIn(CItemTags.MELEE_WEAPONS);
        }),
        ARMOR(stack -> {
            return stack.isIn(CItemTags.ARMOR);
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