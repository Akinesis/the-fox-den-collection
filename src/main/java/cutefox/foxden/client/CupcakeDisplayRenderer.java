package cutefox.foxden.client;

import cutefox.foxden.block.entity.StorageBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.RotationAxis;

public class CupcakeDisplayRenderer implements StorageTypeRenderer {
   public void render(StorageBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks) {
      for(int i = 0; i < itemStacks.size(); ++i) {
         ItemStack stack = (ItemStack)itemStacks.get(i);
         if (!stack.isEmpty() && !(stack.getItem() instanceof BlockItem)) {
            matrices.push();
            matrices.scale(0.3F, 0.4F, 0.3F);
            float xOffset = (float)(i % 3) * 2.1F;
            float yOffset = i < 3 ? 1.3F : 0.35F;
            float zOffset = -1.0F;
            xOffset += i < 3 ? -0.9F : 2.7F;
            matrices.translate(xOffset, yOffset, zOffset);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
            matrices.translate(-1.2F * (float)i, 1.0F, 0.0F);
            ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
            matrices.pop();
         }
      }

   }
}
