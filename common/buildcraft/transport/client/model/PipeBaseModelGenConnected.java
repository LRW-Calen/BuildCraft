/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.transport.client.model;

import buildcraft.api.transport.pipe.PipeApi;
import buildcraft.api.transport.pipe.PipeDefinition;
import buildcraft.transport.client.model.PipeModelCacheBase.PipeBaseCutoutKey;
import buildcraft.transport.client.model.PipeModelCacheBase.PipeBaseTranslucentKey;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public enum PipeBaseModelGenConnected implements IPipeBaseModelGen {
    INSTANCE;

    // Textures
    private static final Map<PipeDefinition, TextureAtlasSprite[]> sprites = new IdentityHashMap<>();

    @Override
//    public void onTextureStitchPre(TextureStitchEvent.Pre event)
    public void onTextureStitchPre() {
//        PipeBaseModelGenStandard.INSTANCE.onTextureStitchPre(event);
        PipeBaseModelGenStandard.INSTANCE.onTextureStitchPre();

        // TODO: Custom sprite creation + stitching
    }

    // Calen 1.20.1
    @Override
    public void onDatagenTextureRegister(Consumer<ResourceLocation> consumer) {
        PipeBaseModelGenStandard.INSTANCE.onDatagenTextureRegister(consumer);
    }

    @Override
    public void onTextureStitchPost(TextureStitchEvent.Post event) {
        PipeBaseModelGenStandard.INSTANCE.onTextureStitchPost(event);
    }

    @Override
    public TextureAtlasSprite[] getItemSprites(PipeDefinition def) {
        return PipeBaseModelGenStandard.INSTANCE.getItemSprites(def);
    }

    // Models

    @Override
    public List<BakedQuad> generateTranslucent(PipeBaseTranslucentKey key) {
        return PipeBaseModelGenStandard.INSTANCE.generateTranslucent(key);
    }

    @Override
    public List<BakedQuad> generateCutout(PipeBaseCutoutKey key) {
        return ImmutableList.of();
    }
}
