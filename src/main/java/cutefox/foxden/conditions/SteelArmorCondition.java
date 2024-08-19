package cutefox.foxden.conditions;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.Utils.FoxDenDefaultConfig;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.Nullable;

public class SteelArmorCondition implements ResourceCondition {
    public static final MapCodec<SteelArmorCondition> CODEC = MapCodec.unit(SteelArmorCondition::new);

    @Override
    public ResourceConditionType<?> getType() {
        return ModConfigConditions.STEEL_ARMOR;
    }

    @Override
    public boolean test(@Nullable RegistryWrapper.WrapperLookup registryLookup) {
        return ConfigBuilder.globalConfig.get(FoxDenDefaultConfig.KNIGHT_ARMOR);
    }
}
