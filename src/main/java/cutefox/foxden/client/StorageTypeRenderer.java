package cutefox.foxden.client;

import cutefox.foxden.block.entity.StorageBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface StorageTypeRenderer {
   void render(StorageBlockEntity var1, MatrixStack var2, VertexConsumerProvider var3, DefaultedList<ItemStack> var4);
}
