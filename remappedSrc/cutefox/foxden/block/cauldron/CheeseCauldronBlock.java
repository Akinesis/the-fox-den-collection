package cutefox.foxden.block.cauldron;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.util.ItemActionResult;

import java.util.Map;

import static net.minecraft.block.cauldron.CauldronBehavior.BEHAVIOR_MAPS;

public class CheeseCauldronBlock extends AbstractCauldronBlock{

    public static final CauldronBehavior.CauldronBehaviorMap CHEESE_CAULDRON_BEHAVIOR = createMap("cheese");

    public CheeseCauldronBlock(Settings settings) {
        super(settings, CHEESE_CAULDRON_BEHAVIOR);

        Map<Item, CauldronBehavior> behaviorMap = this.behaviorMap.map();

    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return null;
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return 0.9375;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }



    static CauldronBehavior.CauldronBehaviorMap createMap(String name) {
        Object2ObjectOpenHashMap<Item, CauldronBehavior> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
        object2ObjectOpenHashMap.defaultReturnValue((state, world, pos, player, hand, stack) -> {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        });
        CauldronBehavior.CauldronBehaviorMap cauldronBehaviorMap = new CauldronBehavior.CauldronBehaviorMap(name, object2ObjectOpenHashMap);
        BEHAVIOR_MAPS.put(name, cauldronBehaviorMap);
        return cauldronBehaviorMap;
    }
}
