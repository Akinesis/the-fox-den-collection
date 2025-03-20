package cutefox.foxden.client;

import cutefox.foxden.block.entity.StorageBlockEntity;
import cutefox.foxden.registery.ModBlocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.RotationAxis;

public class TrayRenderer implements StorageTypeRenderer {
   public void render(StorageBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks) {
      matrices.translate(0.0D, 0.3D, 0.20000000298023224D);
      matrices.scale(0.5F, 0.5F, 0.5F);
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));

      for(int i = 0; i < itemStacks.size(); ++i) {
         ItemStack stack = (ItemStack)itemStacks.get(i);
         if (!stack.isEmpty()) {
            matrices.push();
            Item var8 = stack.getItem();
            if (var8 instanceof BlockItem) {
               BlockItem blockItem = (BlockItem)var8;
               if (blockItem.getBlock() == ModBlocks.CRUSTY_BREAD_BLOCK) {
                  matrices.translate(-0.6F, -0.5F, -1.0F);
                  matrices.scale(2.0F, 2.0F, 2.0F);
                  ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
               } else if (blockItem.getBlock() == ModBlocks.BUN_BLOCK) {
                  matrices.translate(-0.6F, -0.5F, -1.0F);
                  matrices.scale(2.0F, 2.0F, 2.0F);
                  ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
               } else if (blockItem.getBlock() == ModBlocks.BREAD_BLOCK) {
                  matrices.translate(-0.6F, -0.5F, 1.0F);
                  matrices.scale(2.0F, 2.0F, 2.0F);
                  matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
                  ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
               } else if (blockItem.getBlock() == ModBlocks.BRAIDED_BREAD_BLOCK) {
                  matrices.translate(-0.6F, -0.5F, 1.0F);
                  matrices.scale(2.0F, 2.0F, 2.0F);
                  matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
                  ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
               } else if (blockItem.getBlock() == ModBlocks.TOAST_BLOCK) {
                  matrices.translate(-0.6F, -0.5F, 1.0F);
                  matrices.scale(2.0F, 2.0F, 2.0F);
                  matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
                  ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
               } else if (blockItem.getBlock() == ModBlocks.BAGUETTE_BLOCK) {
                  matrices.translate(-0.3F, -0.5F, 0.7F);
                  matrices.scale(1.4F, 1.4F, 1.4F);
                  matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
                  ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
               } else {
                  matrices.translate(0.1D, -0.5D, -0.4000000059604645D);
                  matrices.scale(0.8F, 0.8F, 0.8F);
                  ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
               }
            } else {
               matrices.translate(0.2F * (float)i, 0.0F, 0.0F);
               matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(66.0F));
               ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
            }

            matrices.pop();
         }
      }

   }
}
