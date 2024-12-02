package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {

    public static final TagKey<Item> CHEESE = TagKey.of(RegistryKeys.ITEM, Identifier.of("c","foods/cheese"));

    public static void registerModTags(){
        TheFoxDenCollection.LOGGER.info("Generating item tags for mod : "+TheFoxDenCollection.MOD_ID);
    }
}
