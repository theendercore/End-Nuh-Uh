package org.teamvoided.endnuhuh.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static org.teamvoided.endnuhuh.EndLogic.removeEye;


@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {
    @ModifyReturnValue(method = "onUse", at = @At("RETURN"))
    private ActionResult onUse(ActionResult orignal, BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hitResult) {
        if (((AbstractBlock) (Object) this) instanceof EndPortalFrameBlock) {
            var actionResult = removeEye(state, world, pos, player);
            if (actionResult != null) return actionResult;
        }
        return orignal;
    }
}
