package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils;
import cutefox.foxden.screen.CustomEnchantmentScreenHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlerType <T extends ScreenHandler> implements ToggleableFeature {

    public static final ScreenHandlerType<CustomEnchantmentScreenHandler> CUSTOM_ENCHANTMENT_SCREEN_HANDLER;

    private final FeatureSet requiredFeatures;
    private final ScreenHandlerType.Factory<T> factory;
    static {
        CUSTOM_ENCHANTMENT_SCREEN_HANDLER = register("custom_enchantment", CustomEnchantmentScreenHandler::new);
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return (ScreenHandlerType)Registry.register(Registries.SCREEN_HANDLER, Utils.id(id), new ScreenHandlerType(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void registerModScreenHandlers(){
        TheFoxDenCollection.LOGGER.info("Registering mod screen handlers for : "+ TheFoxDenCollection.MOD_ID);
    }

    public ModScreenHandlerType(ScreenHandlerType.Factory<T> factory, FeatureSet requiredFeatures) {
        this.factory = factory;
        this.requiredFeatures = requiredFeatures;
    }

    public T create(int syncId, PlayerInventory playerInventory) {
        return this.factory.create(syncId, playerInventory);
    }

    public FeatureSet getRequiredFeatures() {
        return this.requiredFeatures;
    }

    public interface Factory<T extends ScreenHandler> {
        T create(int syncId, PlayerInventory playerInventory);
    }
}
