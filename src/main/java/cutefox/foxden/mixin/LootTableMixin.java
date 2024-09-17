package cutefox.foxden.mixin;


import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.Utils.FoxDenDefaultConfig;
import cutefox.foxden.Utils.Utils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;

import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(LootTable.class)
public abstract class LootTableMixin {


    @Redirect(method = "supplyInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;setStack(ILnet/minecraft/item/ItemStack;)V", ordinal = 1))
    public void foxDenCollection$randomNameTag(Inventory inventory, int slotId, ItemStack itemStack){


        if(itemStack.getItem().equals(Items.NAME_TAG) && ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.RANDOM_NAME_TAG)){
            Random rand = Random.create();
            String randomName = Utils.RANDOM_NAMES.get(rand.nextBetween(0, Utils.RANDOM_NAMES.size()-1));
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.of(randomName));
        }

        inventory.setStack(slotId, itemStack);
    }
}