package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModItemTags {

    public static final TagKey<Item> ENCHANTEMNT_INGREDIENT = TagKey.of(RegistryKeys.ITEM,Utils.id("enchantment_ingredients"));

    public static void registerModTags(){
        TheFoxDenCollection.LOGGER.info("Generating item tags for mod : "+TheFoxDenCollection.MOD_ID);
    }
}
