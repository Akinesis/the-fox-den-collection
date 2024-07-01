package cutefox.foxden.mixin;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ForgingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ForgingScreenHandler.class)
public abstract class ForgingScreenHandlerMixin {

    @Shadow
    protected Inventory input;

}
