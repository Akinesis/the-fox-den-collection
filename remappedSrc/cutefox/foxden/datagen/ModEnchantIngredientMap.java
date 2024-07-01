package cutefox.foxden.datagen;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.registery.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ModEnchantIngredientMap {

    public static HashMap<RegistryKey<Enchantment>, List<Item>> map = new HashMap<>();

    static{
        //Armor enchantment
        map.put(Enchantments.PROTECTION, List.of(Items.COBBLESTONE,Items.IRON_INGOT, ModItems.STEEL_INGOT, Items.DIAMOND));
        map.put(Enchantments.FIRE_PROTECTION, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.FEATHER_FALLING, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.BLAST_PROTECTION, List.of(Items.GUNPOWDER, Items.GUNPOWDER, Items.TNT, Items.CREEPER_HEAD));
        map.put(Enchantments.PROJECTILE_PROTECTION, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.RESPIRATION, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.AQUA_AFFINITY, Arrays.asList(Items.TURTLE_SCUTE));
        map.put(Enchantments.THORNS, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.DEPTH_STRIDER, List.of(Items.COD,Items.DEAD_HORN_CORAL, Items.SPONGE));
        map.put(Enchantments.FROST_WALKER, Arrays.asList(Items.DIRT,Items.DIRT)); //Treasure
        map.put(Enchantments.SOUL_SPEED, Arrays.asList(Items.SOUL_SAND,Items.SOUL_SOIL,Items.GHAST_TEAR)); //Treasure
        map.put(Enchantments.SWIFT_SNEAK, Arrays.asList(Items.WHITE_WOOL,Items.WIND_CHARGE,Items.RABBIT_FOOT)); //Treasure

        //Sword enchantment
        map.put(Enchantments.SHARPNESS, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.SMITE, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.BANE_OF_ARTHROPODS, List.of(Items.SPIDER_EYE,Items.SPIDER_EYE,Items.COBWEB, Items.FERMENTED_SPIDER_EYE,Items.FERMENTED_SPIDER_EYE ));
        map.put(Enchantments.KNOCKBACK, Arrays.asList(Items.PISTON, Items.STICKY_PISTON));
        map.put(Enchantments.FIRE_ASPECT, Arrays.asList(Items.DIRT,Items.DIRT));
        map.put(Enchantments.LOOTING, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.SWEEPING_EDGE, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT));

        //Bow enchantment
        map.put(Enchantments.POWER, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.PUNCH, Arrays.asList(Items.DIRT,Items.DIRT));
        map.put(Enchantments.FLAME, Arrays.asList(Items.DIRT));
        map.put(Enchantments.INFINITY, Arrays.asList(Items.DIRT));

        //Tool enchantement
        map.put(Enchantments.EFFICIENCY, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.SILK_TOUCH, Arrays.asList(Items.PAPER));
        map.put(Enchantments.FORTUNE, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT));

        //Fishing tod enchantment
        map.put(Enchantments.LUCK_OF_THE_SEA, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.LURE, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT));

        //Anything enchantment
        map.put(Enchantments.UNBREAKING, Arrays.asList(Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.MENDING, Arrays.asList(Items.EXPERIENCE_BOTTLE)); //Treasure

        //Trident enchantment
        map.put(Enchantments.CHANNELING, List.of(Items.WATER_BUCKET));
        map.put(Enchantments.IMPALING, List.of(Items.IRON_INGOT,Items.POINTED_DRIPSTONE,Items.IRON_BARS,Items.ARROW,Items.IRON_SWORD));
        map.put(Enchantments.LOYALTY, List.of(Items.NAUTILUS_SHELL,Items.NAUTILUS_SHELL,Items.NAUTILUS_SHELL));
        map.put(Enchantments.RIPTIDE, List.of(Items.NAUTILUS_SHELL,Items.NAUTILUS_SHELL,Items.NAUTILUS_SHELL));

        //Crossbow enchantment
        map.put(Enchantments.MULTISHOT, List.of(Items.FIREWORK_ROCKET));
        map.put(Enchantments.PIERCING, List.of(Items.NAUTILUS_SHELL,Items.DIRT,Items.DIRT,Items.DIRT));
        map.put(Enchantments.QUICK_CHARGE, List.of(Items.DIRT,Items.DIRT,Items.DIRT));


    }

    public static void createMap() {
        TheFoxDenCollection.LOGGER.info("Creating enchantement ingredient map for mod : "+TheFoxDenCollection.MOD_ID);
    }

    public void ModEnchantIngredientMap(){

    }
}
