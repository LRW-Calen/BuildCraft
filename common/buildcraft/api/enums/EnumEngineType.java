package buildcraft.api.enums;

import buildcraft.api.core.IEngineType;
import buildcraft.lib.client.model.ModelHolderVariable;
import buildcraft.lib.client.model.MutableQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum EnumEngineType implements StringRepresentable, IEngineType
{
    WOOD("core", "wood"),
    STONE("energy", "stone"),
    IRON("energy", "iron"),
    //    CREATIVE("energy", "creative"),
    CREATIVE("core", "creative"),
    ;

    public final String unlocalizedTag;
    public final String resourceLocation;

    public static final EnumEngineType[] VALUES = values();

    // Calen
    @OnlyIn(Dist.CLIENT)
    private ModelHolderVariable model;
    @OnlyIn(Dist.CLIENT)
    private TextureAtlasSprite particle;

    EnumEngineType(String mod, String loc)
    {
        unlocalizedTag = loc;
        resourceLocation = "buildcraft" + mod + ":blocks/engine/inv/" + loc;
    }

    @OnlyIn(Dist.CLIENT)
    public void setModel(ModelHolderVariable model)
    {
        this.model = model;
    }

    @OnlyIn(Dist.CLIENT)
    public ModelHolderVariable getModel()
    {
        return model;
    }

    @OnlyIn(Dist.CLIENT)
    public TextureAtlasSprite getPticle()
    {
        if (particle == null)
        {
            for (MutableQuad quad : model.getCutoutQuads())
            {
                if (quad.getFace() == Direction.DOWN)
                {
                    particle = quad.getSprite();
                    break;
                }
            }
        }
        return particle;
    }

    @Override
    public String getItemModelLocation()
    {
        return resourceLocation;
    }

    @Override
    public String getSerializedName()
    {
        return unlocalizedTag;
    }

    public static EnumEngineType fromMeta(int meta)
    {
        if (meta < 0 || meta >= VALUES.length)
        {
            meta = 0;
        }
        return VALUES[meta];
    }
}
