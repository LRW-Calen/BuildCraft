package buildcraft.api.statements;

import buildcraft.api.core.IConvertable;
import buildcraft.api.core.render.ISprite;
import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public interface IGuiSlot extends IConvertable
{
    /**
     * Every statement needs a unique tag, it should be in the format of "&lt;modid&gt;:&lt;name&gt;".
     *
     * @return the unique id
     */
    BaseComponent getUniqueTag();

    /**
     * Return the description in the UI. Note that this should NEVER be called directly, instead this acts as a bridge
     * for {@link #getTooltip()}. (As such this might return null or throw an exception)
     */
    @OnlyIn(Dist.CLIENT)
//    String getDescription();
    Component getDescription();

    // Calen
    @OnlyIn(Dist.CLIENT)
    String getDescriptionKey();

    /**
     * @return The full tooltip for the UI.
     */
    @OnlyIn(Dist.CLIENT)
    default List<Component> getTooltip()
    {
//        String desc = getDescription();
        Component desc = getDescription();
        if (desc == null)
        {
            return ImmutableList.of();
        }
//        return ImmutableList.of(new TextComponent(desc));
        return ImmutableList.of(desc);
    }

    // Calen
    @OnlyIn(Dist.CLIENT)
    default List<String> getTooltipKey()
    {
        String desc = getDescriptionKey();
        if (desc == null)
        {
            return ImmutableList.of();
        }
//        return ImmutableList.of(new TextComponent(desc));
        return ImmutableList.of(desc);
    }

    /**
     * @return A sprite to show in a GUI, or null if this should not render a sprite.
     */
    @OnlyIn(Dist.CLIENT)
    @Nullable
    ISprite getSprite();
}
