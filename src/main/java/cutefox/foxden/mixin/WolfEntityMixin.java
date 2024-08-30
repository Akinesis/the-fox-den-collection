package cutefox.foxden.mixin;

import cutefox.foxden.registery.ModItems;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin {

    @Inject(method = "interactMob", at = @At(value = "HEAD"), cancellable = true)
    public void AddCustomWolfArmor(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        WolfEntity wolf = (WolfEntity) (Object)this;

        ItemStack itemInHand = player.getStackInHand(hand);
        Item itemOfStack = itemInHand.getItem();
        if (!wolf.getWorld().isClient || wolf.isBaby() && wolf.isBreedingItem(itemInHand)) {
            if (wolf.isTamed()) {
                if (!(wolf.isBreedingItem(itemInHand) && wolf.getHealth() < wolf.getMaxHealth())) {

                    if (foxDen_isValidWolfArmor(itemInHand) && wolf.isOwner(player) && wolf.getBodyArmor().isEmpty() && !wolf.isBaby()) {
                        wolf.equipBodyArmor(itemInHand.copyWithCount(1));
                        itemInHand.decrementUnlessCreative(1, player);
                        cir.setReturnValue(ActionResult.SUCCESS);
                    }
                }
            }
        }
    }

    @Inject(method = "hasArmor", at = @At(value = "RETURN"), cancellable = true)
    public void HasCustomArmor(CallbackInfoReturnable<Boolean> cir){
        WolfEntity wolf = (WolfEntity) (Object)this;
        boolean foxDen_hasArmor = wolf.getBodyArmor().isOf(Items.WOLF_ARMOR);
        foxDen_hasArmor = foxDen_hasArmor || wolf.getBodyArmor().isOf(ModItems.IRON_WOLF_ARMOR);
        foxDen_hasArmor = foxDen_hasArmor || wolf.getBodyArmor().isOf(ModItems.DIAMOND_WOLF_ARMOR);
        cir.setReturnValue(foxDen_hasArmor);
    }

    private boolean foxDen_isValidWolfArmor(ItemStack armor){
        return armor.isOf(ModItems.IRON_WOLF_ARMOR) || armor.isOf(ModItems.DIAMOND_WOLF_ARMOR);
    }
}
