package cutefox.foxden.entity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class ModStatusEffect extends StatusEffect {
    protected ModStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }
}
