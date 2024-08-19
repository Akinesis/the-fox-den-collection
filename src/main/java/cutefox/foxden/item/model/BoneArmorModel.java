package cutefox.foxden.item.model;

import cutefox.foxden.Utils.Utils;
import cutefox.foxden.item.BoneArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BoneArmorModel extends GeoModel<BoneArmorItem> {

    @Override
    public Identifier getModelResource(BoneArmorItem animatable) {
        return Utils.id("geo/bone_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(BoneArmorItem animatable) {
        return Utils.id("textures/models/armor/bone_armor.png");
    }

    @Override
    public Identifier getAnimationResource(BoneArmorItem animatable) {
        return Utils.id("animations/bone_armor.animation.json");
    }
}