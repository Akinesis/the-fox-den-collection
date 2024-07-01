package cutefox.foxden.mixin;

import cutefox.foxden.registery.ModStatusEffects;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RangedWeaponItem.class)
public abstract class RangedWeaponItemMixin {

    @Inject(method = "getProjectile", at = @At(value = "HEAD"), cancellable = true)
    private static void preventProjectileConsumption(ItemStack stack, ItemStack projectileStack, LivingEntity shooter, boolean multishot, CallbackInfoReturnable<ItemStack> cir){
        if(shooter instanceof PlayerEntity player)
            if(!player.isCreative() && player.getStatusEffect(ModStatusEffects.SKELETON_LOVE) != null){
                if(projectileStack.getCount() > 0){
                    ItemStack itemStack;

                    if(multishot){ //If multishot, consume on arrow
                        itemStack = projectileStack.copyWithCount(1);
                        player.getInventory().removeOne(projectileStack);
                    }else {
                        itemStack = projectileStack.copyWithCount(1);
                        itemStack.set(DataComponentTypes.INTANGIBLE_PROJECTILE, Unit.INSTANCE);
                    }
                    cir.setReturnValue(itemStack);
                }
            }
    }
}
