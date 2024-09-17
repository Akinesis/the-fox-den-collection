package cutefox.foxden.mixin;

import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.Utils.FoxDenDefaultConfig;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.registery.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {

    @Shadow protected abstract void mobTick();

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void foxDenCollection$randomSizeEntity(EntityType entityType, World world, CallbackInfo ci){

        if(ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.RANDOM_MOB_SIZE)){
            MobEntity mob = (MobEntity) (Object)this;

            Random randScale = Random.create();
            float random = randScale.nextBetween(-20,8);

            EntityAttributeModifier randomScaleModifier = new EntityAttributeModifier(Utils.id("random_scale"), random/100, EntityAttributeModifier.Operation.ADD_VALUE);

            mob.getAttributeInstance(EntityAttributes.GENERIC_SCALE).addPersistentModifier(randomScaleModifier);
        }
    }

    @Inject(method = "initEquipment", at = @At(value = "TAIL"))
    public void foxDenCollection$bikeHelmetOnZombie(Random random, LocalDifficulty localDifficulty, CallbackInfo ci){
        MobEntity mob = (MobEntity) (Object)this;

        if(mob instanceof ZombieEntity zombie && ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.BIKE_ARMOR)){
            if(zombie.getEquippedStack(EquipmentSlot.HEAD).isEmpty()){
                if(random.nextFloat() < 0.095F){
                    zombie.equipStack(EquipmentSlot.HEAD, new ItemStack(ModItems.BIKE_HELMET));
                }
            }
        }
    }
}
