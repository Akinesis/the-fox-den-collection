package cutefox.foxden.client;

import cutefox.foxden.block.entity.StorageBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class CakeStandRenderer implements StorageTypeRenderer {
   public void render(StorageBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks) {
      for(int i = 0; i < itemStacks.size(); ++i) {
         ItemStack stack = (ItemStack)itemStacks.get(i);
         if (!stack.isEmpty()) {
            matrices.push();
            Item var8 = stack.getItem();
            if (var8 instanceof BlockItem) {
               BlockItem blockItem = (BlockItem)var8;
               matrices.scale(0.65F, 0.65F, 0.65F);
               matrices.translate(-0.5D, 0.800000011920929D, -0.5D);
               ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
            } else {
               matrices.scale(0.4F, 0.4F, 0.4F);
               if (i == 0) {
                  matrices.translate(-0.4F, 1.3F, 0.4F);
               } else if (i == 1) {
                  matrices.translate(-0.2F, 1.3F, -0.4F);
               } else {
                  matrices.translate(0.4F, 1.3F, 0.2F);
               }

               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
               ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
            }

            matrices.pop();
         }
      }

   }
}
