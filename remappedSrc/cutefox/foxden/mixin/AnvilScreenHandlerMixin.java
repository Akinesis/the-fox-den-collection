package cutefox.foxden.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandlerMixin{

    @Shadow
    private Property levelCost;

    @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
    public void preventCombineEnchantedItem(CallbackInfo ci){
        ItemStack firstStack = input.getStack(0);
        ItemStack secondStack = input.getStack(1);

        if (!firstStack.isEmpty() && !secondStack.isEmpty()){

            if (firstStack.getItem() == Items.ENCHANTED_BOOK || secondStack.getItem() == Items.ENCHANTED_BOOK)
                ci.cancel();

            if (firstStack.getItem() == secondStack.getItem()){
                //if both same items
                if(firstStack.hasEnchantments() && secondStack.hasEnchantments()){
                    //if both items has enchants
                    ci.cancel();
                }
            }
        }

    }

    @Inject(method = "updateResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/AnvilScreenHandler;sendContentUpdates()V"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true)
    public void preventRepaireCost(CallbackInfo ci, ItemStack itemStack, int i, long l, int j, ItemStack itemStack2){
        AnvilScreenHandler screenHandler = (AnvilScreenHandler) (Object) this;

        levelCost.set(j);
    }

    @Inject(method = "canTakeOutput", at = @At("HEAD"), cancellable = true)
    public void canTakeFreeOutput(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue((player.isInCreativeMode() || player.experienceLevel >= this.levelCost.get()) && this.levelCost.get() >= 0);
    }
}
