package cutefox.foxden.item;

import cutefox.foxden.Utils.Utils;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ModWolfArmor extends ArmorItem {

    private final Identifier entityTexture;
    @Nullable
    private final Identifier overlayTexture;
    private final ModWolfArmor.Type type;

    public ModWolfArmor(RegistryEntry<ArmorMaterial> material, ModWolfArmor.Type type, boolean hasOverlay, Item.Settings settings) {
        super(material, ArmorItem.Type.BODY, settings);
        this.type = type;
        Identifier identifier = type.textureId;
        this.entityTexture = identifier.withSuffixedPath(".png");
        if (hasOverlay) {
            this.overlayTexture = identifier.withSuffixedPath("_overlay.png");
        } else {
            this.overlayTexture = null;
        }
    }

    public Identifier getEntityTexture() {
        return this.entityTexture;
    }

    @Nullable
    public Identifier getOverlayTexture() {
        return this.overlayTexture;
    }

    @Override
    public SoundEvent getBreakSound() {
        return this.type.breakSound;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public enum Type {
        IRON(Utils.id("textures/entity/wolf/iron_wolf_armor"), SoundEvents.ITEM_WOLF_ARMOR_BREAK),
        DIAMOND(Utils.id("textures/entity/wolf/diamond_wolf_armor"), SoundEvents.ITEM_WOLF_ARMOR_BREAK);

        final Identifier textureId;
        final SoundEvent breakSound;

        Type(final Identifier textureId, final SoundEvent breakSound) {
            this.textureId = textureId;
            this.breakSound = breakSound;
        }
    }
}
