//package buildcraft.api.calen;
//
//import buildcraft.api.core.BCLog;
//import com.google.common.collect.Sets;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.entity.BlockEntity;
//
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//// Calen
//public class SpecialInfoRegistry
//{
//    private static ConcurrentHashMap<Class<?>, CopyOnWriteArraySet<Class<? extends Block>>> interface2Block = new ConcurrentHashMap<>();
//    private static ConcurrentHashMap<Class<?>, CopyOnWriteArraySet<Class<? extends BlockEntity>>> interface2TileEntity = new ConcurrentHashMap<>();
//
//    public static void register(Class<?> interfaceClass, Class<? extends Block> blockClass, Class<? extends BlockEntity> tileEntityClass)
//    {
//        if (interfaceClass == null || blockClass == null || tileEntityClass == null)
//        {
//            BCLog.logger.warn("[api.special_info_reg] Classes should ne NoneNull! [" + interfaceClass + "] [" + blockClass + "] [" + tileEntityClass + "]");
//        }
//        interface2Block.computeIfAbsent(interfaceClass, c ->
//        {
//            CopyOnWriteArraySet<Class<? extends Block>> set = new CopyOnWriteArraySet<>();
//            set.add(blockClass);
//            return set;
//        });
//        interface2TileEntity.computeIfAbsent(interfaceClass, c ->
//        {
//            CopyOnWriteArraySet<Class<? extends BlockEntity>> set = new CopyOnWriteArraySet<>();
//            set.add(tileEntityClass);
//            return set;
//        });
//    }
//
//    public static Set<Class<? extends Block>> getAllBlocksFor(Class<?> interfaceClass)
//    {
//        CopyOnWriteArraySet<Class<? extends Block>> ret = interface2Block.get(interfaceClass);
//        return ret == null ? Sets.newHashSet() : ret;
//    }
//
//    public static Set<Class<? extends BlockEntity>> getAllTileEntitiesFor(Class<?> interfaceClass)
//    {
//        CopyOnWriteArraySet<Class<? extends BlockEntity>> ret = interface2TileEntity.get(interfaceClass);
//        return ret == null ? Sets.newHashSet() : ret;
//    }
//}
