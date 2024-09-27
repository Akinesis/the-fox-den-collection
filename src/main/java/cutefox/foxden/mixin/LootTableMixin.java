package cutefox.foxden.mixin;


import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.Utils.FoxDenDefaultConfig;
import cutefox.foxden.Utils.Utils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;

import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LootTable.class)
public abstract class LootTableMixin {


    @Inject(method = "supplyInventory", at = @At(value ="INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"))
    public void foxDenCollection$randomNameTag(Inventory inventory, LootContextParameterSet parameters, long seed, CallbackInfo ci, @Local LocalRef<ItemStack> localRef){


        if(localRef.get().getItem().equals(Items.NAME_TAG) && ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.RANDOM_NAME_TAG)){
            ItemStack tagWithName = localRef.get().copy();

            Random rand = Random.create();
            String randomName = Utils.RANDOM_NAMES.get(rand.nextBetween(0, Utils.RANDOM_NAMES.size()-1));
            tagWithName.set(DataComponentTypes.CUSTOM_NAME, Text.of(randomName));
            localRef.set(tagWithName);
        }

    }
}