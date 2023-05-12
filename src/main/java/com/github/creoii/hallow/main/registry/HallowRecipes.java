package com.github.creoii.hallow.main.registry;

import com.github.creoii.hallow.client.gui.AnointingScreen;
import com.github.creoii.hallow.main.TheHallow;
import com.github.creoii.hallow.recipe.AnointingRecipe;
import com.github.creoii.hallow.screen.AnointingScreenHandler;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public final class HallowRecipes {
    public static final RecipeSerializer<AnointingRecipe> ANOINTING_SERIALIZER = new AnointingRecipe.Serializer();
    public static final RecipeType<AnointingRecipe> ANOINTING_TYPE = new AnointingRecipe.Type();
    public static ScreenHandlerType<AnointingScreenHandler> ANOINTING_SCREEN;

    @SuppressWarnings("deprecation")
    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(TheHallow.NAMESPACE, "anointing"), ANOINTING_SERIALIZER);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(TheHallow.NAMESPACE, "anointing"), ANOINTING_TYPE);
        ANOINTING_SCREEN = ScreenHandlerRegistry.registerSimple(new Identifier(TheHallow.NAMESPACE, "anointing_table"), AnointingScreenHandler::new);
        ScreenRegistry.register(ANOINTING_SCREEN, AnointingScreen::new);
    }
}