package com.enderdevelopment.mengelectric;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<RestonBlockEntity> RESTON_BLOCK_ENTITY;

    public static void register() {
        RESTON_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Mengelectric.id("reston_block_entity"),
            FabricBlockEntityTypeBuilder.create(RestonBlockEntity::new, ModBlocks.RESTON_BLOCK).build()
        );
    }
}
