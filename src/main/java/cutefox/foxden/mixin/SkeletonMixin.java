package cutefox.foxden.mixin;

import cutefox.foxden.registery.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonMixin extends LivingEntityMixin {

    @Override
    protected void theFoxDenCollection$preventTargetOnPlayerWithBuff(LivingEntity target, CallbackInfoReturnable<Boolean> cir){
        if(target instanceof PlayerEntity player){
            if(player.getStatusEffect(ModStatusEffects.SKELETON_LOVE) != null)
                cir.setReturnValue(false);
        }
    }
}
