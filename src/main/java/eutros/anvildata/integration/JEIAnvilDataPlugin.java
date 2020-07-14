package eutros.anvildata.integration;

import eutros.anvildata.AnvilData;
import eutros.anvildata.recipe.AnvilRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class JEIAnvilDataPlugin implements IModPlugin {

    private static final ResourceLocation UID = new ResourceLocation(AnvilData.MOD_ID, "jei_plugin");

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IVanillaRecipeFactory factory = registration.getVanillaRecipeFactory();
        ClientWorld world = Minecraft.getInstance().world;
        if(world == null) {
            return;
        }

        List<Object> recipes = world.getRecipeManager()
                .getRecipes(AnvilRecipe.TYPE).values().parallelStream()
                .filter(AnvilRecipe.class::isInstance)
                .map(AnvilRecipe.class::cast)
                .map(recipe -> factory.createAnvilRecipe(
                        Arrays.asList(recipe.getLeft().getMatchingStacks()),
                        Arrays.asList(recipe.getRight().getMatchingStacks()),
                        Collections.singletonList(recipe.getRecipeOutput())))
                .collect(Collectors.toList());

        registration.addRecipes(recipes, VanillaRecipeCategoryUid.ANVIL);
    }

}
