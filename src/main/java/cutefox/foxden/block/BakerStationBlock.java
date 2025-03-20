package cutefox.foxden.block;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.block.cakes.BlankCakeBlock;
import cutefox.foxden.registery.ModBlocks;
import cutefox.foxden.registery.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BakerStationBlock extends HorizontalFacingBlock {
   public static final MapCodec<BakerStationBlock> CODEC = createCodec(BakerStationBlock::new);


   public BakerStationBlock(Settings settings) {
      super(settings);
      this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
   }

   protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
      return CODEC;
   }

   protected BlockState rotate(BlockState state, BlockRotation rotation) {
      return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
   }

   protected BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
   }

   protected ItemActionResult onUseWithItem(ItemStack itemStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (!world.isClient && hand == Hand.MAIN_HAND) {
         BlockPos blockAbove;
         if (!itemStack.isOf(ModItems.CAKE_DOUGH)) {
            if (itemStack.isOf(Items.COOKIE)) {
               blockAbove = pos.up();
               if (world.isAir(blockAbove)) {
                  world.setBlockState(blockAbove, (BlockState)((BlockState)ModBlocks.BLANK_CAKE.getDefaultState().with(BlankCakeBlock.CAKE, false)).with(BlankCakeBlock.COOKIE, true), 3);
                  world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_CAKE_ADD_CANDLE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                  world.syncWorldEvent(2001, blockAbove, Registries.BLOCK.getRawId(ModBlocks.BLANK_CAKE));
                  if (!player.isCreative()) {
                     itemStack.decrement(1);
                  }
               }

               return ItemActionResult.SUCCESS;
            }

            player.sendMessage(Text.translatable("tooltip.fox_den.cakedoughonstation"), true);
            return ItemActionResult.CONSUME;
         }

         blockAbove = pos.up();
         if (world.isAir(blockAbove)) {
            world.setBlockState(blockAbove, ModBlocks.BLANK_CAKE.getDefaultState(), 3);
            world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_CAKE_ADD_CANDLE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.syncWorldEvent(2001, blockAbove, Registries.BLOCK.getRawId(ModBlocks.BLANK_CAKE));
            if (!player.isCreative()) {
               itemStack.decrement(1);
            }

            return ItemActionResult.SUCCESS;
         }
      }

      return ItemActionResult.CONSUME;
   }
}
