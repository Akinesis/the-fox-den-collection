package cutefox.foxden.client;

import cutefox.foxden.block.entity.StorageBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;

public class WallDisplayRenderer implements StorageTypeRenderer {
   public void render(StorageBlockEntity entity, MatrixStack poseStack, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> items) {
      for(int i = 0; i < Math.min(items.size(), 4); ++i) {
         ItemStack stack = (ItemStack)items.get(i);
         float scale;
         float xBaseOffset;
         float yBaseOffset;
         float zBaseOffset;
         float xSpacing;
         float xOffset;
         if (stack.getItem() == Items.BREAD) {
            poseStack.push();
            scale = 0.4F;
            poseStack.scale(scale, scale, scale);
            xBaseOffset = -0.5F;
            yBaseOffset = i < 2 ? 0.82F : 0.16F;
            zBaseOffset = 0.5F;
            xSpacing = 1.0F;
            xOffset = -1.75F;
            xOffset = xBaseOffset + (float)(i % 2) * xSpacing;
            float yOffset = yBaseOffset - (float)(i / 2) * xOffset;
            poseStack.translate(xOffset, yOffset, zBaseOffset);
            ClientUtil.renderItem(stack, poseStack, vertexConsumers, entity);
            poseStack.pop();
         } else if (stack.getItem() instanceof BlockItem) {
            poseStack.push();
            scale = 0.75F;
            poseStack.scale(scale, scale, scale);
            xBaseOffset = -0.75F;
            yBaseOffset = i < 2 ? 0.75F : 0.16F;
            zBaseOffset = -0.19F;
            xSpacing = 0.52F;
            xOffset = xBaseOffset + (float)(i % 2) * xSpacing;
            poseStack.translate(xOffset, yBaseOffset, zBaseOffset);
            ClientUtil.renderBlockFromItem((BlockItem)stack.getItem(), poseStack, vertexConsumers, entity);
            poseStack.pop();
         }
      }

   }
}
