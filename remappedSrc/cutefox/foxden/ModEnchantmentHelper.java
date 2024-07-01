package cutefox.foxden;

import com.google.common.collect.Lists;
import cutefox.foxden.datagen.ModEnchantIngredientMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class ModEnchantmentHelper {

    public static Enchantment getEnchantById(World world, int id){
        IndexedIterable<RegistryEntry<Enchantment>> indexedIterable = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getIndexedEntries();

        return indexedIterable.get(id).value();
    }

    public static int getEnchantmentLevelCost(Enchantment enchantment, int enchantLevel, ItemStack stack){
        int tempCost = Math.round((8f/enchantment.getWeight())+enchantLevel);
        tempCost *= 1+(0.1*stack.getEnchantments().getSize()); //price increase by 10% for each different enchantments.
        return tempCost;
    }

    public static int getEnchantmentLeveRequierment(Enchantment enchantment, int enchantLevel){
        int tempLevelReq = 10-enchantment.getWeight()+(enchantLevel*enchantLevel);
        return tempLevelReq>30?30:tempLevelReq; //Cap level requirement at 30 to keep it vanilla lik
    }

    public static int getBookshelfCountRequierment(Enchantment enchantment, int enchantLevel){
        int tempReq = (int)Math.floor(getEnchantmentLeveRequierment(enchantment, enchantLevel)/2);
        return tempReq<=4?0:tempReq;
    }

    public static Item getEnchantIngredient(RegistryKey<Enchantment> enchantment, int enchantLevel){
        if(ModEnchantIngredientMap.map.containsKey(enchantment))
            if(ModEnchantIngredientMap.map.get(enchantment).size()>enchantLevel)
                return ModEnchantIngredientMap.map.get(enchantment).get(enchantLevel);

        return null;
    }

    public static List<EnchantmentLevelEntry> getPossibleEntries(int bookshelfCount, ItemStack stack, Stream<RegistryEntry<Enchantment>> possibleEnchantments){

        List<EnchantmentLevelEntry> list = Lists.newArrayList();
        boolean isBook = stack.isOf(Items.BOOK);
        if(isBook)
            return list;

        possibleEnchantments.filter((enchant) -> {
            return enchant.value().isAcceptableItem(stack);
        }).forEach((enchantmentx) -> {
            Enchantment enchantment = enchantmentx.value();

            if(isCompatible(stack.getEnchantments().getEnchantments(), enchantmentx)){
                for(int j = enchantment.getMaxLevel(); j >= enchantment.getMinLevel(); --j) {
                    if (getBookshelfCountRequierment(enchantment, j) <= bookshelfCount) {
                        list.add(new EnchantmentLevelEntry(enchantmentx, j));
                        break;
                    }
                }
            }
        });

        return list;
    }

    public static boolean itemHasPreviousLevelOfEnchant(ItemStack stack, RegistryEntry<Enchantment> enchant, int targetLevel){

        int currentEnchantLevel = EnchantmentHelper.getLevel(enchant,stack);
        return currentEnchantLevel == targetLevel;

    }

    public static boolean canBeCombined(RegistryEntry<Enchantment> first, RegistryEntry<Enchantment> second) {
        return !first.value().exclusiveSet().contains(second) && !second.value().exclusiveSet().contains(first);
    }

    public static boolean isCompatible(Collection<RegistryEntry<Enchantment>> existing, RegistryEntry<Enchantment> candidate) {
        for (RegistryEntry<Enchantment> registryEntry : existing) {
            if (!canBeCombined(registryEntry, candidate)){
                if(!registryEntry.equals(candidate))
                    return false;
            }
        }
        return true;
    }

    public static int getEnchantmentIngredientCost(Enchantment value, int displayedEnchantLevel) {
        int tempValue = (int)Math.floor(value.getWeight()/2);
        return tempValue==0?1:tempValue;
    }

    public static List<EnchantmentLevelEntry> addSwordEnchantToAxes(int bookshelfCount, Stream<RegistryEntry<Enchantment>> possibleEnchantments){

        List<EnchantmentLevelEntry> enchantToAdd = new ArrayList<>();
        List<RegistryKey<Enchantment>> swordEnchants = new ArrayList<>();

        swordEnchants.add(Enchantments.FIRE_ASPECT);
        swordEnchants.add(Enchantments.LOOTING);
        swordEnchants.add(Enchantments.KNOCKBACK);

        possibleEnchantments.filter(e -> {
            return swordEnchants.contains(e.getKey().get());
        }).forEach(enchantmentx -> {
            Enchantment enchantment = enchantmentx.value();
            for(int j = enchantment.getMaxLevel(); j >= enchantment.getMinLevel(); --j) {
                if (getBookshelfCountRequierment(enchantment, j) <= bookshelfCount) {
                    enchantToAdd.add(new EnchantmentLevelEntry(enchantmentx, j));
                    break;
                }
            }
        });

        return enchantToAdd;
    }
}
