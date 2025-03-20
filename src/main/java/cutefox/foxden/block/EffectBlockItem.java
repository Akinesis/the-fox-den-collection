package cutefox.foxden.block;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponent.StatusEffectEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EffectBlockItem extends BlockItem {
   public EffectBlockItem(Block block, Item.Settings settings) {
      super(block, settings);
   }

   public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
      List<StatusEffectEntry> list2 = new ArrayList();
      if (itemStack.contains(DataComponentTypes.FOOD)) {
         list2 = ((FoodComponent)itemStack.get(DataComponentTypes.FOOD)).effects();
      }

      if (((List)list2).isEmpty()) {
         tooltip.add(Text.translatable("effect.none").formatted(Formatting.GRAY));
      } else {
         Iterator var6 = ((List)list2).iterator();

         while(var6.hasNext()) {
            StatusEffectEntry effect = (StatusEffectEntry)var6.next();
            tooltip.add(Text.translatable(effect.effect().getTranslationKey()).formatted(new Formatting[]{Formatting.BLUE, Formatting.ITALIC}));
         }
      }

      tooltip.add(Text.empty());
      tooltip.add(Text.translatable("tooltip.fox_den.canbeplaced").formatted(new Formatting[]{Formatting.ITALIC, Formatting.GRAY}));
   }

   public ActionResult place(ItemPlacementContext context) {
      PlayerEntity player = context.getPlayer();
      return player != null && player.isSneaking() ? super.place(context) : ActionResult.PASS;
   }
}
