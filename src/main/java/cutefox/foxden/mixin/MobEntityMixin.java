package cutefox.foxden.mixin;

import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.Utils.FoxDenDefaultConfig;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.registery.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {

    @Shadow protected abstract void mobTick();
    private static List<String> foxDenCollection$additionalMobs = Arrays.asList("Chupacabra","Kobold","Kobold Warrior");

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

    @Inject(method = "canMobSpawn", at = @At(value = "HEAD"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void foxDenCollection$PreventModSpawnNearCampfire(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir){

        if(!spawnReason.equals(SpawnReason.SPAWNER)){
            if(type.getSpawnGroup().equals(SpawnGroup.MONSTER) || foxDenCollection$additionalMobs.contains(type.getName().getString())){
                BlockPos.iterateOutwards(pos,48, 10, 48)
                        .forEach(block -> {
                            BlockEntity blockEntity = world.getBlockEntity(block);
                            if(blockEntity != null && blockEntity.getType().equals(BlockEntityType.CAMPFIRE)){
                                BlockState campfireState = blockEntity.getCachedState();
                                if(campfireState.get(Properties.LIT)){
                                    //A campfire is found withint 48 radius and is lit
                                    cir.setReturnValue(false);
                                }
                            }
                        });
            }
        }

    }
}
