package com.enderdevelopment.mengelectric;

import net.fabricmc.fabric.api.screenhandler.v1.SimpleNamedScreenHandlerFactory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModScreenHandlers {
    public static SimpleNamedScreenHandlerFactory<RestonScreenHandler> RESTON_SCREEN_HANDLER;

    public static void register() {
        RESTON_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Mengelectric.id("reston"),
            new SimpleNamedScreenHandlerFactory<>(
                (syncId, inventory, player) -> new RestonScreenHandler(syncId, inventory),
                (syncId, inventory, player) -> new RestonScreenHandler(syncId, inventory)
            )
        );
    }
}
