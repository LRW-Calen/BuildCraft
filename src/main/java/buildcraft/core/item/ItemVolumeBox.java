/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.core.item;

import buildcraft.core.marker.volume.VolumeBox;
import buildcraft.core.marker.volume.WorldSavedDataVolumeBoxes;
import buildcraft.lib.item.ItemBC_Neptune;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ItemVolumeBox extends ItemBC_Neptune
{
    public ItemVolumeBox(String idBC, Item.Properties properties)
    {
        super(idBC, properties);
    }

    @Override
//    public EnumActionResult onItemUse(Player player, Level world, BlockPos pos, InteractionHand hand, Direction facing, float hitX, float hitY, float hitZ)
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext ctx)
    {
        Player player = ctx.getPlayer();
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        InteractionHand hand = ctx.getHand();
        Direction facing = ctx.getClickedFace();
        Vec3 vec3Pos = ctx.getClickLocation();
        double hitX = vec3Pos.x;
        double hitY = vec3Pos.y;
        double hitZ = vec3Pos.z;
        if (world.isClientSide)
        {
//            return EnumActionResult.PASS;
            return InteractionResult.PASS;
        }

        BlockPos offset = pos.relative(facing);

        WorldSavedDataVolumeBoxes volumeBoxes = WorldSavedDataVolumeBoxes.get(world);
        VolumeBox current = volumeBoxes.getVolumeBoxAt(offset);

        if (current == null)
        {
            volumeBoxes.addVolumeBox(offset);
            volumeBoxes.setDirty();
//            return EnumActionResult.SUCCESS;
            return InteractionResult.SUCCESS;
        }

//        return EnumActionResult.FAIL;
        return InteractionResult.FAIL;
    }
}
