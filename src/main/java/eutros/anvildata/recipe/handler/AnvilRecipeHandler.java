package eutros.anvildata.recipe.handler;

import eutros.anvildata.recipe.AnvilInventory;
import eutros.anvildata.recipe.AnvilRecipe;
import net.minecraft.world.World;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.world.WorldEvent;

import javax.annotation.Nullable;

public class AnvilRecipeHandler {

    @Nullable
    private World world;

    public void onAnvilChange(AnvilUpdateEvent evt) {
        AnvilInventory inv = new AnvilInventory(evt.getLeft(), evt.getRight());

        if(world == null) return;

        world.getRecipeManager()
                .getRecipe(AnvilRecipe.TYPE, inv, world)
                .ifPresent(recipe -> {
                    if(evt.getLeft().getCount() == recipe.getItemCost() &&
                            evt.getRight().getCount() >= recipe.getMaterialCost()) {
                        evt.setOutput(recipe.getCraftingResult(inv));
                        evt.setCost(recipe.getCost());
                        evt.setMaterialCost(recipe.getMaterialCost());
                    }
                });
    }

    public void onWorldLoad(WorldEvent.Load evt) {
        world = evt.getWorld().getWorld();
    }

}
