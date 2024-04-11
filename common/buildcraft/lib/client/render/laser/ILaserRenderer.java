/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.client.render.laser;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector4f;

public interface ILaserRenderer
{
    void vertex(
//            PoseStack.Pose pose,
//            VertexConsumer bufferBuilder,
            Matrix4f matrix,
            Vector4f normal,
            double x, double y, double z,
            double u, double v,
            int lmap,
            int overlay,
            float nx,
            float ny,
            float nz,
            float diffuse
    );
}
