package org.teamvoided.endnuhuh.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(EndPortalFrameBlock.class)
public class EndPortalFrameBlockMixin extends Block {

    public EndPortalFrameBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hitResult) {
        if (state.get(EndPortalFrameBlock.EYE) && player.getMainHandStack().isEmpty() && player.isSneaking()) {
            world.setBlockState(pos, state.with(EndPortalFrameBlock.EYE, false));
            if (!player.isCreative()) {
                ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.98, pos.getZ() + 0.5, Items.ENDER_EYE.getDefaultStack());
                item.setVelocity(0, 0, 0);
                world.spawnEntity(item);
            }
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hitResult);
    }
}
