/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.core.marker.volume;

import buildcraft.lib.item.ItemBC_Neptune;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;

public abstract class ItemAddon extends ItemBC_Neptune {
    public ItemAddon(String idBC, Item.Properties properties) {
        super(idBC, properties);
    }

    public abstract Addon createAddon();

    // TODO Calen: how to use Addon?
    @SuppressWarnings("NullableProblems")
    @Override
//    public ActionResult<ItemStack> onItemRightClick(Level world, Player player, InteractionHand hand)
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (world.isClientSide) {
            return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
        }

        WorldSavedDataVolumeBoxes volumeBoxes = WorldSavedDataVolumeBoxes.get(world);
        Pair<VolumeBox, EnumAddonSlot> selectingVolumeBoxAndSlot = EnumAddonSlot.getSelectingVolumeBoxAndSlot(
                player,
                volumeBoxes.volumeBoxes
        );
        VolumeBox volumeBox = selectingVolumeBoxAndSlot.getLeft();
        EnumAddonSlot slot = selectingVolumeBoxAndSlot.getRight();
        if (volumeBox != null && slot != null) {
            if (!volumeBox.addons.containsKey(slot)) {
                Addon addon = createAddon();
                if (addon.canBePlaceInto(volumeBox)) {
                    addon.volumeBox = volumeBox;
                    volumeBox.addons.put(slot, addon);
                    volumeBox.addons.get(slot).onAdded();
                    volumeBoxes.setDirty();
                    return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
                }
            }
        }

        return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
    }
}
