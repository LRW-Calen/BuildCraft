package buildcraft.api.facades;

import buildcraft.silicon.item.ItemPluggableFacade;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.registries.RegistryObject;

public final class FacadeAPI
{
    public static final String IMC_MOD_TARGET = "buildcraftsilicon";
    public static final String IMC_FACADE_DISABLE = "facade_disable_block";
    public static final String IMC_FACADE_CUSTOM = "facade_custom_map_block_item";
    public static final String NBT_CUSTOM_BLOCK_REG_KEY = "block_registry_name";
    public static final String NBT_CUSTOM_BLOCK_META = "block_meta";
    public static final String NBT_CUSTOM_ITEM_STACK = "item_stack";

//    public static IFacadeItem facadeItem;
//    public static RegistryObject<IFacadeItem> facadeItem;
    public static RegistryObject<ItemPluggableFacade> facadeItem;
    public static IFacadeRegistry registry;

    private FacadeAPI()
    {

    }

    public static void disableBlock(Block block)
    {
//        FMLInterModComms.sendMessage(IMC_MOD_TARGET, IMC_FACADE_DISABLE, block.getRegistryName());
        InterModComms.sendTo(IMC_MOD_TARGET, IMC_FACADE_DISABLE, ()->block.getRegistryName());
    }

    public static void mapStateToStack(BlockState state, ItemStack stack)
    {
        CompoundTag nbt = new CompoundTag();
        nbt.putString(NBT_CUSTOM_BLOCK_REG_KEY, state.getBlock().getRegistryName().toString());
//        nbt.putInt(NBT_CUSTOM_BLOCK_META, state.getBlock().getMetaFromState(state));
        nbt.put(NBT_CUSTOM_BLOCK_META, NbtUtils.writeBlockState(state));
        nbt.put(NBT_CUSTOM_ITEM_STACK, stack.serializeNBT());
//        FMLInterModComms.sendMessage(IMC_MOD_TARGET, IMC_FACADE_CUSTOM, nbt);
        InterModComms.sendTo(IMC_MOD_TARGET, IMC_FACADE_CUSTOM, ()->nbt);
    }

    public static boolean isFacadeMessageId(String id)
    {
        return IMC_FACADE_CUSTOM.equals(id) //
                || IMC_FACADE_DISABLE.equals(id);
    }
}
