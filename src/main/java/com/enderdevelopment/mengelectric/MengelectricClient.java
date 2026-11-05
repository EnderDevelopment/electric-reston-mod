package com.enderdevelopment.mengelectric;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class MengelectricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.RESTON_SCREEN_HANDLER, RestonScreen::new);
    }
}
