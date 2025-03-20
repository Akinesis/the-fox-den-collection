package cutefox.foxden.block.entity;

import cutefox.foxden.Utils.ChairUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ChairEntity extends Entity {
   public ChairEntity(EntityType<?> type, World world) {
      super(type, world);
   }

   @Override
   protected void initDataTracker(DataTracker.Builder builder) {

   }

   public void remove(RemovalReason reason) {
      super.remove(reason);
      ChairUtil.removeChairEntity(this.getWorld(), this.getBlockPos());
   }

   @Override
   protected void readCustomDataFromNbt(NbtCompound nbt) {

   }

   @Override
   protected void writeCustomDataToNbt(NbtCompound nbt) {

   }

   public Vec3d updatePassengerForDismount(LivingEntity passenger) {
      if (passenger instanceof PlayerEntity) {
         PlayerEntity p = (PlayerEntity)passenger;
         BlockPos pos = ChairUtil.getPreviousPlayerPosition(p, this);
         if (pos != null) {
            this.discard();
            return new Vec3d((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D);
         }
      }

      this.discard();
      return super.updatePassengerForDismount(passenger);
   }
}
