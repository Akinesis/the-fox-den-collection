package cutefox.foxden.item;

import cutefox.foxden.item.renderer.SpaceRangerArmorRenderer;
import cutefox.foxden.registery.ModItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class SpaceRangerArmorItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private static boolean isDeploy = false;

    public SpaceRangerArmorItem(RegistryEntry<ArmorMaterial> material, Type type, net.minecraft.item.Item.Settings settings) {
        super(material, type, settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public <T extends LivingEntity> BipedEntityModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable BipedEntityModel<T> original) {
                if(this.renderer == null)
                    this.renderer = new SpaceRangerArmorRenderer();

                GeoModel geoModel = this.renderer.getGeoModel();
                GeoBone hips = (GeoBone) geoModel.getBone("hips").orElse(null);
                if(hips != null){
                    if(equipmentSlot.equals(EquipmentSlot.LEGS))
                        //Hide hips for boots
                        hips.setHidden(false);
                    else
                        hips.setHidden(true);
                }


                return this.renderer;
            }
        });
    }

    @Override
    public Object getRenderProvider() {
        return GeoItem.super.getRenderProvider();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20, state -> {
            // Apply our generic idle animation.
            // Whether it plays or not is decided down below.
            state.getController().setAnimation(DefaultAnimations.IDLE);

            // Let's gather some data from the state to use below
            // This is the entity that is currently wearing/holding the item
            Entity entity = state.getData(DataTickets.ENTITY);

            // We'll just have ArmorStands always animate, so we can return here
            if (entity instanceof ArmorStandEntity)
                return PlayState.CONTINUE;

            // For this example, we only want the animation to play if the entity is wearing all pieces of the armor
            // Let's collect the armor pieces the entity is currently wearing

            boolean isFullSet = isFullSet(entity);

            // Play the animation if the full set is being worn, otherwise stop
            return isFullSet ? PlayState.CONTINUE : PlayState.STOP;
        }));

        controllers.add(new AnimationController<>(this, 0, this::shouldDeploy));
        //controllers.add(new AnimationController<>(this, 0, this::shouldOpenHelmet));

    }

    private <E extends GeoAnimatable>PlayState shouldDeploy(AnimationState<E> state){
        ItemStack stack = state.getData(DataTickets.ITEMSTACK);

        if(stack.getItem().equals(ModItems.SPACE_RANGER_CHESTPLATE)){

                NbtComponent b = stack.get(DataComponentTypes.CUSTOM_DATA);

                if(b != null && b.contains("deployed")){
                    boolean deploy = b.copyNbt().getBoolean("deployed");
                    if(deploy) {
                        state.getController().setAnimation(RawAnimation.begin().thenPlay("animation.SpaceRanger.wingOpen"));
                    }else {
                        state.getController().setAnimation(RawAnimation.begin().thenPlay("animation.SpaceRanger.wingclose"));
                    }
                }else {
                    state.getController().setAnimation(RawAnimation.begin().thenPlay("animation.SpaceRanger.idle"));
                }

        }
        return PlayState.CONTINUE;
    }

    private <E extends GeoAnimatable>PlayState shouldOpenHelmet(AnimationState<E> state){
        ItemStack stack = state.getData(DataTickets.ITEMSTACK);

        if(stack.getItem().equals(ModItems.SPACE_RANGER_HELMET)){

            NbtComponent b = stack.get(DataComponentTypes.CUSTOM_DATA);

            if(b != null && b.contains("opened")){
                boolean opened = b.copyNbt().getBoolean("opened");
                if(opened) {
                    state.getController().setAnimation(RawAnimation.begin().thenPlay("animation.SpaceRanger.helmetOpen"));
                }else {
                    state.getController().setAnimation(RawAnimation.begin().thenPlay("animation.SpaceRanger.idle"));
                }
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private boolean isFullSet(Entity entity){
        // For this example, we only want the animation to play if the entity is wearing all pieces of the armor
        // Let's collect the armor pieces the entity is currently wearing
        Set<Item> wornArmor = new ObjectOpenHashSet<>();

        for (ItemStack stack : ((PlayerEntity)entity).getInventory().armor) {
            // We can stop immediately if any of the slots are empty
            if (stack.isEmpty())
                return false;

            wornArmor.add(stack.getItem());
        }

        // Check each of the pieces match our set
        return wornArmor.containsAll(ObjectArrayList.of(
                Registries.ITEM.getEntry(ModItems.SPACE_RANGER_BOOTS),
                Registries.ITEM.getEntry(ModItems.SPACE_RANGER_LEGGINGS),
                Registries.ITEM.getEntry(ModItems.SPACE_RANGER_CHESTPLATE),
                Registries.ITEM.getEntry(ModItems.SPACE_RANGER_HELMET)));
    }

    public void triggerDeploy(){
        isDeploy = ! isDeploy;
    }
}