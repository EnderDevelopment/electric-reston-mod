package com.enderdevelopment.mengelectric;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {
    public static final Block RESTON_BLOCK = new RestonBlock(
        AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(5.0f, 6.0f)
    );

    public static void register() {
        Registry.register(Registries.BLOCK, Mengelectric.id("reston_block"), RESTON_BLOCK);
    }
}
