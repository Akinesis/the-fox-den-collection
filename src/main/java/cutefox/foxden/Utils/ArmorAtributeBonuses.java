package cutefox.foxden.Utils;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;

public abstract class ArmorAtributeBonuses {


    public static AttributeModifiersComponent getSpaceRangerBootBonuses(){
        EntityAttributeModifier speedModifier = new EntityAttributeModifier(Utils.id("space_ranger_boots_speed"),0.02, EntityAttributeModifier.Operation.ADD_VALUE);
        EntityAttributeModifier movementEfficiency = new EntityAttributeModifier(Utils.id("space_ranger_boots_efficiency"),0.2, EntityAttributeModifier.Operation.ADD_VALUE);

        AttributeModifiersComponent amc = AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speedModifier, AttributeModifierSlot.FEET)
                .add(EntityAttributes.GENERIC_MOVEMENT_EFFICIENCY, movementEfficiency, AttributeModifierSlot.FEET).build();

        return amc;
    }

    public static AttributeModifiersComponent getSpaceRangerLeggingsBonuses(){
        EntityAttributeModifier waterMovementEfficiency = new EntityAttributeModifier(Utils.id("space_ranger_leggings_water_movement"),0.2, EntityAttributeModifier.Operation.ADD_VALUE);
        EntityAttributeModifier sneakingSpeed = new EntityAttributeModifier(Utils.id("space_ranger_leggings_sneaking"),0.2, EntityAttributeModifier.Operation.ADD_VALUE);

        AttributeModifiersComponent amc = AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY, waterMovementEfficiency, AttributeModifierSlot.LEGS)
                .add(EntityAttributes.PLAYER_SNEAKING_SPEED, sneakingSpeed, AttributeModifierSlot.LEGS).build();

        return amc;
    }

    public static AttributeModifiersComponent getSpaceRangerChestplateBonuses(){
        EntityAttributeModifier fallDistance = new EntityAttributeModifier(Utils.id("space_ranger_chestplate_fall_distance"),1, EntityAttributeModifier.Operation.ADD_VALUE);
        EntityAttributeModifier fireTick = new EntityAttributeModifier(Utils.id("space_ranger_chestplate_fire_tick"),-0.2, EntityAttributeModifier.Operation.ADD_VALUE);

        AttributeModifiersComponent amc = AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, fallDistance, AttributeModifierSlot.CHEST)
                .add(EntityAttributes.GENERIC_BURNING_TIME, fireTick, AttributeModifierSlot.CHEST).build();

        return amc;
    }

    public static AttributeModifiersComponent getSpaceRangerHelmetBonuses(){
        EntityAttributeModifier oxygen = new EntityAttributeModifier(Utils.id("space_ranger_helmet_oxygen"),2, EntityAttributeModifier.Operation.ADD_VALUE);
        EntityAttributeModifier luck = new EntityAttributeModifier(Utils.id("space_ranger_helmet_luck"),1, EntityAttributeModifier.Operation.ADD_VALUE);

        AttributeModifiersComponent amc = AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_OXYGEN_BONUS, oxygen, AttributeModifierSlot.HEAD)
                .add(EntityAttributes.GENERIC_LUCK, luck, AttributeModifierSlot.HEAD).build();

        return amc;
    }

    public static AttributeModifiersComponent getBikeHelmetBonuses(){
        EntityAttributeModifier fallDamageReduction = new EntityAttributeModifier(Utils.id("fall_damage_reduction"), -0.2, EntityAttributeModifier.Operation.ADD_VALUE);

        AttributeModifiersComponent amc = AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER, fallDamageReduction, AttributeModifierSlot.HEAD).build();

        return amc;
    }
}
