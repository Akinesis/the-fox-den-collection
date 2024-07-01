package cutefox.foxden.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    protected void preventTargetOnPlayerWithBuff(LivingEntity target, CallbackInfoReturnable<Boolean> cir){

    }

    @Inject(method = "onEquipStack", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;emitGameEvent(Lnet/minecraft/registry/entry/RegistryEntry;)V",
            shift = At.Shift.AFTER))
    public void checkFormArmorBonus(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo ci){

    }

}