package cutefox.foxden.block.storage;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.block.entity.StorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CakeStandBlock extends StorageBlock {
   public static final MapCodec<CakeStandBlock> CODEC = createCodec(CakeStandBlock::new);
   public static VoxelShape SHAPE = makeShape();

   public static VoxelShape makeShape() {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125D, 0.5000625D, 0.125D, 0.125D, 1.0000625D, 0.875D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125D, 0.5000625D, 0.125D, 0.875D, 1.0000625D, 0.125D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125D, 0.5000625D, 0.875D, 0.875D, 1.0000625D, 0.875D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125D, 1.0000625D, 0.125D, 0.875D, 1.0000625D, 0.875D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875D, 0.5000625D, 0.125D, 0.875D, 1.0000625D, 0.875D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375D, 1.0D, 0.4375D, 0.5625D, 1.0625D, 0.5625D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375D, 0.125D, 0.4375D, 0.5625D, 0.4375D, 0.5625D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25D, 0.375D, 0.25D, 0.75D, 0.4375D, 0.75D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125D, 0.4375D, 0.125D, 0.875D, 0.5D, 0.875D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375D, 0.0625D, 0.375D, 0.625D, 0.1875D, 0.625D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875D, 0.4375D, 0.125D, 0.9375D, 0.5D, 0.875D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625D, 0.4375D, 0.125D, 0.125D, 0.5D, 0.875D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125D, 0.4375D, 0.0625D, 0.875D, 0.5D, 0.125D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125D, 0.4375D, 0.875D, 0.875D, 0.5D, 0.9375D));
      shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.0625D, 0.6875D));
      return shape;
   }

   public CakeStandBlock(Settings settings) {
      super(settings);
   }

   protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
      return CODEC;
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   public int findFirstEmpty(DefaultedList<ItemStack> inv) {
      for(int i = 0; i < this.size(); ++i) {
         ItemStack stack = (ItemStack)inv.get(i);
         if (stack.isEmpty()) {
            return i;
         }
      }

      return Integer.MIN_VALUE;
   }

   public int findFirstFull(DefaultedList<ItemStack> inv) {
      for(int i = 0; i < this.size(); ++i) {
         ItemStack stack = (ItemStack)inv.get(i);
         if (!stack.isEmpty()) {
            return i;
         }
      }

      return Integer.MIN_VALUE;
   }

   protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
      Hand hand = player.getActiveHand();
      BlockEntity blockEntity = world.getBlockEntity(pos);
      if (blockEntity instanceof StorageBlockEntity) {
         StorageBlockEntity shelfBlockEntity = (StorageBlockEntity)blockEntity;
         List<Item> items = new LinkedList();
         shelfBlockEntity.getInventory().forEach((stackx) -> {
            if (!stackx.isEmpty()) {
               items.add(stackx.getItem());
            }

         });
         int i;
         if (player.isSneaking()) {
            boolean cCake = false;
            Iterator var11 = items.iterator();

            while(var11.hasNext()) {
               Item item = (Item)var11.next();
               if (item instanceof BlockItem) {
                  cCake = true;
                  break;
               }
            }

            if (cCake) {
               this.remove(world, pos, player, shelfBlockEntity, 0);
               return ActionResult.success(world.isClient);
            }

            i = this.findFirstFull(shelfBlockEntity.getInventory());
            if (i != Integer.MIN_VALUE) {
               this.remove(world, pos, player, shelfBlockEntity, i);
               return ActionResult.success(world.isClient());
            }
         } else {
            ItemStack stack = player.getStackInHand(hand);
            if (!stack.isEmpty() && this.canInsertStack(stack)) {
               if (stack.getItem() instanceof BlockItem) {
                  if (items.isEmpty()) {
                     this.add(world, pos, player, shelfBlockEntity, stack, 0);
                     return ActionResult.success(world.isClient());
                  }
               } else if (!(((ItemStack)shelfBlockEntity.getInventory().get(0)).getItem() instanceof BlockItem)) {
                  i = this.findFirstEmpty(shelfBlockEntity.getInventory());
                  if (i != Integer.MIN_VALUE) {
                     this.add(world, pos, player, shelfBlockEntity, stack, i);
                     return ActionResult.success(world.isClient());
                  }
               }
            }
         }
      }

      return ActionResult.PASS;
   }

   public int size() {
      return 3;
   }

   public Identifier type() {
      return Utils.id("cake_stand");
   }

   public Direction[] unAllowedDirections() {
      return new Direction[0];
   }

   public boolean canInsertStack(ItemStack stack) {
      return stack.contains(DataComponentTypes.FOOD) || stack.getItem() instanceof BlockItem;
   }

   public int getSection(Float var1, Float var2) {
      return 0;
   }

   public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType options) {
      tooltip.add(Text.translatable("tooltip.fox_den.canbeplaced").formatted(new Formatting[]{Formatting.ITALIC, Formatting.GRAY}));
   }
}
