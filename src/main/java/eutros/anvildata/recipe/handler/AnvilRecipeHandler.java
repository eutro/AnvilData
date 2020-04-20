package eutros.anvildata.recipe.handler;

import eutros.anvildata.Reference;
import eutros.anvildata.recipe.AnvilInventory;
import eutros.anvildata.recipe.AnvilRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class AnvilRecipeHandler {

    private static RecipeManager recipeManager;

    @SubscribeEvent(priority = EventPriority.LOW) // allow shadowing of other recipes
    public static void onAnvilChange(AnvilUpdateEvent evt) {
        AnvilInventory inv = new AnvilInventory(evt.getLeft(), evt.getRight());

        //noinspection ConstantConditions world is actually nullable for our use
        recipeManager.getRecipe(AnvilRecipe.TYPE, inv, null).ifPresent(recipe -> {
            if(evt.getLeft().getCount() == recipe.getItemCost() &&
                    evt.getRight().getCount() >= recipe.getMaterialCost()) {
                evt.setOutput(recipe.getCraftingResult(inv));
                evt.setCost(recipe.getCost());
                evt.setMaterialCost(recipe.getMaterialCost());
            }
        });
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load evt) {
        recipeManager = evt.getWorld().getWorld().getRecipeManager();
    }

}
