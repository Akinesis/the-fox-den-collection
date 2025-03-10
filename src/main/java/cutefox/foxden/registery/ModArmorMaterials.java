package cutefox.foxden.registery;


import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.Utils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {

    public static final RegistryEntry<ArmorMaterial> STEEL;
    public static final RegistryEntry<ArmorMaterial> BONE;
    public static final RegistryEntry<ArmorMaterial> SPACE_STEEL;
    public static final RegistryEntry<ArmorMaterial> IRON_WOLF;
    public static final RegistryEntry<ArmorMaterial> DIAMOND_WOLF;
    public static final RegistryEntry<ArmorMaterial> BIKE_GEAR;

    public static final HashMap<RegistryEntry<ArmorMaterial>, RegistryEntry<StatusEffect>> MATERIAL_TO_STATUS;

    public static void registerModMaterials(){
        TheFoxDenCollection.LOGGER.info("Registering mod armor materials for : "+ TheFoxDenCollection.MOD_ID);
    }

    static {
        STEEL = register("steel", Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
            map.put(ArmorItem.Type.BOOTS, 2);
            map.put(ArmorItem.Type.LEGGINGS, 5);
            map.put(ArmorItem.Type.CHESTPLATE, 7);
            map.put(ArmorItem.Type.HELMET, 2);
            map.put(ArmorItem.Type.BODY, 7);
        }), 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F, 0.05F, () -> Ingredient.ofItems(ModItems.STEEL_INGOT));

        BONE = register("bone", Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
            map.put(ArmorItem.Type.BOOTS, 1);
            map.put(ArmorItem.Type.LEGGINGS, 4);
            map.put(ArmorItem.Type.CHESTPLATE, 5);
            map.put(ArmorItem.Type.HELMET, 2);
            map.put(ArmorItem.Type.BODY, 11);
        }), 15, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, () -> Ingredient.ofItems(Items.BONE));

        SPACE_STEEL = register("space_steel", Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
            map.put(ArmorItem.Type.BOOTS, 2);
            map.put(ArmorItem.Type.LEGGINGS, 6);
            map.put(ArmorItem.Type.CHESTPLATE, 7);
            map.put(ArmorItem.Type.HELMET, 2);
            map.put(ArmorItem.Type.BODY, 10);
        }), 12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2.0F, 0.1F, () -> Ingredient.ofItems(ModItems.STEEL_INGOT));

        IRON_WOLF = register("iron_wolf", Util.make(new EnumMap(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, 1);
            map.put(ArmorItem.Type.LEGGINGS, 1);
            map.put(ArmorItem.Type.CHESTPLATE, 1);
            map.put(ArmorItem.Type.HELMET, 1);
            map.put(ArmorItem.Type.BODY, 14);
        }), 12, SoundEvents.ITEM_ARMOR_EQUIP_WOLF, 0.0F, 0.0F, () -> Ingredient.ofItems(Items.IRON_INGOT));

        DIAMOND_WOLF = register("diamond_wolf", Util.make(new EnumMap(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, 1);
            map.put(ArmorItem.Type.LEGGINGS, 1);
            map.put(ArmorItem.Type.CHESTPLATE, 1);
            map.put(ArmorItem.Type.HELMET, 1);
            map.put(ArmorItem.Type.BODY, 16);
        }), 16, SoundEvents.ITEM_ARMOR_EQUIP_WOLF, 1.0F, 0.0F, () -> Ingredient.ofItems(Items.DIAMOND));

        BIKE_GEAR = register("bike_gear", Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
            map.put(ArmorItem.Type.BOOTS, 2);
            map.put(ArmorItem.Type.LEGGINGS, 5);
            map.put(ArmorItem.Type.CHESTPLATE, 7);
            map.put(ArmorItem.Type.HELMET, 2);
            map.put(ArmorItem.Type.BODY, 7);
        }), 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0.05F, () -> Ingredient.ofItems(ModItems.STEEL_INGOT));


        MATERIAL_TO_STATUS = new HashMap<>();
        MATERIAL_TO_STATUS.put(BONE, ModStatusEffects.SKELETON_LOVE);
    }

    private static RegistryEntry<ArmorMaterial> register(String id, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(Utils.id(id)));
        EnumMap<ArmorItem.Type, Integer> defenseCopy = new EnumMap(ArmorItem.Type.class);

        for(ArmorItem.Type type : defense.keySet()){
            ArmorItem.Type t = type;
            defenseCopy.put(type, defense.get(type));
        }

        return Registry.registerReference(Registries.ARMOR_MATERIAL, Utils.id(id), new ArmorMaterial(defenseCopy, enchantability, equipSound, repairIngredient, layers, toughness, knockbackResistance));
    }

    public static RegistryEntry<ArmorMaterial> getDefault(Registry<ArmorMaterial> registry) {
        return ArmorMaterials.LEATHER;
    }
}
