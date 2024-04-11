/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package buildcraft.factory.item;

import buildcraft.factory.block.BlockPlastic;
import buildcraft.lib.item.ItemBlockBCMulti;
import buildcraft.lib.misc.ColourUtil;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Locale;

public class ItemPlastic extends ItemBlockBCMulti
{
    public ItemPlastic(Item.Properties properties, BlockPlastic block)
    {
//        super(block, createNameArray());
        super(block, properties);
//        this.setMaxDamage(0);
//        this.setHasSubtypes(true);
    }

    private static String[] createNameArray()
    {
        String[] arr = ColourUtil.getNameArray();
        String[] switched = new String[16];
        for (int i = 0; i < arr.length; i++)
        {
            switched[15 - i] = arr[i].toLowerCase(Locale.ROOT);
        }
        return switched;
    }

    // Calen: not still useful in 1.18.2
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void addModelVariants(TIntObjectHashMap<ModelResourceLocation> variants)
//    {
//        for (DyeColor colour : DyeColor.values())
//        {
////            addVariant(variants, colour.getMetadata(), colour.getName());
//            addVariant(variants, colour.getId(), colour.getName());
//        }
//    }
}
