package org.teamvoided.template.mixin;

import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.teamvoided.template.EyeFailLogic;


@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {

	@Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;pushEntitiesUpBeforeBlockChange(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"), cancellable = true)
	private void disablePortal(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		EyeFailLogic.disablePortal(context);
        cir.setReturnValue(ActionResult.PASS);
	}
}
