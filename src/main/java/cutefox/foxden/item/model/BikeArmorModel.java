package cutefox.foxden.item.model;

import cutefox.foxden.Utils.Utils;
import cutefox.foxden.item.BikeArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BikeArmorModel extends GeoModel<BikeArmorItem> {

    @Override
    public Identifier getModelResource(BikeArmorItem animatable) {
        return Utils.id("geo/bike_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(BikeArmorItem animatable) {
        return Utils.id("textures/models/armor/bike_armor.png");
    }

    @Override
    public Identifier getAnimationResource(BikeArmorItem animatable) {
        return Utils.id("animations/bike_armor.animation.json");
    }
}
