package cutefox.foxden.item.model;

import cutefox.foxden.Utils;
import cutefox.foxden.item.SteelArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SteelArmorModel extends GeoModel<SteelArmorItem> {

    @Override
    public Identifier getModelResource(SteelArmorItem animatable) {
        return Utils.id("geo/knight_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(SteelArmorItem animatable) {
        return Utils.id("textures/models/armor/knight_texture.png");
    }

    @Override
    public Identifier getAnimationResource(SteelArmorItem animatable) {
        return Utils.id("animations/knight_armor.animation.json");
    }
}
