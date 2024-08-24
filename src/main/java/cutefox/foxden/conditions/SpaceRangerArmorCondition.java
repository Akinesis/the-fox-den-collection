package cutefox.foxden.conditions;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.Utils.FoxDenDefaultConfig;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.Nullable;

public class SpaceRangerArmorCondition implements ResourceCondition {
    public static final MapCodec<SpaceRangerArmorCondition> CODEC = MapCodec.unit(SpaceRangerArmorCondition::new);

    @Override
    public ResourceConditionType<?> getType() {
        return ModConfigConditions.SPACE_ARMOR;
    }

    @Override
    public boolean test(@Nullable RegistryWrapper.WrapperLookup registryLookup) {
        return ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.SPACE_ARMOR);
    }
}
