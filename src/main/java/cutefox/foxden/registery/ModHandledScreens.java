package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.screen.CustomEnchantmentScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class ModHandledScreens {


    public static void registerModScreen(){
        TheFoxDenCollection.LOGGER.info("Registering mod screens for : "+ TheFoxDenCollection.MOD_ID);
        HandledScreens.register(ModScreenHandlerType.CUSTOM_ENCHANTMENT_SCREEN_HANDLER, CustomEnchantmentScreen::new);
    }

}
