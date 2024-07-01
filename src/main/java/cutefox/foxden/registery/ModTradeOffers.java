package cutefox.foxden.registery;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

public class ModTradeOffers extends TradeOffers {

    public static final void removeEnchantedBooks(){
        //TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.LIBRARIAN) .replace(1, new TheFoxDenCollection.Factory[]{new TradeOffers.BuyItemFactory(Items.PAPER, 24, 16, 2), new TradeOffers.BuyItemFactory(Items.DIAMOND, 24, 16, 2), new TradeOffers.SellItemFactory(Blocks.BOOKSHELF, 9, 1, 12, 1)});

        PROFESSION_TO_LEVELED_TRADE.put(VillagerProfession.LIBRARIAN,
                copyToFastUtilMap(ImmutableMap.of(
                        1, new Factory[]{new BuyItemFactory(Items.PAPER, 24, 16, 2), new EnchantmentIngredientsFactory(2), new SellItemFactory(Blocks.BOOKSHELF, 9, 1, 12, 1)},
                        2, new Factory[]{new BuyItemFactory(Items.BOOK, 4, 12, 10), new EnchantmentIngredientsFactory(5), new SellItemFactory(Items.LANTERN, 1, 1, 5)},
                        3, new Factory[]{new BuyItemFactory(Items.INK_SAC, 5, 12, 20), new EnchantmentIngredientsFactory(10), new SellItemFactory(Items.GLASS, 1, 4, 10)},
	                    4, new Factory[]{new BuyItemFactory(Items.WRITABLE_BOOK, 2, 12, 30), new EnchantmentIngredientsFactory(15), new SellItemFactory(Items.CLOCK, 5, 1, 15), new SellItemFactory(Items.COMPASS, 4, 1, 15)},
                        5, new Factory[]{new SellItemFactory(Items.NAME_TAG, 20, 1, 30)})));

    }

    private static Int2ObjectMap<Factory[]> copyToFastUtilMap(ImmutableMap<Integer, Factory[]> map) {
        return new Int2ObjectOpenHashMap<Factory[]>(map);
    }

    public static class EnchantmentIngredientsFactory
            implements Factory {
        private final int experience;
        private final int minLevel;
        private final int maxLevel;

        public EnchantmentIngredientsFactory(int experience) {
            this(experience, 0, Integer.MAX_VALUE);
        }

        public EnchantmentIngredientsFactory(int experience, int minLevel, int maxLevel) {
            this.minLevel = minLevel;
            this.maxLevel = maxLevel;
            this.experience = experience;
        }

        @Override
        public TradeOffer create(Entity entity, Random random) {
            int l;
            ItemStack itemStack;
            RegistryEntryList.Named<Item> ingredients = Registries.ITEM.getOrCreateEntryList(ModItemTags.ENCHANTEMNT_INGREDIENT);
            if(ingredients.size() > 0){
                itemStack = ingredients.get(random.nextBetween(0,ingredients.size()-1)).value().getDefaultStack();
            }else {
                itemStack = new ItemStack(Items.BOOK);
            }
            int price = random.nextBetween(15,28);
            return new TradeOffer(new TradedItem(Items.EMERALD, price), itemStack, 12, this.experience, 0.2f);
        }
    }
}
