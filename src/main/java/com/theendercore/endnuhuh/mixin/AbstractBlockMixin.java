package com.theendercore.endnuhuh.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.theendercore.endnuhuh.EndLogic.removeEye;


@Mixin(BlockBehaviour.class)
public abstract class AbstractBlockMixin {

    @ModifyReturnValue(method = "useWithoutItem", at = @At("RETURN"))
    private InteractionResult onUse(InteractionResult original, BlockState blockState, Level world, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (((BlockBehaviour) (Object) this) instanceof EndPortalFrameBlock) {
            var result = removeEye(blockState, world, pos, player);
            if (result != null) return result;
        }
        return original;
    }

}
