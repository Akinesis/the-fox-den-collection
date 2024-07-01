package cutefox.foxden.item;

import cutefox.foxden.registery.ModArmorMaterials;
import cutefox.foxden.registery.ModStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModArmorItem_old extends ArmorItem {

    private static final Map<RegistryEntry<ArmorMaterial>, ArrayList<StatusEffectInstance>> MATERIAL_TO_EFFECT;

    static{
        MATERIAL_TO_EFFECT = new HashMap<>();

        addValue(ModArmorMaterials.BONE, ModStatusEffects.SKELETON_LOVE, 0);
    }

    public ModArmorItem_old(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    private static void addValue(RegistryEntry<ArmorMaterial> key, RegistryEntry<StatusEffect> effect, int amplifier) {

        ArrayList<StatusEffectInstance> tempList;

        StatusEffectInstance effectInstance = new StatusEffectInstance(effect,600, amplifier,false,false, true);

        if(MATERIAL_TO_EFFECT.containsKey(key)){
            tempList = MATERIAL_TO_EFFECT.get(key);
            if(tempList==null)
                tempList = new ArrayList<>();

            tempList.add(effectInstance);
        }else {
            tempList = new ArrayList<>();
            tempList.add(effectInstance);
        }

        MATERIAL_TO_EFFECT.put(key, tempList);

    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient())
            if(entity instanceof PlayerEntity player && hasFullArmorSet(player))
                evaluateArmorEffect(player);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private boolean hasFullArmorSet(PlayerEntity player) {
        ItemStack boots = player.getInventory().getArmorStack(0);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack helmet = player.getInventory().getArmorStack(3);

        return !boots.isEmpty() && !leggings.isEmpty() && !chestplate.isEmpty() && !helmet.isEmpty();
    }

    private void evaluateArmorEffect(PlayerEntity player) {

        for(Map.Entry<RegistryEntry<ArmorMaterial>,ArrayList<StatusEffectInstance>> entry : MATERIAL_TO_EFFECT.entrySet()){

            ArmorMaterial material = entry.getKey().value();

            entry.getValue().forEach( effect -> {
                if(hasCorrectArmor(material,player) && isStatusFading(player, effect))
                    addStatus(player, effect);
            });

        }
    }

    private boolean hasCorrectArmor(ArmorMaterial material, PlayerEntity player) {
        for(ItemStack armor : player.getInventory().armor)
            if(!(armor.getItem() instanceof ArmorItem))
                return false;

        ArmorItem boots = (ArmorItem) player.getInventory().getArmorStack(0).getItem();
        ArmorItem leggings = (ArmorItem) player.getInventory().getArmorStack(1).getItem();
        ArmorItem chestplate = (ArmorItem) player.getInventory().getArmorStack(2).getItem();
        ArmorItem helmet = (ArmorItem) player.getInventory().getArmorStack(3).getItem();

        return boots.getMaterial().value() == material &&
                leggings.getMaterial().value() == material &&
                chestplate.getMaterial().value() == material &&
                helmet.getMaterial().value() == material;
    }

    private boolean isStatusFading(PlayerEntity player, StatusEffectInstance effect) {
        StatusEffectInstance playerEffect = player.getStatusEffect(effect.getEffectType());

        return  playerEffect == null ||
                !player.hasStatusEffect(effect.getEffectType()) ||
                playerEffect.isDurationBelow(220);

    }

    private void addStatus(PlayerEntity player, StatusEffectInstance effect) {
        player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(),
                                                        effect.getDuration(),
                                                        effect.getAmplifier(),
                                                        effect.isAmbient(),
                                                        effect.shouldShowParticles(),
                                                        effect.shouldShowIcon()));
    }

}
