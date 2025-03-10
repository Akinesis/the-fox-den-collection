package cutefox.foxden.Utils;

import com.mojang.datafixers.util.Pair;
import cutefox.foxden.block.entity.ChairEntity;
import cutefox.foxden.registery.ModBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChairUtil {

    private static final Map<Identifier, Map<BlockPos, Pair<ChairEntity, BlockPos>>> CHAIRS = new HashMap();

    public ChairUtil() {
    }

    public static void removeChairEntity(World world, BlockPos blockPos) {
    }

    public static ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,BlockHitResult hit, double extraHeight) {
        if (world.isClient) {
            return ActionResult.PASS;
        } else if (player.isSneaking()) {
            return ActionResult.PASS;
        } else if (isPlayerSitting(player)) {
            return ActionResult.PASS;
        } else if (hit.getSide() == Direction.DOWN) {
            return ActionResult.PASS;
        } else {
            BlockPos hitPos = hit.getBlockPos();
            if (!isOccupied(world, hitPos) && player.getStackInHand(player.getActiveHand()).isEmpty()) {
                ChairEntity chair = ModBlockEntityType.CHAIR.create(world);
                chair.refreshPositionAndAngles((double)hitPos.getX() + 0.5, (double)hitPos.getY() + 0.25 + extraHeight, (double)hitPos.getZ() + 0.5, 0.0F, 0.0F);
                if (addChairEntity(world, hitPos, chair, player.getBlockPos())) {
                    world.spawnEntity(chair);
                    player.startRiding(chair);
                    return ActionResult.SUCCESS;
                }
            }

            return ActionResult.PASS;
        }
    }

    public static boolean addChairEntity(World world, BlockPos blockPos, ChairEntity entity, BlockPos playerPos) {
        if (!world.isClient) {
            Identifier id = getDimensionTypeId(world);
            if (!CHAIRS.containsKey(id)) {
                CHAIRS.put(id, new HashMap());
            }

            ((Map)CHAIRS.get(id)).put(blockPos, Pair.of(entity, playerPos));
            return true;
        } else {
            return false;
        }
    }

    public static BlockPos getPreviousPlayerPosition(PlayerEntity player, ChairEntity chairEntity) {
        if (!player.getWorld().isClient()) {
            Identifier id = getDimensionTypeId(player.getWorld());
            if (CHAIRS.containsKey(id)) {
                Iterator var3 = ((Map)CHAIRS.get(id)).values().iterator();

                while(var3.hasNext()) {
                    Pair<ChairEntity, BlockPos> pair = (Pair)var3.next();
                    if (pair.getFirst() == chairEntity) {
                        return pair.getSecond();
                    }
                }
            }
        }

        return null;
    }

    public static boolean isOccupied(World world, BlockPos pos) {
        Identifier id = getDimensionTypeId(world);
        return CHAIRS.containsKey(id) && ((Map)CHAIRS.get(id)).containsKey(pos);
    }

    public static boolean isPlayerSitting(PlayerEntity player) {
        Iterator var1 = CHAIRS.keySet().iterator();

        while(var1.hasNext()) {
            Identifier i = (Identifier)var1.next();
            Iterator var3 = ((Map)CHAIRS.get(i)).values().iterator();

            while(var3.hasNext()) {
                Pair<ChairEntity, BlockPos> pair = (Pair)var3.next();
                if ((pair.getFirst()).hasPassenger(player)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static Identifier getDimensionTypeId(World world) {
        return world.getDimension().effects();
    }

    public static void onStateReplaced(World world, BlockPos pos) {
        if (!world.isClient) {
            ChairEntity entity = getChairEntity(world, pos);
            if (entity != null) {
                removeChairEntity(world, pos);
                entity.removeAllPassengers();
            }
        }

    }

    public static ChairEntity getChairEntity(World world, BlockPos pos) {
        if (!world.isClient()) {
            Identifier id = getDimensionTypeId(world);
            if (CHAIRS.containsKey(id) && ((Map)CHAIRS.get(id)).containsKey(pos)) {
                return (ChairEntity)((Pair)((Map)CHAIRS.get(id)).get(pos)).getFirst();
            }
        }

        return null;
    }
}
