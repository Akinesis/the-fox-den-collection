package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.Utils.FoxDenDefaultConfig;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class ModLootTableModifiers {

    public static void modifyLootTables(){
        TheFoxDenCollection.LOGGER.info("Registering all event for : "+TheFoxDenCollection.MOD_ID);


        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) ->  {

            if(EntityType.SKELETON.getLootTableId() == key && source.isBuiltin()
                && ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.BONE_ARMOR)){
                LootPool.Builder pool = LootPool.builder()
                        .conditionally(RandomChanceLootCondition.builder(.1f))
                        .with(ItemEntry.builder(ModItems.BONE_BOOTS))
                        .with(ItemEntry.builder(ModItems.BONE_LEGGINGS))
                        .with(ItemEntry.builder(ModItems.BONE_CHESTPLATE))
                        .with(ItemEntry.builder(ModItems.BONE_HELMET))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,1)));
                tableBuilder.pool(pool);
            }

            if(LootTables.END_CITY_TREASURE_CHEST == key && source.isBuiltin()
             && ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.SPACE_ARMOR)){
                LootPool.Builder pool = LootPool.builder()
                        .conditionally(RandomChanceLootCondition.builder(.15f))
                        .with(ItemEntry.builder(ModItems.SPACE_RANGER_BOOTS))
                        .with(ItemEntry.builder(ModItems.SPACE_RANGER_LEGGINGS))
                        .with(ItemEntry.builder(ModItems.SPACE_RANGER_CHESTPLATE))
                        .with(ItemEntry.builder(ModItems.SPACE_RANGER_HELMET))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,1)));
                tableBuilder.pool(pool);
            }

        });
    }


}
