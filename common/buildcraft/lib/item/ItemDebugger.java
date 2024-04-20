/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.item;

import buildcraft.lib.debug.BCAdvDebugging;
import buildcraft.lib.debug.IAdvDebugTarget;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class ItemDebugger extends ItemBC_Neptune {
    public ItemDebugger(String idBC, Item.Properties properties) {
        super(idBC, properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext ctx) {
        Player player = ctx.getPlayer();
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        InteractionHand hand = ctx.getHand();
        Direction side = ctx.getClickedFace();
        Vec3 vec3Pos = ctx.getClickLocation();
        double hitX = vec3Pos.x;
        double hitY = vec3Pos.y;
        double hitZ = vec3Pos.z;


        if (world.isClientSide()) {
            return InteractionResult.PASS;
        }
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile == null) {
            return InteractionResult.FAIL;
        }
        if (tile instanceof IAdvDebugTarget) {
            BCAdvDebugging.setCurrentDebugTarget((IAdvDebugTarget) tile);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

//    public InteractionResult onItemUseFirst(Player player, Level world, BlockPos pos, Direction side, float hitX, float hitY, float hitZ, InteractionHand hand)
//    {
//        ItemStack stack = player.getItemInHand(hand);
//        return onItemUseFirst(stack, new UseOnContext(world, player, hand, stack, new BlockHitResult(new Vec3(hitX, hitY, hitZ), side, pos, false)));
//    }

    public static boolean isShowDebugInfo(Player player) {
        return player.getAbilities().instabuild ||
                player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ItemDebugger ||
                player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ItemDebugger;
    }
}
