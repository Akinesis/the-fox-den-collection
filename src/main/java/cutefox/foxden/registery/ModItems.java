package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.ArmorAtributeBonuses;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.block.EffectBlockItem;
import cutefox.foxden.item.*;
import net.minecraft.block.Block;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

import java.util.*;

public class ModItems {

    private static Item.Settings foodItem(FoodComponent component){
        return new Item.Settings().food(component);
    }

    public static void registerModItems(){
        TheFoxDenCollection.LOGGER.info("Registering mod iems for : "+ TheFoxDenCollection.MOD_ID);
    }

    public static final Map<ArmorMaterial, Item> ARMOR_SET = new HashMap<>();

    //region FOOD ITEMS
    public static final Item CHEESE = registerItem("cheese", new Item(foodItem(ModFoodComponents.CHEESE)));
    public static final Item BLUE_CHEESE = registerItem("blue_cheese", new Item(foodItem(ModFoodComponents.BLUE_CHEESE)));
    public static Item CHEESE_BLOCK;
    public static final Item YEAST = registerItem("yeast", new Item(new Item.Settings()));
    public static final Item OIL_BUCKET = registerItem("oil_bucket", new Item(new Item.Settings()));
    public static final Item FRIES = registerItem("fries", new Item(foodItem(ModFoodComponents.FRIES)));
    public static final Item BROWN_SAUCE = registerItem("brown_sauce", new Item(new Item.Settings().maxCount(16).recipeRemainder(null)));
    public static final Item POUTINE = registerItem("poutine", new Item(foodItem(ModFoodComponents.POUTINE)));

    //endregion

    //region MATERIALS
    public static final Item STEEL_BLEND = registerItem("steel_blend", new Item(new Item.Settings()));
    public static final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new Item.Settings()));
    public static final Item ROTTEN_LEATHER = registerItem("rotten_leather", new Item(new Item.Settings()));
    public static Item STEEL_BLOCK;
    //endregion

    //region UPGRADE TEMPLATES
    public static final Item IRON_UPGRADE_SMITHING_TEMPLATE = registerItem("iron_upgrade_smithing_template", new Item(new Item.Settings()));
    public static final Item DIAMOND_UPGRADE_SMITHING_TEMPLATE = registerItem("diamond_upgrade_smithing_template", new Item(new Item.Settings()));
    //endregion

    //region STEEL ARMORS
    public static final Item STEEL_HELMET = registerItem("steel_helmet",
            new SteelArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(22))));
    public static final Item STEEL_CHESTPLATE = registerItem("steel_chestplate",
            new SteelArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(22))));
    public static final Item STEEL_LEGGINGS = registerItem("steel_leggings",
            new SteelArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(22))));
    public static final Item STEEL_BOOTS = registerItem("steel_boots",
            new SteelArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(22))));
    //endregion

    //region BONE ARMORS
    public static final Item BONE_HELMET = registerItem("bone_helmet",
            new BoneArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(13))));
    public static final Item BONE_CHESTPLATE = registerItem("bone_chestplate",
            new BoneArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(13))));
    public static final Item BONE_LEGGINGS = registerItem("bone_leggings",
            new BoneArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(13))));
    public static final Item BONE_BOOTS = registerItem("bone_boots",
            new BoneArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(13))));
    //endregion

    //region SPACE RANGER ARMOR

    public static final Item SPACE_RANGER_HELMET = registerItem("space_ranger_helmet",
            new SpaceRangerArmorItem(ModArmorMaterials.SPACE_STEEL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(35))
                    .attributeModifiers(ArmorAtributeBonuses.getSpaceRangerHelmetBonuses())));
    public static final Item SPACE_RANGER_CHESTPLATE = registerItem("space_ranger_chestplate",
            new SpaceRangerArmorItem(ModArmorMaterials.SPACE_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(35))
                    .attributeModifiers(ArmorAtributeBonuses.getSpaceRangerChestplateBonuses())));
    public static final Item SPACE_RANGER_LEGGINGS = registerItem("space_ranger_leggings",
            new SpaceRangerArmorItem(ModArmorMaterials.SPACE_STEEL, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS
                    .getMaxDamage(35))
                    .attributeModifiers(ArmorAtributeBonuses.getSpaceRangerLeggingsBonuses())));
    public static final Item SPACE_RANGER_BOOTS = registerItem("space_ranger_boots",
            new SpaceRangerArmorItem(ModArmorMaterials.SPACE_STEEL, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS
                    .getMaxDamage(35))
                    .attributeModifiers(ArmorAtributeBonuses.getSpaceRangerBootBonuses())));
    //endregion

    //region WOLF ARMORS
    public static final Item IRON_WOLF_ARMOR = registerItem("iron_wolf_armor",
            new ModWolfArmor(ModArmorMaterials.IRON_WOLF, ModWolfArmor.Type.IRON, false,
                    new Item.Settings().maxDamage(ArmorItem.Type.BODY.getMaxDamage(6))));

    public static final Item DIAMOND_WOLF_ARMOR = registerItem("diamond_wolf_armor",
            new ModWolfArmor(ModArmorMaterials.DIAMOND_WOLF, ModWolfArmor.Type.DIAMOND, false,
                    new Item.Settings().maxDamage(ArmorItem.Type.BODY.getMaxDamage(8))));
    //endregion

    //region BIKE ARMOR
    public static final Item BIKE_HELMET = registerItem("bike_helmet",
            new BikeArmorItem(ModArmorMaterials.BIKE_GEAR, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET
                    .getMaxDamage(18))
                    .attributeModifiers(ArmorAtributeBonuses.getBikeHelmetBonuses())));
    //endregion

    //region LEAVES WALL
    public static Item OAK_LEAVES_WALL;
    public static Item SPRUCE_LEAVES_WALL;
    public static Item CHERRY_LEAVES_WALL;
    public static Item BIRCH_LEAVES_WALL;
    public static Item JUNGLE_LEAVES_WALL;
    public static Item MANGROVE_LEAVES_WALL;
    public static Item ACACIA_LEAVES_WALL;
    public static Item DARK_OAK_LEAVES_WALL;
    public static Item AZALEA_LEAVES_WALL;
    public static Item FLOWERING_AZALEA_LEAVES_WALL;

    public static List<Item> LEAVES_WALLS = new ArrayList<>();
    //endregion

    //region BAKERY
    public static Map<String, Item> BAKERY_BLOCK_ITEMS;
    public static List<Item> BAKERY_ITEMS;
    public static Item BAKING_STATION;
    public static final Item CAKE_DOUGH;
    public static final Item SWEET_DOUGH;
    public static final Item DOUGHT;
    public static final Item ROLLING_PIN;
    public static final Item BREAD_KNIFE;
    public static final Item CROISSANT;
    public static final Item CROISSANT_RAW;
    public static final Item CORNET;
    public static final Item RAW_CORNET;
    public static final Item JAM_ROLL;
    public static final Item CHOCOLATE_TRUFFLE;
    public static Item JAR;
    public static Item STRAWBERRY_JAM;
    public static Item GLOWBERRY_JAM;
    public static Item SWEETBERRY_JAM;
    public static Item CHOCOLATE_JAM;
    public static Item APPLE_JAM;
    public static final Item SANDWICH;
    public static final Item VEGETABLE_SANDWICH;
    public static final Item GRILLED_SALMON_SANDWICH;
    public static final Item GRILLED_BACON_SANDWICH;
    public static final Item BREAD_WITH_JAM;
    public static final Item STRAWBERRY_CAKE_SLICE;
    public static final Item SWEETBERRY_CAKE_SLICE;
    public static final Item CHOCOLATE_CAKE_SLICE;
    public static final Item CHOCOLATE_GATEAU_SLICE;
    public static final Item BUNDT_CAKE_SLICE;
    public static final Item LINZER_TART_SLICE;
    public static final Item APPLE_PIE_SLICE;
    public static final Item GLOWBERRY_PIE_SLICE;
    public static final Item CHOCOLATE_TART_SLICE;
    public static final Item PUDDING_SLICE;
    public static final Item STRAWBERRY_CUPCAKE;
    public static final Item SWEETBERRY_CUPCAKE;
    public static final Item APPLE_CUPCAKE;
    public static final Item STRAWBERRY_GLAZED_COOKIE;
    public static final Item SWEETBERRY_GLAZED_COOKIE;
    public static final Item CHOCOLATE_GLAZED_COOKIE;
    public static Item CRUSTY_BREAD;
    public static Item RAW_CRUSTY_BREAD;
    public static Item BREAD;
    public static Item RAW_BREAD;
    public static Item BAGUETTE;
    public static Item RAW_BAGUETTE;
    public static Item TOAST;
    public static Item RAW_TOAST;
    public static Item BRAIDED_BREAD;
    public static Item RAW_BRAIDED_BREAD;
    public static Item BUN;
    public static Item RAW_BUN;
    public static Item WAFFLE;
    public static Item RAW_WAFFLE;
    public static Item BUNDT_CAKE;
    public static Item RAW_BUNDT_CAKE;
    public static Item LINZER_TART;
    public static Item RAW_LINZER_TART;
    public static Item APPLE_PIE;
    public static Item RAW_APPLE_PIE;
    //endregion

    //region SHIPS
    public static Item SHIP_MAST;
    //endregion

    static {
        BAKERY_BLOCK_ITEMS = new HashMap();
        BAKERY_ITEMS = new ArrayList();
        CAKE_DOUGH = registerBackeryItem("cake_dough", new Item((new Item.Settings()).food(FoodComponents.SWEET_BERRIES)));
        SWEET_DOUGH = registerBackeryItem("sweet_dough", new Item((new Item.Settings()).food(FoodComponents.SWEET_BERRIES)));
        DOUGHT = registerBackeryItem("dough", new Item((new Item.Settings()).food(FoodComponents.SWEET_BERRIES)));
        ROLLING_PIN = registerBackeryItem("rolling_pin", new RollingPinItem(ToolMaterials.WOOD, (new Item.Settings()).rarity(Rarity.COMMON).recipeRemainder(Items.OAK_PLANKS)));
        BREAD_KNIFE = registerBackeryItem("bread_knife", new SwordItem(ToolMaterials.IRON, (new Item.Settings()).rarity(Rarity.COMMON)));
        CROISSANT = registerBackeryItem("croissant", new Item(foodItem(ModFoodComponents.PASTRIES)));
        CROISSANT_RAW = registerBackeryItem("croissant_raw", new Item(foodItem(FoodComponents.SWEET_BERRIES)));
        CORNET = registerBackeryItem("cornet", new Item(foodItem(ModFoodComponents.PASTRIES)));
        RAW_CORNET = registerBackeryItem("cornet_raw", new Item(foodItem(FoodComponents.SWEET_BERRIES)));
        JAM_ROLL = registerBackeryItem("jam_roll", new Item(foodItem(ModFoodComponents.PASTRIES)));
        CHOCOLATE_TRUFFLE = registerBackeryItem("chocolate_truffle", new Item(foodItem(ModFoodComponents.PASTRIES)));
        SANDWICH = registerBackeryItem("sandwich", new Item(foodItem(ModFoodComponents.SANDWICH)));
        VEGETABLE_SANDWICH = registerBackeryItem("vegetable_sandwich", new Item(foodItem(ModFoodComponents.SANDWICH)));
        GRILLED_SALMON_SANDWICH = registerBackeryItem("grilled_salmon_sandwich", new Item(foodItem(ModFoodComponents.SANDWICH)));
        GRILLED_BACON_SANDWICH = registerBackeryItem("grilled_bacon_sandwich", new Item(foodItem(ModFoodComponents.SANDWICH)));
        BREAD_WITH_JAM = registerBackeryItem("bread_with_jam", new Item(foodItem(ModFoodComponents.BREAD_JAMED)));
        STRAWBERRY_CAKE_SLICE = registerBackeryItem("strawberry_cake_slice", new Item(foodItem(ModFoodComponents.CAKE_SLICE)));
        SWEETBERRY_CAKE_SLICE = registerBackeryItem("sweetberry_cake_slice", new Item(foodItem(ModFoodComponents.CAKE_SLICE)));
        CHOCOLATE_CAKE_SLICE = registerBackeryItem("chocolate_cake_slice", new Item(foodItem(ModFoodComponents.CAKE_SLICE)));
        CHOCOLATE_GATEAU_SLICE = registerBackeryItem("chocolate_gateau_slice", new Item(foodItem(ModFoodComponents.CAKE_SLICE)));
        BUNDT_CAKE_SLICE = registerBackeryItem("bundt_cake_slice", new Item(foodItem(ModFoodComponents.CAKE_SLICE)));
        LINZER_TART_SLICE = registerBackeryItem("linzer_tart_slice", new Item(foodItem(ModFoodComponents.CAKE_SLICE)));
        APPLE_PIE_SLICE = registerBackeryItem("apple_pie_slice", new Item(foodItem(ModFoodComponents.CAKE_SLICE)));
        GLOWBERRY_PIE_SLICE = registerBackeryItem("glowberry_pie_slice", new Item(foodItem(ModFoodComponents.CAKE_SLICE)));
        CHOCOLATE_TART_SLICE = registerBackeryItem("chocolate_tart_slice", new Item(foodItem(ModFoodComponents.CAKE_SLICE)));
        PUDDING_SLICE = registerBackeryItem("pudding_slice", new Item(foodItem(ModFoodComponents.CAKE_SLICE)));
        STRAWBERRY_CUPCAKE = registerBackeryItem("strawberry_cupcake", new Item(foodItem(ModFoodComponents.COOKIES_N_CUPCAKE)));
        SWEETBERRY_CUPCAKE = registerBackeryItem("sweetberry_cupcake", new Item(foodItem(ModFoodComponents.COOKIES_N_CUPCAKE)));
        APPLE_CUPCAKE = registerBackeryItem("apple_cupcake", new Item(foodItem(ModFoodComponents.COOKIES_N_CUPCAKE)));
        STRAWBERRY_GLAZED_COOKIE = registerBackeryItem("strawberry_glazed_cookie", new Item(foodItem(ModFoodComponents.COOKIES_N_CUPCAKE)));
        SWEETBERRY_GLAZED_COOKIE = registerBackeryItem("sweetberry_glazed_cookie", new Item(foodItem(ModFoodComponents.COOKIES_N_CUPCAKE)));
        CHOCOLATE_GLAZED_COOKIE = registerBackeryItem("chocolate_glazed_cookie", new Item(foodItem(ModFoodComponents.COOKIES_N_CUPCAKE)));
        RAW_CRUSTY_BREAD = registerBackeryItem("crusty_bread_raw", new Item(foodItem(FoodComponents.SWEET_BERRIES)));
        RAW_BREAD = registerBackeryItem("bread_raw", new Item(foodItem(FoodComponents.SWEET_BERRIES)));
        RAW_BAGUETTE = registerBackeryItem("baguette_raw", new Item(foodItem(FoodComponents.SWEET_BERRIES)));
        RAW_TOAST = registerBackeryItem("toast_raw", new Item(foodItem(FoodComponents.SWEET_BERRIES)));
        RAW_BRAIDED_BREAD = registerBackeryItem("braided_bread_raw", new Item(foodItem(FoodComponents.SWEET_BERRIES)));
        RAW_BUN = registerBackeryItem("bun_raw", new Item(foodItem(FoodComponents.SWEET_BERRIES)));
        RAW_WAFFLE = registerBackeryItem("waffle_raw", new Item(foodItem(FoodComponents.SWEET_BERRIES)));
        RAW_BUNDT_CAKE = registerBackeryItem("bundt_cake_raw", new Item(foodItem(FoodComponents.SWEET_BERRIES)));
        RAW_LINZER_TART = registerBackeryItem("linzer_tart_raw", new Item(foodItem(FoodComponents.SWEET_BERRIES)));
        RAW_APPLE_PIE = registerBackeryItem("apple_pie_raw", new Item(foodItem(FoodComponents.SWEET_BERRIES)));
    }

    public static void registerBlockItems(){
        CHEESE_BLOCK = registerItem("cheese_block", new BlockItem(ModBlocks.CHEESE_BLOCK, new Item.Settings()));
        STEEL_BLOCK = registerItem("steel_block", new BlockItem(ModBlocks.STEEL_BLOCK, new Item.Settings()));

        //Leaves wall
        OAK_LEAVES_WALL = registerItem("oak_leaves_wall", new BlockItem(ModBlocks.OAK_LEAVES_WALL, new Item.Settings()));
        LEAVES_WALLS.add(OAK_LEAVES_WALL);

        SPRUCE_LEAVES_WALL = registerItem("spruce_leaves_wall", new BlockItem(ModBlocks.SPRUCE_LEAVES_WALL, new Item.Settings()));
        LEAVES_WALLS.add(SPRUCE_LEAVES_WALL);

        BIRCH_LEAVES_WALL = registerItem("birch_leaves_wall", new BlockItem(ModBlocks.BIRCH_LEAVES_WALL, new Item.Settings()));
        LEAVES_WALLS.add(BIRCH_LEAVES_WALL);

        JUNGLE_LEAVES_WALL = registerItem("jungle_leaves_wall", new BlockItem(ModBlocks.JUNGLE_LEAVES_WALL, new Item.Settings()));
        LEAVES_WALLS.add(JUNGLE_LEAVES_WALL);

        MANGROVE_LEAVES_WALL = registerItem("mangrove_leaves_wall", new BlockItem(ModBlocks.MANGROVE_LEAVES_WALL, new Item.Settings()));
        LEAVES_WALLS.add(MANGROVE_LEAVES_WALL);

        ACACIA_LEAVES_WALL = registerItem("acacia_leaves_wall", new BlockItem(ModBlocks.ACACIA_LEAVES_WALL, new Item.Settings()));
        LEAVES_WALLS.add(ACACIA_LEAVES_WALL);

        DARK_OAK_LEAVES_WALL = registerItem("dark_oak_leaves_wall", new BlockItem(ModBlocks.DARK_OAK_LEAVES_WALL, new Item.Settings()));
        LEAVES_WALLS.add(DARK_OAK_LEAVES_WALL);

        CHERRY_LEAVES_WALL = registerItem("cherry_leaves_wall", new BlockItem(ModBlocks.CHERRY_LEAVES_WALL, new Item.Settings()));
        LEAVES_WALLS.add(CHERRY_LEAVES_WALL);

        AZALEA_LEAVES_WALL = registerItem("azalea_leaves_wall", new BlockItem(ModBlocks.AZALEA_LEAVES_WALL, new Item.Settings()));
        LEAVES_WALLS.add(AZALEA_LEAVES_WALL);

        FLOWERING_AZALEA_LEAVES_WALL = registerItem("flowering_azalea_leaves_wall", new BlockItem(ModBlocks.FLOWERING_AZALEA_LEAVES_WALL, new Item.Settings()));
        LEAVES_WALLS.add(FLOWERING_AZALEA_LEAVES_WALL);

        Iterator var0 = BAKERY_BLOCK_ITEMS.entrySet().iterator();

        while(var0.hasNext()) {
            Map.Entry<String, Item> block = (Map.Entry)var0.next();
            registerItem((String)block.getKey(), (Item)block.getValue());
        }

        JAR = registerItem("jar", new BlockItem(ModBlocks.JAR, new Item.Settings()));
        STRAWBERRY_JAM = registerItem("strawberry_jam", new BlockItem(ModBlocks.STRAWBERRY_JAM, new Item.Settings()));
        GLOWBERRY_JAM = registerItem("glowberry_jam", new BlockItem(ModBlocks.GLOWBERRY_JAM, new Item.Settings()));
        SWEETBERRY_JAM = registerItem("sweetberry_jam", new BlockItem(ModBlocks.SWEETBERRY_JAM, new Item.Settings()));
        CHOCOLATE_JAM = registerItem("chocolate_jam", new BlockItem(ModBlocks.CHOCOLATE_JAM, new Item.Settings()));
        APPLE_JAM = registerItem("apple_jam", new BlockItem(ModBlocks.APPLE_JAM, new Item.Settings()));
        CRUSTY_BREAD = registerItem("crusty_bread", new EffectBlockItem(ModBlocks.CRUSTY_BREAD_BLOCK, foodItem(ModFoodComponents.BREAD_LIKE)));
        BREAD = registerItem("bread", new EffectBlockItem(ModBlocks.BREAD_BLOCK, foodItem(ModFoodComponents.BREAD_LIKE)));
        BAGUETTE = registerItem("baguette", new EffectBlockItem(ModBlocks.BAGUETTE_BLOCK, foodItem(ModFoodComponents.BREAD_LIKE)));
        TOAST = registerItem("toast", new EffectBlockItem(ModBlocks.TOAST_BLOCK, foodItem(ModFoodComponents.BREAD_LIKE)));
        BRAIDED_BREAD = registerItem("braided_bread", new EffectBlockItem(ModBlocks.BRAIDED_BREAD_BLOCK, foodItem(ModFoodComponents.BREAD_LIKE)));
        BUN = registerItem("bun", new EffectBlockItem(ModBlocks.BUN_BLOCK, foodItem(ModFoodComponents.BREAD_LIKE)));
        WAFFLE = registerItem("waffle", new EffectBlockItem(ModBlocks.WAFFLE_BLOCK, foodItem(ModFoodComponents.BREAD_LIKE)));
        LINZER_TART = registerItem("linzer_tart", new EffectBlockItem(ModBlocks.LINZER_TART, foodItem(ModFoodComponents.CAKE_SLICE)));
        APPLE_PIE = registerItem("apple_pie", new EffectBlockItem(ModBlocks.APPLE_PIE, foodItem(ModFoodComponents.CAKE_SLICE)));
        BUNDT_CAKE = registerItem("bundt_cake", new EffectBlockItem(ModBlocks.BUNDT_CAKE, foodItem(ModFoodComponents.CAKE_SLICE)));

        SHIP_MAST = registerItem("ship_mast", new BlockItem(ModBlocks.SHIP_MAST, new Item.Settings()));
    }

    public static void addBackeryBlockItem(String id, Block block) {
        BAKERY_BLOCK_ITEMS.put(id, new BlockItem(block, new Item.Settings()));
    }

    private static Item registerBackeryItem(String id, Item item) {
        Item newItem = (Item)Registry.register(Registries.ITEM, Utils.id(id), item);
        BAKERY_ITEMS.add(newItem);
        return newItem;
    }

    private static Item registerItem(String id, Item item){
        return Registry.register(Registries.ITEM, Utils.id(id), item);
    }

}