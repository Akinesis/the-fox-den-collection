package cutefox.foxden.item.renderer;


import cutefox.foxden.item.BikeArmorItem;
import cutefox.foxden.item.model.BikeArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BikeArmorRenderer extends GeoArmorRenderer<BikeArmorItem> {

    public BikeArmorRenderer() {
        super(new BikeArmorModel());
    }
}
