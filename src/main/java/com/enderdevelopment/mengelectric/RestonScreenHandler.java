package com.enderdevelopment.mengelectric;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

public class RestonScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final ScreenHandlerContext context;
    private final RestonBlockEntity blockEntity;

    private int energy = 0;

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            if (index == 0) {
                return blockEntity != null ? blockEntity.getEnergy() : energy;
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                energy = value;
            }
        }

        @Override
        public int size() {
            return 1;
        }
    };

    // Server-side constructor used when a player opens the block.
    public RestonScreenHandler(int syncId, PlayerInventory playerInventory,
                               RestonBlockEntity blockEntity, ScreenHandlerContext context) {
        this(syncId, playerInventory, (Inventory) blockEntity, context, blockEntity);
    }

    // Client-side constructor used to rebuild the screen layout.
    public RestonScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new ImplementedInventory() {
            private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

            @Override
            public DefaultedList<ItemStack> getItems() {
                return items;
            }
        }, ScreenHandlerContext.EMPTY, null);
    }

    private RestonScreenHandler(int syncId, PlayerInventory playerInventory,
                                Inventory inventory, ScreenHandlerContext context,
                                RestonBlockEntity blockEntity) {
        super(ModScreenHandlers.RESTON_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.context = context;
        this.blockEntity = blockEntity;
        checkSize(inventory, 1);
        inventory.onOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 80, 17));
        this.addProperties(this.propertyDelegate);

        addPlayerInventory(playerInventory, 8, 84);
        addPlayerHotbar(playerInventory, 8, 142);
    }

    public int getEnergy() {
        return energy;
    }

    public int getCapacity() {
        return RestonBlockEntity.ENERGY_CAPACITY;
    }

    public RestonBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(ItemStack stack) {
        return stack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory, int x, int y) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, x + col * 18, y + row * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory, int x, int y) {
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, x + col * 18, y));
        }
    }
}
