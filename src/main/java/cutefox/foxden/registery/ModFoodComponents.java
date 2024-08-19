package cutefox.foxden.registery;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ModFoodComponents {

    public static final FoodComponent CHEESE = new FoodComponent.Builder()
            .nutrition(2).saturationModifier(0.8f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 1),1).build();

    public static final FoodComponent BLUE_CHEESE = new FoodComponent.Builder()
            .nutrition(4).saturationModifier(1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20*60, 0),1).build();

    public static final FoodComponent FRIES = new FoodComponent.Builder()
            .nutrition(3).saturationModifier(1.2f)
            .build();

    public static final FoodComponent POUTINE = new FoodComponent.Builder()
            .nutrition(10).saturationModifier(2f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20*30,0),1)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20*60,0),1)
            .statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 20*60,0),1)
            .build();
}
