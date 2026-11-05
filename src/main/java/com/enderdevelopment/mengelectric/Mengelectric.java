package com.enderdevelopment.mengelectric;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mengelectric implements ModInitializer {
    public static final String MOD_ID = "mengelectric";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModItems.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModScreenHandlers.register();
        ModItemGroup.register();
        LOGGER.info("Menga Electric mod loaded. Reston battery, Black Reston and Reston Block added.");
    }
}
