package cutefox.foxden.block.entity;

import cutefox.foxden.Utils.Utils;
import cutefox.foxden.registery.ModBlockEntityType;
import java.util.Iterator;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import org.jetbrains.annotations.Nullable;

public class StorageBlockEntity extends BlockEntity implements Inventory {
   private int size;
   private DefaultedList<ItemStack> inventory;

   public StorageBlockEntity(BlockPos pos, BlockState state) {
      super(ModBlockEntityType.STORAGE_ENTITY, pos, state);
      this.size = 0;
   }

   public StorageBlockEntity(BlockPos pos, BlockState state, int size) {
      super(ModBlockEntityType.STORAGE_ENTITY, pos, state);
      this.size = size;
      this.inventory = DefaultedList.ofSize(size, ItemStack.EMPTY);
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.inventory.isEmpty();
   }

   public ItemStack getStack(int slot) {
      return (ItemStack)this.inventory.get(slot);
   }

   public ItemStack removeStack(int slot, int amount) {
      if (slot >= 0 && slot < this.inventory.size()) {
         ItemStack stack = (ItemStack)this.inventory.set(slot, ItemStack.EMPTY);
         this.markDirty();
         return stack;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public void markDirty() {
      World var2 = this.getWorld();
      if (var2 instanceof ServerWorld) {
         ServerWorld serverLevel = (ServerWorld)var2;
         if (!this.world.isClient()) {
            Packet<ClientPlayPacketListener> updatePacket = this.toUpdatePacket();
            Iterator var3 = Utils.tracking(serverLevel, this.getPos()).iterator();

            while(var3.hasNext()) {
               ServerPlayerEntity player = (ServerPlayerEntity)var3.next();
               player.networkHandler.send(updatePacket, null);
            }
         }
      }

      super.markDirty();
   }

   public ItemStack removeStack(int slot) {
      if (slot >= 0 && slot < this.inventory.size()) {
         ItemStack stack = (ItemStack)this.inventory.set(slot, ItemStack.EMPTY);
         this.markDirty();
         return stack;
      } else {
         return ItemStack.EMPTY;
      }
   }

   protected void readNbt(NbtCompound nbt, WrapperLookup registryLookup) {
      super.readNbt(nbt, registryLookup);
      this.size = nbt.getInt("size");
      this.inventory = DefaultedList.ofSize(this.size, ItemStack.EMPTY);
      Inventories.readNbt(nbt, this.inventory, registryLookup);
   }

   protected void writeNbt(NbtCompound nbt, WrapperLookup registryLookup) {
      nbt.putInt("size", this.size);
      Inventories.writeNbt(nbt, this.inventory, registryLookup);
      super.writeNbt(nbt, registryLookup);
   }



   @Nullable
   public Packet<ClientPlayPacketListener> toUpdatePacket() {
      return BlockEntityUpdateS2CPacket.create(this);
   }

   public NbtCompound toInitialChunkDataNbt(WrapperLookup registryLookup) {
      return this.createComponentlessNbt(registryLookup);
   }

   public DefaultedList<ItemStack> getInventory() {
      return this.inventory;
   }

   public void setInventory(DefaultedList<ItemStack> inventory) {
      for(int i = 0; i < inventory.size(); ++i) {
         this.inventory.set(i, (ItemStack)inventory.get(i));
      }

   }

   public void setStack(int slot, ItemStack stack) {
      if (slot >= 0 && slot < this.inventory.size()) {
         this.inventory.set(slot, stack);
         this.markDirty();
      }

   }

   public boolean canPlayerUse(PlayerEntity player) {
      return true;
   }

   public void clear() {
      this.getInventory().clear();
   }
}
