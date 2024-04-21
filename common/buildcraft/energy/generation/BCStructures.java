/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2021, TeamAppliedEnergistics, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package buildcraft.energy.generation;

import buildcraft.api.core.BCLog;
import buildcraft.energy.BCEnergy;
import buildcraft.energy.BCEnergyConfig;
import buildcraft.energy.generation.structure.$OilStructurePlacement;
import buildcraft.energy.generation.structure.EnergyStructureFeatureRegistry;
import buildcraft.energy.generation.structure.OilFeatureConfiguration;
import buildcraft.energy.generation.structure.OilStructureGenerator;
import buildcraft.lib.oredicttag.OreDictTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;

public final class BCStructures {
    // Calen for test
    public static final StructurePlacementType<$OilStructurePlacement> OIL = register("buildcraft:oil_well", $OilStructurePlacement.CODEC);

    private static <SP extends StructurePlacement> StructurePlacementType<SP> register(String id, Codec<SP> codec) {
        return Registry.register(Registry.STRUCTURE_PLACEMENT_TYPE, id, () -> codec);
    }


    public static void register(RegistryEvent.Register<StructureFeature<?>> event) {
        BCStructures.init(event.getRegistry());
    }

    public static Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_INSTANCE_OIL_STRUCTURE;

    // Calen: called if config allows
    public static void init(IForgeRegistry<StructureFeature<?>> registry) {
        if (BCEnergyConfig.enableOilGeneration) {
            registerStructure(
                    registry,
                    new ResourceLocation(BCEnergy.MODID, BCWorldGenNames.STRUCTURE_OIL_WELL),
                    EnergyStructureFeatureRegistry.OIL_WELL,
//                Decoration.TOP_LAYER_MODIFICATION
                    Decoration.FLUID_SPRINGS
            );

            CONFIGURED_INSTANCE_OIL_STRUCTURE = StructureFeatures.register(
                    EnergyStructureFeatureRegistry.STRUCTURE_FEATURE_KEY,
                    EnergyStructureFeatureRegistry.OIL_WELL.configured(
//                            NoneFeatureConfiguration.INSTANCE,
                            OilFeatureConfiguration.INSTANCE,
                            OreDictTags.OIL_GEN
                    )
            );

            StructureSets.register(
                    EnergyStructureFeatureRegistry.STRUCTURE_SET_KEY,
                    new StructureSet(
                            List.of(StructureSet.entry(BCStructures.CONFIGURED_INSTANCE_OIL_STRUCTURE)),
//                            new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 124895654)
                            // 好像中间这个调大一点可以提高生成概率
                            // 2 1 生成概率非常高 可成片生成 1 0 更高
                            // 0 -1 不生成
                            new RandomSpreadStructurePlacement(1, 0, RandomSpreadType.LINEAR, (int) OilStructureGenerator.MAGIC_GEN_NUMBER >> 32)
//                            new $OilStructurePlacement(1, 0, RandomSpreadType.LINEAR, (int) OilStructureGenerator.MAGIC_GEN_NUMBER >> 32)
//                            new RandomSpreadStructurePlacement(16, 8, RandomSpreadType.LINEAR, (int)OilStructureGenerator.MAGIC_GEN_NUMBER>>32)
//                            new RandomSpreadStructurePlacement(5, 8, RandomSpreadType.LINEAR, 124895654)
//                            new $OilStructurePlacement(32, 16, RandomSpreadType.LINEAR, 124895654, Vec3i.ZERO)
                    )
            );
            BCLog.logger.info("[energy.oilgen] Registered the oil spout generator");
        } else {
            BCLog.logger.info("[energy.oilgen] Not registering the oil spout generator, as it has been disabled by the config file.");
        }
    }

    // This mirrors the Vanilla registration method for structures, but uses the
    // Forge registry instead
    private static <F extends StructureFeature<?>> void registerStructure(
            IForgeRegistry<StructureFeature<?>> registry,
            ResourceLocation id,
            F structure,
            Decoration stage
    ) {
        StructureFeature.STEP.put(structure, stage);
        structure.setRegistryName(id);
        registry.register(structure);
    }

}
