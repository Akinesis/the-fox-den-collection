package cutefox.foxden.client;

import cutefox.foxden.block.entity.StorageBlockEntity;
import cutefox.foxden.block.storage.StorageBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

import java.util.HashMap;

public class StorageBlockEntityRenderer implements BlockEntityRenderer<StorageBlockEntity> {
   private static final HashMap<Block, StorageTypeRenderer> STORAGE_TYPES = new HashMap();

   public static Block registerStorageType(Block block, StorageTypeRenderer renderer) {
      STORAGE_TYPES.put(block, renderer);
      return block;
   }

   public StorageBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
   }

   public void render(StorageBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
      if (entity.hasWorld()) {
         BlockState state = entity.getCachedState();
         Block block = state.getBlock();
         if (block instanceof StorageBlock) {
            DefaultedList<ItemStack> itemStacks = entity.getInventory();
            if (itemStacks.isEmpty()) {
               return;
            }

            matrices.push();
            applyBlockAngle(matrices, state, 180.0F);
            StorageTypeRenderer renderer = getRendererForId(block);
            if (renderer != null) {
               renderer.render(entity, matrices, vertexConsumers, itemStacks);
            }

            matrices.pop();
         }
      }

   }

   public static StorageTypeRenderer getRendererForId(Block block) {
      return (StorageTypeRenderer)STORAGE_TYPES.get(block);
   }

   public static void applyBlockAngle(MatrixStack matrices, BlockState state, float angleOffset) {
      float angle = ((Direction)state.get(StorageBlock.FACING)).asRotation();
      matrices.translate(0.5D, 0.0D, 0.5D);
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angleOffset - angle));
   }
}
