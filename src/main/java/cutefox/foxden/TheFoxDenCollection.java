package cutefox.foxden;

import cutefox.betterenchanting.Util.BetterEnchantingApi;
import cutefox.foxden.Utils.BonusDropHelper;
import cutefox.foxden.Utils.ConfigBuilder;
import cutefox.foxden.Utils.FoxDenDefaultConfig;
import cutefox.foxden.Utils.Utils;
import cutefox.foxden.conditions.ModConfigConditions;
import cutefox.foxden.networking.SpaceRangerArmorWingsPayload;
import cutefox.foxden.registery.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TheFoxDenCollection implements ModInitializer {

	public static final String MOD_ID = "TheFoxDenCollection";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static boolean isBetterEnchantingPresent;

	@Override
	public void onInitialize() {
		LOGGER.info("Starting to initialize "+MOD_ID);

		loadConfig();
		ModConfigConditions.registerConditions();

		isBetterEnchantingPresent = FabricLoader.getInstance().isModLoaded("betterenchanting");
		ModModels.loadModels();

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModArmorMaterials.registerModItems();
		ModItemTags.registerModTags();
		ModBlockEntityType.registerModBlocksEntities();
		if(ConfigBuilder.moreAttributes())
			ModEntityAttributes.registerAttributes();

		Registry.register(Registries.ITEM_GROUP, Utils.id("item_group"), generateItemGroup());

		ModLootTableModifiers.modifyLootTables();

		addEventListner();
		registerNetworking();

		ModItems.registerBlockItems();
	}

	private void addEventListner(){

		UseBlockCallback.EVENT.register((player, world, hand, pos) -> {
			BlockState state = world.getBlockState(pos.getBlockPos());
			if (!player.isSpectator() && hand == Hand.MAIN_HAND) {
				if(state.getBlock() == ModBlocks.CHEESE_CAULDRON){
					if(player.getMainHandStack().getItem() == Items.BROWN_MUSHROOM || player.getMainHandStack().getItem() == Items.RED_MUSHROOM){
						//make blue cheese
						player.getMainHandStack().decrementUnlessCreative(1, player);
						world.setBlockState(pos.getBlockPos(), ModBlocks.BLUE_CHEESE_CAULDRON.getDefaultState());
						return ActionResult.SUCCESS;
					}else {
						int randomAmount = Random.create().nextBetween(2,4);
						ItemStack cheese = new ItemStack(ModItems.CHEESE,randomAmount); //make random amount
						Block.dropStack(world,pos.getBlockPos(),cheese);
						world.setBlockState(pos.getBlockPos(), Blocks.CAULDRON.getDefaultState());
						world.playSound(null, pos.getBlockPos(), SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.BLOCKS, .6f, .7f);
						return ActionResult.SUCCESS;
					}

				}

				if(state.getBlock() == ModBlocks.BLUE_CHEESE_CAULDRON){
					int randomAmount = Random.create().nextBetween(2,4);
					ItemStack cheese = new ItemStack(ModItems.BLUE_CHEESE,randomAmount); //make random amount
					Block.dropStack(world,pos.getBlockPos(),cheese);
					world.setBlockState(pos.getBlockPos(), Blocks.CAULDRON.getDefaultState());
					world.playSound(null, pos.getBlockPos(), SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.BLOCKS, .6f, .7f);
					return ActionResult.SUCCESS;
				}

			}
			return ActionResult.PASS;
		});

		EntityElytraEvents.CUSTOM.register((entity, tickElytra)->{
			if(entity instanceof PlayerEntity player){
					if(player.getInventory().getArmorStack(2).getItem().equals(ModItems.SPACE_RANGER_CHESTPLATE)){

						NbtComponent b = player.getInventory().getArmorStack(2).get(DataComponentTypes.CUSTOM_DATA);

						if(b != null && b.contains("deployed"))
							return b.copyNbt().getBoolean("deployed");

						return false;
					}
				}
			return false;
		});

		ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((e,p) -> {
			//ModEnchantIngredientMap.genMapFromJson(e.getWorld(ServerWorld.OVERWORLD));
		});

		PlayerBlockBreakEvents.BEFORE.register(BonusDropHelper::handleBlockBreak);

	}

	private static void loadConfig(){
		ConfigBuilder.createConfigFile();
	}

	private static void registerNetworking(){
		PayloadTypeRegistry.playC2S().register(SpaceRangerArmorWingsPayload.ID, SpaceRangerArmorWingsPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(SpaceRangerArmorWingsPayload.ID, (payload, context)->{
			NbtCompound nbt = new NbtCompound();
			nbt.putBoolean("deployed", payload.deployed());
			NbtComponent component = NbtComponent.of(nbt);

			context.player().getInventory().getArmorStack(2).set(DataComponentTypes.CUSTOM_DATA, component);
		});
	}

	private ItemGroup generateItemGroup(){
		ItemGroup itemGroup = FabricItemGroup.builder()
				.icon(() -> new ItemStack(Items.RABBIT_FOOT))
				.displayName(Text.translatable("itemGroup.fox_den.item_group"))
				.entries((context, entries) -> {
					if(ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.POUTINE)){
						entries.add(ModItems.CHEESE);
						entries.add(ModItems.BLUE_CHEESE);
						entries.add(ModItems.CHEESE_BLOCK);
						entries.add(ModItems.YEAST);
						entries.add(ModItems.OIL_BUCKET);
						entries.add(ModItems.FRIES);
						entries.add(ModItems.BROWN_SAUCE);
					}
					if(ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.KNIGHT_ARMOR)){
						entries.add(ModItems.STEEL_BLEND);
						entries.add(ModItems.STEEL_INGOT);
						entries.add(ModItems.STEEL_BOOTS);
						entries.add(ModItems.STEEL_CHESTPLATE);
						entries.add(ModItems.STEEL_HELMET);
						entries.add(ModItems.STEEL_LEGGINGS);
						entries.add(ModItems.STEEL_BLOCK);
					}

					if(ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.BONE_ARMOR)){
						entries.add(ModItems.BONE_BOOTS);
						entries.add(ModItems.BONE_CHESTPLATE);
						entries.add(ModItems.BONE_HELMET);
						entries.add(ModItems.BONE_LEGGINGS);
					}

					if(ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.SPACE_ARMOR)){
						entries.add(ModItems.SPACE_RANGER_HELMET);
						entries.add(ModItems.SPACE_RANGER_CHESTPLATE);
						entries.add(ModItems.SPACE_RANGER_LEGGINGS);
						entries.add(ModItems.SPACE_RANGER_BOOTS);
					}

					if(ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.UPGRADE_TEMPLATE ) && !TheFoxDenCollection.isBetterEnchantingPresent){
						entries.add(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE);
						entries.add(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE);
					}

					if(ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.LEAVES_WALL)){
						ModItems.LEAVES_WALLS.stream().forEach(wall -> {
							entries.add(wall);
						});
					}

					if(ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.BIKE_ARMOR)){
						entries.add(ModItems.BIKE_HELMET);
					}

					if(ConfigBuilder.yamlConfig.getBoolean(FoxDenDefaultConfig.WOLF_ARMOR)){
						entries.add(ModItems.IRON_WOLF_ARMOR);
						entries.add(ModItems.DIAMOND_WOLF_ARMOR);
					}

					entries.add(ModItems.ROTTEN_LEATHER);

					entries.add(ModItems.SHIP_MAST);
				})
				.build();

		return itemGroup;
	}

}

