package cutefox.foxden.item.model;

import cutefox.foxden.Utils;
import cutefox.foxden.item.SpaceRangerArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpaceRangerArmorModel extends GeoModel<SpaceRangerArmorItem> {

    @Override
    public Identifier getModelResource(SpaceRangerArmorItem animatable) {
        return Utils.id("geo/space_ranger.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpaceRangerArmorItem animatable) {
        return Utils.id("textures/models/armor/space_ranger.png");
    }

    @Override
    public Identifier getAnimationResource(SpaceRangerArmorItem animatable) {
        return Utils.id("animations/space_ranger.animation.json");
    }
}
