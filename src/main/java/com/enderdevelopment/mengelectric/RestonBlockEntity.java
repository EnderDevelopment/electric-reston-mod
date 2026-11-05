package com.enderdevelopment.mengelectric;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RestonBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory {
    public static final int ENERGY_CAPACITY = 15000; // 15k electric

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int energy = 0;

    public RestonBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RESTON_BLOCK_ENTITY, pos, state);
    }

    public int getEnergy() {
        return energy;
    }

    public int getCapacity() {
        return ENERGY_CAPACITY;
    }

    public void addEnergy(int amount) {
        energy = Math.min(ENERGY_CAPACITY, energy + amount);
        markDirty();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public static void tick(World world, BlockPos pos, BlockState state, RestonBlockEntity be) {
        if (world == null || world.isClient) {
            return;
        }
        ItemStack stack = be.getStack(0);
        if (stack.isEmpty()) {
            return;
        }

        // A Black Reston put into the slot becomes a fully charged Reston
        // when the block has enough energy ("add Reston, it gets electric").
        if (stack.getItem() == ModItems.BLACK_RESTON) {
            if (be.energy >= RestonItem.MAX_ENERGY) {
                be.energy -= RestonItem.MAX_ENERGY;
                ItemStack reston = new ItemStack(ModItems.RESTON);
                RestonItem.setEnergy(reston, RestonItem.MAX_ENERGY);
                be.setStack(0, reston);
                be.markDirty();
            }
            return;
        }

        // A partially charged Reston is topped up from the block energy.
        if (stack.getItem() instanceof RestonItem) {
            int restonEnergy = RestonItem.getEnergy(stack);
            if (restonEnergy < RestonItem.MAX_ENERGY && be.energy > 0) {
                int toTransfer = Math.min(10, Math.min(RestonItem.MAX_ENERGY - restonEnergy, be.energy));
                RestonItem.setEnergy(stack, restonEnergy + toTransfer);
                be.energy -= toTransfer;
                be.markDirty();
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.energy = nbt.getInt("Energy");
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("Energy", this.energy);
        Inventories.writeNbt(nbt, this.inventory);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RestonScreenHandler(syncId, playerInventory, this, ScreenHandlerContext.create(world, pos));
    }
}
