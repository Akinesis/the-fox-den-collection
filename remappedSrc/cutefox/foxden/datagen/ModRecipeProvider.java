package cutefox.foxden.datagen;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils;
import cutefox.foxden.registery.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        TheFoxDenCollection.LOGGER.info("Generating recipes for : "+TheFoxDenCollection.MOD_ID);

        //region FOOD
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.YEAST, 1)
                .input(Items.SUGAR)
                .input(Ingredient.ofItems(Items.RED_MUSHROOM, Items.BROWN_MUSHROOM))
                .criterion(hasItem(Items.SUGAR), conditionsFromItem(Items.SUGAR))
                .criterion(hasItem(Items.MILK_BUCKET), conditionsFromItem(Items.MILK_BUCKET))
                .criterion(hasItem(Items.RED_MUSHROOM), conditionsFromItem(Items.RED_MUSHROOM))
                .criterion(hasItem(Items.BROWN_MUSHROOM), conditionsFromItem(Items.BROWN_MUSHROOM))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.YEAST)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.OIL_BUCKET)
                .pattern("SSS")
                .pattern("SBS")
                .pattern("SSS")
                .input('S', Items.SUNFLOWER)
                .input('B', Items.BUCKET)
                .criterion(hasItem(Items.SUNFLOWER), conditionsFromItem(Items.SUNFLOWER))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.OIL_BUCKET)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.BROWN_SAUCE, 1)
                .input(Items.HONEY_BOTTLE)
                .input(Items.BROWN_MUSHROOM)
                .criterion(hasItem(Items.HONEY_BOTTLE), conditionsFromItem(Items.HONEY_BOTTLE))
                .criterion(hasItem(Items.BROWN_MUSHROOM), conditionsFromItem(Items.BROWN_MUSHROOM))
                .criterion(hasItem(ModItems.FRIES), conditionsFromItem(ModItems.FRIES))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.BROWN_SAUCE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.POUTINE)
                .pattern("CSC")
                .pattern("FFF")
                .input('S', ModItems.BROWN_SAUCE)
                .input('C', ModItems.CHEESE)
                .input('F', ModItems.FRIES)
                .criterion(hasItem(ModItems.CHEESE), conditionsFromItem(ModItems.CHEESE))
                .criterion(hasItem(ModItems.FRIES), conditionsFromItem(ModItems.FRIES))
                .criterion(hasItem(ModItems.BROWN_SAUCE), conditionsFromItem(ModItems.BROWN_SAUCE))
                .criterion(hasItem(Items.POTATO), conditionsFromItem(Items.POTATO))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.POUTINE)));

        //endregion

        //region CRAFTING COMPONENTS
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.STEEL_BLEND, 1)
                .input(Items.RAW_IRON)
                .input(Ingredient.ofItems(Items.COAL, Items.CHARCOAL))
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.RAW_IRON))
                .criterion(hasItem(Items.COAL), conditionsFromItem(Items.COAL))
                .criterion(hasItem(Items.CHARCOAL), conditionsFromItem(Items.CHARCOAL))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.STEEL_BLEND)));
        //endregion

        //region SMELTING
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(ModItems.STEEL_BLEND), RecipeCategory.MISC, ModItems.STEEL_INGOT, 1,200)
                .criterion(hasItem(ModItems.STEEL_BLEND), conditionsFromItem(ModItems.STEEL_BLEND))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.STEEL_INGOT)));
        //endregion

        //region STEEL ARMORS
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STEEL_HELMET, 1)
                .pattern("SSS")
                .pattern("S S")
                .input('S', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.STEEL_HELMET)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STEEL_CHESTPLATE, 1)
                .pattern("S S")
                .pattern("SSS")
                .pattern("SSS")
                .input('S', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.STEEL_CHESTPLATE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STEEL_LEGGINGS, 1)
                .pattern("SSS")
                .pattern("S S")
                .pattern("S S")
                .input('S', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.STEEL_LEGGINGS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STEEL_BOOTS, 1)
                .pattern("S S")
                .pattern("S S")
                .input('S', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.STEEL_BOOTS)));
        //endregion
    }
}
