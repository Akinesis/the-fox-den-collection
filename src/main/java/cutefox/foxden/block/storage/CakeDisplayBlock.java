package cutefox.foxden.block.storage;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.Utils.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.text.Text;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.state.StateManager.Builder;
import org.jetbrains.annotations.Nullable;

public class CakeDisplayBlock extends StorageBlock {
   public static final MapCodec<CakeDisplayBlock> CODEC = createCodec(CakeDisplayBlock::new);
   public static final EnumProperty<Utils.LineConnectingType> TYPE;
   private static final Supplier<VoxelShape> voxelShapeSupplier;
   public static final Map<Direction, VoxelShape> SHAPE;

   public CakeDisplayBlock(Settings settings) {
      super(settings);
      this.setDefaultState((BlockState)((BlockState)((BlockState)this.getStateManager().getDefaultState()).with(FACING, Direction.NORTH)).with(TYPE, Utils.LineConnectingType.NONE));
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      super.appendProperties(builder);
      builder.add(new Property[]{TYPE});
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return (VoxelShape)SHAPE.get(state.get(FACING));
   }

   protected BlockState rotate(BlockState state, BooleanBiFunction rotation) {
      return (BlockState)state.with(FACING, state.get(FACING));
   }

   protected BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation(state.get(FACING)));
   }

   public int size() {
      return 6;
   }

   public Identifier type() {
      return Utils.id("cake_display");
   }

   public Direction[] unAllowedDirections() {
      return new Direction[]{Direction.DOWN, Direction.UP};
   }

   public boolean canInsertStack(ItemStack stack) {
      return !(stack.getItem() instanceof BlockItem);
   }

   public int getSection(Float x, Float y) {
      int i = y >= 0.5F ? 0 : 1;
      int j = getXSection(x);
      return j + i * 3;
   }

   private static int getXSection(float f) {
      if (f < 0.375F) {
         return 2;
      } else {
         return f < 0.6875F ? 1 : 0;
      }
   }

   protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
      return CODEC;
   }

   @Nullable
   public BlockState getPlacementState(ItemPlacementContext context) {
      Direction facing = context.getHorizontalPlayerFacing().getOpposite();
      BlockState blockState = (BlockState)this.getDefaultState().with(FACING, facing);
      World world = context.getWorld();
      BlockPos clickedPos = context.getBlockPos();
      BlockState var10000;
      switch(facing) {
      case EAST:
         var10000 = (BlockState)blockState.with(TYPE, this.getType(blockState, world.getBlockState(clickedPos.south()), world.getBlockState(clickedPos.north())));
         break;
      case SOUTH:
         var10000 = (BlockState)blockState.with(TYPE, this.getType(blockState, world.getBlockState(clickedPos.west()), world.getBlockState(clickedPos.east())));
         break;
      case WEST:
         var10000 = (BlockState)blockState.with(TYPE, this.getType(blockState, world.getBlockState(clickedPos.north()), world.getBlockState(clickedPos.south())));
         break;
      default:
         var10000 = (BlockState)blockState.with(TYPE, this.getType(blockState, world.getBlockState(clickedPos.east()), world.getBlockState(clickedPos.west())));
      }

      return var10000;
   }

   public Utils.LineConnectingType getType(BlockState state, BlockState left, BlockState right) {
      boolean shape_left_same = left.getBlock() == state.getBlock() && left.get(FACING) == state.get(FACING);
      boolean shape_right_same = right.getBlock() == state.getBlock() && right.get(FACING) == state.get(FACING);
      if (shape_left_same && shape_right_same) {
         return Utils.LineConnectingType.MIDDLE;
      } else if (shape_left_same) {
         return Utils.LineConnectingType.LEFT;
      } else {
         return shape_right_same ? Utils.LineConnectingType.RIGHT : Utils.LineConnectingType.NONE;
      }
   }

   protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
      if (!world.isClient) {
         Direction facing = (Direction)state.get(FACING);
         Utils.LineConnectingType type;
         switch(facing) {
         case EAST:
            type = this.getType(state, world.getBlockState(pos.south()), world.getBlockState(pos.north()));
            break;
         case SOUTH:
            type = this.getType(state, world.getBlockState(pos.west()), world.getBlockState(pos.east()));
            break;
         case WEST:
            type = this.getType(state, world.getBlockState(pos.north()), world.getBlockState(pos.south()));
            break;
         default:
            type = this.getType(state, world.getBlockState(pos.east()), world.getBlockState(pos.west()));
         }

         if (state.get(TYPE) != type) {
            state = (BlockState)state.with(TYPE, type);
         }

         world.setBlockState(pos, state, 3);
      }
   }

   public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType options) {
      tooltip.add(Text.translatable("tooltip.fox_den.canbeplaced").formatted(new Formatting[]{Formatting.ITALIC, Formatting.GRAY}));
   }

   static {
      TYPE = Utils.LINE_CONNECTING_TYPE;
      voxelShapeSupplier = () -> {
         VoxelShape shape = VoxelShapes.empty();
         shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.9375D));
         shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0D, 0.0625D, 0.375D, 1.0D, 0.875D, 0.9375D));
         return shape;
      };
      SHAPE = (Map)Util.make(new HashMap(), (map) -> {
         Iterator var1 = Type.HORIZONTAL.stream().toList().iterator();

         while(var1.hasNext()) {
            Direction direction = (Direction)var1.next();
            map.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)voxelShapeSupplier.get()));
         }

      });
   }
}
