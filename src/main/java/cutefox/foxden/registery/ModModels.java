package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.Utils;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;

import java.util.Optional;

public class ModModels {

    public static final Model LEAVES_WALL_INVENTORY = block("leaves_wall_inventory", "_inventory", TextureKey.WALL);
    public static final Model TEMPLATE_LEAVES_WALL_POST = block("template_leaves_wall_post", "_post", TextureKey.WALL);
    public static final Model TEMPLATE_LEAVES_WALL_SIDE = block("template_leaves_wall_side", "_side", TextureKey.WALL);
    public static final Model TEMPLATE_LEAVES_WALL_SIDE_TALL = block("template_leaves_wall_side_tall", "_side_tall", TextureKey.WALL);

    public static void loadModels(){
        TheFoxDenCollection.LOGGER.info("Creating Models.");
    }

    private static Model block(String parent, String variant,TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Utils.blockId(parent)), Optional.of(variant), requiredTextureKeys);
    }

}
