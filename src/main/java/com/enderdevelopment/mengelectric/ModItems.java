package com.enderdevelopment.mengelectric;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item RESTON = new RestonItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    public static final Item BLACK_RESTON = new Item(new Item.Settings().maxCount(64));
    public static final Item RESTON_BLOCK = new BlockItem(ModBlocks.RESTON_BLOCK, new Item.Settings().maxCount(64));

    public static void register() {
        Registry.register(Registries.ITEM, Mengelectric.id("reston"), RESTON);
        Registry.register(Registries.ITEM, Mengelectric.id("black_reston"), BLACK_RESTON);
        Registry.register(Registries.ITEM, Mengelectric.id("reston_block"), RESTON_BLOCK);
    }
}
