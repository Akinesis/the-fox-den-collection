package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.Utils;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {

    public static final TagKey<Item> CHEESE = TagKey.of(RegistryKeys.ITEM, Identifier.of("c","foods/cheese"));
    public static final TagKey<Item> IRON_UPGRADE_TEMPLATE = TagKey.of(RegistryKeys.ITEM, Utils.id("iron_upgrade_template"));
    public static final TagKey<Item> DIAMOND_UPGRADE_TEMPLATE = TagKey.of(RegistryKeys.ITEM, Utils.id("diamond_upgrade_template"));

    public static void registerModTags(){
        TheFoxDenCollection.LOGGER.info("Generating item tags for mod : "+TheFoxDenCollection.MOD_ID);
    }
}
