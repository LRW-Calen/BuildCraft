/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package buildcraft.core.block;


import buildcraft.api.enums.EnumEngineType;
import buildcraft.lib.engine.BlockEngineBase_BC8;
import buildcraft.lib.engine.TileEngineBase_BC8;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IBlockRenderProperties;

import java.util.function.Consumer;

public class BlockEngine_BC8 extends BlockEngineBase_BC8<EnumEngineType> {
    public BlockEngine_BC8(
            String id,
            BlockBehaviour.Properties properties,
            EnumEngineType type
    ) {
        super(
                id,
                properties,
                type
        );
    }

//    @Override
//    public Property<EnumEngineType> getEngineProperty()
//    {
//        return BuildCraftProperties.ENGINE_TYPE;
//    }

//    @Override
//    public EnumEngineType getEngineType(int meta)
//    {
//        return EnumEngineType.fromMeta(meta);
//    }

//    @Override
//    public String getUnlocalizedName()
//    {
////        return TagManager.getTag("block.engine.bc." + engine.unlocalizedTag, TagManager.EnumTagType.UNLOCALIZED_NAME);
//        return TagManager.getTag("block.engine.bc." + this.engineType.unlocalizedTag, TagManager.EnumTagType.UNLOCALIZED_NAME);
//    }

    // Calen for particles instead of missingno
    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IBlockRenderProperties> consumer) {
        consumer.accept(new IBlockRenderProperties() {

            @Override
//            public boolean addHitEffects(BlockState state, Level world, HitResultBC target, ParticleManager manager)
            public boolean addHitEffects(BlockState state, Level worldIn, HitResult hitIn, ParticleEngine manager) {
                if (hitIn.getType() != HitResult.Type.BLOCK) {
                    return false;
                }
                BlockHitResult target = (BlockHitResult) hitIn;
                ClientLevel world = (ClientLevel) worldIn;
                BlockEntity te = world.getBlockEntity(target.getBlockPos());
                if (te instanceof TileEngineBase_BC8 engine) {
                    double x = Math.random();
                    double y = Math.random();
                    double z = Math.random();

                    x += target.getLocation().x;
                    y += target.getLocation().y;
                    z += target.getLocation().z;

                    TerrainParticle particle = new TerrainParticle(world, x, y, z, 0, 0, 0, state);
                    particle.setPos(x, y, z);
                    TextureAtlasSprite texture = engineType.getPticle();
                    if (texture == null) {
                        return false;
                    }
                    particle.setSprite(texture);
                    particle.setPower(0.2F);
                    particle.scale(0.6F);
                    manager.add(particle);

                    return true;
                }

                return false;
            }

            @Override
            public boolean addDestroyEffects(BlockState state, Level worldIn, BlockPos pos, ParticleEngine manager) {
                ClientLevel world = (ClientLevel) worldIn;
                BlockEntity te = world.getBlockEntity(pos);
                if (te instanceof TileEngineBase_BC8 engine) {
                    int countX = 2;
                    int countY = 2;
                    int countZ = 2;

                    TextureAtlasSprite texture = engineType.getPticle();
                    if (texture == null) {
                        return false;
                    }

                    for (int x = 0; x < countX; x++) {
                        for (int y = 0; y < countY; y++) {
                            for (int z = 0; z < countZ; z++) {
                                double _x = pos.getX() + 0.5;
                                double _y = pos.getY() + 0.5;
                                double _z = pos.getZ() + 0.5;

                                TerrainParticle particle = new TerrainParticle(world, _x, _y, _z, 0, 0, 0, state);
                                particle.setPos(_x, _y, _z);
                                particle.setSprite(texture);
                                manager.add(particle);
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
