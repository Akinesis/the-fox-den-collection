package cutefox.foxden.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cutefox.foxden.Utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Iterator;

public class StoveRecipe implements Recipe<RecipeInput> {

    protected final DefaultedList<Ingredient> ingredients;
    protected final ItemStack output;
    protected final float experience;

    public StoveRecipe(DefaultedList<Ingredient> inputs, ItemStack output, float experience) {
        this.ingredients = inputs;
        this.output = output;
        this.experience = experience;
    }

    public static class Type implements RecipeType<StoveRecipe> {

        private Type() {}
        public static final Type INSTANCE = new Type();

        public static final Identifier ID = Utils.id("stove");

    }

    @Override
    public boolean matches(RecipeInput input, World world) {
        return Utils.matchesRecipe(input, ingredients,1,3);
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
        return this.output.copy();
    }

    @Override
    public RecipeSerializer<StoveRecipe> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<StoveRecipe> getType() {
        return Type.INSTANCE;
    }

    public float getExperience() {
        return experience;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return ingredients;
    }

    public static class Serializer implements RecipeSerializer<StoveRecipe> {

        private Serializer() {
        }

        public static final MapCodec<StoveRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").flatXmap(list -> {
                            Ingredient[] ingredients = list.toArray(Ingredient[]::new);
                            if (ingredients.length == 0) {
                                return DataResult.error(() -> "No ingredients for shapeless recipe");
                            }
                            return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients));
                        }, DataResult::success).forGetter(StoveRecipe::getIngredients),
                        ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                        Codec.FLOAT.fieldOf("experience").forGetter(StoveRecipe::getExperience)
                ).apply(instance, StoveRecipe::new)
        );

        public static final StoveRecipe.Serializer INSTANCE = new Serializer();

        public static final PacketCodec<RegistryByteBuf, StoveRecipe> PACKET_CODEC = PacketCodec
                .ofStatic(StoveRecipe.Serializer::write, StoveRecipe.Serializer::read);


        @Override
        public MapCodec<StoveRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, StoveRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static StoveRecipe read(RegistryByteBuf buf) {
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll((empty) ->
                    Ingredient.PACKET_CODEC.decode(buf)
            );
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            float experience = buf.readFloat();
            return new StoveRecipe(defaultedList, itemStack, experience);
        }

        private static void write(RegistryByteBuf buf, StoveRecipe recipe) {

            buf.writeVarInt(recipe.ingredients.size());
            Iterator var2 = recipe.ingredients.iterator();

            while(var2.hasNext()) {
                Ingredient ingredient = (Ingredient)var2.next();
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            ItemStack.PACKET_CODEC.encode(buf, recipe.output);
            buf.writeFloat(recipe.experience);
        }
    }
}
