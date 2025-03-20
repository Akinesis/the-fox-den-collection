package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.entity.effect.SkeletonLoveStatusEffect;
import cutefox.foxden.entity.effect.SweetsEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;


public class ModStatusEffects {

    public static final RegistryEntry<StatusEffect> SKELETON_LOVE;
    public static final RegistryEntry<StatusEffect> SWEETS = registerStatusEffect("sweets", new SweetsEffect());
    public static final RegistryEntry<StatusEffect> HORSE_FODDER = registerStatusEffect("horse_fodder", new SweetsEffect());
    public static final RegistryEntry<StatusEffect> DOG_FOOD = registerStatusEffect("dog_food", new SweetsEffect());
    public static final RegistryEntry<StatusEffect> CLUCK = registerStatusEffect("cluck", new SweetsEffect());
    public static final RegistryEntry<StatusEffect> GRANDMAS_BLESSING = registerStatusEffect("grandmas_blessing", new SweetsEffect());
    public static final RegistryEntry<StatusEffect> RESTED = registerStatusEffect("rested", new SweetsEffect());
    public static final RegistryEntry<StatusEffect> FARMERS_BLESSING = registerStatusEffect("farmers_blessing", new SweetsEffect());
    public static final RegistryEntry<StatusEffect> SUSTENANCE = registerStatusEffect("sustenance", new SweetsEffect());
    public static final RegistryEntry<StatusEffect> SATIATION = registerStatusEffect("satiation", new SweetsEffect());
    public static final RegistryEntry<StatusEffect> FEAST = registerStatusEffect("feast", new SweetsEffect());


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
