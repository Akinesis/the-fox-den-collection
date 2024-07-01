package cutefox.foxden.mixin;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.registery.ModArmorMaterials;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntityMixin{

    @Shadow public abstract void remove(Entity.RemovalReason reason);

    @Shadow public abstract void setMainArm(Arm arm);

    @Shadow public int experienceLevel;
    private ModArmorMaterials FoxDen_fullSetBonus;

    @Override
    public void checkFormArmorBonus(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if(!newStack.isEmpty()){
            //If adding an armor

            //Case if armor swaping, clear statusEffect just in case
            if((oldStack.getItem() instanceof ArmorItem oldArmorItem)){
                RegistryEntry<StatusEffect> effect = ModArmorMaterials.MATERIAL_TO_STATUS.get(oldArmorItem.getMaterial());
                if(effect != null){
                    player.removeStatusEffect(effect);
                }
            }

            if((newStack.getItem() instanceof ArmorItem armorItem)){
                //If addind an armor
                if(ModArmorMaterials.MATERIAL_TO_STATUS.containsKey(armorItem.getMaterial())){
                    //If material of armor add effect

                    //Checking for set
                    RegistryEntry<ArmorMaterial> armorSetType = foxDen_hasFullArmorSet(player, slot.getEntitySlotId(), newStack );

                    if(armorSetType != null){
                        //applie set bonus
                        StatusEffectInstance effect = new StatusEffectInstance(ModArmorMaterials.MATERIAL_TO_STATUS.get(armorSetType),
                                StatusEffectInstance.INFINITE, 0,false,false,true);
                        player.addStatusEffect(effect);

                    }
                }
            }
        }else{
            //if removing an armor
            TheFoxDenCollection.LOGGER.info("Player as removed armor");

            if((oldStack.getItem() instanceof ArmorItem armorItem)){
                RegistryEntry<StatusEffect> effect = ModArmorMaterials.MATERIAL_TO_STATUS.get(armorItem.getMaterial());
                if(effect != null){
                    player.removeStatusEffect(effect);
                }
            }
        }
    }

    private RegistryEntry<ArmorMaterial> foxDen_hasFullArmorSet(PlayerEntity player, int slotId, ItemStack newArmor) {

        RegistryEntry<ArmorMaterial> materialSet = null;
        boolean assertionFailed = true;

        ItemStack boots = (slotId==0)?newArmor:player.getInventory().getArmorStack(0);
        if(!boots.isEmpty()){
            if(boots.getItem() instanceof ArmorItem bootsArmor) {
                materialSet = bootsArmor.getMaterial();
                assertionFailed = false;
            }
        }

        if(assertionFailed)
            return null;

        assertionFailed = true;

        ItemStack leggings = (slotId==1)?newArmor:player.getInventory().getArmorStack(1);
        if(!leggings.isEmpty()){
            if(leggings.getItem() instanceof ArmorItem leggingsArmor) {
                if (leggingsArmor.getMaterial() == materialSet)
                    assertionFailed = false;
            }
        }

        if(assertionFailed)
            return null;

        assertionFailed = true;

        ItemStack chestplate = (slotId==2)?newArmor:player.getInventory().getArmorStack(2);
        if(!chestplate.isEmpty()){
            if(chestplate.getItem() instanceof ArmorItem chestplateArmor) {
                if (chestplateArmor.getMaterial() == materialSet)
                    assertionFailed = false;
            }
        }

        if(assertionFailed)
            return null;

        assertionFailed = true;

        ItemStack helmet = (slotId==3)?newArmor:player.getInventory().getArmorStack(3);
        if(!helmet.isEmpty()){
            if(helmet.getItem() instanceof ArmorItem helmetArmor) {
                if (helmetArmor.getMaterial() == materialSet)
                    assertionFailed = false;
            }
        }

        return assertionFailed?null:materialSet;
    }
}
