/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.transport.item;

import buildcraft.lib.client.render.font.SpecialColourFontRenderer;
import buildcraft.lib.item.ItemBC_Neptune;
import buildcraft.lib.misc.ColourUtil;
import buildcraft.lib.misc.LocaleUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemWire extends ItemBC_Neptune {
    public ItemWire(String idBC, Item.Properties properties) {
        super(idBC, properties);
//        setHasSubtypes(true);
    }

    @Override
    public void addSubItems(CreativeModeTab tab, NonNullList<ItemStack> subItems) {
        for (int i = 0; i < 16; i++) {
            // Calen: meta -> tag
            ItemStack stack = new ItemStack(this, 1);
            subItems.add(ColourUtil.addColourTagToStack(stack, i));
        }
    }

    // Calen: not still useful in 1.18.2
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void addModelVariants(TIntObjectHashMap<ModelResourceLocation> variants)
//    {
//        for (DyeColor color : DyeColor.values())
//        {
//            addVariant(variants, color.getId(), color.getName());
//        }
//    }

    @Override
//    public String getItemStackDisplayName(ItemStack stack)
    public Component getName(ItemStack stack) {
////        return ColourUtil.getTextFullTooltipSpecial(EnumDyeColor.byMetadata(stack.getMetadata())) + " " + super.getItemStackDisplayName(stack);
//        return new TextComponent(ColourUtil.getTextFullTooltipSpecial(ColourUtil.getStackColourFromTag(stack)) + " ").append(super.getName(stack));

        DyeColor colour = ColourUtil.getStackColourFromTag(stack);
        if (LocaleUtil.modLangResourceNotLoaded()) {
            if (colour != null) {
                MutableComponent colourComponent = ColourUtil.getTextFullTooltipSpecialComponent(colour);
                return colourComponent.append(new TextComponent(" ")).append(super.getName(stack));
            } else {
                return super.getName(stack);
            }
        } else {
            String prefix = colour == null ? "" : (ColourUtil.getTextFullTooltipSpecial(ColourUtil.getStackColourFromTag(stack)) + " ");
            return new TextComponent(prefix).append(super.getName(stack));
        }
    }

    // TODO Calen getFontRenderer???
//    @Override
    @OnlyIn(Dist.CLIENT)
    public Font getFontRenderer(ItemStack stack) {
        return SpecialColourFontRenderer.INSTANCE;
    }
}
