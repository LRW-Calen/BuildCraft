package buildcraft.compat.module.waila;

import buildcraft.lib.block.BlockBCTile_Neptune;
import buildcraft.lib.tile.TileBC_Neptune;
import mcp.mobius.waila.api.*;

@WailaPlugin
public class BCPluginJade implements IWailaPlugin
{
    // Calen: in 1.18.2 here should use Block or TE class instead of interface, it's too difficult to get all matched if allowing some BC modules absent,
    // so just use TileBC_Neptune and BlockBCTile_Neptune
    @Override
    public void register(IWailaCommonRegistration registrar)
    {
        IServerDataProvider autoCraftNbtProvider = new AutoCraftDataProvider.NBTProvider();
        IServerDataProvider laserTargetNbtProvider = new LaserTargetDataProvider.NBTProvider();
        IServerDataProvider assemblyCraftNbtProvider = new AssemblyCraftDataProvider.NBTProvider();

        registrar.registerBlockDataProvider(autoCraftNbtProvider, TileBC_Neptune.class);
        registrar.registerBlockDataProvider(laserTargetNbtProvider, TileBC_Neptune.class);
        registrar.registerBlockDataProvider(assemblyCraftNbtProvider, TileBC_Neptune.class);

//        for (Block b : ForgeRegistries.BLOCKS.getValues())
//        for (BlockEntityType<?> tet : ForgeRegistries.BLOCK_ENTITIES.getValues())
//        {
//            isTeClassOfTileEntityExtends(tet, IAutoCraft.class).ifPresent(c -> registrar.registerBlockDataProvider(autoCraftNbtProvider, c));
//            isTeClassOfTileEntityExtends(tet, ILaserTarget.class).ifPresent(c -> registrar.registerBlockDataProvider(laserTargetNbtProvider, c));
//            isTeClassOfTileEntityExtends(tet, IAssemblyCraft.class).ifPresent(c -> registrar.registerBlockDataProvider(assemblyCraftNbtProvider, c));
//
////            isTeClassOfBlockExtends(b, IAutoCraft.class).ifPresent(c -> registrar.registerBlockDataProvider(autoCraftNbtProvider, c));
////            isTeClassOfBlockExtends(b, ILaserTarget.class).ifPresent(c -> registrar.registerBlockDataProvider(laserTargetNbtProvider, c));
////            isTeClassOfBlockExtends(b, IAssemblyCraft.class).ifPresent(c -> registrar.registerBlockDataProvider(assemblyCraftNbtProvider, c));
//        }
    }

    @Override
    public void registerClient(IWailaClientRegistration registrar)
    {
        IComponentProvider autoCraftBodyProvider = new AutoCraftDataProvider.BodyProvider();
        IComponentProvider laserTargetBodyProvider = new LaserTargetDataProvider.BodyProvider();
        IComponentProvider assemblyCraftBodyProvider = new AssemblyCraftDataProvider.BodyProvider();

        registrar.registerComponentProvider(autoCraftBodyProvider, TooltipPosition.BODY, BlockBCTile_Neptune.class);
        registrar.registerComponentProvider(laserTargetBodyProvider, TooltipPosition.BODY, BlockBCTile_Neptune.class);
        registrar.registerComponentProvider(assemblyCraftBodyProvider, TooltipPosition.BODY, BlockBCTile_Neptune.class);

////        for (Block b : ForgeRegistries.BLOCKS.getValues())
//        for (BlockEntityType<?> tet : ForgeRegistries.BLOCK_ENTITIES.getValues())
//        {
////            isTeClassOfBlockExtends(b, IAutoCraft.class).ifPresent(c -> registrar.registerComponentProvider(autoCraftBodyProvider, TooltipPosition.BODY, b.getClass()));
////            isTeClassOfBlockExtends(b, ILaserTarget.class).ifPresent(c -> registrar.registerComponentProvider(laserTargetBodyProvider, TooltipPosition.BODY, b.getClass()));
////            isTeClassOfBlockExtends(b, IAssemblyCraft.class).ifPresent(c -> registrar.registerComponentProvider(assemblyCraftBodyProvider, TooltipPosition.BODY, b.getClass()));
//
//            isTeClassOfTileEntityExtends(tet, IAutoCraft.class).ifPresent(c ->
//                    tet.validBlocks.forEach(b ->
//                            registrar.registerComponentProvider(autoCraftBodyProvider, TooltipPosition.BODY, b.getClass())
//                    )
//            );
//            isTeClassOfTileEntityExtends(tet, ILaserTarget.class).ifPresent(c ->
//                    tet.validBlocks.forEach(b ->
//                            registrar.registerComponentProvider(laserTargetBodyProvider, TooltipPosition.BODY, b.getClass())
//                    )
//            );
//            isTeClassOfTileEntityExtends(tet, IAssemblyCraft.class).ifPresent(c ->
//                    tet.validBlocks.forEach(b ->
//                            registrar.registerComponentProvider(assemblyCraftBodyProvider, TooltipPosition.BODY, b.getClass())
//                    )
//            );
//
////            if (((ParameterizedType) tet.getClass().getGenericSuperclass()).getActualTypeArguments()[0] instanceof Class tec && IAutoCraft.class.isAssignableFrom(tec))
////            {
////                tet.validBlocks.forEach(b -> registrar.registerComponentProvider(autoCraftBodyProvider, TooltipPosition.BODY, ((Block) b).getClass()));
////            }
////            if (((ParameterizedType) tet.getClass().getGenericSuperclass()).getActualTypeArguments()[0] instanceof Class tec && ILaserTarget.class.isAssignableFrom(tec))
////            {
////                tet.validBlocks.forEach(b -> registrar.registerComponentProvider(laserTargetBodyProvider, TooltipPosition.BODY, ((Block) b).getClass()));
////            }
////            if (((ParameterizedType) tet.getClass().getGenericSuperclass()).getActualTypeArguments()[0] instanceof Class tec && IAssemblyCraft.class.isAssignableFrom(tec))
////            {
////                tet.validBlocks.forEach(b -> registrar.registerComponentProvider(assemblyCraftBodyProvider, TooltipPosition.BODY, ((Block) b).getClass()));
////            }
//        }
    }

//    private List<Class<?>> getAllClassesHasAnnotation(Class<?> annotation)
//    {
//        List<String> classNames = (List) ModList.get().getAllScanData().stream().flatMap(($) ->
//        {
//            return $.getAnnotations().stream();
//        }).filter(($) ->
//        {
//            if ($.annotationType().getClassName().equals(annotation.getName()))
//            {
//                String required = (String) $.annotationData().getOrDefault("value", "");
//                if (required.isEmpty() || ModList.get().isLoaded(required))
//                {
//                    return true;
//                }
//            }
//
//            return false;
//        }).map(ModFileScanData.AnnotationData::memberName).collect(Collectors.toList());
//
//        for (String className : classNames)
//        {
//            BCLog.logger.info("Starting looking fo c");
//
//            try
//            {
//                Class<?> clazz = Class.forName(className);
//                if (IWailaPlugin.class.isAssignableFrom(clazz))
//                {
//                    IWailaPlugin plugin = (IWailaPlugin) clazz.getDeclaredConstructor().newInstance();
//                    plugin.register(WailaRegistrar.INSTANCE);
//                    plugin.register(WailaCommonRegistration.INSTANCE);
//                    if (FMLEnvironment.dist.isClient())
//                    {
//                        plugin.registerClient(WailaClientRegistration.INSTANCE);
//                    }
//                }
//            }
//            catch (Throwable var7)
//            {
//                LOGGER.error("Error loading plugin at {}", className, var7);
//            }
//        }
//
//    }

//    private <T extends BlockEntity> LazyOptional<Class> isTeClassOfTileEntityExtends(BlockEntityType<T> tet, Class typeToMatch)
//    {
//
//        Class tec = ((Class) ((ParameterizedType) tet.factory.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
//        if (typeToMatch.isAssignableFrom(tec))
//        {
//            return LazyOptional.of(() -> tec);
//        }
//        return LazyOptional.empty();
//    }

//    private LazyOptional<Class> isTeClassOfBlockExtends(Block b, Class typeToMatch)
//    {
//        if (b instanceof EntityBlock)
//        {
//            Class c = getTileEntityClassOfBlock(b);
//            if (c != null && typeToMatch.isAssignableFrom(c))
//            {
//                return LazyOptional.of(() -> c);
//            }
//        }
//        return LazyOptional.empty();
//    }
//
//    private Class getTileEntityClassOfBlock(Block b)
//    {
//        if (b.getClass().getGenericSuperclass() instanceof ParameterizedType parameterizedType)
//        {
//            Type[] types = parameterizedType.getActualTypeArguments();
//            if (types.length != 0 && types[0] instanceof Class<?> tec)
//            {
//                return tec;
//            }
//        }
//        return null;
//    }
}
