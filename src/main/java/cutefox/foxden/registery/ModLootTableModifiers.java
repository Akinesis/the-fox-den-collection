package cutefox.foxden.registery;

import cutefox.foxden.TheFoxDenCollection;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class ModLootTableModifiers {

    public static void modifyLootTables(){
        TheFoxDenCollection.LOGGER.info("Registering all event for : "+TheFoxDenCollection.MOD_ID);


        LootTableEvents.MODIFY.register((key, tableBuilder, source) ->  {

            if(EntityType.SKELETON.getLootTableId() == key && source.isBuiltin()){
                LootPool.Builder pool = LootPool.builder()
                        .conditionally(RandomChanceLootCondition.builder(.1f))
                        .with(ItemEntry.builder(ModItems.BONE_BOOTS))
                        .with(ItemEntry.builder(ModItems.BONE_LEGGINGS))
                        .with(ItemEntry.builder(ModItems.BONE_CHESTPLATE))
                        .with(ItemEntry.builder(ModItems.BONE_HELMET))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,1)));
                tableBuilder.pool(pool);
            }

        });
    }


}