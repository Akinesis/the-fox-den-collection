package cutefox.foxden.client;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.registery.ModBlocks;
import net.minecraft.block.Block;

public class ClientStorageTypes {
   public static void registerStorageType(Block block, StorageTypeRenderer renderer) {
      StorageBlockEntityRenderer.registerStorageType(block, renderer);
   }

   public static void init() {
      TheFoxDenCollection.LOGGER.debug("Registering Storage Block Renderers!");
      registerStorageType(ModBlocks.CAKE_STAND, new CakeStandRenderer());
      registerStorageType(ModBlocks.TRAY, new TrayRenderer());
      registerStorageType(ModBlocks.CAKE_DISPLAY, new CakeDisplayRenderer());
      registerStorageType(ModBlocks.CUPCAKE_DISPLAY, new CupcakeDisplayRenderer());
      registerStorageType(ModBlocks.BREADBOX, new BreadBoxRenderer());
      registerStorageType(ModBlocks.WALL_DISPLAY, new WallDisplayRenderer());
   }
}
