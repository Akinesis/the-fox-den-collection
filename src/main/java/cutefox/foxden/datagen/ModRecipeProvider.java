package cutefox.foxden.datagen;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.conditions.*;
import cutefox.foxden.registery.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    private static final ResourceCondition UPGRADE_TEMPLATE = new UpgradeTemplateCondition();
    private static final ResourceCondition POUTINE = new PoutineCondition();
    private static final ResourceCondition BONE_ARMOR = new BoneArmorCondition();
    private static final ResourceCondition KNIGHT_ARMOR = new SteelArmorCondition();
    private static final ResourceCondition SPACE_RANGER_ARMOR = new SpaceRangerArmorCondition();
    private static final ResourceCondition LEAVES_WALL = new LeavesWallCondition();


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
                .offerTo(withConditions(exporter, POUTINE), Utils.id(getRecipeName(ModItems.YEAST)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.OIL_BUCKET)
                .pattern("SSS")
                .pattern("SBS")
                .pattern("SSS")
                .input('S', Items.SUNFLOWER)
                .input('B', Items.BUCKET)
                .criterion(hasItem(Items.SUNFLOWER), conditionsFromItem(Items.SUNFLOWER))
                .offerTo(withConditions(exporter, POUTINE), Utils.id(getRecipeName(ModItems.OIL_BUCKET)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.BROWN_SAUCE, 1)
                .input(Items.HONEY_BOTTLE)
                .input(Items.BROWN_MUSHROOM)
                .criterion(hasItem(Items.HONEY_BOTTLE), conditionsFromItem(Items.HONEY_BOTTLE))
                .criterion(hasItem(Items.BROWN_MUSHROOM), conditionsFromItem(Items.BROWN_MUSHROOM))
                .criterion(hasItem(ModItems.FRIES), conditionsFromItem(ModItems.FRIES))
                .offerTo(withConditions(exporter, POUTINE), Utils.id(getRecipeName(ModItems.BROWN_SAUCE)));

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
                .offerTo(withConditions(exporter, POUTINE), Utils.id(getRecipeName(ModItems.POUTINE)));

        //endregion

        //region CRAFTING COMPONENTS
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.STEEL_BLEND, 1)
                .input(Items.RAW_IRON)
                .input(Ingredient.ofItems(Items.COAL, Items.CHARCOAL))
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.RAW_IRON))
                .criterion(hasItem(Items.COAL), conditionsFromItem(Items.COAL))
                .criterion(hasItem(Items.CHARCOAL), conditionsFromItem(Items.CHARCOAL))
                .offerTo(withConditions(exporter, KNIGHT_ARMOR), Utils.id(getRecipeName(ModItems.STEEL_BLEND)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ROTTEN_LEATHER, 1)
                .pattern("RR")
                .pattern("RR")
                .input('R', Items.ROTTEN_FLESH)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .criterion(hasItem(Items.ROTTEN_FLESH), conditionsFromItem(Items.ROTTEN_FLESH))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.ROTTEN_LEATHER)));
        //endregion

        //region SMELTING
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(ModItems.STEEL_BLEND), RecipeCategory.MISC, ModItems.STEEL_INGOT, 1,200)
                .criterion(hasItem(ModItems.STEEL_BLEND), conditionsFromItem(ModItems.STEEL_BLEND))
                .offerTo(withConditions(exporter, KNIGHT_ARMOR), Utils.id(getRecipeName(ModItems.STEEL_INGOT)));

        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(ModItems.ROTTEN_LEATHER), RecipeCategory.MISC, Items.LEATHER, 1,200)
                .criterion(hasItem(ModItems.ROTTEN_LEATHER), conditionsFromItem(ModItems.ROTTEN_LEATHER))
                .offerTo(exporter, Utils.id(getRecipeName(Items.LEATHER)));

        //endregion

        //region STEEL ARMORS
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STEEL_HELMET, 1)
                .pattern("SSS")
                .pattern("S S")
                .input('S', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT))
                .offerTo(withConditions(exporter, KNIGHT_ARMOR), Utils.id(getRecipeName(ModItems.STEEL_HELMET)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STEEL_CHESTPLATE, 1)
                .pattern("S S")
                .pattern("SSS")
                .pattern("SSS")
                .input('S', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT))
                .offerTo(withConditions(exporter, KNIGHT_ARMOR), Utils.id(getRecipeName(ModItems.STEEL_CHESTPLATE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STEEL_LEGGINGS, 1)
                .pattern("SSS")
                .pattern("S S")
                .pattern("S S")
                .input('S', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT))
                .offerTo(withConditions(exporter, KNIGHT_ARMOR), Utils.id(getRecipeName(ModItems.STEEL_LEGGINGS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STEEL_BOOTS, 1)
                .pattern("S S")
                .pattern("S S")
                .input('S', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT))
                .offerTo(withConditions(exporter, KNIGHT_ARMOR), Utils.id(getRecipeName(ModItems.STEEL_BOOTS)));
        //endregion

        //region SMITHING RECIPE

        //Stone to Iron
        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.STONE_AXE),
                        Ingredient.ofItems(Items.IRON_INGOT),
                        RecipeCategory.TOOLS,
                        Items.IRON_AXE)
                .criterion(hasItem(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("iron_axe_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.STONE_PICKAXE),
                        Ingredient.ofItems(Items.IRON_INGOT),
                        RecipeCategory.TOOLS,
                        Items.IRON_PICKAXE)
                .criterion(hasItem(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("iron_pickaxe_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.STONE_HOE),
                        Ingredient.ofItems(Items.IRON_INGOT),
                        RecipeCategory.TOOLS,
                        Items.IRON_HOE)
                .criterion(hasItem(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("iron_hoe_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.STONE_SHOVEL),
                        Ingredient.ofItems(Items.IRON_INGOT),
                        RecipeCategory.TOOLS,
                        Items.IRON_SHOVEL)
                .criterion(hasItem(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("iron_shovel_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.STONE_SWORD),
                        Ingredient.ofItems(Items.IRON_INGOT),
                        RecipeCategory.COMBAT,
                        Items.IRON_SWORD)
                .criterion(hasItem(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("iron_sword_smithing"));

        //Gold to Diamond

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.GOLDEN_AXE),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.TOOLS,
                        Items.DIAMOND_AXE)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_gold_axe_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.GOLDEN_PICKAXE),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.TOOLS,
                        Items.DIAMOND_PICKAXE)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_gold_pickaxe_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.GOLDEN_HOE),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.TOOLS,
                        Items.DIAMOND_HOE)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_gold_hoe_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.GOLDEN_SHOVEL),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.TOOLS,
                        Items.DIAMOND_SHOVEL)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_gold_shovel_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.GOLDEN_SWORD),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.COMBAT,
                        Items.DIAMOND_SWORD)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_gold_sword_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.GOLDEN_HELMET),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.COMBAT,
                        Items.DIAMOND_HELMET)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_gold_helmet_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.GOLDEN_CHESTPLATE),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.COMBAT,
                        Items.DIAMOND_CHESTPLATE)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_gold_chestplate_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.GOLDEN_LEGGINGS),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.COMBAT,
                        Items.DIAMOND_LEGGINGS)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_gold_leggings_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.GOLDEN_BOOTS),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.COMBAT,
                        Items.DIAMOND_BOOTS)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_gold_boots_smithing"));

        //Iron to Diamond

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.IRON_AXE),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.TOOLS,
                        Items.DIAMOND_AXE)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_iron_axe_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.IRON_PICKAXE),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.TOOLS,
                        Items.DIAMOND_PICKAXE)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_iron_pickaxe_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.IRON_HOE),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.TOOLS,
                        Items.DIAMOND_HOE)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_iron_hoe_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.IRON_SHOVEL),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.TOOLS,
                        Items.DIAMOND_SHOVEL)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_iron_shovel_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.IRON_SWORD),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.COMBAT,
                        Items.DIAMOND_SWORD)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_iron_sword_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.IRON_HELMET),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.COMBAT,
                        Items.DIAMOND_HELMET)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_iron_helmet_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.IRON_CHESTPLATE),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.COMBAT,
                        Items.DIAMOND_CHESTPLATE)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_iron_chestplate_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.IRON_LEGGINGS),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.COMBAT,
                        Items.DIAMOND_LEGGINGS)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_iron_leggings_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.IRON_BOOTS),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.COMBAT,
                        Items.DIAMOND_BOOTS)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_iron_boots_smithing"));

        //endregion

        //region LEAVES WALLS
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.OAK_LEAVES_WALL,6)
                .pattern("LLL")
                .pattern("LLL")
                .input('L', Items.OAK_LEAVES)
                .criterion(hasItem(Items.OAK_LEAVES), conditionsFromItem(Items.OAK_LEAVES))
                .criterion(hasItem(Items.SHEARS), conditionsFromItem(Items.SHEARS))
                .offerTo(withConditions(exporter, LEAVES_WALL), Utils.id(getRecipeName(ModItems.OAK_LEAVES_WALL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.SPRUCE_LEAVES_WALL,6)
                .pattern("LLL")
                .pattern("LLL")
                .input('L', Items.SPRUCE_LEAVES)
                .criterion(hasItem(Items.SPRUCE_LEAVES), conditionsFromItem(Items.SPRUCE_LEAVES))
                .criterion(hasItem(Items.SHEARS), conditionsFromItem(Items.SHEARS))
                .offerTo(withConditions(exporter, LEAVES_WALL), Utils.id(getRecipeName(ModItems.SPRUCE_LEAVES_WALL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.BIRCH_LEAVES_WALL,6)
                .pattern("LLL")
                .pattern("LLL")
                .input('L', Items.BIRCH_LEAVES)
                .criterion(hasItem(Items.BIRCH_LEAVES), conditionsFromItem(Items.BIRCH_LEAVES))
                .criterion(hasItem(Items.SHEARS), conditionsFromItem(Items.SHEARS))
                .offerTo(withConditions(exporter, LEAVES_WALL), Utils.id(getRecipeName(ModItems.BIRCH_LEAVES_WALL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.ACACIA_LEAVES_WALL,6)
                .pattern("LLL")
                .pattern("LLL")
                .input('L', Items.ACACIA_LEAVES)
                .criterion(hasItem(Items.ACACIA_LEAVES), conditionsFromItem(Items.ACACIA_LEAVES))
                .criterion(hasItem(Items.SHEARS), conditionsFromItem(Items.SHEARS))
                .offerTo(withConditions(exporter, LEAVES_WALL), Utils.id(getRecipeName(ModItems.ACACIA_LEAVES_WALL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.JUNGLE_LEAVES_WALL,6)
                .pattern("LLL")
                .pattern("LLL")
                .input('L', Items.JUNGLE_LEAVES)
                .criterion(hasItem(Items.JUNGLE_LEAVES), conditionsFromItem(Items.JUNGLE_LEAVES))
                .criterion(hasItem(Items.SHEARS), conditionsFromItem(Items.SHEARS))
                .offerTo(withConditions(exporter, LEAVES_WALL), Utils.id(getRecipeName(ModItems.JUNGLE_LEAVES_WALL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.MANGROVE_LEAVES_WALL,6)
                .pattern("LLL")
                .pattern("LLL")
                .input('L', Items.MANGROVE_LEAVES)
                .criterion(hasItem(Items.MANGROVE_LEAVES), conditionsFromItem(Items.MANGROVE_LEAVES))
                .criterion(hasItem(Items.SHEARS), conditionsFromItem(Items.SHEARS))
                .offerTo(withConditions(exporter, LEAVES_WALL), Utils.id(getRecipeName(ModItems.MANGROVE_LEAVES_WALL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.DARK_OAK_LEAVES_WALL,6)
                .pattern("LLL")
                .pattern("LLL")
                .input('L', Items.DARK_OAK_LEAVES)
                .criterion(hasItem(Items.DARK_OAK_LEAVES), conditionsFromItem(Items.DARK_OAK_LEAVES))
                .criterion(hasItem(Items.SHEARS), conditionsFromItem(Items.SHEARS))
                .offerTo(withConditions(exporter, LEAVES_WALL), Utils.id(getRecipeName(ModItems.DARK_OAK_LEAVES_WALL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.CHERRY_LEAVES_WALL,6)
                .pattern("LLL")
                .pattern("LLL")
                .input('L', Items.CHERRY_LEAVES)
                .criterion(hasItem(Items.CHERRY_LEAVES), conditionsFromItem(Items.CHERRY_LEAVES))
                .criterion(hasItem(Items.SHEARS), conditionsFromItem(Items.SHEARS))
                .offerTo(withConditions(exporter, LEAVES_WALL), Utils.id(getRecipeName(ModItems.CHERRY_LEAVES_WALL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.AZALEA_LEAVES_WALL,6)
                .pattern("LLL")
                .pattern("LLL")
                .input('L', Items.AZALEA_LEAVES)
                .criterion(hasItem(Items.AZALEA_LEAVES), conditionsFromItem(Items.AZALEA_LEAVES))
                .criterion(hasItem(Items.SHEARS), conditionsFromItem(Items.SHEARS))
                .offerTo(withConditions(exporter, LEAVES_WALL), Utils.id(getRecipeName(ModItems.AZALEA_LEAVES_WALL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.FLOWERING_AZALEA_LEAVES_WALL,6)
                .pattern("LLL")
                .pattern("LLL")
                .input('L', Items.FLOWERING_AZALEA_LEAVES)
                .criterion(hasItem(Items.FLOWERING_AZALEA_LEAVES), conditionsFromItem(Items.FLOWERING_AZALEA_LEAVES))
                .criterion(hasItem(Items.SHEARS), conditionsFromItem(Items.SHEARS))
                .offerTo(withConditions(exporter, LEAVES_WALL), Utils.id(getRecipeName(ModItems.FLOWERING_AZALEA_LEAVES_WALL)));
        //endregion

    }
}
