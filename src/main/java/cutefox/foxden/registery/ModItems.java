package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.ArmorAtributeBonuses;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.item.*;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //endregion

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


    }

    private static Item registerItem(String id, Item item){
        return Registry.register(Registries.ITEM, Utils.id(id), item);
    }

}