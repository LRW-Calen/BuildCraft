package buildcraft.api.transport.pipe;

import buildcraft.transport.item.ItemPipeHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Consumer;

public interface IPipeRegistry
{
    PipeDefinition getDefinition(ResourceLocation identifier);

    void registerPipe(PipeDefinition definition);

    /**
     * Maps the given {@link PipeDefinition} to an {@link IItemPipe}. This acts exactly akin to
     * {@link Map#put(Object, Object)}.
     */
//    void setItemForPipe(PipeDefinition definition, @Nullable IItemPipe item);
//    void setItemForPipe(PipeDefinition definition, @Nullable RegistryObject<? extends IItemPipe> item);
    void setItemForPipe(PipeDefinition definition, Map<DyeColor, RegistryObject<? extends IItemPipe>> item);

    // Calen: reg different item object for different colour
    //    IItemPipe getItemForPipe(PipeDefinition definition);
    IItemPipe getItemForPipe(PipeDefinition definition, DyeColor colour);

    /**
     * Creates an {@link IItemPipe} for the given {@link PipeDefinition}. If the {@link PipeDefinition} has been
     * registered with {@link #registerPipe(PipeDefinition)} then it will also be registered with
     * {@link #setItemForPipe(PipeDefinition, IItemPipe)}. The returned item will be automatically registered with
     * forge.
     */
//    IItemPipe createItemForPipe(PipeDefinition definition);
//    RegistryObject<? extends IItemPipe> createItemForPipe(PipeDefinition definition);
    Map<DyeColor, RegistryObject<? extends IItemPipe>> createItemForPipe(PipeDefinition definition);

    // Calen: never used in 1.12.2
//    /** Identical to {@link #createItemForPipe(PipeDefinition)}, but doesn't require registering tags with buildcraftcore
//     * lib in order to register.
//     *
//     * @param postCreate A function to call in order to setup the {@link Item#setRegistryName(ResourceLocation)} and
//     *            {@link Item#setUnlocalizedName(String)}. */
//    IItemPipe createUnnamedItemForPipe(PipeDefinition definition, Consumer<Item> postCreate);

    Iterable<PipeDefinition> getAllRegisteredPipes();
}
