package cutefox.foxden.item;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;

public class ModArmorItem extends ArmorItem {

    public ModArmorItem(RegistryEntry<ArmorMaterial> material, Type type, net.minecraft.item.Item.Settings settings) {
        super(material, type, settings);
    }


}
