package cutefox.foxden.block.cakes;

import cutefox.foxden.registery.ModBlocks;
import cutefox.foxden.registery.ModItemTags;
import cutefox.foxden.registery.ModItems;
import net.minecraft.util.Hand;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.WorldView;
import net.minecraft.registry.Registries;
import net.minecraft.util.ItemActionResult;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.block.AbstractBlock.Settings;

public class BlankCakeBlock extends Block {
   public static final BooleanProperty CAKE = BooleanProperty.of("cake");
   public static final BooleanProperty CUPCAKE = BooleanProperty.of("cupcake");
   public static final BooleanProperty COOKIE = BooleanProperty.of("cookie");
   private static final VoxelShape CAKE_SHAPE = VoxelShapes.cuboid(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D);
   private static final VoxelShape BASE_SHAPE = VoxelShapes.empty();
   private static final VoxelShape CUPCAKE_SHAPE;
   private static final VoxelShape COOKIE_SHAPE;

   public BlankCakeBlock(Settings settings) {
      super(settings);
      this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(CAKE, true)).with(CUPCAKE, false)).with(COOKIE, false));
   }

   protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      if ((Boolean)state.get(CAKE)) {
         return CAKE_SHAPE;
      } else if ((Boolean)state.get(CUPCAKE)) {
         return CUPCAKE_SHAPE;
      } else {
         return (Boolean)state.get(COOKIE) ? COOKIE_SHAPE : VoxelShapes.empty();
      }
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

   protected void appendProperties(Builder<Block, BlockState> builder) {
      super.appendProperties(builder);
      builder.add(new Property[]{CAKE, CUPCAKE, COOKIE});
   }

   protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (!world.isClient) {
         ItemStack itemStack = player.getStackInHand(hand);
         Item item = itemStack.getItem();
         boolean isCake = (Boolean)state.get(CAKE);
         boolean isCupcake = (Boolean)state.get(CUPCAKE);
         boolean isCookie = (Boolean)state.get(COOKIE);
         if (item instanceof BlockItem) {
            Block block = ((BlockItem)item).getBlock();
            boolean matched = false;
            if (isCake) {
               if (block == ModBlocks.STRAWBERRY_JAM) {
                  world.setBlockState(pos, ModBlocks.STRAWBERRY_CAKE.getDefaultState(), 3);
                  world.syncWorldEvent(2001, pos, Registries.BLOCK.getRawId(ModBlocks.STRAWBERRY_CAKE));
                  matched = true;
                  if (!player.getInventory().insertStack(ModBlocks.JAR.asItem().getDefaultStack())) {
                     world.spawnEntity(new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), ModBlocks.JAR.asItem().getDefaultStack()));
                  }
               } else if (block == ModBlocks.CHOCOLATE_JAM) {
                  world.setBlockState(pos, ModBlocks.CHOCOLATE_CAKE.getDefaultState(), 3);
                  world.syncWorldEvent(2001, pos, Registries.BLOCK.getRawId(ModBlocks.CHOCOLATE_CAKE));
                  matched = true;
                  if (!player.getInventory().insertStack(ModBlocks.JAR.asItem().getDefaultStack())) {
                     world.spawnEntity(new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), ModBlocks.JAR.asItem().getDefaultStack()));
                  }
               } else if (block == ModBlocks.SWEETBERRY_JAM) {
                  world.setBlockState(pos, ModBlocks.SWEETBERRY_CAKE.getDefaultState(), 3);
                  world.syncWorldEvent(2001, pos, Registries.BLOCK.getRawId(ModBlocks.SWEETBERRY_CAKE));
                  matched = true;
                  if (!player.getInventory().insertStack(ModBlocks.JAR.asItem().getDefaultStack())) {
                     world.spawnEntity(new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), ModBlocks.JAR.asItem().getDefaultStack()));
                  }
               }
            } else if (isCupcake) {
               if (block == ModBlocks.STRAWBERRY_JAM) {
                  world.setBlockState(pos, ModBlocks.STRAWBERRY_CUPCAKE_BLOCK.getDefaultState(), 3);
                  world.syncWorldEvent(2001, pos, Registries.BLOCK.getRawId(ModBlocks.STRAWBERRY_CUPCAKE_BLOCK));
                  matched = true;
                  if (!player.getInventory().insertStack(ModBlocks.JAR.asItem().getDefaultStack())) {
                     world.spawnEntity(new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), ModBlocks.JAR.asItem().getDefaultStack()));
                  }
               } else if (block == ModBlocks.APPLE_JAM) {
                  world.setBlockState(pos, ModBlocks.APPLE_CUPCAKE_BLOCK.getDefaultState(), 3);
                  world.syncWorldEvent(2001, pos, Registries.BLOCK.getRawId(ModBlocks.APPLE_CUPCAKE_BLOCK));
                  matched = true;
                  if (!player.getInventory().insertStack(ModBlocks.JAR.asItem().getDefaultStack())) {
                     world.spawnEntity(new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), ModBlocks.JAR.asItem().getDefaultStack()));
                  }
               } else if (block == ModBlocks.SWEETBERRY_JAM) {
                  world.setBlockState(pos, ModBlocks.SWEETBERRY_CUPCAKE_BLOCK.getDefaultState(), 3);
                  world.syncWorldEvent(2001, pos, Registries.BLOCK.getRawId(ModBlocks.SWEETBERRY_CUPCAKE_BLOCK));
                  matched = true;
                  if (!player.getInventory().insertStack(ModBlocks.JAR.asItem().getDefaultStack())) {
                     world.spawnEntity(new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), ModBlocks.JAR.asItem().getDefaultStack()));
                  }
               }
            } else if (isCookie) {
               if (block == ModBlocks.STRAWBERRY_JAM) {
                  world.setBlockState(pos, ModBlocks.STRAWBERRY_COOKIE_BLOCK.getDefaultState(), 3);
                  world.syncWorldEvent(2001, pos, Registries.BLOCK.getRawId(ModBlocks.STRAWBERRY_COOKIE_BLOCK));
                  matched = true;
                  if (!player.getInventory().insertStack(ModBlocks.JAR.asItem().getDefaultStack())) {
                     world.spawnEntity(new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), ModBlocks.JAR.asItem().getDefaultStack()));
                  }
               } else if (block == ModBlocks.CHOCOLATE_JAM) {
                  world.setBlockState(pos, ModBlocks.CHOCOLATE_COOKIE_BLOCK.getDefaultState(), 3);
                  world.syncWorldEvent(2001, pos, Registries.BLOCK.getRawId(ModBlocks.CHOCOLATE_COOKIE_BLOCK));
                  matched = true;
                  if (!player.getInventory().insertStack(ModBlocks.JAR.asItem().getDefaultStack())) {
                     world.spawnEntity(new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), ModBlocks.JAR.asItem().getDefaultStack()));
                  }
               } else if (block == ModBlocks.SWEETBERRY_JAM) {
                  world.setBlockState(pos, ModBlocks.SWEETBERRY_COOKIE_BLOCK.getDefaultState(), 3);
                  world.syncWorldEvent(2001, pos, Registries.BLOCK.getRawId(ModBlocks.SWEETBERRY_COOKIE_BLOCK));
                  matched = true;
                  if (!player.getInventory().insertStack(ModBlocks.JAR.asItem().getDefaultStack())) {
                     world.spawnEntity(new ItemEntity(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), ModBlocks.JAR.asItem().getDefaultStack()));
                  }
               }
            }

            if (matched) {
               world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_SLIME_BLOCK_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
               if (!player.isCreative()) {
                  itemStack.decrement(1);
               }

               return ItemActionResult.success(false);
            }
         } else {
            if (isCake && item == ModItems.CHOCOLATE_TRUFFLE) {
               player.getItemCooldownManager().set(item, 20);
               world.setBlockState(pos, ModBlocks.CHOCOLATE_GATEAU.getDefaultState(), 3);
               world.syncWorldEvent(2001, pos, Registries.BLOCK.getRawId(ModBlocks.CHOCOLATE_GATEAU));
               world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_SLIME_BLOCK_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
               if (!player.isCreative()) {
                  itemStack.decrement(1);
               }

               return ItemActionResult.success(false);
            }

            if (isCake && itemStack.isIn(ModItemTags.KNIVES)) {
               world.setBlockState(pos, (BlockState)((BlockState)state.with(CAKE, false)).with(CUPCAKE, true), 3);
               world.syncWorldEvent(2001, pos, Registries.BLOCK.getRawId(state.getBlock()));
               world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
               return ItemActionResult.success(false);
            }

            if (isCupcake && item == ModItems.ROLLING_PIN) {
               world.setBlockState(pos, (BlockState)((BlockState)state.with(CUPCAKE, false)).with(COOKIE, true), 3);
               world.syncWorldEvent(2001, pos, Registries.BLOCK.getRawId(state.getBlock()));
               world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_GENERIC_BIG_FALL, SoundCategory.BLOCKS, 1.0F, 1.0F);
               return ItemActionResult.success(false);
            }
         }
      }

      return ItemActionResult.CONSUME;
   }

   static {
      CUPCAKE_SHAPE = VoxelShapes.union(BASE_SHAPE, new VoxelShape[]{VoxelShapes.cuboid(0.125D, 0.0D, 0.125D, 0.4375D, 0.375D, 0.4375D), VoxelShapes.cuboid(0.125D, 0.0D, 0.5625D, 0.4375D, 0.375D, 0.875D), VoxelShapes.cuboid(0.5625D, 0.0D, 0.125D, 0.875D, 0.375D, 0.4375D), VoxelShapes.cuboid(0.5625D, 0.0D, 0.5625D, 0.875D, 0.375D, 0.875D)});
      COOKIE_SHAPE = VoxelShapes.union(BASE_SHAPE, new VoxelShape[]{VoxelShapes.cuboid(0.125D, 0.0D, 0.125D, 0.4375D, 0.0625D, 0.4375D), VoxelShapes.cuboid(0.125D, 0.0D, 0.5625D, 0.4375D, 0.0625D, 0.875D), VoxelShapes.cuboid(0.5625D, 0.0D, 0.125D, 0.875D, 0.0625D, 0.4375D), VoxelShapes.cuboid(0.5625D, 0.0D, 0.5625D, 0.875D, 0.0625D, 0.875D)});
   }
}
