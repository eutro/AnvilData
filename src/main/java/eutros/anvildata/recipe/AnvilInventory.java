package eutros.anvildata.recipe;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class AnvilInventory implements IInventory {

    private final Pair<ItemStack, ItemStack> contents;

    public AnvilInventory(ItemStack first, ItemStack second) {
        this(Pair.of(first, second));
    }

    public AnvilInventory(Pair<ItemStack, ItemStack> contents) {
        this.contents = contents;
    }

    public ItemStack getLeft() {
        return contents.getLeft();
    }

    public ItemStack getRight() {
        return contents.getRight();
    }

    // blah blah blah

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStack.EMPTY;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
    }

    @Override
    public void markDirty() {
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
    }

}
