package cutefox.foxden.client;

import cutefox.foxden.block.entity.StorageBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.RotationAxis;

public class CakeDisplayRenderer implements StorageTypeRenderer {
   public void render(StorageBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks) {
      int invSize = 6;
      float scale = 0.35F;
      float itemScaleY = 0.4F;
      float yOffsetFirstColumn = 1.15F;
      float yOffsetSecondColumn = 0.25F;
      float zOffset = -0.45F;
      float xOffsetModifier = 2.1F;
      float secondColumnAdditionalOffset = -1.5F;
      float firstColumnLeftShift = 1.2F;

      for(int i = 0; i < invSize; ++i) {
         ItemStack stack = (ItemStack)itemStacks.get(i);
         if (!stack.isEmpty() && !(stack.getItem() instanceof BlockItem)) {
            matrices.push();
            matrices.scale(scale, itemScaleY, scale);
            boolean isFirstColumn = i < invSize / 2;
            float xOffset = (float)(i - 1) * xOffsetModifier + (isFirstColumn ? firstColumnLeftShift : secondColumnAdditionalOffset);
            float yOffset = isFirstColumn ? yOffsetFirstColumn : yOffsetSecondColumn;
            matrices.translate(xOffset, yOffset, zOffset);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
            matrices.translate(-1.2F * (float)i, 1.0F, 0.0F);
            ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
            matrices.pop();
         }
      }

   }
}
