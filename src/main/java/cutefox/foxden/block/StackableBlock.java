package cutefox.foxden.block;

import cutefox.foxden.Utils.Utils;
import cutefox.foxden.registery.ModFoodComponents;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.WorldView;
import net.minecraft.util.math.random.Random;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.block.AbstractBlock.Settings;
import org.jetbrains.annotations.Nullable;

public class StackableBlock extends Block {
   private static final IntProperty STACK_PROPERTY = IntProperty.of("stack", 1, 8);
   public static final DirectionProperty FACING;
   private final int maxStack;
   private static final VoxelShape SHAPE;

   public StackableBlock(Settings settings, int maxStack) {
      super(settings);
      this.maxStack = maxStack;
      this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(STACK_PROPERTY, 1)).with(FACING, Direction.NORTH));
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
      return SHAPE;
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      super.appendProperties(builder);
      builder.add(new Property[]{STACK_PROPERTY});
      builder.add(new Property[]{FACING});
   }

   protected BlockState rotate(BlockState state, BlockRotation rotation) {
      return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
   }

   @Nullable
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
   }

   protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      VoxelShape shape = world.getBlockState(pos.down()).getCollisionShape(world, pos.down());
      Direction direction = Direction.UP;
      return Block.isFaceFullSquare(shape, direction);
   }

   protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
      if (!state.canPlaceAt(world, pos)) {
         world.scheduleBlockTick(pos, this, 1);
      }

      return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
   }

   protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (!state.canPlaceAt(world, pos)) {
         world.removeBlock(pos, true);
      }

   }

   protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult blockHitResult) {
      ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
      if (player.isSneaking() && stack.isEmpty()) {
         if (!world.isClient) {
            if ((Integer)state.get(STACK_PROPERTY) > 1) {
               world.setBlockState(pos, (BlockState)state.with(STACK_PROPERTY, (Integer)state.get(STACK_PROPERTY) - 1), 3);
               player.getHungerManager().eat(ModFoodComponents.CAKE_SLICE);
               world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 1.0F, 1.0F);
               if (world instanceof ServerWorld) {
                  ServerWorld serverWorld = (ServerWorld)world;

                  for(int count = 0; count < 10; ++count) {
                     double d0 = world.random.nextGaussian() * 0.02D;
                     double d1 = world.random.nextGaussian() * 0.0D;
                     double d2 = world.random.nextGaussian() * 0.02D;
                     serverWorld.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), (double)pos.getX() + 0.5D, (double)pos.getY() + 1.0D, (double)pos.getZ() + 0.5D, 1, d0, d1, d2, 0.1D);
                  }
               }
            } else {
               world.removeBlock(pos, false);
            }

            return ActionResult.success(world.isClient);
         }
      } else if (stack.getItem() == this.asItem()) {
         if ((Integer)state.get(STACK_PROPERTY) < this.maxStack) {
            world.setBlockState(pos, (BlockState)state.with(STACK_PROPERTY, (Integer)state.get(STACK_PROPERTY) + 1), 3);
            if (!player.isCreative()) {
               stack.decrement(1);
            }

            return ActionResult.SUCCESS;
         }
      } else if (stack.isEmpty()) {
         if ((Integer)state.get(STACK_PROPERTY) > 1) {
            world.setBlockState(pos, (BlockState)state.with(STACK_PROPERTY, (Integer)state.get(STACK_PROPERTY) - 1), 3);
         } else if ((Integer)state.get(STACK_PROPERTY) == 1) {
            world.removeBlock(pos, false);
         }

         Direction direction = player.getFacing().getOpposite();
         double xMotion = (double)direction.getOffsetX() * 0.13D;
         double yMotion = 0.35D;
         double zMotion = (double)direction.getOffsetZ() * 0.13D;
         Utils.spawnSlice(world, new ItemStack(this), (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, xMotion, yMotion, zMotion);
         return ActionResult.SUCCESS;
      }

      return super.onUse(state, world, pos, player, blockHitResult);
   }

   static {
      FACING = Properties.HORIZONTAL_FACING;
      SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
   }
}
