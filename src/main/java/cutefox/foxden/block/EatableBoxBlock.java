package cutefox.foxden.block;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.Utils.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.Util;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.block.AbstractBlock.Settings;

public class EatableBoxBlock extends HorizontalFacingBlock {
   public static final MapCodec<EatableBoxBlock> CODEC = createCodec(EatableBoxBlock::new);
   public static final IntProperty CUTS = IntProperty.of("cuts", 0, 5);
   private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
      VoxelShape shape = VoxelShapes.empty();
      VoxelShape var10001 = VoxelShapes.cuboid(0.1875D, 0.0D, 0.25D, 0.8125D, 0.125D, 0.74375D);
      BooleanBiFunction var10002 = BooleanBiFunction.OR;
      shape = VoxelShapes.combine(shape, var10001, BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.75D, 0.125D, 0.25D, 0.8125D, 0.1875D, 0.74375D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875D, 0.125D, 0.25D, 0.25D, 0.1875D, 0.74375D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25D, 0.125D, 0.25D, 0.75D, 0.1875D, 0.30625D), BooleanBiFunction.OR);
      shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25D, 0.125D, 0.68125D, 0.75D, 0.1875D, 0.74375D), BooleanBiFunction.OR);
      return shape;
   };
   public static final Map<Direction, VoxelShape> SHAPE = (Map)Util.make(new HashMap(), (map) -> {
      Iterator var1 = Type.HORIZONTAL.stream().toList().iterator();

      while(var1.hasNext()) {
         Direction direction = (Direction)var1.next();
         map.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)voxelShapeSupplier.get()));
      }

   });

   public EatableBoxBlock(Settings settings) {
      super(settings);
      this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(CUTS, 0));
   }

   protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
      return CODEC;
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      super.appendProperties(builder);
      builder.add(new Property[]{FACING});
      builder.add(new Property[]{CUTS});
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return (VoxelShape)SHAPE.get(state.get(FACING));
   }

   protected BlockState rotate(BlockState state, BlockRotation rotation) {
      return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
   }

   protected BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
   }

   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
   }

   protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      return world.getBlockState(pos.down()).isSolid();
   }

   protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
      return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
   }

   protected ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, BlockHitResult hit) {
      ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);
      World playerWorld = player.getWorld();
      if (playerWorld.isClient) {
         if (this.tryEat(playerWorld, blockPos, blockState, player).isAccepted()) {
            return ActionResult.SUCCESS;
         }

         if (itemStack.isEmpty()) {
            return ActionResult.CONSUME;
         }
      }

      return this.tryEat(playerWorld, blockPos, blockState, player);
   }

   private ActionResult tryEat(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_FOX_EAT, SoundCategory.PLAYERS, 0.5F, world.getRandom().nextFloat() * 0.1F + 0.9F);
      player.getHungerManager().eat((new FoodComponent.Builder()).nutrition(1).saturationModifier(0.4F).build());
      int cuts = (Integer)state.get(CUTS);
      world.emitGameEvent(player, GameEvent.EAT, pos);
      if (cuts < 5) {
         world.setBlockState(pos, (BlockState)state.with(CUTS, cuts + 1), 3);
      } else {
         world.removeBlock(pos, false);
         ItemStack bowlStack = new ItemStack(Items.CHEST);
         ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, bowlStack);
         world.spawnEntity(itemEntity);
      }

      return ActionResult.SUCCESS;
   }
}
