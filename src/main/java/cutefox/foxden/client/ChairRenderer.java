package cutefox.foxden.client;

import cutefox.foxden.block.entity.ChairEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class ChairRenderer extends EntityRenderer<ChairEntity> {
   public ChairRenderer(EntityRendererFactory.Context ctx) {
      super(ctx);
   }

   public boolean shouldRender(ChairEntity entity, Frustum frustum, double x, double y, double z) {
      return false;
   }

   public Identifier getTexture(ChairEntity entity) {
      return null;
   }
}
