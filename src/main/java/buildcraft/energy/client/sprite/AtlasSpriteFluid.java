///*
// * Copyright (c) 2017 SpaceToad and the BuildCraft team
// * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
// * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
// */
//
//package buildcraft.energy.client.sprite;
//
//import buildcraft.api.core.BCLog;
//import buildcraft.lib.fluid.BCFluid;
//import buildcraft.lib.misc.SpriteUtil;
//import com.mojang.blaze3d.platform.NativeImage;
//import net.minecraft.client.renderer.texture.TextureAtlas;
//import net.minecraft.client.renderer.texture.TextureAtlasSprite;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.packs.resources.ResourceManager;
//
//import java.util.function.Function;
//
////public class AtlasSpriteFluid extends AtlasSpriteSwappable
//public class AtlasSpriteFluid extends TextureAtlasSprite
//{
////    final ResourceLocation fromName;
////    final BCFluid fluid;
////    final int colourLight, colourDark;
//
//    // Calen
//    private TextureAtlasSprite.Info info;
//    private int atlasWidth, atlasHeight, spriteX, spriteY, mipmapLevel;
//    private NativeImage nativeImage;
//
//    //    public AtlasSpriteFluid(String baseName, ResourceLocation fromName, BCFluid fluid)
//    public AtlasSpriteFluid(TextureAtlas atlas, TextureAtlasSprite.Info textureInfo, int atlasWidth, int atlasHeight, int spriteX, int spriteY, int mipmapLevel, NativeImage nativeImage)
//    {
////        super(baseName);
//        super(atlas, textureInfo, atlasWidth, atlasHeight, spriteX, spriteY, mipmapLevel, nativeImage);
////        this.fromName = textureInfo.name();
////        this.fluid = null;
////        colourLight = -1;
////        colourDark = -1;
//
//        // Calem
//        this.info = textureInfo;
//        this.atlasWidth = atlasWidth;
//        this.atlasHeight = atlasHeight;
//        this.spriteX = spriteX;
//        this.spriteY = spriteY;
//        this.mipmapLevel = mipmapLevel;
//        this.nativeImage = nativeImage;
//    }
//
//    //    public AtlasSpriteFluid(String baseName, ResourceLocation fromName, BCFluid fluid)
//    public AtlasSpriteFluid(BCFluid fluid, ResourceLocation fromName, TextureAtlas atlas, TextureAtlasSprite.Info textureInfo, int atlasWidth, int atlasHeight, int spriteX, int spriteY, int mipmapLevel, NativeImage nativeImage)
//    {
////        super(baseName);
//        super(atlas, textureInfo, atlasWidth, atlasHeight, spriteX, spriteY, mipmapLevel, nativeImage);
////        this.fromName = fromName;
////        this.fluid = fluid;
////        colourLight = fluid.getLightColour();
////        colourDark = fluid.getDarkColour();
////
////        // Calem
////        this.info = textureInfo;
////        this.atlasWidth = atlasWidth;
////        this.atlasHeight = atlasHeight;
////        this.spriteX = spriteX;
////        this.spriteY = spriteY;
////        this.mipmapLevel = mipmapLevel;
////        this.nativeImage = nativeImage;
//    }
//
//    // Calen
//    public AtlasSpriteFluid copy(ResourceLocation baseName, BCFluid fluid)
//    {
////        ResourceLocation from = SpriteUtil.transformLocation(fromName);
////        TextureAtlasSprite sprite = loadSprite(manager, from.toString(), from, true);
////        if (sprite == null)
////        {
////            BCLog.logger.warn("Unable to recolour " + from + " as it couldn't be loaded!");
//////            return true;
////        }
////        for (int f = 0; f < sprite.getFrameCount(); f++)
//        NativeImage newNativeImage = new NativeImage(this.nativeImage.getWidth(), this.nativeImage.getWidth(), false);
//        newNativeImage.copyFrom(this.nativeImage);
////        for (int f = 0; f < this.getFrameCount(); f++)
////        {
//////            recolourFrame(sprite, f);
////        }
//        recolour(newNativeImage);
////        swapWith(sprite);
////        swapWith(newNativeImage);
//        return new AtlasSpriteFluid(fluid, info.name(), super.atlas(), new Info(baseName, info.width(), info.height(), info.metadata), atlasWidth, atlasHeight, spriteX, spriteY, mipmapLevel, newNativeImage);
//    }
//
////    @Override
////    public boolean load(IResourceManager manager, ResourceLocation location, Function<ResourceLocation, TextureAtlasSprite> textureGetter)
////    {
////        ResourceLocation from = SpriteUtil.transformLocation(fromName);
////        TextureAtlasSprite sprite = loadSprite(manager, from.toString(), from, true);
////        if (sprite == null)
////        {
////            BCLog.logger.warn("Unable to recolour " + from + " as it couldn't be loaded!");
////            return true;
////        }
////        for (int f = 0; f < sprite.getFrameCount(); f++)
////        {
////            recolourFrame(sprite, f);
////        }
////        swapWith(sprite);
////        return false;
////    }
//
////    //    private void recolourFrame(TextureAtlasSprite sprite, int f)
////    private void recolourFrame(NativeImage sprite, int f)
////    {
////        int[][] frameData = sprite.getFrameTextureData(f);
////        if (frameData != null)
////        {
////            // frameData[0] is mipmap 0
////            int[] pixels = frameData[0];
////            for (int i = 0; i < pixels.length; i++)
////            {
////                recolourPixel(pixels, i);
////            }
////        }
////    }
//
//    private void recolour(NativeImage image)
//    {
//
////        for (int x = 0; x < image.getWidth(); x++)
////        {
////            for (int y = 0; y < image.getHeight(); y++)
////            {
////
////                int rgba = image.getPixelRGBA(x, y);
////                int r = recolourSubPixel(rgba, 0);
////                int g = recolourSubPixel(rgba, 8);
////                int b = recolourSubPixel(rgba, 16);
////                int a = 0xFF;// recolourSubPixel(rgba, 24);
////                image.setPixelRGBA(x, y, (a << 24) | (b << 16) | (g << 8) | r);
////            }
////        }
//    }
//
////    //    private void recolourPixel(int[] pixels, int i)
////    private void recolourPixel(NativeImage image, int i)
////    {
////        int rgba = pixels[i];
////        int r = recolourSubPixel(rgba, 0);
////        int g = recolourSubPixel(rgba, 8);
////        int b = recolourSubPixel(rgba, 16);
////        int a = 0xFF;// recolourSubPixel(rgba, 24);
////        pixels[i] = (a << 24) | (b << 16) | (g << 8) | r;
////    }
//
////    private int recolourSubPixel(int rgba, int offset)
////    {
////        int data = (rgba >>> offset) & 0xFF;
////        int dark = (colourDark >>> offset) & 0xFF;
////        int light = (colourLight >>> offset) & 0xFF;
////        return (dark * (256 - data) + light * data) / 256;
////    }
//}
