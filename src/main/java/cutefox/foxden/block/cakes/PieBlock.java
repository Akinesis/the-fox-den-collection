package cutefox.foxden.block.cakes;

import com.mojang.serialization.MapCodec;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.registery.ModItemTags;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.BlockRotation;
import net.minecraft.text.Text;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.util.ItemActionResult;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.component.type.FoodComponent.StatusEffectEntry;
import net.minecraft.block.AbstractBlock.Settings;

public class PieBlock extends HorizontalFacingBlock {
   public static final MapCodec<PieBlock> CODEC = createCodec(PieBlock::new);
   public static final IntProperty CUTS = IntProperty.of("cuts", 0, 3);
   private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
      VoxelShape shape = VoxelShapes.empty();
      shape = VoxelShapes.combineAndSimplify(shape, VoxelShapes.cuboid(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), BooleanBiFunction.OR);
      return shape;
   };
   public static final Map<Direction, VoxelShape> SHAPE = (Map)Util.make(new HashMap(), (map) -> {
      Type var10000 = Type.HORIZONTAL;
      Iterator var1 = Type.HORIZONTAL.stream().toList().iterator();

      while(var1.hasNext()) {
         Direction direction = (Direction)var1.next();
         map.put(direction, Utils.rotateShape(Direction.NORTH, direction, (VoxelShape)voxelShapeSupplier.get()));
      }

   });
   public final Item Slice;

   public PieBlock(Settings settings) {
      super(settings);
      this.Slice = Items.COOKIE;
      this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(CUTS, 0));
   }

   public PieBlock(Settings settings, Item slice) {
      super(settings);
      this.Slice = slice;
      this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(CUTS, 0));
   }

   protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
      return CODEC;
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      super.appendProperties(builder);
      builder.add(new Property[]{CUTS});
      builder.add(new Property[]{FACING});
   }

   protected BlockState rotate(BlockState state, BlockRotation rotation) {
      return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
   }

   protected BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
   }

   protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
      return this.getMaxCuts() - (Integer)state.get(CUTS);
   }

   protected boolean hasComparatorOutput(BlockState state) {
      return true;
   }

   public ItemStack getPieSliceItem() {
      return new ItemStack(this.Slice);
   }

   protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (!world.isClient && !player.isSneaking() && (Integer)state.get(CUTS) == 0 && stack.isEmpty()) {
         Direction direction = player.getFacing().getOpposite();
         double xMotion = (double)direction.getOffsetX() * 0.13D;
         double yMotion = 0.35D;
         double zMotion = (double)direction.getOffsetZ() * 0.13D;
         Utils.spawnSlice(world, new ItemStack(this), (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, xMotion, yMotion, zMotion);
         world.removeBlock(pos, false);
         return ItemActionResult.SUCCESS;
      } else if (player.isSneaking() && (stack.isEmpty() || stack.isIn(ModItemTags.KNIVES))) {
         return this.consumeBite(world, pos, state, player);
      } else {
         return !player.isSneaking() && stack.isIn(ModItemTags.KNIVES) ? this.cutSlice(world, pos, state, player) : ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }
   }

   protected ItemActionResult consumeBite(World level, BlockPos pos, BlockState state, PlayerEntity playerIn) {
      if (!playerIn.canConsume(false)) {
         return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      } else {
         ItemStack sliceStack = this.getPieSliceItem();
         FoodComponent sliceFood = (FoodComponent)sliceStack.getOrDefault(DataComponentTypes.FOOD, (Object)null);
         playerIn.getHungerManager().eat(sliceFood);
         Iterator var7 = sliceFood.effects().iterator();

         while(var7.hasNext()) {
            StatusEffectEntry effect = (StatusEffectEntry)var7.next();
            if (!level.isClient && level.random.nextFloat() < effect.probability()) {
               playerIn.addStatusEffect(effect.effect());
            }
         }

         int cuts = (Integer)state.get(CUTS);
         if (cuts < this.getMaxCuts() - 1) {
            level.setBlockState(pos, (BlockState)state.with(CUTS, cuts + 1), 3);
         } else {
            level.breakBlock(pos, false);
         }

         level.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 0.8F, 0.8F);
         return ItemActionResult.SUCCESS;
      }
   }

   protected ItemActionResult cutSlice(World level, BlockPos pos, BlockState state, PlayerEntity player) {
      int cuts = (Integer)state.get(CUTS);
      if (cuts < this.getMaxCuts() - 1) {
         level.setBlockState(pos, (BlockState)state.with(CUTS, cuts + 1), 3);
      } else {
         level.removeBlock(pos, false);
      }

      Direction direction = player.getFacing().getOpposite();
      double xMotion = (double)direction.getOffsetX() * 0.13D;
      double yMotion = 0.35D;
      double zMotion = (double)direction.getOffsetZ() * 0.13D;
      Utils.spawnSlice(level, this.getPieSliceItem(), (double)pos.getX() + 0.5D, (double)pos.getY() + 0.3D, (double)pos.getZ() + 0.5D, xMotion, yMotion, zMotion);
      level.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.PLAYERS, 0.75F, 0.75F);
      return ItemActionResult.SUCCESS;
   }

   public int getMaxCuts() {
      return 4;
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return (VoxelShape)SHAPE.get(state.get(FACING));
   }

   public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType options) {
      tooltip.add(Text.translatable("tooltip.fox_den.canbeplaced").formatted(new Formatting[]{Formatting.ITALIC, Formatting.GRAY}));
      tooltip.add(Text.empty());
      tooltip.add(Text.translatable("tooltip.fox_den.cake_1").formatted(Formatting.WHITE));
      tooltip.add(Text.translatable("tooltip.fox_den.cake_2").formatted(Formatting.WHITE));
      tooltip.add(Text.translatable("tooltip.fox_den.cake_3").formatted(Formatting.WHITE));
   }
}
