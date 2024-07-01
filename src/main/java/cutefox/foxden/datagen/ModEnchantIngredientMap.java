package cutefox.foxden.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.registery.ModItems;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModEnchantIngredientMap {

    public static HashMap<String, List<String>> defaultMap = new HashMap<>();
    public static HashMap<RegistryKey<Enchantment>, List<Item>> map = new HashMap<>();
    public static HashMap<String, List<String>> stringMap = new HashMap<>();


    static{
        //Armor enchantment
        defaultMap.put(Enchantments.PROTECTION.getValue().toString(), listOfIdentifiers(List.of(Items.COPPER_INGOT,Items.IRON_INGOT, ModItems.STEEL_INGOT,Items.DIAMOND)));
        defaultMap.put(Enchantments.FIRE_PROTECTION.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.FEATHER_FALLING.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.BLAST_PROTECTION.getValue().toString(), listOfIdentifiers(List.of(Items.GUNPOWDER, Items.GUNPOWDER, Items.TNT, Items.CREEPER_HEAD)));
        defaultMap.put(Enchantments.PROJECTILE_PROTECTION.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.RESPIRATION.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.AQUA_AFFINITY.getValue().toString(), listOfIdentifiers(List.of(Items.TURTLE_SCUTE)));
        defaultMap.put(Enchantments.THORNS.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.DEPTH_STRIDER.getValue().toString(), listOfIdentifiers(List.of(Items.COD,Items.DEAD_HORN_CORAL, Items.SPONGE)));
        defaultMap.put(Enchantments.FROST_WALKER.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT))); //Treasure
        defaultMap.put(Enchantments.SOUL_SPEED.getValue().toString(), listOfIdentifiers(List.of(Items.SOUL_SAND,Items.SOUL_SOIL,Items.GHAST_TEAR))); //Treasure
        defaultMap.put(Enchantments.SWIFT_SNEAK.getValue().toString(), listOfIdentifiers(List.of(Items.WHITE_WOOL,Items.WIND_CHARGE,Items.RABBIT_FOOT))); //Treasure

        //Sword enchantment
        defaultMap.put(Enchantments.SHARPNESS.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.SMITE.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.BANE_OF_ARTHROPODS.getValue().toString(), listOfIdentifiers(List.of(Items.SPIDER_EYE,Items.SPIDER_EYE,Items.COBWEB, Items.FERMENTED_SPIDER_EYE,Items.FERMENTED_SPIDER_EYE )));
        defaultMap.put(Enchantments.KNOCKBACK.getValue().toString(), listOfIdentifiers(List.of(Items.PISTON, Items.STICKY_PISTON)));
        defaultMap.put(Enchantments.FIRE_ASPECT.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.LOOTING.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.SWEEPING_EDGE.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT)));

        //Bow enchantment
        defaultMap.put(Enchantments.POWER.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.PUNCH.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.FLAME.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT)));
        defaultMap.put(Enchantments.INFINITY.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT)));

        //Tool enchantement
        defaultMap.put(Enchantments.EFFICIENCY.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.SILK_TOUCH.getValue().toString(), listOfIdentifiers(List.of(Items.PAPER)));
        defaultMap.put(Enchantments.FORTUNE.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT)));

        //Fishing tod enchantment
        defaultMap.put(Enchantments.LUCK_OF_THE_SEA.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.LURE.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT)));

        //Anything enchantment
        defaultMap.put(Enchantments.UNBREAKING.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.MENDING.getValue().toString(), listOfIdentifiers(List.of(Items.EXPERIENCE_BOTTLE))); //Treasure

        //Trident enchantment
        defaultMap.put(Enchantments.CHANNELING.getValue().toString(), listOfIdentifiers(List.of(Items.WATER_BUCKET)));
        defaultMap.put(Enchantments.IMPALING.getValue().toString(), listOfIdentifiers(List.of(Items.IRON_INGOT,Items.POINTED_DRIPSTONE,Items.IRON_BARS,Items.ARROW,Items.IRON_SWORD)));
        defaultMap.put(Enchantments.LOYALTY.getValue().toString(), listOfIdentifiers(List.of(Items.NAUTILUS_SHELL,Items.NAUTILUS_SHELL,Items.NAUTILUS_SHELL)));
        defaultMap.put(Enchantments.RIPTIDE.getValue().toString(), listOfIdentifiers(List.of(Items.NAUTILUS_SHELL,Items.NAUTILUS_SHELL,Items.NAUTILUS_SHELL)));

        //Crossbow enchantment
        defaultMap.put(Enchantments.MULTISHOT.getValue().toString(), listOfIdentifiers(List.of(Items.FIREWORK_ROCKET)));
        defaultMap.put(Enchantments.PIERCING.getValue().toString(), listOfIdentifiers(List.of(Items.NAUTILUS_SHELL,Items.DIRT,Items.DIRT,Items.DIRT)));
        defaultMap.put(Enchantments.QUICK_CHARGE.getValue().toString(), listOfIdentifiers(List.of(Items.DIRT,Items.DIRT,Items.DIRT)));

        stringMap.put("minecraft:silk_touch".toString(), List.of("minecraft:diamond","minecraft:acacia_planks"));
        stringMap.put("minecraft:quick_charge".toString(), List.of("minecraft:dirt","minecraft:nautilus_shell"));

    }

    public static void createMap() {
        TheFoxDenCollection.LOGGER.info("Creating enchantement ingredient map for mod : "+TheFoxDenCollection.MOD_ID);

    }

    public static void genMapFromJson(World world){
        Gson gson = new GsonBuilder().create();

        String tempJson = "{\"minecraft:silk_touch\" : [\"minecraft:acacia_planks\",\"minecraft:acacia_planks\",\"minecraft:acacia_planks\"]}";

        try {

            File configFolder = new File(FabricLoader.getInstance().getConfigDir().resolve("TheFoxDenCollection").toString());
            configFolder.mkdirs();

            File configFile = new File(configFolder.getAbsolutePath()+"/enchantment_ingredients.json");
            if(configFile.createNewFile()){
                //init default file
                BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
                String temp = gson.toJson(defaultMap);
                writer.write(temp);
                writer.close();
            }

            //String path = FabricLoader.getInstance().getGameDir().resolve("custom/enchantment_ingredients.json").toString();
            JsonReader reader = new JsonReader(new FileReader(configFile));
            Map<String, List<String>> jsonMap = new HashMap<>();
            jsonMap = gson.fromJson(reader, Map.class);

            RegistryKey<Enchantment> enchantementKey;
            Enchantment enchantment;

            for (String key : jsonMap.keySet()) {
                Identifier enchantId = Identifier.of(key);
                enchantment = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).get(enchantId);
                enchantementKey = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getKey(enchantment).get();

                if (enchantementKey != null) {

                    List<Item> ingredients = new ArrayList<>();

                    jsonMap.get(key).forEach(s -> {
                        Identifier itemId = Identifier.of(s);
                        ingredients.add(Registries.ITEM.get(itemId));
                    });

                    map.put(enchantementKey, ingredients);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e.toString());
        }
    }

    private static List<String> listOfIdentifiers(List<Item> items){

        List<String> list = new ArrayList<>();

        for(Item item : items){
            list.add(Registries.ITEM.getId(item).toString());
        }

        return list;
    }

    private String getEnchantIdentifier(RegistryKey<Enchantment> enchant){
        enchant.getValue().toString();
        return "";
    }
}
