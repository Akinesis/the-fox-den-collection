package cutefox.foxden;

import cutefox.foxden.item.SpaceRangerArmorItem;
import cutefox.foxden.networking.SpaceRangerArmorWingsPayload;
import cutefox.foxden.registery.ModBlocks;
import cutefox.foxden.registery.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.world.biome.GrassColors;
import org.lwjgl.glfw.GLFW;

public class TheFoxDenCollectionClient implements ClientModInitializer {

    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {

        registerKeybind();
        registerLeavesWall();
    }

    private void registerLeavesWall(){
        ModBlocks.LEAVES_WALL.stream().forEach(wall -> {
            BlockRenderLayerMap.INSTANCE.putBlock(wall, RenderLayer.getCutout());
            if(!(wall.equals(ModBlocks.CHERRY_LEAVES_WALL) || wall.equals(ModBlocks.AZALEA_LEAVES_WALL) || wall.equals(ModBlocks.FLOWERING_AZALEA_LEAVES_WALL))) {
                ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> BiomeColors.getFoliageColor(world, pos)), wall);
            }
        });

        ModItems.LEAVES_WALLS.stream().forEach(wall -> {
            if(!(wall.equals(ModItems.CHERRY_LEAVES_WALL) || wall.equals(ModItems.AZALEA_LEAVES_WALL) || wall.equals(ModItems.FLOWERING_AZALEA_LEAVES_WALL))) {
                ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.8, 0.5), wall);
            }
        });

    }

    private void registerKeybind(){
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
