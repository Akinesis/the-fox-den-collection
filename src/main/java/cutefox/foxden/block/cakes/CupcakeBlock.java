package cutefox.foxden.block.cakes;

import cutefox.foxden.registery.ModBlocks;
import cutefox.foxden.registery.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;

public class CupcakeBlock extends Block {
   private static final VoxelShape SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0.125D, 0.0D, 0.125D, 0.4375D, 0.375D, 0.4375D), new VoxelShape[]{VoxelShapes.cuboid(0.125D, 0.0D, 0.5625D, 0.4375D, 0.375D, 0.875D), VoxelShapes.cuboid(0.5625D, 0.0D, 0.125D, 0.875D, 0.375D, 0.4375D), VoxelShapes.cuboid(0.5625D, 0.0D, 0.5625D, 0.875D, 0.375D, 0.875D)});

   public CupcakeBlock(Settings settings) {
      super(settings);
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
      super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
      if (!world.isClient && !this.canPlaceAt(state, world, pos)) {
         world.removeBlock(pos, true);
      }

   }

   protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      return !world.isAir(pos.down());
   }

   @NotNull
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
      if (!world.isClient) {
         if (state.isOf(ModBlocks.SWEETBERRY_CUPCAKE_BLOCK)) {
            this.popItem(world, pos, new ItemStack(ModItems.SWEETBERRY_CUPCAKE, 4));
         } else if (state.isOf(ModBlocks.STRAWBERRY_CUPCAKE_BLOCK)) {
            this.popItem(world, pos, new ItemStack(ModItems.STRAWBERRY_CUPCAKE, 4));
         } else if (state.isOf(ModBlocks.APPLE_CUPCAKE_BLOCK)) {
            this.popItem(world, pos, new ItemStack(ModItems.APPLE_CUPCAKE, 4));
         }

         world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
         world.syncWorldEvent(2001, pos, Block.getRawIdFromState(Blocks.CAKE.getDefaultState()));
         world.removeBlock(pos, false);
         return ActionResult.success(false);
      } else {
         return super.onUse(state, world, pos, player, hit);
      }
   }

   private void popItem(World world, BlockPos pos, ItemStack stack) {
      Block.dropStack(world, pos, stack);
   }
}
