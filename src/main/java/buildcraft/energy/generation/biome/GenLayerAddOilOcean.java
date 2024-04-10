///*
// * Copyright (c) 2017 SpaceToad and the BuildCraft team
// * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
// * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
// */
//
//package buildcraftcore.energy.generation;
//
//import net.minecraft.world.level.biome.Biome;
//import net.minecraftforge.common.BiomeDictionary;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class GenLayerAddOilOcean extends GenLayerBiomeReplacer {
//    private static final double NOISE_FIELD_SCALE = 0.0005;
//    private static final double NOISE_FIELD_THRESHOLD = 0.9;
//
//    private static final List<BiomeDictionary.Type> REQUIRED_TYPES = Arrays.asList(BiomeDictionary.Type.OCEAN);
//
//    public GenLayerAddOilOcean(long worldSeed, long seed, GenLayer parent) {
//        super(worldSeed, seed, parent, NOISE_FIELD_SCALE, NOISE_FIELD_THRESHOLD, getOilBiomeId());
//    }
//
//    public static int getOilBiomeId() {
//        return Biome.getIdForBiome(BiomeOilOcean.INSTANCE);
//    }
//
//    @Override
//    protected boolean canReplaceBiome(int biomeId) {
//        Biome biome = Biome.getBiomeForId(biomeId);
//        if (biome == null) {
//            return false;
//        }
//        return BiomeDictionary.getTypes(biome).containsAll(REQUIRED_TYPES);
//    }
//}
