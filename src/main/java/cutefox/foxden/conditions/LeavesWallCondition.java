package cutefox.foxden.conditions;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.Utils.FoxDenDefaultConfig;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.Nullable;

public class LeavesWallCondition implements ResourceCondition {
    public static final MapCodec<LeavesWallCondition> CODEC = MapCodec.unit(LeavesWallCondition::new);

    @Override
    public ResourceConditionType<?> getType() {
        return ModConfigConditions.LEAVES_WALL;
    }

    @Override
    public boolean test(@Nullable RegistryWrapper.WrapperLookup registryLookup) {
        return ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.LEAVES_WALL);
    }
}
