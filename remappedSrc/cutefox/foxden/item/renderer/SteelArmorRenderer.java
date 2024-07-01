package cutefox.foxden.item.renderer;


import cutefox.foxden.item.SteelArmorItem;
import cutefox.foxden.item.model.SteelArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SteelArmorRenderer extends GeoArmorRenderer<SteelArmorItem> {

    public SteelArmorRenderer() {
        super(new SteelArmorModel());
    }
}
