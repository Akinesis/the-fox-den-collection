package cutefox.foxden.Utils;

import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Utils {

    public static final EnumProperty<LineConnectingType> LINE_CONNECTING_TYPE = EnumProperty.of("type", LineConnectingType.class);

    public static final List<String> RANDOM_NAMES = List.of(
            "Gaston Mike Willson Henri Salade",
            "Connor",
            "Némésis",
            "Panache",
            "Julie",
            "Rocky",
            "Skye",
            "Chase (acab)",
            "Marshall",
            "Rubble",
            "Zuma",
            "Evrest",
            "Tracker",
            "Cici"
            );

    public static Identifier id(String path) {
        return Identifier.of("fox_den", path);
    }

    public static Identifier blockId(String path) {
        return Identifier.of("fox_den", "block/"+path);
    }

    public static Identifier id() {
        return Identifier.of("fox_den");
    }

    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, VoxelShapes.empty()};
        int times = (to.getHorizontal() - from.getHorizontal() + 4) % 4;

        for(int i = 0; i < times; ++i) {
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
                buffer[1] = VoxelShapes.combine(buffer[1], VoxelShapes.cuboid(1.0 - maxZ, minY, minX, 1.0 - minZ, maxY, maxX), BooleanBiFunction.OR);
            });
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }

        return buffer[0];
    }

    public static boolean isFullAndSolid(World world, BlockPos blockPos) {
        return isFaceFull(world, blockPos) && isSolid(world, blockPos);
    }

    public static boolean isFaceFull(World world, BlockPos blockPos) {
        BlockPos belowPos = blockPos.down();
        return Block.isFaceFullSquare(world.getBlockState(belowPos).getOutlineShape(world, belowPos), Direction.UP);
    }

    public static boolean isSolid(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos.down()).isSolid();
    }

    public static void spawnSlice(World level, ItemStack stack, double x, double y, double z, double xMotion, double yMotion, double zMotion) {
        ItemEntity entity = new ItemEntity(level, x, y, z, stack);
        entity.setVelocity(xMotion, yMotion, zMotion);
        level.spawnEntity(entity);
    }

    public static boolean matchesRecipe(RecipeInput inventory, List<Ingredient> recipe, int startIndex, int endIndex) {
        List<ItemStack> validStacks = new ArrayList();

        for(int i = startIndex; i <= endIndex; ++i) {
            ItemStack stackInSlot = inventory.getStackInSlot(i);
            if (!stackInSlot.isEmpty()) {
                validStacks.add(stackInSlot);
            }
        }

        Iterator var10 = recipe.iterator();

        boolean matches;
        do {
            if (!var10.hasNext()) {
                return true;
            }

            Ingredient entry = (Ingredient)var10.next();
            matches = false;
            Iterator var8 = validStacks.iterator();

            while(var8.hasNext()) {
                ItemStack item = (ItemStack)var8.next();
                if (entry.test(item)) {
                    matches = true;
                    validStacks.remove(item);
                    break;
                }
            }
        } while(matches);

        return false;
    }

    public static Collection<ServerPlayerEntity> tracking(ServerWorld world, BlockPos pos) {
        Objects.requireNonNull(pos, "BlockPos cannot be null");
        return tracking(world, new ChunkPos(pos));
    }

    public static Collection<ServerPlayerEntity> tracking(ServerWorld world, ChunkPos pos) {
        Objects.requireNonNull(world, "The world cannot be null");
        Objects.requireNonNull(pos, "The chunk pos cannot be null");
        return world.getChunkManager().chunkLoadingManager.getPlayersWatchingChunk(pos, false);
    }

    public static Optional<Pair<Float, Float>> getRelativeHitCoordinatesForBlockFace(BlockHitResult blockHitResult, Direction direction, Direction[] unAllowedDirections) {


        Direction direction2 = blockHitResult.getSide();
        if(Arrays.stream(unAllowedDirections).toList().contains(direction2)) return Optional.empty();
        if (direction != direction2 && direction2 != Direction.UP  && direction2 != Direction.DOWN) {
            return Optional.empty();
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos().offset(direction2);
            Vec3d vec3 = blockHitResult.getPos().subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            float d = (float) vec3.x;
            float f = (float) vec3.z;

            float y = (float) vec3.y;

            if(direction2 == Direction.UP || direction2 == Direction.DOWN) direction2 = direction;
            return switch (direction2) {
                case NORTH -> Optional.of(new Pair<>((float) (1.0 - d), y));
                case SOUTH -> Optional.of(new Pair<>(d, y));
                case WEST -> Optional.of(new Pair<>(f, y));
                case EAST -> Optional.of(new Pair<>((float) (1.0 - f), y));
                case DOWN, UP -> Optional.empty();
            };
        }

    }

    public enum LineConnectingType implements StringIdentifiable {
        NONE("none"),
        MIDDLE("middle"),
        LEFT("left"),
        RIGHT("right");

        private final String name;

        private LineConnectingType(String type) {
            this.name = type;
        }

        @Override
        public String asString() {
            return name;
        }
    }
}
