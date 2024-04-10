//package buildcraft.compat.network;
//
//import buildcraft.compat.BCCompat;
//import buildcraft.compat.CompatUtils;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.entity.BlockEntity;
//
//import javax.annotation.Nullable;
//
//public enum CompatGui
//{
//    FORESTRY_PROPOLIS_PIPE(CompatGui.IGuiTarget.TILE);
//
//    static final CompatGui[] VALUES = values();
//    //    @SidedProxy(
////            modId = "buildcraftcompat"
////    )
//    public static CommonProxy guiHandlerProxy;
//    public final IGuiTarget target;
//
//    private CompatGui(IGuiTarget target)
//    {
//        this.target = target;
//    }
//
//    public void openGui(EntityPlayer player)
//    {
//        this.openGui(player, 0, 0, 0, 0);
//    }
//
//    public void openGui(EntityPlayer player, BlockPos pos)
//    {
//        this.openGui(player, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), 0);
//    }
//
//    public void openGui(EntityPlayer player, int x, int y, int z)
//    {
//        this.openGui(player, x, y, z, 0);
//    }
//
//    public void openGui(EntityPlayer player, int data)
//    {
//        this.openGui(player, 0, 0, 0, data);
//    }
//
//    public void openGui(EntityPlayer player, BlockPos pos, int data)
//    {
//        this.openGui(player, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), data);
//    }
//
//    public void openGui(EntityPlayer player, int x, int y, int z, int data)
//    {
//        player.openGui(BCCompat.instance, packGui(this, data), player.field_70170_p, x, y, z);
//    }
//
//    protected static int packGui(Enum<?> gui, int data)
//    {
//        if (data >= 0 && data <= 16777215)
//        {
//            return data << 8 | gui.ordinal();
//        }
//        else
//        {
//            throw new IllegalArgumentException("Data must be between 0 and 0xFF_FF_FF (inclusive)");
//        }
//    }
//
//    @Nullable
//    protected static CompatGui getGui(int id)
//    {
//        id &= 255;
//        return id >= 0 && id < VALUES.length ? VALUES[id] : null;
//    }
//
//    protected static int getData(int id)
//    {
//        return id >>> 8;
//    }
//
//    public static class ClientProxy extends CommonProxy
//    {
//        public ClientProxy()
//        {
//        }
//
//        @Nullable
//        public Object getClientGuiElement(int id, Player player, Level world, int x, int y, int z)
//        {
//            IGuiCreator creator = getGuiCreator(id, player, world, x, y, z);
//            return creator == null ? null : creator.getClientGuiElement(CompatGui.getData(id), player);
//        }
//    }
//
//    public static class ServerProxy extends CommonProxy
//    {
//        public ServerProxy()
//        {
//        }
//
//        @Nullable
//        public Object getClientGuiElement(int id, Player player, Level world, int x, int y, int z)
//        {
//            return null;
//        }
//    }
//
//    //    public abstract static class CommonProxy implements IGuiHandler
//    public abstract static class CommonProxy
//    {
//        public CommonProxy()
//        {
//        }
//
//        @Nullable
//        protected static IGuiCreator getGuiCreator(int id, Player player, Level world, int x, int y, int z)
//        {
//            CompatGui type = CompatGui.getGui(id);
//            int data = CompatGui.getData(id);
//            if (type == null)
//            {
//                return null;
//            }
//            else
//            {
//                IGuiCreator creator = type.target.getCreator(player, world, x, y, z, data);
//                return creator != null && creator.getGuiType() == type ? creator : null;
//            }
//        }
//
//        @Nullable
//        public Object getServerGuiElement(int id, Player player, Level world, int x, int y, int z)
//        {
//            IGuiCreator creator = getGuiCreator(id, player, world, x, y, z);
//            return creator == null ? null : creator.getServerGuiElement(CompatGui.getData(id), player);
//        }
//    }
//
//    @FunctionalInterface
//    public interface IGuiTarget
//    {
//        IGuiTarget TILE = (player, world, x, y, z, data) ->
//        {
//            BlockEntity tile = world.getBlockEntity(new BlockPos(x, y, z));
//            if (tile instanceof IGuiCreator)
//            {
//                return (IGuiCreator) tile;
//            }
//            else
//            {
//                return tile != null ? (IGuiCreator) tile.getCapability(CompatUtils.CAP_GUI_CREATOR, null) : null;
//            }
//        };
//
//        @Nullable
//        IGuiCreator getCreator(Player var1, Level var2, int var3, int var4, int var5, int var6);
//    }
//}
