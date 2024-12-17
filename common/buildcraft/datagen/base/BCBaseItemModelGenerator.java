package buildcraft.datagen.base;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class BCBaseItemModelGenerator extends ItemModelProvider {
    protected static final ResourceLocation GENERATED = new ResourceLocation("minecraft", "item/generated");
    protected static final ResourceLocation CUBE_ALL = new ResourceLocation("minecraft", "block/cube_all");
    protected static final ResourceLocation HANDHELD = new ResourceLocation("minecraft", "item/handheld");
    protected static final ResourceLocation BLOCK = new ResourceLocation("minecraft", "block/block");
    protected static final ModelFile BUILTIN_ENTITY = new ModelFile.UncheckedModelFile(new ResourceLocation("minecraft", "builtin/entity"));

    public BCBaseItemModelGenerator(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }
}
