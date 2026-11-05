package com.enderdevelopment.mengelectric;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RestonItem extends Item {
    public static final int MAX_ENERGY = 150;

    public RestonItem(Settings settings) {
        super(settings);
    }

    public static int getEnergy(ItemStack stack) {
        return stack.getOrCreateNbt().getInt("Energy");
    }

    public static void setEnergy(ItemStack stack, int energy) {
        stack.getOrCreateNbt().putInt("Energy", energy);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            int energy = getEnergy(stack) - 10;
            if (energy <= 0) {
                // Charge fully depleted -> it becomes a Black Reston.
                user.setStackInHand(hand, new ItemStack(ModItems.BLACK_RESTON, stack.getCount()));
            } else {
                setEnergy(stack, energy);
            }
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        // Show how much charge remains: full bar at 150, empty at 0.
        return Math.round(13.0F * Math.max(0, getEnergy(stack)) / MAX_ENERGY);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0xFF00FF00;
    }
}
