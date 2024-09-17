package cutefox.foxden.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ArmorWithEffect{

    void applyEffect(LivingEntity entity);

    void removeEffect(LivingEntity entity);

    default boolean hasItemEquiped(LivingEntity livingEntity, Item item){
        for(ItemStack stack : livingEntity.getArmorItems()){
            if(stack.getItem().equals(item))
                return true;
        }
        return false;
    }
}
