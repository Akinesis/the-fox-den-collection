package cutefox.foxden.block.entity;

import cutefox.foxden.registery.ModBlockEntityType;
import cutefox.foxden.registery.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class YeastMilkBlockEntity extends BlockEntity {

    int fireCounter = 0;

    public YeastMilkBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.YEAST_MILK, pos, state);
    }


    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if(blockEntity instanceof YeastMilkBlockEntity yeastMilkBlockEntity)
            yeastMilkBlockEntity.tickFire(world, pos, state);
    }

    public void tickFire(World world, BlockPos pos, BlockState state){
        if(world.getBlockState(pos.down()).getBlock() instanceof FireBlock || CampfireBlock.isLitCampfire(world.getBlockState(pos.down()))) {
            fireCounter++;
            if((fireCounter%10) == 0){
                //every two seconds
                world.playSound(null, pos, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 1.5f, 0.8f);
            }

            if(fireCounter>=(200)){
                //Cheese is cooked
                world.setBlockState(pos, ModBlocks.CHEESE_CAULDRON.getDefaultState());
            }
        }
    }
}
