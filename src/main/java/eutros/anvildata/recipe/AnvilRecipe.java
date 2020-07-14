package eutros.anvildata.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eutros.anvildata.AnvilData;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class AnvilRecipe implements IRecipe<AnvilInventory> {

    private static final ResourceLocation serializerID = new ResourceLocation(AnvilData.MOD_ID, "anvil_recipe");
    public static final IRecipeSerializer<AnvilRecipe> SERIALIZER = new Serializer();

    private static final Logger LOGGER = LogManager.getLogger();

    public static final IRecipeType<AnvilRecipe> TYPE =
            Registry.register(Registry.RECIPE_TYPE, serializerID,
                    new IRecipeType<AnvilRecipe>() {
                        public String toString() {
                            return String.format("AnvilRecipeType(%s)", serializerID.toString());
                        }
                    }
            );

    private final Pair<Ingredient, Ingredient> ingredients;
    private final ItemStack output;
    private final int cost;
    private final int materialCost;
    private final int itemCost;

    private final ResourceLocation id;

    private AnvilRecipe(ResourceLocation id, Ingredient first, Ingredient second, ItemStack output, int cost, int materialCost, int itemCost) {
        this(id, Pair.of(first, second), output, cost, materialCost, itemCost);
    }

    private AnvilRecipe(ResourceLocation id, Pair<Ingredient, Ingredient> ingredients, ItemStack output, int cost, int materialCost, int itemCost) {
        this.id = id;
        this.ingredients = ingredients;
        this.output = output;
        this.cost = cost;
        this.materialCost = materialCost;
        this.itemCost = itemCost;
    }

    public Ingredient getLeft() {
        return ingredients.getLeft();
    }

    public Ingredient getRight() {
        return ingredients.getRight();
    }

    @Override
    public boolean matches(AnvilInventory inv, @Nullable World worldIn) {
        return getLeft().test(inv.getLeft()) &&
                getRight().test(inv.getRight());
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    @Override
    public ItemStack getCraftingResult(AnvilInventory inv) {
        return output.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    public int getCost() {
        return cost;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return TYPE;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    public int getMaterialCost() {
        return materialCost;
    }

    public int getItemCost() {
        return itemCost;
    }

    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AnvilRecipe> {

        public Serializer() {
            setRegistryName(serializerID);
        }

        @Override
        public AnvilRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient first = CraftingHelper.getIngredient(json.get("first"));
            Ingredient second = CraftingHelper.getIngredient(json.get("second"));
            ItemStack output = CraftingHelper.getItemStack(json.getAsJsonObject("result"), true);

            int cost = 4;
            JsonElement costObj = json.get("cost");
            if(costObj != null) {
                int val = costObj.getAsInt();
                if(val >= 0)
                    cost = val;
            }

            int itemCost = 1;
            if(first.hasNoMatchingItems())
                LOGGER.warn("Anvil recipe: {}: no items that satisfy the first ingredient.", recipeId);
            else
                itemCost = first.getMatchingStacks()[0].getCount();

            int materialCost = 1;
            if(second.hasNoMatchingItems())
                LOGGER.warn("Anvil recipe: {}: no items that satisfy the second ingredient.", recipeId);
            else
                materialCost = second.getMatchingStacks()[0].getCount();

            return new AnvilRecipe(recipeId, first, second, output, cost, materialCost, itemCost);
        }

        @Override
        public AnvilRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            return new AnvilRecipe(recipeId,
                    Ingredient.read(buffer),
                    Ingredient.read(buffer),
                    buffer.readItemStack(),
                    buffer.readVarInt(),
                    buffer.readVarInt(),
                    buffer.readVarInt());
        }

        @Override
        public void write(PacketBuffer buffer, AnvilRecipe recipe) {
            CraftingHelper.write(buffer, recipe.getLeft());
            CraftingHelper.write(buffer, recipe.getRight());
            buffer.writeItemStack(recipe.output)
                    .writeVarInt(recipe.getCost())
                    .writeVarInt(recipe.getMaterialCost())
                    .writeVarInt(recipe.getItemCost());
        }

    }

}
