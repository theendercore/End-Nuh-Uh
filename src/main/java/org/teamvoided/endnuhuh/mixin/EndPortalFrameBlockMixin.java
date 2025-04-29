package org.teamvoided.endnuhuh.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import static org.teamvoided.endnuhuh.EndLogic.removeEye;


@Mixin(EndPortalFrameBlock.class)
public class EndPortalFrameBlockMixin extends Block {

    public EndPortalFrameBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hitResult) {
        var actionResult = removeEye(state, world, pos, player);
        return (actionResult != null) ? actionResult : super.onUse(state, world, pos, player, hitResult);
    }
}
