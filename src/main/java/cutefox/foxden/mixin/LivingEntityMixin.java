package cutefox.foxden.mixin;

import cutefox.foxden.registery.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    protected void theFoxDenCollection$preventTargetOnPlayerWithBuff(LivingEntity target, CallbackInfoReturnable<Boolean> cir){

    }

    @Inject(method = "onEquipStack", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;emitGameEvent(Lnet/minecraft/registry/entry/RegistryEntry;)V"))
    public void theFoxDenCollection$checkFormArmorBonus(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo ci){

    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "HEAD"))
    public float theFoxDenCollection$ApplySpaceArmorChestplateDamageReduction(float amount, DamageSource source){
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        ItemStack chestPlate = livingEntity.getEquippedStack(EquipmentSlot.CHEST);

        if(!chestPlate.isEmpty() && chestPlate.getItem().equals(ModItems.SPACE_RANGER_CHESTPLATE) && !source.isIn(DamageTypeTags.BYPASSES_RESISTANCE)){
            //Entity is equipped with space ranger chestplate and source don't bypass resistance
            float damageReduction = amount*0.08f; //8% damage reduction.
            amount -=damageReduction;
        }

        return amount;
    }

}