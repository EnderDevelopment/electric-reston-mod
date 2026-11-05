package com.enderdevelopment.mengelectric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItemGroup {
    public static final ItemGroup MENGE_ELECTRIC_GROUP = FabricItemGroup.builder()
        .icon(() -> new ItemStack(ModItems.RESTON_BLOCK))
        .displayName(Text.translatable("itemGroup.mengelectric"))
        .entries((displayContext, entries) -> {
            entries.add(ModItems.RESTON);
            entries.add(ModItems.BLACK_RESTON);
            entries.add(ModItems.RESTON_BLOCK);
        })
        .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, Mengelectric.id("menge_electric"), MENGE_ELECTRIC_GROUP);
    }
}
