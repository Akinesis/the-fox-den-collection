package cutefox.foxden.item.renderer;

import cutefox.foxden.item.BoneArmorItem;
import cutefox.foxden.item.model.BoneArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BoneArmorRenderer extends GeoArmorRenderer<BoneArmorItem> {

    public BoneArmorRenderer() {
        super(new BoneArmorModel());
    }
}