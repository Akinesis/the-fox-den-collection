package cutefox.foxden.mixin;

import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.Utils.FoxDenDefaultConfig;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EnderDragonFight.class)
public class EnderDragonFightMixin {

    @Shadow
    private boolean previouslyKilled;

    @Inject(method = "dragonKilled", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonFight;generateNewEndGateway()V", shift = At.Shift.AFTER))
    public void allwaysSpawnDragonEgg(EnderDragonEntity dragon, CallbackInfo ci){
        if(ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.RESPAWN_DRAGON_EGG))
            this.previouslyKilled = false;
    }
}
