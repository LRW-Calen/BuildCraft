/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.fluid;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class BCFluidBlock extends LiquidBlock
{
    private boolean sticky = false;

    public BCFluidBlock(Supplier<? extends FlowingFluid> p_54694_, BlockBehaviour.Properties properties, boolean sticky)
    {
        super(p_54694_, properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(LEVEL, Integer.valueOf(0))
        );
        this.sticky = sticky;
//        this.setRegistryName(namespace, path); // Calen: don't set reg name here, or will cause IllegalStateException at ForgeRegistryEntry:29
//        renderLayer = BlockRenderLayer.SOLID; // Calen: moved to BCEnergy#clientInit
    }

//    public BCFluidBlock(Fluid fluid, Material material)
//    {
//        super(fluid, material);
//    }

    // displacements.put(...)
    @Override
    public boolean canBeReplaced(BlockState p_56589_, BlockPlaceContext p_56590_)
    {
//        displacements.put(Blocks.WATER, displaceWater);
//        displacements.put(Blocks.FLOWING_WATER, displaceWater);
//
//        Boolean displaceLava = fluid.getDensity() > 9000;
//        displacements.put(Blocks.LAVA, displaceLava);
//        displacements.put(Blocks.FLOWING_LAVA, displaceLava);
//        RenderProperties.get(this).
//        RenderType.solid().
        Boolean displaceWater = this.getFluid().getAttributes().getDensity() > 1000;
        Boolean displaceLava = this.getFluid().getAttributes().getDensity() > 9000;
        Item itemInHand = p_56590_.getItemInHand().getItem();
        if (itemInHand == Items.WATER_BUCKET && displaceWater)
        {
            return false;
        }
        else if (itemInHand == Items.LAVA_BUCKET && displaceLava)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


//    @Override
//    public Boolean isEntityInsideMaterial(BlockAccess world, BlockPos pos, BlockState state, Entity entity, double yToTest, Material material, boolean testingHead)
//    {
//        if (material == Material.WATER)
//        {
//            return true;
//        }
//        return null;
//    }

    // public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        return this.material.isFlammable() ? 200 : 0;
    }

    // public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        return this.material.isFlammable() ? 200 : 0;
    }

    // public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    @Override
    public void entityInside(BlockState p_58180_, Level p_58181_, BlockPos p_58182_, Entity entityIn)
    {
        if (sticky)
        {
            entityIn.makeStuckInBlock(p_58180_, new Vec3(0.25D, (double) 0.05F, 0.25D));
        }
    }

//    public void setSticky(boolean sticky)
//    {
//        this.sticky = sticky;
//    }
}
