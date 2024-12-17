/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.client.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Set;

/** Defines an object that will hold a model, and is automatically refreshed from the filesystem when the client reloads
 * all of its resources. */
public abstract class ModelHolder {
    public final ResourceLocation modelLocation;
    protected String failReason = "";

    public ModelHolder(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;
        ModelHolderRegistry.HOLDERS.add(this);
    }

    public ModelHolder(String modelLocation) {
        this(new ResourceLocation(modelLocation));
    }

    protected abstract void onModelBake();

    protected abstract void onTextureStitchPre(Set<ResourceLocation> toRegisterSprites);

    // Calen 1.20.1
    protected abstract void onDatagenTextureRegister(Set<ResourceLocation> toRegisterSprites, ExistingFileHelper fileHelper);

    public abstract boolean hasBakedQuads();
}
