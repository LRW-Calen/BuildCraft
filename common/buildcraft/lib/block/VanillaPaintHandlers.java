/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.block;

import buildcraft.api.blocks.BlockConstants;
import buildcraft.api.blocks.CustomPaintHelper;
import buildcraft.api.blocks.ICustomPaintHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VanillaPaintHandlers {

    public static void fmlInit() {
//        registerDoubleTypedHandler(Blocks.GLASS, Blocks.STAINED_GLASS, StainedGlassBlock.COLOR);
//        registerDoubleTypedHandler(Blocks.GLASS_PANE, Blocks.STAINED_GLASS_PANE, BlockStainedGlassPane.COLOR);
//        registerDoubleTypedHandler(Blocks.TERRACOTTA, Blocks.STAINED_HARDENED_CLAY, BlockColored.COLOR);
        registerDoubleTypedHandler(Blocks.GLASS, createColourBlockMap("minecraft", "_terracotta"));
        registerDoubleTypedHandler(Blocks.GLASS_PANE, createColourBlockMap("minecraft", "_stained_glass"));
        registerDoubleTypedHandler(Blocks.TERRACOTTA, createColourBlockMap("minecraft", "_stained_glass_pane"));
    }

    // private static void registerDoubleTypedHandler(Block clear, Block dyed, Property<DyeColor> colourProp)
    private static void registerDoubleTypedHandler(Block clear, Map<DyeColor, ? extends Block> dyed) {
//        ICustomPaintHandler handler = createDoubleTypedPainter(clear, dyed, colourProp);
        ICustomPaintHandler handler = createDoubleTypedPainter(clear, dyed);
        CustomPaintHelper.INSTANCE.registerHandler(clear, handler);
//        CustomPaintHelper.INSTANCE.registerHandler(dyed, handler);
        dyed.values().forEach(b -> CustomPaintHelper.INSTANCE.registerHandler(b, handler));
    }

    // public static ICustomPaintHandler createDoubleTypedPainter(Block clear, Block dyed, Property<DyeColor> colourProp)
    public static ICustomPaintHandler createDoubleTypedPainter(Block clear, Map<DyeColor, ? extends Block> dyed) {
        return (world, pos, state, hitPos, hitSide, to) ->
        {
            // clear -> ?
            if (state.getBlock() == clear) {
                // We are currently clear
                if (to == null) {
                    return ActionResultType.FAIL;
                }
//                BlockState painted = dyed.defaultBlockState().setValue(colourProp, to);
                Block painted = dyed.get(to);
//                world.setBlock(pos, painted, Block.UPDATE_ALL);
//                return ActionResultType.SUCCESS;
                if (painted != null) {
                    world.setBlock(pos, painted.defaultBlockState(), BlockConstants.UPDATE_ALL);
                    return ActionResultType.SUCCESS;
                } else {
                    return ActionResultType.FAIL;
                }
            }
            // dyed -> ?
//            else if (state.getBlock() == dyed)
            else if (dyed.containsValue(state.getBlock())) {
                // the same colour
//                if (to == state.getValue(colourProp))
                if (dyed.get(to) == state.getBlock()) {
                    return ActionResultType.FAIL;
                }
                if (to == null) {
                    // to colorless
                    state = clear.defaultBlockState();
                } else {
                    // to another colour
//                    state = state.setValue(colourProp, to);
                    Block b = dyed.get(to);
                    if (b != null) {
                        state = b.defaultBlockState();
                    } else {
                        return ActionResultType.FAIL;
                    }
                }
                world.setBlock(pos, state, BlockConstants.UPDATE_ALL);
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.PASS;
        };
    }

    // Calen
    public static Map<DyeColor, ? extends Block> createColourBlockMap(String namespace, String pathSuffix) {
        Map<DyeColor, Block> ret = new HashMap<>();
        Arrays.stream(DyeColor.values()).forEach(
                c ->
                {
                    ResourceLocation blockName = new ResourceLocation(namespace, c.getSerializedName() + pathSuffix);
                    Block block = ForgeRegistries.BLOCKS.getValue(blockName);
                    if (block == null || block == Blocks.AIR)
                        throw new IllegalStateException("Unknown block: " + blockName.toString());
                    ret.put(c, block);
                }
        );
        return ret;
    }
}
