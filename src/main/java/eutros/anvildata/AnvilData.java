package eutros.anvildata;

import eutros.anvildata.recipe.AnvilRecipe;
import eutros.anvildata.recipe.handler.AnvilRecipeHandler;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AnvilData.MOD_ID)
public class AnvilData {

    public static final String MOD_ID = "anvildata";

    public AnvilData() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, this::registerRecipeSerializers);
        AnvilRecipeHandler arh = new AnvilRecipeHandler();
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, arh::onAnvilChange); // allow shadowing of other recipes
        MinecraftForge.EVENT_BUS.addListener(arh::onWorldLoad);
    }

    public void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> evt) {
        evt.getRegistry().register(AnvilRecipe.SERIALIZER);
    }

}
