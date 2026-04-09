package com.theendercore.endnuhuh.mixin;

import com.theendercore.endnuhuh.EndNuhUh;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.theendercore.endnuhuh.EndLogic.preventReopen;
import static com.theendercore.endnuhuh.EndLogic.tryDisablePortal;


@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"), cancellable = true)
    private void disablePortal(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        if (tryDisablePortal(useOnContext)) cir.setReturnValue(InteractionResult.PASS);
    }

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;levelEvent(ILnet/minecraft/core/BlockPos;I)V"), cancellable = true)
    private void disableDoublePlace(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        if (EndNuhUh.config.getPreventPortalReopening() && preventReopen(useOnContext)) cir.setReturnValue(InteractionResult.SUCCESS);
    }

}
