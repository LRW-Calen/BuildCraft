/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.transport.item;

import buildcraft.api.transport.pipe.IItemPipe;
import buildcraft.api.transport.pipe.PipeApi;
import buildcraft.api.transport.pipe.PipeDefinition;
import buildcraft.lib.registry.CreativeTabManager;
import buildcraft.lib.registry.TagManager;
import buildcraft.transport.BCTransport;
import buildcraft.transport.BCTransportBlocks;
import buildcraft.lib.item.IItemBuildCraft;
import buildcraft.lib.client.render.font.SpecialColourFontRenderer;
import buildcraft.lib.misc.ColourUtil;
import buildcraft.lib.misc.LocaleUtil;
import buildcraft.transport.pipe.PipeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPipeHolder extends BlockItem implements IItemBuildCraft, IItemPipe
{
    public final PipeDefinition definition;
    private final String namespace;
    private final String id;
    private String unlocalizedName;
    private final DyeColor colour;
//    private CreativeModeTab creativeTab;

    //    protected ItemPipeHolder(PipeDefinition definition, String tagId)
    protected ItemPipeHolder(PipeDefinition definition, String tagId, DyeColor colour)
    {
//        super(BCTransportBlocks.pipeHolder.get(), definition.properties);
        super(BCTransportBlocks.pipeHolder.get(), definition.properties.tab(CreativeTabManager.getTab(TagManager.getTag(tagId, TagManager.EnumTagType.CREATIVE_TAB))));
        this.definition = definition;
        this.namespace = definition.identifier.getNamespace();
        this.id = tagId;
//        this.id = definition.identifier.getPath();
//        this.setMaxDamage(0);
//        this.setHasSubtypes(true);
        if (!"".equals(id))
        {
            init();
        }
        this.colour = colour;
    }

    // Calen
    @Override
    public DyeColor getColour()
    {
        return colour;
    }


    // Calen: Unused
//    /**
//     * Creates a new {@link ItemPipeHolder} without requiring a tag.
//     */
////    public static ItemPipeHolder create(PipeDefinition definition)
//    public static RegistryObject<? extends IItemPipe> create(PipeDefinition definition)
//    {
////        return new ItemPipeHolder(definition, "");
//        return PipeRegistry.PIPE_ITEMS.register("", () -> new ItemPipeHolder(definition, "pipe." + ""));
//    }

    /**
     * Creates a new {@link ItemPipeHolder} with a tag that will be taken from {@link TagManager}.
     */
//    public static ItemPipeHolder createAndTag(PipeDefinition definition)
//    public static RegistryObject<ItemPipeHolder> createAndTag(PipeDefinition definition)
    public static RegistryObject<ItemPipeHolder> createAndTag(PipeDefinition definition, DyeColor colour)
    {
        ResourceLocation reg = definition.identifier;
        String suffix = colour == null ? "_colorless" : "_" + colour.getName();
//        String tagId = "item.pipe." + reg.getNamespace() + "." + reg.getPath();
//        String tagId = "item.pipe." + reg.getNamespace() + "." + reg.getPath();
        String tagId = "item.pipe." + reg.getNamespace() + "." + reg.getPath();
        String regName = TagManager.getTag(tagId, TagManager.EnumTagType.REGISTRY_NAME).replace(BCTransport.MOD_ID + ":", "") + suffix;
//        return new ItemPipeHolder(definition, tagId);
//        return new ItemPipeHolder(definition, "pipe." + reg.getPath());
        return PipeRegistry.PIPE_ITEMS.register(regName, () -> new ItemPipeHolder(definition, tagId, colour));
    }

    // Calen: never used in 1.12.2
//    public ItemPipeHolder registerWithPipeApi()
//    {
//        PipeApi.pipeRegistry.setItemForPipe(definition, this);
//        return this;
//    }

    @Override
//    public void getSubItems(CreativeModeTab tab, NonNullList<ItemStack> items)
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items)
    {
//        if (this.isInCreativeTab(tab))
        if (allowdedIn(tab))
        {
            items.add(new ItemStack(this));
        }
    }

    @Override
    public String getIdBC()
    {
        return id;
    }

    @Override
    public PipeDefinition getDefinition()
    {
        return definition;
    }

    // Calen: not still useful in 1.18.2
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void addModelVariants(TIntObjectHashMap<ModelResourceLocation> variants)
//    {
//        for (int i = 0; i <= 16; i++)
//        {
//            variants.put(i, new ModelResourceLocation("buildcrafttransport:pipe_item#inventory"));
//        }
//    }

    @Override
//    public String getItemStackDisplayName(ItemStack stack)
    public Component getName(ItemStack stack)
    {
//        String colourComponent = "";
//        int meta = ColourUtil.getStackColourIdFromTag(stack);
//        if (meta >= 0 && meta < 16)
//        {
//            DyeColor colour = DyeColor.byId(meta);
//            colourComponent = ColourUtil.getTextFullTooltipSpecial(colour) + " ";
//        }
        if (LocaleUtil.modLangResourceNotLoaded())
        {
            MutableComponent colour = this.colour == null ? new TextComponent("") : ColourUtil.getTextFullTooltipSpecialComponent(this.colour).append(new TextComponent(" "));
            return colour.append(new TranslatableComponent(this.getDescriptionId(stack)));
        }
        else
        {
            String colourStr = this.colour == null ? "" : (ColourUtil.getTextFullTooltipSpecial(this.colour) + " ");
            return new TextComponent(colourStr).append(new TranslatableComponent(this.getDescriptionId(stack)));
        }
    }

    // TODO Calen getFontRenderer???
//    @Override
    @OnlyIn(Dist.CLIENT)
    public Font getFontRenderer(ItemStack stack)
    {
        return SpecialColourFontRenderer.INSTANCE;
    }

    // ItemBlock overrides these to point to the block

    // Calen: can not override final method
    @Override
    public void setUnlocalizedName(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
//        return this;
    }

    @Override
//    public String getUnlocalizedName()
    public String getDescriptionId(ItemStack stack)
    {
        return this.unlocalizedName;
    }


    // Calen Deprecated
//    @Override
//    public String getUnlocalizedName(ItemStack stack)
//    {
//        return unlocalizedName;
//    }

    // Calen Deprecated
//    @Override
//    public Item setCreativeTab(CreativeModeTab tab)
//    {
//        creativeTab = tab;
//        return this;
//    }

    // Calen Deprecated
//    @Override
//    public CreativeModeTab getCreativeTab()
//    {
//        return creativeTab;
//    }

    // Misc usefulness

    @Override
    @OnlyIn(Dist.CLIENT)
//    public void addInformation(ItemStack stack, Level world, List<String> tooltip, ITooltipFlag flag)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag)
    {
//        String tipName = "tip." + unlocalizedName.replace(".name", "").replace("item.", "");
        String tipName = "tip." + this.unlocalizedName.replace(".name", "").replace("item.", "");
        String localised = I18n.get(tipName);
        if (!localised.equals(tipName))
        {
            tooltip.add(new TextComponent(ChatFormatting.GRAY + localised));
        }
        if (definition.flowType == PipeApi.flowFluids)
        {
            PipeApi.FluidTransferInfo fti = PipeApi.getFluidTransferInfo(definition);
//            tooltip.add(new TextComponent(LocaleUtil.localizeFluidFlow(fti.transferPerTick)));
            tooltip.add(LocaleUtil.localizeFluidFlowToTranslatableComponent(fti.transferPerTick));
            tooltip.add(LocaleUtil.localizeFluidFlowToTranslatableComponent(fti.transferPerTick));
        }
        else if (definition.flowType == PipeApi.flowPower)
        {
            PipeApi.PowerTransferInfo pti = PipeApi.getPowerTransferInfo(definition);
//            tooltip.add(new TextComponent(LocaleUtil.localizeMjFlow(pti.transferPerTick)));
            tooltip.add(LocaleUtil.localizeMjFlowComponent(pti.transferPerTick));
            // TODO: remove this! (Not localised b/c localisations happen AFTER this is removed)
            tooltip.add(new TextComponent("Work in progress - the above limit isn't enforced!"));
        }
    }
}
