package cutefox.foxden.block;

import cutefox.foxden.Utils.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.potion.Potions;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.state.property.Property;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.WorldView;
import net.minecraft.world.RegistryWorldView;
import net.minecraft.util.math.random.Random;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.block.AbstractBlock.Settings;
import org.jetbrains.annotations.Nullable;

public class SinkBlock extends Block {
   public static final BooleanProperty FILLED = BooleanProperty.of("filled");
   public static final EnumProperty<DoubleBlockHalf> HALF = EnumProperty.of("half", DoubleBlockHalf.class);
   public static final DirectionProperty FACING;
   public static final Map<Direction, VoxelShape> TOP_SHAPES;
   public static final Map<Direction, VoxelShape> BOTTOM_SHAPES;

   public SinkBlock(Settings settings) {
      super(settings);
      this.setDefaultState((BlockState)(((this.stateManager.getDefaultState()).with(FILLED, false)).with(HALF, DoubleBlockHalf.LOWER)).with(FACING, Direction.NORTH));
   }

   private static VoxelShape makeTopShape() {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.375D, 0.0D, 0.75D, 0.625D, 0.0625D, 1.0D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125D, 0.125D, 0.75D, 0.375D, 0.3125D, 0.9375D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4375D, 0.3125D, 0.5D, 0.5625D, 0.5D, 0.625D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.375D, 0.25D, 0.4375D, 0.625D, 0.3125D, 0.6875D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4375D, 0.0625D, 0.8125D, 0.5625D, 0.375D, 0.9375D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4375D, 0.375D, 0.625D, 0.5625D, 0.5D, 0.9375D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.375D, 0.1875D, 0.8125D, 0.4375D, 0.25D, 0.875D), BooleanBiFunction.OR);
      return shape;
   }

   private static VoxelShape makeBottomShape() {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0D, 0.0D, 0.125D, 1.0D, 0.75D, 1.0D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0D, 0.75D, 0.1875D, 0.1875D, 1.0D, 0.75D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0D, 0.75D, 0.75D, 1.0D, 1.0D, 1.0D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 0.1875D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125D, 0.75D, 0.1875D, 1.0D, 1.0D, 0.75D), BooleanBiFunction.OR);
      return shape;
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      super.appendProperties(builder);
      builder.add(new Property[]{FILLED});
      builder.add(new Property[]{HALF});
      builder.add(new Property[]{FACING});
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      Direction facing = (Direction)state.get(FACING);
      return state.get(HALF) == DoubleBlockHalf.UPPER ? (VoxelShape)TOP_SHAPES.get(facing) : (VoxelShape)BOTTOM_SHAPES.get(facing);
   }

   protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      if (state.get(HALF) != DoubleBlockHalf.UPPER) {
         return super.canPlaceAt(state, world, pos);
      } else {
         BlockPos blockPos = pos.down();
         BlockState blockState = world.getBlockState(blockPos);
         return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
      }
   }

   protected ActionResult onUse(BlockState blockState, World level, BlockPos blockPos, PlayerEntity player, BlockHitResult blockHitResult) {
      if (!level.isClient && blockState.get(HALF) == DoubleBlockHalf.LOWER) {
         ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);
         Item item = itemStack.getItem();
         if (itemStack.isEmpty() && !(Boolean)blockState.get(FILLED)) {
            level.setBlockState(blockPos, (BlockState)blockState.with(FILLED, true), 3);
            level.playSound((PlayerEntity)null, (double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
         } else if ((item == Items.WATER_BUCKET || item == Items.GLASS_BOTTLE) && !(Boolean)blockState.get(FILLED)) {
            level.setBlockState(blockPos, (BlockState)blockState.with(FILLED, true), 3);
            level.playSound((PlayerEntity)null, (double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
               if (item == Items.WATER_BUCKET) {
                  itemStack.decrement(1);
                  player.giveItemStack(new ItemStack(Items.BUCKET));
               } else {
                  itemStack.decrement(1);
                  player.giveItemStack(new ItemStack(Items.GLASS_BOTTLE));
               }
            }

            return ActionResult.SUCCESS;
         } else if ((item == Items.BUCKET || item == Items.GLASS_BOTTLE) && (Boolean)blockState.get(FILLED)) {
            level.setBlockState(blockPos, (BlockState)blockState.with(FILLED, false), 3);
            level.playSound((PlayerEntity)null, (double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
               if (item == Items.BUCKET) {
                  itemStack.decrement(1);
                  player.giveItemStack(new ItemStack(Items.WATER_BUCKET));
               } else {
                  itemStack.decrement(1);
                  player.giveItemStack(PotionContentsComponent.createStack(Items.POTION, Potions.WATER));
               }
            }

            return ActionResult.SUCCESS;
         } else {
            return ActionResult.PASS;
         }
      } else {
         return ActionResult.SUCCESS;
      }
   }

   @Nullable
   public BlockState getPlacementState(ItemPlacementContext context) {
      BlockPos blockPos = context.getBlockPos();
      return context.getWorld().getBlockState(blockPos.up()).canReplace(context) ? (BlockState)((BlockState)this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER)).with(FACING, context.getHorizontalPlayerFacing().getOpposite()) : null;
   }

   public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
      world.setBlockState(pos.up(), (BlockState)state.with(HALF, DoubleBlockHalf.UPPER), 3);
   }

   private static BlockState copyWaterloggedFrom(RegistryWorldView levelReader, BlockPos blockPos, BlockState blockState) {
      return blockState.contains(Properties.WATERLOGGED) ? (BlockState)blockState.with(Properties.WATERLOGGED, levelReader.isWater(blockPos)) : blockState;
   }

   protected void prepare(BlockState blockState, WorldAccess levelAccessor, BlockPos blockPos, int flags, int maxUpdateDepth) {
      BlockPos blockPos2 = blockPos.up();
   }

   public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      DoubleBlockHalf half = (DoubleBlockHalf)state.get(HALF);
      BlockPos blockPos = half == DoubleBlockHalf.LOWER ? pos.up() : pos.down();
      BlockState blockState = world.getBlockState(blockPos);
      if (blockState.getBlock() == this && blockState.get(HALF) != half) {
         world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
         world.syncWorldEvent(player, 2001, blockPos, Registries.BLOCK.getRawId(state.getBlock()));
         if (!world.isClient && !player.isCreative()) {
            Block.dropStacks(state, world, blockPos, (BlockEntity)null, player, player.getMainHandStack());
            Block.dropStacks(blockState, world, blockPos, (BlockEntity)null, player, player.getMainHandStack());
         }
      }

      super.onBreak(world, pos, state, player);
      return blockState;
   }

   public void afterBreak(World level, PlayerEntity player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack tool) {
      super.afterBreak(level, player, blockPos, Blocks.AIR.getDefaultState(), blockEntity, tool);
      BlockPos blockPos2;
      BlockState blockState2;
      if (blockState.get(HALF) == DoubleBlockHalf.LOWER) {
         blockPos2 = blockPos.up();
         blockState2 = level.getBlockState(blockPos2);
         if (blockState2.isOf(this) && blockState2.get(HALF) == DoubleBlockHalf.UPPER) {
            level.removeBlock(blockPos2, true);
         }
      } else {
         blockPos2 = blockPos.down();
         blockState2 = level.getBlockState(blockPos2);
         if (blockState2.isOf(this) && blockState2.get(HALF) == DoubleBlockHalf.LOWER) {
            level.removeBlock(blockPos2, true);
         }
      }

   }

   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      float chance = random.nextFloat();
      if (chance < 0.1F) {
         spawnDripParticle(world, pos, state);
      }

   }

   public static void spawnDripParticle(World level, BlockPos blockPos, BlockState blockState) {
      Vec3d vec3 = blockState.getModelOffset(level, blockPos);
      double d = 0.0625D;
      double e = (double)blockPos.getX() + 0.5D + vec3.x;
      double f = (double)((float)((double)blockPos.getY() + 0.9D) - 0.6875F) - d;
      double g = (double)blockPos.getZ() + 0.5D + vec3.z;
      ParticleEffect particleOptions = ParticleTypes.DRIPPING_WATER;
      level.addParticle(particleOptions, e, f, g, 0.0D, 0.0D, 0.0D);
   }

   static {
      FACING = Properties.HORIZONTAL_FACING;
      TOP_SHAPES = new HashMap();
      BOTTOM_SHAPES = new HashMap();
      Supplier<VoxelShape> topShapeSupplier = SinkBlock::makeTopShape;
      Supplier<VoxelShape> bottomShapeSupplier = SinkBlock::makeBottomShape;
      Iterator var2 = Type.HORIZONTAL.iterator();

      while(var2.hasNext()) {
         Direction direction = (Direction)var2.next();
         TOP_SHAPES.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)topShapeSupplier.get()));
         BOTTOM_SHAPES.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)bottomShapeSupplier.get()));
      }

   }
}
