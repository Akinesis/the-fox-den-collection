package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.Utils;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEntityAttributes {

    public static final RegistryEntry<EntityAttribute> PLAYER_BONUS_BREAKING;
    public static final RegistryEntry<EntityAttribute> PLAYER_BONUS_HARVEST;
    public static final RegistryEntry<EntityAttribute> PLAYER_BONUS_MINING;
    public static final RegistryEntry<EntityAttribute> PLAYER_BONUS_SHOVELING;
    public static final RegistryEntry<EntityAttribute> PLAYER_BONUS_LOGGING;

    public static void registerAttributes(){
        TheFoxDenCollection.LOGGER.info("Registering custom attributes");
    }

    static {
        PLAYER_BONUS_BREAKING = register("player.bonus_breaking", (new ClampedEntityAttribute(
                "attribute.name.player.bonus_breaking",
                0, 0.0, 100.0)).setTracked(true));
        PLAYER_BONUS_HARVEST = register("player.bonus_harvest", (new ClampedEntityAttribute(
                "attribute.name.player.bonus_harvest",
                0, 0.0, 100.0)).setTracked(true));
        PLAYER_BONUS_MINING = register("player.bonus_mining", (new ClampedEntityAttribute(
                "attribute.name.player.bonus_mining",
                0, 0.0, 100.0)).setTracked(true));
        PLAYER_BONUS_SHOVELING = register("player.bonus_shoveling", (new ClampedEntityAttribute(
                "attribute.name.player.bonus_shoveling",
                0, 0.0, 100.0)).setTracked(true));
        PLAYER_BONUS_LOGGING = register("player.bonus_logging", (new ClampedEntityAttribute(
                "attribute.name.player.bonus_logging",
                0, 0.0, 100.0)).setTracked(true));
    }

    private static RegistryEntry<EntityAttribute> register(String id, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, Utils.id(id), attribute);
    }

}
