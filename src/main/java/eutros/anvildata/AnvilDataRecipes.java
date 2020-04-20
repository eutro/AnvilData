package eutros.anvildata;

import eutros.anvildata.recipe.AnvilRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnvilDataRecipes {

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> evt) {
        IForgeRegistry<IRecipeSerializer<?>> r = evt.getRegistry();

        r.register(AnvilRecipe.SERIALIZER);
    }

}
