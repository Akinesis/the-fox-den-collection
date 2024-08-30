package cutefox.foxden.item.renderer;

import cutefox.foxden.item.SpaceRangerArmorItem;
import cutefox.foxden.item.model.SpaceRangerArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class SpaceRangerArmorRenderer extends GeoArmorRenderer<SpaceRangerArmorItem> {

    public SpaceRangerArmorRenderer() {
        super(new SpaceRangerArmorModel());
        addRenderLayer(new AutoGlowingGeoLayer(this));
    }
}