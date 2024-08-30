package cutefox.foxden.mixin;

import cutefox.foxden.item.ModWolfArmor;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.WolfArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WolfArmorFeatureRenderer.class)
public abstract class WolfArmorFeatureRendererMixin {

    @Shadow private WolfEntityModel<WolfEntity> model;

    @Shadow public abstract void renderCracks(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack);

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/WolfEntity;FFFFFF)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"),
    locals = LocalCapture.CAPTURE_FAILHARD)
    public void RenderCustomArmor(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, WolfEntity wolfEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci, ItemStack itemStack){

        WolfArmorFeatureRenderer renderer = (WolfArmorFeatureRenderer)(Object)this;

        if (itemStack.getItem() instanceof ModWolfArmor animalArmorItem) {
            renderer.getContextModel().copyStateTo(this.model);
            this.model.animateModel(wolfEntity, f, g, h);
            this.model.setAngles(wolfEntity, f, g, j, k, l);
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(animalArmorItem.getEntityTexture()));
            this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
            this.renderCracks(matrixStack, vertexConsumerProvider, i, itemStack);
            return;
        }

    }

}
