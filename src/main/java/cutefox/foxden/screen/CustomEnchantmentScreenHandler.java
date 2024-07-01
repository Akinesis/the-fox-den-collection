package cutefox.foxden.screen;

import com.mojang.datafixers.util.Pair;
import cutefox.foxden.ModEnchantmentHelper;
import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.registery.ModEnchantmentTags;
import cutefox.foxden.registery.ModScreenHandlerType;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.util.math.BlockPos;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class CustomEnchantmentScreenHandler extends ScreenHandler {
    static final Identifier EMPTY_LAPIS_SLOT_TEXTURE = Identifier.ofVanilla("item/empty_slot_lapis_lazuli");
    private final Inventory inventory;
    private final ScreenHandlerContext context;
    public final int[] enchantmentPower;
    public final int[] enchantmentId;
    public final int[] enchantmentLevel;
    private List<EnchantmentLevelEntry> possibleEnchantments;

    public CustomEnchantmentScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public CustomEnchantmentScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlerType.CUSTOM_ENCHANTMENT_SCREEN_HANDLER, syncId);
        this.inventory = new SimpleInventory(3) {
            public void markDirty() {
                super.markDirty();
                CustomEnchantmentScreenHandler.this.onContentChanged(this);
            }
        };

        this.enchantmentPower = new int[3];
        this.enchantmentId = new int[]{-1, -1, -1,-1, -1, -1,-1, -1, -1,-1, -1, -1,-1, -1, -1};
        this.enchantmentLevel = new int[]{-1, -1, -1,-1, -1, -1,-1, -1, -1,-1, -1, -1,-1, -1, -1};
        this.context = context;

        this.addSlot(new Slot(this.inventory, 0, 11, 91) {
            public int getMaxItemCount() {
                return 1;
            }
        });

        this.addSlot(new Slot(this.inventory, 1, 31, 91) {
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.LAPIS_LAZULI);
            }

            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, CustomEnchantmentScreenHandler.EMPTY_LAPIS_SLOT_TEXTURE);
            }
        });
        this.addSlot(new Slot(this.inventory, 2, 20, 111) { });

        int i;
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 11 + j * 18, 140 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 11 + i * 18, 198));
        }

        this.addProperty(Property.create(this.enchantmentPower, 0));
        this.addProperty(Property.create(this.enchantmentPower, 1));
        this.addProperty(Property.create(this.enchantmentPower, 2));
        this.addProperty(Property.create(this.enchantmentId, 0));
        this.addProperty(Property.create(this.enchantmentId, 1));
        this.addProperty(Property.create(this.enchantmentId, 2));
        this.addProperty(Property.create(this.enchantmentId, 3));
        this.addProperty(Property.create(this.enchantmentId, 4));
        this.addProperty(Property.create(this.enchantmentId, 5));
        this.addProperty(Property.create(this.enchantmentId, 6));
        this.addProperty(Property.create(this.enchantmentId, 7));
        this.addProperty(Property.create(this.enchantmentId, 8));
        this.addProperty(Property.create(this.enchantmentId, 9));
        this.addProperty(Property.create(this.enchantmentId, 10));
        this.addProperty(Property.create(this.enchantmentId, 11));
        this.addProperty(Property.create(this.enchantmentId, 12));
        this.addProperty(Property.create(this.enchantmentId, 13));
        this.addProperty(Property.create(this.enchantmentId, 14));
        this.addProperty(Property.create(this.enchantmentLevel, 0));
        this.addProperty(Property.create(this.enchantmentLevel, 1));
        this.addProperty(Property.create(this.enchantmentLevel, 2));
        this.addProperty(Property.create(this.enchantmentLevel, 3));
        this.addProperty(Property.create(this.enchantmentLevel, 4));
        this.addProperty(Property.create(this.enchantmentLevel, 5));
        this.addProperty(Property.create(this.enchantmentLevel, 6));
        this.addProperty(Property.create(this.enchantmentLevel, 7));
        this.addProperty(Property.create(this.enchantmentLevel, 8));
        this.addProperty(Property.create(this.enchantmentLevel, 9));
        this.addProperty(Property.create(this.enchantmentLevel, 10));
        this.addProperty(Property.create(this.enchantmentLevel, 11));
        this.addProperty(Property.create(this.enchantmentLevel, 12));
        this.addProperty(Property.create(this.enchantmentLevel, 13));
        this.addProperty(Property.create(this.enchantmentLevel, 14));
    }

    public void onContentChanged(Inventory inventory) {
        if (inventory == this.inventory) {
            ItemStack itemStack = inventory.getStack(0);
            if (!itemStack.isEmpty() && itemStack.getItem().isEnchantable(itemStack)) {
                this.context.run((world, pos) -> {
                    IndexedIterable<RegistryEntry<Enchantment>> indexedIterable = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getIndexedEntries();
                    int i = 0;
                    Iterator var6 = EnchantingTableBlock.POWER_PROVIDER_OFFSETS.iterator();

                    while(var6.hasNext()) {
                        BlockPos blockPos = (BlockPos)var6.next();
                        if (EnchantingTableBlock.canAccessPowerProvider(world, pos, blockPos)) {
                            ++i;
                        }
                    }

                    for(int k =0; k<15; k++){
                        this.enchantmentId[k] = -1;
                        this.enchantmentLevel[k] = -1;
                    }

                    possibleEnchantments = getListOfApplicableEnchantments(world.getRegistryManager(), itemStack, i);
                    int increment = 0;
                    if( possibleEnchantments != null && !possibleEnchantments.isEmpty()){
                        for(EnchantmentLevelEntry enchant : possibleEnchantments){
                            this.enchantmentId[increment] = indexedIterable.getRawId(enchant.enchantment);
                            this.enchantmentLevel[increment] = enchant.level;
                            increment++;
                        }
                    }

                    this.sendContentUpdates();
                });
            } else {
                for(int i = 0; i < 15; ++i) {
                    //this.enchantmentPower[i] = 0;
                    this.enchantmentId[i] = -1;
                    this.enchantmentLevel[i] = -1;
                }
            }
        }

    }

    public boolean onButtonClick(PlayerEntity player, int buttonId) {
        //TheFoxDenCollection.LOGGER.info("Button clicked : "+id);

        int id = (int)Math.floor(buttonId/10);
        int level = buttonId-(10*id);

        if(enchantmentId[id] > -1){
            ItemStack itemToEnchant = this.inventory.getStack(0);
            ItemStack lapisStack = this.inventory.getStack(1);
            ItemStack enchantMaterialStack = this.inventory.getStack(2);


            Optional<RegistryEntry.Reference<Enchantment>> enchant =  player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(this.enchantmentId[id]);
            RegistryEntry<Enchantment> enchantEntry1 = player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(enchant.get().value());
            int displayedEnchantLevel = level + 1;
            int enchantLevelCost = ModEnchantmentHelper.getEnchantmentLevelCost(enchant.get().value(),displayedEnchantLevel,itemToEnchant);
            int enchantLevReq = ModEnchantmentHelper.getEnchantmentLeveRequierment(enchant.get().value(),displayedEnchantLevel);
            int enchantIngredientCost = ModEnchantmentHelper.getEnchantmentIngredientCost(enchant.get().value(),displayedEnchantLevel);
            int lapisCost = (int)Math.floor(enchantLevelCost/2);
            boolean hasEnchantLevel = EnchantmentHelper.getLevel(enchantEntry1,itemToEnchant)>=displayedEnchantLevel;

            if(level > 0 && !ModEnchantmentHelper.itemHasPreviousLevelOfEnchant(itemToEnchant, enchantEntry1, level) && !hasEnchantLevel){
                return false;
            }

            if(hasEnchantLevel)
                return false;

            Item enchantIngredient = ModEnchantmentHelper.getEnchantIngredient(enchantEntry1.getKey().get(), level);

            if ((lapisStack.isEmpty() || lapisStack.getCount() < lapisCost) && !player.isInCreativeMode()) {
                return false;
            } else if (itemToEnchant.isEmpty() || (player.experienceLevel < enchantLevelCost || player.experienceLevel < enchantLevReq) && !player.getAbilities().creativeMode) {
                return false;
            }else if(getSlot(2).getStack().getItem() != enchantIngredient && getSlot(2).getStack().getCount() < enchantIngredientCost && !player.isInCreativeMode()){
                return false;
            }else{
                //can enchant
                this.context.run((world, pos) -> {
                    RegistryEntry<Enchantment> enchantEntry = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(enchant.get().value());

                    //ItemStack itemToEnchantCopy = itemToEnchant;
                    if(!player.isInCreativeMode())
                        player.applyEnchantmentCosts(itemToEnchant, enchantLevelCost);
                    itemToEnchant.addEnchantment(enchantEntry, displayedEnchantLevel);

                    lapisStack.decrementUnlessCreative(lapisCost, player);
                    if (lapisStack.isEmpty()) {
                        this.inventory.setStack(1, ItemStack.EMPTY);
                    }

                    enchantMaterialStack.decrementUnlessCreative(enchantIngredientCost, player);
                    if (enchantMaterialStack.isEmpty()) {
                        this.inventory.setStack(2, ItemStack.EMPTY);
                    }

                    player.incrementStat(Stats.ENCHANT_ITEM);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ENCHANTED_ITEM.trigger((ServerPlayerEntity)player, itemToEnchant, enchantLevelCost);
                    }

                    this.inventory.markDirty();
                    this.onContentChanged(this.inventory);
                    world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);

                });
                return true;
            }

        }else {
            String var10000 = String.valueOf(player.getName());
            Util.error(var10000 + " pressed invalid button id: " + id);
            return false;
        }

    }

    private List<EnchantmentLevelEntry> getListOfApplicableEnchantments(DynamicRegistryManager registryManager, ItemStack itemToEnchant, int numberOfBookshelf){
        Optional<RegistryEntryList.Named<Enchantment>> enchantingTableList = registryManager.get(RegistryKeys.ENCHANTMENT).getEntryList(EnchantmentTags.IN_ENCHANTING_TABLE);
        Optional<RegistryEntryList.Named<Enchantment>> treasureList = registryManager.get(RegistryKeys.ENCHANTMENT).getEntryList(ModEnchantmentTags.BENEFICIAL_TREASURE);
        if (enchantingTableList.isEmpty()) {
            return List.of();
        }else{
            RegistryEntryList.Named enchantingTableEnchants = enchantingTableList.get();
            List<EnchantmentLevelEntry> list = ModEnchantmentHelper.getPossibleEntries(numberOfBookshelf, itemToEnchant, enchantingTableEnchants.stream());

            if( !treasureList.isEmpty())
                list.addAll(ModEnchantmentHelper.getPossibleEntries(numberOfBookshelf, itemToEnchant, ((RegistryEntryList.Named)treasureList.get()).stream()));

            if(itemToEnchant.isIn(ItemTags.AXES))
                list.addAll(ModEnchantmentHelper.addSwordEnchantToAxes(numberOfBookshelf, enchantingTableEnchants.stream()));

            return list;
        }
    }

    public int getLapisCount() {
        ItemStack itemStack = this.inventory.getStack(1);
        return itemStack.isEmpty() ? 0 : itemStack.getCount();
    }

    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> {
            this.dropInventory(player, this.inventory);
        });
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, Blocks.ENCHANTING_TABLE);
    }

    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = (Slot)this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot == 0) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot == 1) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemStack2.isOf(Items.LAPIS_LAZULI)) {
                if (!this.insertItem(itemStack2, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (((Slot)this.slots.get(0)).hasStack() || !((Slot)this.slots.get(0)).canInsert(itemStack2)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemStack3 = itemStack2.copyWithCount(1);
                itemStack2.decrement(1);
                ((Slot)this.slots.get(0)).setStack(itemStack3);
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    public int totalEnchantForItem(){
        int numberOfEnchant = 0;
        for(int i =0; i<15;i++){
            if(enchantmentId[i]>-1)
                numberOfEnchant++;
        }
        return numberOfEnchant;
    }

    public List<EnchantmentLevelEntry> getPossibleEnchantments(){
        return possibleEnchantments;
    }

    private void autofill(ItemStack stack) {
        for (int i = 3; i < 39; ++i) {
            ItemStack itemStack =  slots.get(i).getStack();
            if (itemStack.isEmpty() || !ItemStack.areItemsAndComponentsEqual(itemStack, stack)) continue;
            int j = itemStack.getMaxCount();
            //int k = Math.min(j - itemStack2.getCount(), itemStack.getCount());
            ItemStack itemStack3 = itemStack.copyWithCount(itemStack.getCount());
            //itemStack.decrement(k);
            this.inventory.setStack(2, itemStack3);
            slots.remove(i);
            break;
            //if (itemStack3.getCount() >= j) break;
        }
    }
}
