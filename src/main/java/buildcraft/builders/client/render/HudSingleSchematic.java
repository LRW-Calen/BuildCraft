/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.builders.client.render;

import buildcraft.lib.client.render.HudRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class HudSingleSchematic extends HudRenderer
{
    @Override
//    protected void renderImpl(Minecraft mc, EntityPlayerSP player)
    protected void renderImpl(Minecraft mc, ServerPlayer player)
    {

    }

    @Override
//    protected boolean shouldRender(Minecraft mc, EntityPlayerSP player)
    protected boolean shouldRender(Minecraft mc, ServerPlayer player)
    {
//        ItemStack stack = player.getHeldItemMainhand();
        ItemStack stack = player.getMainHandItem();
        return false;
    }
}
