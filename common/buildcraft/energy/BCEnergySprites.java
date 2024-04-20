/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.energy;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = BCEnergy.MODID)
public class BCEnergySprites {
    // Calen: ModBusEvent in 1.18.2!
//    public static void fmlPreInit()
//    {
//        MinecraftForge.EVENT_BUS.register(BCEnergySprites.class);
//    }

//    public static final ITextureAtlasSpriteLoader OIL_SPRITE_LOADER = (
//            atlas,
//            resourceManager, textureInfo,
//            resource,
//            atlasWidth, atlasHeight,
//            spriteX, spriteY, mipmapLevel,
//            nativeImage
//    ) ->
//    {
//        return new AtlasSpriteFluid(atlas, textureInfo, atlasWidth, atlasHeight, spriteX, spriteY, mipmapLevel, nativeImage);
//    };
//
//    // Calen
//    @SubscribeEvent
//    public static void onTextureAtlasSpriteLoaderRegister(FMLClientSetupEvent event)
//    {
//        if (BCLibConfig.useSwappableSprites)
//        {
//            MinecraftForgeClient.registerTextureAtlasSpriteLoader(new ResourceLocation(NameSpaces.BUILDCRAFT_ENERGY, "oil_sprite_loader"), OIL_SPRITE_LOADER);
//        }
//
//    }

    @SubscribeEvent
    public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
        // TODO Calen frozen

//        TextureMap map = event.getMap();

//        if (!BCLibConfig.useSwappableSprites)
//        {
//            for (RegistryObject<BCFluid> fr : BCEnergyFluids.allFluids)
//            {
//                BCFluid f = fr.get();
//                // So this doesn't work properly as we don't have the sprites.
//                // but that's ok as we said that these don't work if disabled ~anyway~
////                map.registerSprite(f.getStill());
//                event.addSprite(f.getSource().getRegistryName());
////                map.registerSprite(f.getFlowing());
//                event.addSprite(f.getFlowing().getRegistryName());
//
////                // Calen test
////                Minecraft.getInstance().getTextureManager().register(f.getSource().getRegistryName(), missingTexture);
//            }
//            return;
//        }
    }

    @SubscribeEvent
    public static void onTextureStitchPost(TextureStitchEvent.Post event) {
        // TODO Calen frozen

//        ResourceLocation[][] fromSprites = new ResourceLocation[3][2];
//        for (int h = 0; h < 3; h++)
//        {
//            fromSprites[h][0] = new ResourceLocation("buildcraftenergy:blocks/fluids/heat_" + h + "_still");
//            fromSprites[h][1] = new ResourceLocation("buildcraftenergy:blocks/fluids/heat_" + h + "_flow");
//        }
//        for (RegistryObject<BCFluid> fr : BCEnergyFluids.allFluids)
//        {
//            BCFluid f = fr.get();
//            ResourceLocation[] sprites = fromSprites[f.getHeatValue()];
//            TextureAtlas map = event.getAtlas();
////            map.setTextureEntry(new AtlasSpriteFluid(f.getStill().toString(), sprites[0], f));
////            map.setTextureEntry(new AtlasSpriteFluid(f.getFlowing().toString(), sprites[1], f));
//
//            map.texturesByName.put(sprites[0], new AtlasSpriteFluid(f.getSource().toString(), sprites[0], f));
//            map.setTextureEntry(new AtlasSpriteFluid(f.getFlowing().toString(), sprites[1], f));
//
//            event.getAtlas().getSprite()
//        }

//        TextureAtlas map = event.getAtlas();
//        if (!map.location().equals(TextureAtlas.LOCATION_BLOCKS))
//        {
//            return;
//        }
//        ResourceLocation[][] fromSprites = new ResourceLocation[3][2];
//        for (int h = 0; h < 3; h++)
//        {
//            fromSprites[h][0] = new ResourceLocation("buildcraftenergy:blocks/fluids/heat_" + h + "_still");
//            fromSprites[h][1] = new ResourceLocation("buildcraftenergy:blocks/fluids/heat_" + h + "_flow");
//        }
//
//        for (RegistryObject<BCFluid> fr : BCEnergyFluids.allFluids)
//        {
//            BCFluid f = fr.get();
//            ResourceLocation[] sprites = fromSprites[f.getHeatValue()];
//            if (map.getSprite(sprites[0]) instanceof AtlasSpriteFluid still)
//            {
//                // Calen test
//                regSprite(map, f.getSource().getAttributes().getStillTexture(), still.copy(f.getSource().getAttributes().getStillTexture(), f));
//            }
//            if (map.getSprite(sprites[1]) instanceof AtlasSpriteFluid flow)
//            {
//                regSprite(map, f.getFlowing().getAttributes().getStillTexture(), flow.copy(f.getFlowing().getAttributes().getStillTexture(), f));
//            }
//        }
    }

    private static void regSprite(TextureAtlas map, ResourceLocation location, TextureAtlasSprite textureatlassprite) {

        map.sprites.add(location);
        map.texturesByName.put(location, textureatlassprite);
        try {
            textureatlassprite.uploadFirstFrame();
        }
        catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.forThrowable(throwable, "[BuildCraft Energy] Stitching texture atlas");
            CrashReportCategory crashreportcategory = crashreport.addCategory("[BuildCraft Energy] Texture being stitched together");
            crashreportcategory.setDetail("[BuildCraft Energy] Atlas path", map.location());
            crashreportcategory.setDetail("[BuildCraft Energy] Sprite", textureatlassprite);
            throw new ReportedException(crashreport);
        }

        Tickable tickable = textureatlassprite.getAnimationTicker();
        if (tickable != null) {
            map.animatedTextures.add(tickable);
        }
    }
}
