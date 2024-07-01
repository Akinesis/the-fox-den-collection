package cutefox.foxden.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class SkeletonLoveStatusEffect extends ModStatusEffect {

    public SkeletonLoveStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xf7e8e8);
    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            return true;
        }

        return false;
    }

}
