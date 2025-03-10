package cutefox.foxden.entity.effect;

import cutefox.foxden.Utils.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.awt.*;

public class SweetsEffect extends StatusEffect {

    private static Identifier SPEED_MODIFIER_ID = Utils.id("sweets_speed_modifier");
    private static Identifier ATTACK_DAMAGE_MODIFIER_ID = Utils.id("sweets_attack_damage_modifier");
    private static Identifier ATTACK_SPEED_MODIFIER_ID = Utils.id("sweets_attack_speed_modifier");

    public SweetsEffect() {
        super(StatusEffectCategory.BENEFICIAL, Color.GREEN.getRGB());
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the effect every tick
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            //((PlayerEntity) entity).addExperience(1 << amplifier); // Higher amplifier gives you experience faster
            double percentIncrease = 0.02 * (double)(amplifier + 1);
            percentIncrease = Math.min(percentIncrease, 0.3);

            this.applyModifier(player, EntityAttributes.GENERIC_MOVEMENT_SPEED, SPEED_MODIFIER_ID, percentIncrease);
            this.applyModifier(player, EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER_ID, percentIncrease);
            this.applyModifier(player, EntityAttributes.GENERIC_ATTACK_SPEED, ATTACK_SPEED_MODIFIER_ID, percentIncrease);
        }

        return super.applyUpdateEffect(entity, amplifier);
    }

    private void applyModifier(PlayerEntity player, RegistryEntry<EntityAttribute> attribute, Identifier id, double percentIncrease){

        EntityAttributeInstance attributeInstance = player.getAttributeInstance(attribute);
        if(attributeInstance != null){
            attributeInstance.removeModifier(id);
        }
        double increase = attribute.value().getDefaultValue() * percentIncrease;
        attributeInstance.addTemporaryModifier(new EntityAttributeModifier(id, increase, EntityAttributeModifier.Operation.ADD_VALUE));
    }
}
