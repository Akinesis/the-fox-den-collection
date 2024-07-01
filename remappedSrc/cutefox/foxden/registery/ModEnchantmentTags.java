package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModEnchantmentTags {
    public static final TagKey<Enchantment> BENEFICIAL_TREASURE = TagKey.of(RegistryKeys.ENCHANTMENT, Utils.id("beneficial_treasure"));

    public static void registerModTags(){
        TheFoxDenCollection.LOGGER.info("Registering mod enchantment tags for : "+ TheFoxDenCollection.MOD_ID);
    }
}
