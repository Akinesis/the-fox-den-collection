package cutefox.foxden;

import cutefox.foxden.item.SpaceRangerArmorItem;
import cutefox.foxden.networking.SpaceRangerArmorWingsPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import org.lwjgl.glfw.GLFW;

public class TheFoxDenCollectionClient implements ClientModInitializer {

    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        keyBindTest();
    }

    private void keyBindTest(){
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.fox_den.armor_action", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_U, // The keycode of the key
                "category.fox_den.keys" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {

                if(client.player.getInventory().getArmorStack(2).getItem() instanceof SpaceRangerArmorItem){
                    final boolean isDeployed;

                    NbtComponent b = client.player.getInventory().getArmorStack(2).get(DataComponentTypes.CUSTOM_DATA);
                    if (b== null || !b.contains("deployed"))
                        isDeployed = true; //first time using
                    else{
                        isDeployed = !b.copyNbt().getBoolean("deployed");
                    }

                    ClientPlayNetworking.send(new SpaceRangerArmorWingsPayload(isDeployed,true));

                }
            }
        });
    }
}
