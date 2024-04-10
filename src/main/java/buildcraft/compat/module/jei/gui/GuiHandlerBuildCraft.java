package buildcraft.compat.module.jei.gui;

import buildcraft.lib.gui.GuiBC8;
import buildcraft.lib.gui.IGuiElement;
import buildcraft.lib.gui.pos.GuiRectangle;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GuiHandlerBuildCraft implements IGuiContainerHandler<GuiBC8<?>>
{
    @NotNull
    @Override
    public List<Rect2i> getGuiExtraAreas(GuiBC8<?> guiDirty)
    {
//        return screen.getGuiExtraAreas();
        List<Rect2i> list = new ArrayList();

        for (IGuiElement element : guiDirty.mainGui.shownElements)
        {
            GuiRectangle rect = element.asImmutable();
            int x = (int) rect.x;
            int y = (int) rect.y;
//            int endX = MathHelper.ceil(rect.getEndX());
            int endX = Mth.ceil(rect.getEndX());
//            int endY = MathHelper.ceil(rect.getEndY());
            int endY = Mth.ceil(rect.getEndY());
            int width = endX - x;
            int height = endY - y;
            list.add(new Rect2i(x, y, width, height));
        }

        return list;
    }


//    @Nullable
//    @Override
//    public Object getIngredientUnderMouse(GuiBC8<?> screen, double mouseX, double mouseY)
//    {
//        return screen.getIngredientUnderMouse(mouseX, mouseY);
//    }

//    @NotNull
//    @Override
//    public Collection<IGuiClickableArea> getGuiClickableAreas(GuiBC8<?> screen, double mouseX,
//                                                                       double mouseY)
//    {
////                        if (screen instanceof InscriberScreen) {
////                            return Collections.singletonList(
////                                    IGuiClickableArea.createBasic(82, 39, 26, 16, InscriberRecipeCategory.RECIPE_TYPE));
////                        }
//
//        return Collections.emptyList();
//    }
}
