package cutefox.foxden.item.renderer;

import cutefox.foxden.item.SpaceRangerArmorItem;
import cutefox.foxden.item.model.SpaceRangerArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SpaceRangerArmorRenderer extends GeoArmorRenderer<SpaceRangerArmorItem> {

    public SpaceRangerArmorRenderer() {
        super(new SpaceRangerArmorModel());
    }
}