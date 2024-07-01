package cutefox.foxden.screen;

import com.mojang.datafixers.util.Pair;
import cutefox.foxden.TheFoxDenCollection;
import cutefox.foxden.block.CustomEnchantingTable;
import cutefox.foxden.registery.ModBlocks;
import cutefox.foxden.registery.ModScreenHandlerType;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
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

public class CustomEnchantmentScreenHandler_backup extends ScreenHandler {
    static final Identifier EMPTY_LAPIS_SLOT_TEXTURE = Identifier.ofVanilla("item/empty_slot_lapis_lazuli");
    private final Inventory inventory;
    private final ScreenHandlerContext context;
    public final int[] enchantmentPower;
    public final int[] enchantmentId;
    public final int[] enchantmentLevel;
    public List<EnchantmentLevelEntry> possibleEnchantments;

    public CustomEnchantmentScreenHandler_backup(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public CustomEnchantmentScreenHandler_backup(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlerType.CUSTOM_ENCHANTMENT_SCREEN_HANDLER, syncId);
        this.inventory = new SimpleInventory(3) {
            public void markDirty() {
                super.markDirty();
                CustomEnchantmentScreenHandler_backup.this.onContentChanged(this);
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
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, CustomEnchantmentScreenHandler_backup.EMPTY_LAPIS_SLOT_TEXTURE);
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
            if (!itemStack.isEmpty() && itemStack.isEnchantable()) {
                this.context.run((world, pos) -> {
                    IndexedIterable<RegistryEntry<Enchantment>> indexedIterable = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getIndexedEntries();
                    int i = 0;
                    Iterator var6 = CustomEnchantingTable.POWER_PROVIDER_OFFSETS.iterator();

                    while(var6.hasNext()) {
                        BlockPos blockPos = (BlockPos)var6.next();
                        if (CustomEnchantingTable.canAccessPowerProvider(world, pos, blockPos)) {
                            ++i;
                        }
                    }

                    for(int k =0; k<15; k++){
                        enchantmentId[i] = -1;
                        enchantmentLevel[i] = -1;
                    }

                    possibleEnchantments = getListOfApplicableEnchantments(world.getRegistryManager(), itemStack, i+15);
                    int increment = 0;
                    TheFoxDenCollection.LOGGER.info("possibleEnchantments size : "+possibleEnchantments.size());
                    if( possibleEnchantments != null && !possibleEnchantments.isEmpty()){
                        for(EnchantmentLevelEntry enchant : possibleEnchantments){
                            TheFoxDenCollection.LOGGER.info("Enchant : "+enchant.enchantment.value() + "With max level : "+enchant.level);
                            enchantmentId[increment] = indexedIterable.getRawId(enchant.enchantment);
                            enchantmentLevel[increment] = enchant.level;
                            increment++;
                        }
                    }

                    /*this.random.setSeed(this.seed.get());

                    int j;
                    for(j = 0; j < 3; ++j) {
                        this.enchantmentPower[j] = EnchantmentHelper.calculateRequiredExperienceLevel(this.random, j, i, itemStack);
                        this.enchantmentId[j] = -1;
                        this.enchantmentLevel[j] = -1;
                        if (this.enchantmentPower[j] < j + 1) {
                            this.enchantmentPower[j] = 0;
                        }
                    }

                    for(j = 0; j < 3; ++j) {
                        if (this.enchantmentPower[j] > 0) {
                            List<EnchantmentLevelEntry> list = this.generateEnchantments(world.getRegistryManager(), itemStack, j, this.enchantmentPower[j]);
                            if (list != null && !list.isEmpty()) {
                                EnchantmentLevelEntry enchantmentLevelEntry = (EnchantmentLevelEntry)list.get(this.random.nextInt(list.size()));
                                this.enchantmentId[j] = indexedIterable.getRawId(enchantmentLevelEntry.enchantment);
                                this.enchantmentLevel[j] = enchantmentLevelEntry.level;
                            }
                        }
                    }*/

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

    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id >= 0 && id < this.enchantmentPower.length) {
            ItemStack itemStack = this.inventory.getStack(0);
            ItemStack itemStack2 = this.inventory.getStack(1);
            int i = id + 1;
            if ((itemStack2.isEmpty() || itemStack2.getCount() < i) && !player.isInCreativeMode()) {
                return false;
            } else if (this.enchantmentPower[id] <= 0 || itemStack.isEmpty() || (player.experienceLevel < i || player.experienceLevel < this.enchantmentPower[id]) && !player.getAbilities().creativeMode) {
                return false;
            } else {
                this.context.run((world, pos) -> {
                    ItemStack itemStack3 = itemStack;
                    List<EnchantmentLevelEntry> list = this.generateEnchantments(world.getRegistryManager(), itemStack3, id, this.enchantmentPower[id]);
                    if (!list.isEmpty()) {
                        player.applyEnchantmentCosts(itemStack3, i);
                        if (itemStack3.isOf(Items.BOOK)) {
                            itemStack3 = itemStack.withItem(Items.ENCHANTED_BOOK);
                            this.inventory.setStack(0, itemStack3);
                        }

                        Iterator var10 = list.iterator();

                        while(var10.hasNext()) {
                            EnchantmentLevelEntry enchantmentLevelEntry = (EnchantmentLevelEntry)var10.next();
                            itemStack3.addEnchantment(enchantmentLevelEntry.enchantment, enchantmentLevelEntry.level);
                        }

                        itemStack2.decrementUnlessCreative(i, player);
                        if (itemStack2.isEmpty()) {
                            this.inventory.setStack(1, ItemStack.EMPTY);
                        }

                        player.incrementStat(Stats.ENCHANT_ITEM);
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ENCHANTED_ITEM.trigger((ServerPlayerEntity)player, itemStack3, i);
                        }

                        this.inventory.markDirty();
                        //this.seed.set(player.getEnchantmentTableSeed());
                        this.onContentChanged(this.inventory);
                        world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
                    }

                });
                return true;
            }
        } else {
            String var10000 = String.valueOf(player.getName());
            Util.error(var10000 + " pressed invalid button id: " + id);
            return false;
        }
    }

    private List<EnchantmentLevelEntry> generateEnchantments(DynamicRegistryManager registryManager, ItemStack stack, int slot, int level) {
        /*this.random.setSeed((long)(this.seed.get() + slot));
        Optional<RegistryEntryList.Named<Enchantment>> optional = registryManager.get(RegistryKeys.ENCHANTMENT).getEntryList(EnchantmentTags.IN_ENCHANTING_TABLE);
        if (optional.isEmpty()) {
            return List.of();
        } else {
            List<EnchantmentLevelEntry> list = EnchantmentHelper.generateEnchantments(this.random, stack, level, ((RegistryEntryList.Named)optional.get()).stream());
            if (stack.isOf(Items.BOOK) && list.size() > 1) {
                list.remove(this.random.nextInt(list.size()));
            }

            return list;
        }*/
        return null;
    }

    private List<EnchantmentLevelEntry> getListOfApplicableEnchantments(DynamicRegistryManager registryManager, ItemStack itemToEnchant, int numberOfBookshelf){
        Optional<RegistryEntryList.Named<Enchantment>> optional = registryManager.get(RegistryKeys.ENCHANTMENT).getEntryList(EnchantmentTags.IN_ENCHANTING_TABLE);
        if (optional.isEmpty()) {
            return List.of();
        }else{
            List<EnchantmentLevelEntry> list = EnchantmentHelper.getPossibleEntries(numberOfBookshelf+2, itemToEnchant, ((RegistryEntryList.Named)optional.get()).stream());

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
        return canUse(this.context, player, ModBlocks.CUSTOM_ENCHANTING_TABLE);
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
}
