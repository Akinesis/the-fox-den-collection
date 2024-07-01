package cutefox.foxden.block.entity;


import cutefox.foxden.registery.ModBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class OilCauldronBlockEntity extends BlockEntity {

    int fireCounter = 0;
    private boolean isBoiling = false;

    public OilCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.OIL_CAULDRON, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        //tick only on server
        if(blockEntity instanceof OilCauldronBlockEntity oilCauldronBlockEntity)
            oilCauldronBlockEntity.tickFire(world, pos, state);
    }

    public void tickFire(World world, BlockPos pos, BlockState state){
        if(world.getBlockState(pos.down()).getBlock() instanceof FireBlock || CampfireBlock.isLitCampfire(world.getBlockState(pos.down()))) {
            if(!isBoiling)
                isBoiling = true;

            fireCounter++;

            Random random = world.getRandom();
            random.nextFloat();
            SimpleParticleType simpleParticleType = ParticleTypes.BUBBLE_POP;
            if(world instanceof ServerWorld serverWorld){
                 serverWorld.spawnParticles(simpleParticleType,pos.toCenterPos().x, pos.getY()+0.9, pos.toCenterPos().z,1,0.25f,0,.25f,0f);
            }

            if((fireCounter%10) == 0){
                //every two seconds
                world.playSound(null, pos, SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.BLOCKS, 0.7f, 0.4f);

            }

            if(fireCounter>=(100)){
                //Cheese is cooked
                fireCounter = 0;
            }
        }else {
            if (isBoiling)
                isBoiling = false;
        }
    }

    public boolean isBoiling(){
        return isBoiling;
    }
}
