package cutefox.foxden.conditions;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.Utils;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

public class ModConfigConditions {

    public static final ResourceConditionType<UpgradeTemplateCondition> UPGRADE_TEMPLATE = createResourceConditionType("upgrade_template", UpgradeTemplateCondition.CODEC);
    public static final ResourceConditionType<PoutineCondition> POUTINE = createResourceConditionType("poutine", PoutineCondition.CODEC);
    public static final ResourceConditionType<SteelArmorCondition> STEEL_ARMOR = createResourceConditionType("steel_armor", SteelArmorCondition.CODEC);
    public static final ResourceConditionType<BoneArmorCondition> BONE_ARMOR = createResourceConditionType("bone_armor", BoneArmorCondition.CODEC);
    public static final ResourceConditionType<SpaceRangerArmorCondition> SPACE_ARMOR = createResourceConditionType("space_ranger_armor", SpaceRangerArmorCondition.CODEC);

    public static void registerConditions(){
        TheFoxDenCollection.LOGGER.info("Registering conditions for : "+TheFoxDenCollection.MOD_ID);
        ResourceConditions.register(UPGRADE_TEMPLATE);
        ResourceConditions.register(POUTINE);
        ResourceConditions.register(STEEL_ARMOR);
        ResourceConditions.register(BONE_ARMOR);
        ResourceConditions.register(SPACE_ARMOR);
    }

    private static <T extends ResourceCondition> ResourceConditionType<T> createResourceConditionType(String name, MapCodec<T> codec) {
        return ResourceConditionType.create(Utils.id(name), codec);
    }
}
