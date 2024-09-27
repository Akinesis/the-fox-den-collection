package cutefox.foxden.mixin;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.item.ArmorWithEffect;
import cutefox.foxden.registery.ModArmorMaterials;
import cutefox.foxden.registery.ModEntityAttributes;
import cutefox.foxden.registery.ModItems;
import cutefox.foxden.registery.ModStatusEffects;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Arm;
import org.jetbrains.annotations.Blocking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import software.bernie.geckolib.animation.PlayState;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntityMixin{

    @Override
    public void theFoxDenCollection$checkFormArmorBonus(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo ci) {

        PlayerEntity player = (PlayerEntity) (Object) this;

        if(!newStack.isEmpty()){
            //If adding an armor

            //Remove all existing armor set status
            //TODO : this will break for armor giving status effect on less than a full set
            for(RegistryEntry<ArmorMaterial> key : ModArmorMaterials.MATERIAL_TO_STATUS.keySet()){
                player.removeStatusEffect(ModArmorMaterials.MATERIAL_TO_STATUS.get(key));
            }

            if((newStack.getItem() instanceof ArmorItem armorItem)){
                //If addind an armor
                if(ModArmorMaterials.MATERIAL_TO_STATUS.containsKey(armorItem.getMaterial())){
                    //If material of armor add effect

                    //Checking for set
                    RegistryEntry<ArmorMaterial> armorSetType = theFoxDenCollection$hasFullArmorSet(player, slot.getEntitySlotId(), newStack );

                    if(armorSetType != null){
                        //applie set bonus
                        StatusEffectInstance effect = new StatusEffectInstance(ModArmorMaterials.MATERIAL_TO_STATUS.get(armorSetType),
                                StatusEffectInstance.INFINITE, 0,false,false,true);
                        player.addStatusEffect(effect);

                    }
                }

                //Check for individual bonuses
                if(armorItem instanceof ArmorWithEffect armorWithEffect){
                    armorWithEffect.applyEffect(player);
                }

            }
        }else{
            //if removing an armor
            if((oldStack.getItem() instanceof ArmorItem armorItem)){
                RegistryEntry<StatusEffect> effect = ModArmorMaterials.MATERIAL_TO_STATUS.get(armorItem.getMaterial());
                if(effect != null){
                    player.removeStatusEffect(effect);
                }
            }
        }
    }

    private RegistryEntry<ArmorMaterial> theFoxDenCollection$hasFullArmorSet(PlayerEntity player, int slotId, ItemStack newArmor) {

        RegistryEntry<ArmorMaterial> setMaterial = null;
        List<ItemStack> playerArmor = new ArrayList<>();

        playerArmor.add((slotId==0)?newArmor:player.getInventory().getArmorStack(0));
        playerArmor.add((slotId==1)?newArmor:player.getInventory().getArmorStack(1));
        playerArmor.add((slotId==2)?newArmor:player.getInventory().getArmorStack(2));
        playerArmor.add((slotId==3)?newArmor:player.getInventory().getArmorStack(3));

        for (ItemStack stack : playerArmor) {
            // We can stop immediately if any of the slots are empty
            if (stack.isEmpty())
                return null;

            if(stack.getItem() instanceof ArmorItem armorItem){
                if (setMaterial == null) {
                    setMaterial = armorItem.getMaterial();
                }else if (!setMaterial.equals(armorItem.getMaterial())){
                    return null;
                }
            }else {
                return null;
            }
        }

        //return assertionFailed?null:materialSet;
        return setMaterial;
    }

    @Inject(method = "createPlayerAttributes", at = @At(value = "RETURN"))
    private static void theFoxDenCollection$registerCustomAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir){
        TheFoxDenCollection.LOGGER.info("Registering custom attributes for player");
        if(ConfigBuilder.moreAttributes())
            cir.getReturnValue()
                    .add(ModEntityAttributes.PLAYER_BONUS_BREAKING)
                    .add(ModEntityAttributes.PLAYER_BONUS_HARVEST)
                    .add(ModEntityAttributes.PLAYER_BONUS_LOGGING)
                    .add(ModEntityAttributes.PLAYER_BONUS_SHOVELING)
                    .add(ModEntityAttributes.PLAYER_BONUS_MINING);
    }
}
