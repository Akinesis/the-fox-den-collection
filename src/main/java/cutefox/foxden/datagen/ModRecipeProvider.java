package cutefox.foxden.datagen;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.conditions.*;
import cutefox.foxden.registery.ModBlocks;
import cutefox.foxden.registery.ModItemTags;
import cutefox.foxden.registery.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    private static final ResourceCondition UPGRADE_TEMPLATE = new UpgradeTemplateCondition();
    private static final ResourceCondition POUTINE = new PoutineCondition();
    private static final ResourceCondition BONE_ARMOR = new BoneArmorCondition();
    private static final ResourceCondition KNIGHT_ARMOR = new SteelArmorCondition();
    private static final ResourceCondition SPACE_RANGER_ARMOR = new SpaceRangerArmorCondition();
    private static final ResourceCondition LEAVES_WALL = new LeavesWallCondition();
    private static final ResourceCondition BIKE_ARMOR = new BikeArmorCondition();


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

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, Items.POISONOUS_POTATO)
                .input(Items.POTATO)
                .input(Items.SPIDER_EYE)
                .input(ModItemTags.YEAST)
                .criterion(hasItem(Items.POTATO), conditionsFromItem(Items.POTATO))
                .offerTo(exporter, Utils.id(getRecipeName(Items.POISONOUS_POTATO)));

        //endregion

        //region CRAFTING COMPONENTS
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.STEEL_BLEND, 1)
                .input(Items.RAW_IRON)
                .input(Ingredient.ofItems(Items.COAL, Items.CHARCOAL))
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.RAW_IRON))
                .criterion(hasItem(Items.COAL), conditionsFromItem(Items.COAL))
                .criterion(hasItem(Items.CHARCOAL), conditionsFromItem(Items.CHARCOAL))
                .offerTo(withConditions(exporter, KNIGHT_ARMOR), Utils.id(getRecipeName(ModItems.STEEL_BLEND)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.STEEL_BLOCK, 1)
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .input('I', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT))
                .offerTo(withConditions(exporter, KNIGHT_ARMOR), Utils.id(getRecipeName(ModItems.STEEL_BLOCK)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.STEEL_INGOT, 9)
                .input(ModItems.STEEL_BLOCK)
                .criterion(hasItem(ModItems.STEEL_BLOCK), conditionsFromItem(ModItems.STEEL_BLOCK))
                .offerTo(withConditions(exporter, KNIGHT_ARMOR), Utils.id(getRecipeName(ModItems.STEEL_INGOT)+"_block"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ROTTEN_LEATHER, 1)
                .pattern("RR")
                .pattern("RR")
                .input('R', Items.ROTTEN_FLESH)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .criterion(hasItem(Items.ROTTEN_FLESH), conditionsFromItem(Items.ROTTEN_FLESH))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.ROTTEN_LEATHER)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.BONE_MEAL, 6)
                .input(ModItems.BONE_BOOTS)
                .criterion(hasItem(ModItems.BONE_BOOTS), conditionsFromItem(ModItems.BONE_BOOTS))
                .offerTo(withConditions(exporter, BONE_ARMOR), Utils.id(getRecipeName(Items.BONE_MEAL)+"_from_bone_boots"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.BONE_MEAL, 9)
                .input(ModItems.BONE_LEGGINGS)
                .criterion(hasItem(ModItems.BONE_LEGGINGS), conditionsFromItem(ModItems.BONE_LEGGINGS))
                .offerTo(withConditions(exporter, BONE_ARMOR), Utils.id(getRecipeName(Items.BONE_MEAL)+"_from_bone_leggings"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.BONE_MEAL, 10)
                .input(ModItems.BONE_CHESTPLATE)
                .criterion(hasItem(ModItems.BONE_CHESTPLATE), conditionsFromItem(ModItems.BONE_CHESTPLATE))
                .offerTo(withConditions(exporter, BONE_ARMOR), Utils.id(getRecipeName(Items.BONE_MEAL)+"_from_bone_chestplate"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.BONE_MEAL, 7)
                .input(ModItems.BONE_HELMET)
                .criterion(hasItem(ModItems.BONE_HELMET), conditionsFromItem(ModItems.BONE_HELMET))
                .offerTo(withConditions(exporter, BONE_ARMOR), Utils.id(getRecipeName(Items.BONE_MEAL)+"_from_bone_helmet"));

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

        //Wolf armors
        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.WOLF_ARMOR),
                        Ingredient.ofItems(Items.IRON_INGOT),
                        RecipeCategory.COMBAT,
                        ModItems.IRON_WOLF_ARMOR)
                .criterion(hasItem(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("iron_wolf_armor_smithing"));

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(ModItems.IRON_WOLF_ARMOR),
                        Ingredient.ofItems(Items.DIAMOND),
                        RecipeCategory.COMBAT,
                        ModItems.DIAMOND_WOLF_ARMOR)
                .criterion(hasItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                .offerTo(withConditions(exporter, UPGRADE_TEMPLATE), Utils.id("diamond_wolf_armor_smithing"));
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

        //region Bakery

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.DOUGHT, 12)
                .input(ConventionalItemTags.WHEAT_CROPS)
                .input(ConventionalItemTags.WHEAT_CROPS)
                .input(ModItemTags.YEAST)
                .input(ConventionalItemTags.WATER_BUCKETS)
                .criterion("has_wheat", conditionsFromTag(ConventionalItemTags.WHEAT_CROPS))
                .criterion("has_yeast", conditionsFromTag(ModItemTags.YEAST))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.DOUGHT)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CAKE_DOUGH, 12)
                .input(ConventionalItemTags.MILK_BUCKETS)
                .input(ConventionalItemTags.WHEAT_CROPS)
                .input(Items.SUGAR)
                .input(Items.EGG)
                .criterion(hasItem(Items.SUGAR), conditionsFromItem(Items.SUGAR))
                .criterion("has_wheat", conditionsFromTag(ConventionalItemTags.WHEAT_CROPS))
                .criterion("has_milk", conditionsFromTag(ConventionalItemTags.MILK_BUCKETS))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.CAKE_DOUGH)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.SWEET_DOUGH, 12)
                .input(Items.WATER_BUCKET)
                .input(ConventionalItemTags.WHEAT_CROPS)
                .input(Items.SUGAR)
                .input(Items.EGG)
                .criterion(hasItem(Items.SUGAR), conditionsFromItem(Items.SUGAR))
                .criterion("has_wheat", conditionsFromTag(ConventionalItemTags.WHEAT_CROPS))
                .criterion(hasItem(Items.EGG), conditionsFromItem(Items.EGG))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.SWEET_DOUGH)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CROISSANT_RAW, 8)
                .input(ModItems.SWEET_DOUGH)
                .input(ModItemTags.YEAST)
                .input(Items.SUGAR)
                .input(ModItems.ROLLING_PIN)
                .criterion(hasItem(ModItems.SWEET_DOUGH), conditionsFromItem(ModItems.SWEET_DOUGH))
                .criterion(hasItem(ModItems.ROLLING_PIN), conditionsFromItem(ModItems.ROLLING_PIN))
                .criterion("has_yeast", conditionsFromTag(ModItemTags.YEAST))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.CROISSANT_RAW)));
        
        this.addCookRecipe(ModItems.CROISSANT_RAW, ModItems.CROISSANT, 0.35F, exporter);
        
        Item rawItemOfRecipe = ModItems.RAW_CRUSTY_BREAD;
        Item itemOfRecipe = ModItems.CRUSTY_BREAD;
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, rawItemOfRecipe, 4)
                .input(ConventionalItemTags.WATER_BUCKETS)
                .input(ModItemTags.YEAST)
                .input(ModItemTags.DOUGHT)
                .input(ModItems.ROLLING_PIN)
                .criterion("has_daught", conditionsFromTag(ModItemTags.DOUGHT))
                .criterion("has_yeast", conditionsFromTag(ModItemTags.YEAST))
                .criterion(hasItem(ModItems.ROLLING_PIN), conditionsFromItem(ModItems.ROLLING_PIN))
                .offerTo(exporter, Utils.id(getRecipeName(rawItemOfRecipe)));
        this.addCookRecipe(rawItemOfRecipe, itemOfRecipe, 0.35F, exporter);
        
        rawItemOfRecipe = ModItems.RAW_BAGUETTE;
        itemOfRecipe = ModItems.BAGUETTE;
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, rawItemOfRecipe, 3)
                .input(ModItemTags.YEAST)
                .input(ModItemTags.DOUGHT)
                .input(ModItemTags.DOUGHT)
                .input(ModItems.ROLLING_PIN)
                .criterion("has_daught", conditionsFromTag(ModItemTags.DOUGHT))
                .criterion("has_yeast", conditionsFromTag(ModItemTags.YEAST))
                .criterion(hasItem(ModItems.ROLLING_PIN), conditionsFromItem(ModItems.ROLLING_PIN))
                .offerTo(exporter, Utils.id(getRecipeName(rawItemOfRecipe)));
        this.addCookRecipe(rawItemOfRecipe, itemOfRecipe, 0.35F, exporter);

        rawItemOfRecipe = ModItems.RAW_BRAIDED_BREAD;
        itemOfRecipe = ModItems.BRAIDED_BREAD;
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, rawItemOfRecipe, 4)
                .input(ModItems.SWEET_DOUGH)
                .input(Items.SUGAR)
                .input(ModItemTags.DOUGHT)
                .input(ModItems.ROLLING_PIN)
                .criterion("has_daught", conditionsFromTag(ModItemTags.DOUGHT))
                .criterion(hasItem(ModItems.SWEET_DOUGH), conditionsFromItem(ModItems.SWEET_DOUGH))
                .criterion("has_yeast", conditionsFromTag(ModItemTags.YEAST))
                .criterion(hasItem(ModItems.ROLLING_PIN), conditionsFromItem(ModItems.ROLLING_PIN))
                .offerTo(exporter, Utils.id(getRecipeName(rawItemOfRecipe)));
        this.addCookRecipe(rawItemOfRecipe, itemOfRecipe, 0.35F, exporter);

        rawItemOfRecipe = ModItems.RAW_CORNET;
        itemOfRecipe = ModItems.CORNET;
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, rawItemOfRecipe, 4)
                .input(ModItems.SWEET_DOUGH)
                .input(Items.SUGAR)
                .input(ModItems.APPLE_JAM)
                .input(ModItems.ROLLING_PIN)
                .criterion(hasItem(ModItems.SWEET_DOUGH), conditionsFromItem(ModItems.SWEET_DOUGH))
                .criterion(hasItem(ModItems.APPLE_JAM), conditionsFromItem(ModItems.APPLE_JAM))
                .criterion(hasItem(ModItems.ROLLING_PIN), conditionsFromItem(ModItems.ROLLING_PIN))
                .offerTo(exporter, Utils.id(getRecipeName(rawItemOfRecipe)));
        this.addCookRecipe(rawItemOfRecipe, itemOfRecipe, 0.35F, exporter);

        rawItemOfRecipe = ModItems.RAW_TOAST;
        itemOfRecipe = ModItems.TOAST;
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, rawItemOfRecipe, 3)
                .input(ModItemTags.DOUGHT)
                .input(Items.SUGAR)
                .input(ConventionalItemTags.MILK_BUCKETS)
                .input(ModItems.ROLLING_PIN)
                .criterion("has_daught", conditionsFromTag(ModItemTags.DOUGHT))
                .criterion("has_milk", conditionsFromTag(ConventionalItemTags.MILK_BUCKETS))
                .criterion(hasItem(ModItems.ROLLING_PIN), conditionsFromItem(ModItems.ROLLING_PIN))
                .offerTo(exporter, Utils.id(getRecipeName(rawItemOfRecipe)));
        this.addCookRecipe(rawItemOfRecipe, itemOfRecipe, 0.35F, exporter);

        rawItemOfRecipe = ModItems.RAW_BUN;
        itemOfRecipe = ModItems.BUN;
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, rawItemOfRecipe, 5)
                .input(ModItemTags.DOUGHT)
                .input(ModItemTags.YEAST)
                .input(ModItemTags.EGG)
                .input(ModItems.ROLLING_PIN)
                .criterion("has_daught", conditionsFromTag(ModItemTags.DOUGHT))
                .criterion("has_yeast", conditionsFromTag(ModItemTags.YEAST))
                .criterion("has_egg", conditionsFromTag(ModItemTags.EGG))
                .criterion(hasItem(ModItems.ROLLING_PIN), conditionsFromItem(ModItems.ROLLING_PIN))
                .offerTo(exporter, Utils.id(getRecipeName(rawItemOfRecipe)));
        this.addCookRecipe(rawItemOfRecipe, itemOfRecipe, 0.35F, exporter);

        rawItemOfRecipe = ModItems.RAW_WAFFLE;
        itemOfRecipe = ModItems.WAFFLE;
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, rawItemOfRecipe, 6)
                .input(ModItemTags.DOUGHT)
                .input(Items.SUGAR)
                .input(ModItemTags.EGG)
                .input(ModItems.ROLLING_PIN)
                .criterion("has_daught", conditionsFromTag(ModItemTags.DOUGHT))
                .criterion("has_egg", conditionsFromTag(ModItemTags.EGG))
                .criterion(hasItem(ModItems.ROLLING_PIN), conditionsFromItem(ModItems.ROLLING_PIN))
                .offerTo(exporter, Utils.id(getRecipeName(rawItemOfRecipe)));
        this.addCookRecipe(rawItemOfRecipe, itemOfRecipe, 0.35F, exporter);

        rawItemOfRecipe = ModItems.RAW_APPLE_PIE;
        itemOfRecipe = ModItems.APPLE_PIE;
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, rawItemOfRecipe, 6)
                .input(Items.APPLE)
                .input(Items.SUGAR)
                .input(ModItems.ROLLING_PIN)
                .input(ModItems.SWEET_DOUGH)
                .criterion(hasItem(Items.APPLE), conditionsFromItem(Items.APPLE))
                .criterion(hasItem(ModItems.SWEET_DOUGH), conditionsFromItem(ModItems.SWEET_DOUGH))
                .criterion(hasItem(ModItems.ROLLING_PIN), conditionsFromItem(ModItems.ROLLING_PIN))
                .offerTo(exporter, Utils.id(getRecipeName(rawItemOfRecipe)));
        this.addCookRecipe(rawItemOfRecipe, itemOfRecipe, 0.35F, exporter);

        rawItemOfRecipe = ModItems.RAW_BUNDT_CAKE;
        itemOfRecipe = ModItems.BUNDT_CAKE;
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, rawItemOfRecipe, 6)
                .input(ModItemTags.YEAST)
                .input(Items.SUGAR)
                .input(ModItemTags.EGG)
                .criterion("has_yeast", conditionsFromTag(ModItemTags.YEAST))
                .criterion("has_egg", conditionsFromTag(ModItemTags.EGG))
                .criterion(hasItem(ModItems.ROLLING_PIN), conditionsFromItem(ModItems.ROLLING_PIN))
                .offerTo(exporter, Utils.id(getRecipeName(rawItemOfRecipe)));
        this.addCookRecipe(rawItemOfRecipe, itemOfRecipe, 0.35F, exporter);

        rawItemOfRecipe = ModItems.RAW_LINZER_TART;
        itemOfRecipe = ModItems.LINZER_TART;
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, rawItemOfRecipe, 6)
                .input(ModItems.SWEET_DOUGH)
                .input(Items.SUGAR)
                .input(Items.SWEET_BERRIES)
                .input(ModItems.ROLLING_PIN)
                .criterion(hasItem(ModItems.SWEET_DOUGH), conditionsFromItem(ModItems.SWEET_DOUGH))
                .criterion(hasItem(Items.SWEET_BERRIES), conditionsFromItem(Items.SWEET_BERRIES))
                .criterion("has_egg", conditionsFromTag(ModItemTags.EGG))
                .criterion(hasItem(ModItems.ROLLING_PIN), conditionsFromItem(ModItems.ROLLING_PIN))
                .offerTo(exporter, Utils.id(getRecipeName(rawItemOfRecipe)));
        this.addCookRecipe(rawItemOfRecipe, itemOfRecipe, 0.35F, exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.JAR, 2)
                .pattern("S")
                .pattern("G")
                .pattern("G")
                .input('S', ItemTags.WOODEN_SLABS)
                .input('G', Items.GLASS_PANE)
                .criterion(hasItem(ModItems.JAR), conditionsFromItem(ModItems.JAR))
                .criterion(hasItem(Items.GLASS_PANE), conditionsFromItem(Items.GLASS_PANE))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.JAR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BREADBOX, 1)
                .pattern("BBB")
                .pattern("B B")
                .pattern("LLL")
                .input('L', ItemTags.WOODEN_SLABS)
                .input('B', Items.STICK)
                .criterion(hasItem(ModBlocks.BREADBOX), conditionsFromItem(ModBlocks.BREADBOX))
                .criterion("has_wooden_slab", conditionsFromTag(ItemTags.WOODEN_SLABS))
                .offerTo(exporter, Utils.id(getRecipeName(ModBlocks.BREADBOX)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BREAD_CRATE, 1)
                .pattern(" U ")
                .pattern("_BV")
                .pattern(" T ")
                .input('U', ModItemTags.BREAD)
                .input('B', ModItems.BUN)
                .input('_', ModItems.BAGUETTE)
                .input('V', ModItems.BRAIDED_BREAD)
                .input('T', ModBlocks.TRAY)
                .criterion(hasItem(ModBlocks.TRAY), conditionsFromItem(ModBlocks.TRAY))
                .criterion(hasItem(ModBlocks.BREAD_CRATE), conditionsFromItem(ModBlocks.BREAD_CRATE))
                .criterion(hasItem(ModItems.BUN), conditionsFromItem(ModItems.BUN))
                .criterion(hasItem(ModItems.BAGUETTE), conditionsFromItem(ModItems.BAGUETTE))
                .criterion(hasItem(ModItems.BRAIDED_BREAD), conditionsFromItem(ModItems.BRAIDED_BREAD))
                .offerTo(exporter, Utils.id(getRecipeName(ModBlocks.BREAD_CRATE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CUPCAKE_DISPLAY, 2)
                .pattern("NNN")
                .pattern("NWN")
                .pattern("NWN")
                .input('W', ItemTags.WOODEN_SLABS)
                .input('N', Items.IRON_NUGGET)
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .criterion("has_wooden_slab", conditionsFromTag(ItemTags.WOODEN_SLABS))
                .offerTo(exporter, Utils.id(getRecipeName(ModBlocks.CUPCAKE_DISPLAY)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.WALL_DISPLAY, 1)
                .pattern("SWS")
                .pattern("SWS")
                .pattern("SWS")
                .input('W', ItemTags.WOODEN_SLABS)
                .input('S', Items.STICK)
                .criterion("has_wooden_slab", conditionsFromTag(ItemTags.WOODEN_SLABS))
                .offerTo(exporter, Utils.id(getRecipeName(ModBlocks.WALL_DISPLAY)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CHOCOLATE_BOX, 1)
                .pattern("TTT")
                .pattern("TCT")
                .pattern("TTT")
                .input('T', ModItems.CHOCOLATE_TRUFFLE)
                .input('C', Items.CHEST)
                .criterion(hasItem(ModItems.CHOCOLATE_TRUFFLE), conditionsFromItem(ModItems.CHOCOLATE_TRUFFLE))
                .offerTo(exporter, Utils.id(getRecipeName(ModBlocks.CHOCOLATE_BOX)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.BREAD_WITH_JAM, 5)
                .input(ModItemTags.BREAD)
                .input(ModItemTags.JAM)
                .criterion("has_bread", conditionsFromTag(ModItemTags.BREAD))
                .criterion("has_jam", conditionsFromTag(ModItemTags.JAM))
                .offerTo(exporter, Utils.id(getRecipeName(ModItems.BREAD_WITH_JAM)));

        FarmerDelightRecipeBuilder.buildRecipe(FarmerDelightRecipeBuilder.createCookingPotRecipe(ModItems.APPLE_JAM)
                .addIngredient(Items.APPLE)
                .addIngredient(Items.SUGAR)
                .addContainer(ModItems.JAR));

        FarmerDelightRecipeBuilder.buildRecipe(FarmerDelightRecipeBuilder.createCookingPotRecipe(Items.BEETROOT_SOUP)
                .addIngredient(Items.BEETROOT)
                .addContainer(Items.BOWL));

        FarmerDelightRecipeBuilder.buildRecipe(FarmerDelightRecipeBuilder.createCookingPotRecipe(ModItems.CHOCOLATE_TRUFFLE, 4)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.COCOA_BEANS)
                .addIngredient(ConventionalItemTags.MILK_BUCKETS)
                .addContainer(Items.BOWL));

        FarmerDelightRecipeBuilder.buildRecipe(FarmerDelightRecipeBuilder.createCookingPotRecipe(ModItems.GLOWBERRY_JAM)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.GLOW_BERRIES)
                .addContainer(ModItems.JAR));

        FarmerDelightRecipeBuilder.buildRecipe(FarmerDelightRecipeBuilder.createCookingPotRecipe(ModItems.SWEETBERRY_JAM)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SWEET_BERRIES)
                .addContainer(ModItems.JAR));

        FarmerDelightRecipeBuilder.buildRecipe(FarmerDelightRecipeBuilder.createCookingPotRecipe(ModItems.CHOCOLATE_JAM)
                .addIngredient(ConventionalItemTags.MILK_BUCKETS)
                .addIngredient(ModItems.CHOCOLATE_TRUFFLE)
                .addIngredient(Items.SUGAR)
                .addContainer(ModItems.JAR));

        FarmerDelightRecipeBuilder.buildRecipe(FarmerDelightRecipeBuilder.createCookingPotRecipe(Items.RABBIT_STEW)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.BROWN_MUSHROOM)
                .addIngredient(Items.CARROT)
                .addIngredient(Items.RABBIT)
                .addContainer(Items.BOWL));

        FarmerDelightRecipeBuilder.buildRecipe(FarmerDelightRecipeBuilder.createCookingPotRecipe(ModBlocks.PUDDING.asItem())
                .addIngredient(ModItemTags.EGG)
                .addIngredient(ModItemTags.JAM)
                .addIngredient(ConventionalItemTags.MILK_BUCKETS)
                .addContainer(Items.BOWL));

        //endregion

    }

    private void addCookRecipe(Item input, Item output, float experience, RecipeExporter exporter){

        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(input),RecipeCategory.FOOD,output,experience, 300)
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter,Utils.id(getRecipeName(input)+"_smelting"));
        CookingRecipeJsonBuilder.createCampfireCooking(Ingredient.ofItems(input),RecipeCategory.FOOD,output,experience, 600)
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter,Utils.id(getRecipeName(input)+"_campfire"));
        CookingRecipeJsonBuilder.createSmoking(Ingredient.ofItems(input),RecipeCategory.FOOD,output,experience, 200)
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter,Utils.id(getRecipeName(input)+"_smoking"));
    }
}
