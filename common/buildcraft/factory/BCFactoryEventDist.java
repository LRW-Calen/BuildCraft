/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.factory;

public enum BCFactoryEventDist {
    INSTANCE;

//    @SubscribeEvent
//    @OnlyIn(Dist.CLIENT)
//    public static void textureStitchPre(TextureStitchEvent.Pre event)
//    {
//        RenderPump.textureStitchPre();
////        RenderMiningWell.textureStitchPre();
//    }

//    @SubscribeEvent
//    @OnlyIn(Dist.CLIENT)
//    public static void textureStitchPost(TextureStitchEvent.Post event)
//    {
//        if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS))
//        {
//            // Calen: don't call here! The event will be called several times, then the setWhiteTex(event) will be called duplicated to make the texture lean
//            // moved to RenderPump&RenderMiningWell#initWhiteTex()
//            RenderPump.textureStitchPost();
//            RenderMiningWell.textureStitchPost();
//        }
//    }
}
