package cutefox.foxden;

import cutefox.foxden.registery.ModHandledScreens;
import net.fabricmc.api.ClientModInitializer;

public class TheFoxDenCollectionClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModHandledScreens.registerModScreen();
    }
}
