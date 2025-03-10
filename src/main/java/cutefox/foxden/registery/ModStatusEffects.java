package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.entity.effect.SkeletonLoveStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;


public class ModStatusEffects {

    public static final RegistryEntry<StatusEffect> SKELETON_LOVE;


    static {
        SKELETON_LOVE = registerStatusEffect("skeleton_love", new SkeletonLoveStatusEffect());

    }

    private static RegistryEntry<StatusEffect> registerStatusEffect(String id, StatusEffect statusEffect){
        return Registry.registerReference(Registries.STATUS_EFFECT, Utils.id(id), statusEffect);
    }

    public static void registerModStatusEffects(){
        TheFoxDenCollection.LOGGER.info("Registering mod status effects for : "+ TheFoxDenCollection.MOD_ID);
    }
}
