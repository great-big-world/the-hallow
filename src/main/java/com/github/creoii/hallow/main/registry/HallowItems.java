package com.github.creoii.hallow.main.registry;

import com.github.creoii.creolib.api.util.item.ItemRegistryHelper;
import com.github.creoii.hallow.item.TestPetrifyItem;
import com.github.creoii.hallow.main.TheHallow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;

public class HallowItems {
    public static final Item TEST_PETRIFY = new TestPetrifyItem();

    public static void register() {
        ItemRegistryHelper.registerItem(new Identifier(TheHallow.NAMESPACE, "test_petrify"), TEST_PETRIFY, ItemGroups.FUNCTIONAL);
    }
}
