package cutefox.foxden.mixin;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.Utils.FoxDenDefaultConfig;
import cutefox.foxden.Utils.Utils;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.include.com.google.gson.JsonElement;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    //@Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("HEAD"))
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        if ((Boolean) ConfigBuilder.globalConfig.get(FoxDenDefaultConfig.UPGRADE_TEMPLATE) && !TheFoxDenCollection.isBetterEnchantingPresent) {
            //map.put(Utils.id("iron_upgrade_template"), ModUpgra);
        }
    }
}
