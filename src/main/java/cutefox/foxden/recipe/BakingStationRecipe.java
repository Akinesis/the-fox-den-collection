package cutefox.foxden.recipe;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cutefox.foxden.Utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class BakingStationRecipe implements Recipe<RecipeInput> {
    private final DefaultedList<Ingredient> ingredients;
    private final ItemStack result;

    public BakingStationRecipe(DefaultedList<Ingredient> inputs, ItemStack output) {
        this.ingredients = inputs;
        this.result = output;
    }


    @Override
    public boolean matches(RecipeInput recipeInput, World world) {
        return Utils.matchesRecipe(recipeInput,ingredients,1,3);
    }


    @Override
    public ItemStack craft(RecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.result.copy();
    }

    public @NotNull ItemStack getResultItem() {
        return getResult(null);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public static class Serializer implements RecipeSerializer<BakingStationRecipe> {

        public static final MapCodec<BakingStationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").flatXmap(list -> {
                            Ingredient[] ingredients = list.toArray(Ingredient[]::new);
                            if (ingredients.length == 0) {
                                return DataResult.error(() -> "No ingredients for shapeless recipe");
                            }
                            return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients));
                        }, DataResult::success).forGetter(BakingStationRecipe::getIngredients),
                        ItemStack.CODEC.fieldOf("result").forGetter(recipe -> {
                            return recipe.result;
                        })
                ).apply(instance, BakingStationRecipe::new)
        );

        /*public static final PacketCodec<RegistryByteBuf, BakingStationRecipe> STREAM_CODEC = PacketCodec.(
                StreamCodecUtil.nonNullList(Ingredient.PACKET_CODEC, Ingredient.EMPTY), BakingStationRecipe::getIngredients,
                ItemStack.PACKET_CODEC, BakingStationRecipe::getResultItem,
                BakingStationRecipe::new
        );*/


        public static final PacketCodec<RegistryByteBuf, BakingStationRecipe> PACKET_CODEC = PacketCodec.ofStatic(BakingStationRecipe.Serializer::write, BakingStationRecipe.Serializer::read);

        public MapCodec<BakingStationRecipe> codec() {
            return CODEC;
        }

        public PacketCodec<RegistryByteBuf, BakingStationRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static BakingStationRecipe read(RegistryByteBuf buf) {
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll((empty) ->
                Ingredient.PACKET_CODEC.decode(buf)
            );
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            return new BakingStationRecipe(defaultedList, itemStack);
        }

        private static void write(RegistryByteBuf buf, BakingStationRecipe recipe) {

            buf.writeVarInt(recipe.ingredients.size());
            Iterator var2 = recipe.ingredients.iterator();

            while(var2.hasNext()) {
                Ingredient ingredient = (Ingredient)var2.next();
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
        }
    }
}
