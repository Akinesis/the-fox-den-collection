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

    public static final FoodComponent CHOCOLATE = new FoodComponent.Builder()
            .nutrition(2).saturationModifier(1.2f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20*20, 0),0.75f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20*30, 0),1)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20*30, 0),0.1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 20*20, 0),0.05f)
            .build();

    public static final FoodComponent POUTINE = new FoodComponent.Builder()
            .nutrition(10).saturationModifier(2f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20*30,0),1)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20*60,0),1)
            .statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 20*60,0),1)
            .build();

    public static final FoodComponent CAKE_SLICE;
    public static final FoodComponent COOKIES_N_CUPCAKE;
    public static final FoodComponent PASTRIES;
    public static final FoodComponent BREAD_LIKE;
    public static final FoodComponent SANDWICH;
    public static final FoodComponent BREAD_JAMED;

    static {
        CAKE_SLICE = (new FoodComponent.Builder()).nutrition(5).saturationModifier(0.7F).statusEffect(new StatusEffectInstance(ModStatusEffects.SWEETS, 900, 0), 1.0F).build();
        COOKIES_N_CUPCAKE = (new FoodComponent.Builder()).nutrition(3).saturationModifier(0.5F).statusEffect(new StatusEffectInstance(ModStatusEffects.SWEETS, 900, 0), 1.0F).build();
        PASTRIES = (new FoodComponent.Builder()).nutrition(5).saturationModifier(0.6F).statusEffect(new StatusEffectInstance(ModStatusEffects.SWEETS, 900, 0), 1.0F).build();
        BREAD_LIKE = (new FoodComponent.Builder()).nutrition(5).saturationModifier(1.2F).statusEffect(new StatusEffectInstance(ModStatusEffects.SWEETS, 4200, 0), 1.0F).build();
        SANDWICH = (new FoodComponent.Builder()).nutrition(7).saturationModifier(0.8F).statusEffect(new StatusEffectInstance(ModStatusEffects.SWEETS, 1800, 0), 1.0F).build();
        BREAD_JAMED = (new FoodComponent.Builder()).nutrition(5).saturationModifier(0.5F).statusEffect(new StatusEffectInstance(ModStatusEffects.SWEETS, 400, 0), 1.0F).build();
    }
}
