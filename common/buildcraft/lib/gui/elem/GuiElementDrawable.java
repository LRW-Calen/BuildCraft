/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.gui.elem;

import buildcraft.lib.expression.api.IExpressionNode.INodeBoolean;
import buildcraft.lib.expression.node.value.NodeConstantBoolean;
import buildcraft.lib.gui.BuildCraftGui;
import buildcraft.lib.gui.GuiElementSimple;
import buildcraft.lib.gui.ISimpleDrawable;
import buildcraft.lib.gui.pos.IGuiArea;
import net.minecraft.client.gui.GuiGraphics;

public class GuiElementDrawable extends GuiElementSimple {
    private final ISimpleDrawable drawable;
    private final INodeBoolean visible;
    private final boolean foreground;

    public GuiElementDrawable(BuildCraftGui gui, IGuiArea element, ISimpleDrawable drawable, boolean foreground) {
        this(gui, element, drawable, foreground, NodeConstantBoolean.TRUE);
    }

    public GuiElementDrawable(BuildCraftGui gui, IGuiArea element, ISimpleDrawable drawable, boolean foreground, INodeBoolean visible) {
        super(gui, element);
        this.drawable = drawable;
        this.visible = visible;
        this.foreground = foreground;
    }

    @Override
    public void drawBackground(float partialTicks, GuiGraphics guiGraphics) {
        if (!foreground) {
            draw(guiGraphics);
        }
    }

    @Override
    public void drawForeground(GuiGraphics guiGraphics, float partialTicks) {
        if (foreground) {
            draw(guiGraphics);
        }
    }

    private void draw(GuiGraphics guiGraphics) {
        if (visible.evaluate()) {
            drawable.drawAt(this, guiGraphics);
        }
    }
}
