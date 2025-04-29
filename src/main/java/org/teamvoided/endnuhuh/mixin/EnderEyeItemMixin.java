package org.teamvoided.endnuhuh.mixin;

import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.teamvoided.endnuhuh.EndLogic.tryDisablePortal;


@Debug(export = true)
@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {

    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"), cancellable = true)
    private void disablePortal(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (tryDisablePortal(context)) cir.setReturnValue(ActionResult.PASS);
    }
}
