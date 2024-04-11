package buildcraft.api.imc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

// Calen: from 1.12.2 forge
public class BcImcMessage
{
    @Nonnull
    public final String key;
    @Nonnull
    private final Object value;

    public BcImcMessage(@Nonnull String key, @Nonnull Object value)
    {
        this.key = key;
        this.value = value;
    }

    public ResourceLocation getResourceLocationValue()
    {
        return (ResourceLocation) value;
    }

    public CompoundTag getNBTValue()
    {
        return (CompoundTag) value;
    }

    public boolean isNBTMessage()
    {
        return CompoundTag.class.isAssignableFrom(getMessageType());
    }

    public Class<?> getMessageType()
    {
        return value.getClass();
    }
}