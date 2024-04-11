package buildcraft.datagen.base;

import buildcraft.builders.BCBuildersBlocks;
import buildcraft.core.BCCore;
import buildcraft.core.BCCoreBlocks;
import buildcraft.factory.BCFactoryBlocks;
import buildcraft.lib.oredicttag.OreDictTags;
import buildcraft.silicon.BCSiliconBlocks;
import buildcraft.transport.BCTransportBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.nio.file.Path;

public class BCBlockTagsProvider extends net.minecraft.data.tags.BlockTagsProvider
{
    public BCBlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, BCCore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags()
    {
        tag(OreDictTags.WORKBENCHES_BLOCK)
                .add(Blocks.CRAFTING_TABLE)
        ;

        tag(BlockTags.DRAGON_IMMUNE)
                .add(BCCoreBlocks.springWater.get())
                .add(BCCoreBlocks.springOil.get())
                .add(BCFactoryBlocks.tube.get())
        ;

        tag(BlockTags.WITHER_IMMUNE)
                .add(BCCoreBlocks.springWater.get())
                .add(BCCoreBlocks.springOil.get())
                .add(BCFactoryBlocks.tube.get())
        ;


        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        BCCoreBlocks.engineBlockMap.values().stream().map(r -> r.get()).toArray(Block[]::new)
                )
                .add(
                        BCFactoryBlocks.autoWorkbenchItems.get(),
                        BCFactoryBlocks.chute.get(),
                        BCFactoryBlocks.distiller.get(),
                        BCFactoryBlocks.heatExchange.get(),
                        BCFactoryBlocks.tank.get(),
                        BCFactoryBlocks.pump.get(),
                        BCFactoryBlocks.miningWell.get(),
                        BCFactoryBlocks.waterGel.get(),
                        BCFactoryBlocks.floodGate.get(),
                        BCBuildersBlocks.quarry.get(),
                        BCBuildersBlocks.builder.get(),
                        BCBuildersBlocks.architect.get(),
                        BCBuildersBlocks.filler.get(),
                        BCBuildersBlocks.library.get(),
                        BCBuildersBlocks.replacer.get(),
                        BCSiliconBlocks.integrationTable.get(),
                        BCSiliconBlocks.assemblyTable.get(),
                        BCSiliconBlocks.advancedCraftingTable.get(),
                        BCSiliconBlocks.programmingTable.get(),
                        BCSiliconBlocks.laser.get(),
                        BCSiliconBlocks.chargingTable.get(),
                        BCTransportBlocks.filteredBuffer.get()
                )
        ;

//        // block
//        tag(OreDictTag.PIPE)
//                .add(BCCoreBlocks.PIPE_WOOD.get());

//        // Black- and whitelist tags
//        tag(AETags.SPATIAL_BLACKLIST).add(Blocks.BEDROCK);
//        tag(AETags.ANNIHILATION_PLANE_BLOCK_BLACKLIST);
//        tag(AETags.FACADE_BLOCK_WHITELIST)
//                .add(Blocks.GLASS,
//                        AEBlocks.QUARTZ_GLASS.block(),
//                        AEBlocks.QUARTZ_VIBRANT_GLASS.block())
//                .addTag(ConventionTags.STAINED_GLASS_BLOCK);
//
//        tag(ConventionTags.CERTUS_QUARTZ_ORE_BLOCK)
//                .add(AEBlocks.QUARTZ_ORE.block())
//                .add(AEBlocks.DEEPSLATE_QUARTZ_ORE.block());
//
//        tag(ConventionTags.ORES)
//                .addTag(ConventionTags.CERTUS_QUARTZ_ORE_BLOCK);
//
//        tag(ConventionTags.CERTUS_QUARTZ_STORAGE_BLOCK_BLOCK)
//                .add(AEBlocks.QUARTZ_BLOCK.block());
//        tag(Tags.Blocks.STORAGE_BLOCKS)
//                .addTag(ConventionTags.CERTUS_QUARTZ_STORAGE_BLOCK_BLOCK);
//
//        tag(ConventionTags.TERRACOTTA_BLOCK).add(
//                Blocks.TERRACOTTA,
//                Blocks.WHITE_TERRACOTTA,
//                Blocks.ORANGE_TERRACOTTA,
//                Blocks.MAGENTA_TERRACOTTA,
//                Blocks.LIGHT_BLUE_TERRACOTTA,
//                Blocks.YELLOW_TERRACOTTA,
//                Blocks.LIME_TERRACOTTA,
//                Blocks.PINK_TERRACOTTA,
//                Blocks.GRAY_TERRACOTTA,
//                Blocks.LIGHT_GRAY_TERRACOTTA,
//                Blocks.CYAN_TERRACOTTA,
//                Blocks.PURPLE_TERRACOTTA,
//                Blocks.BLUE_TERRACOTTA,
//                Blocks.BROWN_TERRACOTTA,
//                Blocks.GREEN_TERRACOTTA,
//                Blocks.RED_TERRACOTTA,
//                Blocks.BLACK_TERRACOTTA);
//
//        // Special behavior is associated with this tag, so our walls need to be added to it
//        tag(BlockTags.WALLS).add(
//                AEBlocks.SKY_STONE_WALL.block(),
//                AEBlocks.SMOOTH_SKY_STONE_WALL.block(),
//                AEBlocks.SKY_STONE_BRICK_WALL.block(),
//                AEBlocks.SKY_STONE_SMALL_BRICK_WALL.block(),
//                AEBlocks.FLUIX_WALL.block(),
//                AEBlocks.QUARTZ_WALL.block(),
//                AEBlocks.CHISELED_QUARTZ_WALL.block(),
//                AEBlocks.QUARTZ_PILLAR_WALL.block());
//
//        // Fixtures should cause walls to have posts
//        tag(BlockTags.WALL_POST_OVERRIDE).add(AEBlocks.QUARTZ_FIXTURE.block(), AEBlocks.LIGHT_DETECTOR.block());
//
//        addEffectiveTools();
    }

//    /**
//     * All sky-stone related blocks should be minable with iron-pickaxes and up.
//     */
//    private static final BCBlockDefinition<?>[] SKY_STONE_BLOCKS = {
//            AEBlocks.SKY_STONE_BLOCK,
//            AEBlocks.SMOOTH_SKY_STONE_BLOCK,
//            AEBlocks.SKY_STONE_BRICK,
//            AEBlocks.SKY_STONE_SMALL_BRICK,
//            AEBlocks.SKY_STONE_CHEST,
//            AEBlocks.SMOOTH_SKY_STONE_CHEST,
//            AEBlocks.SKY_STONE_STAIRS,
//            AEBlocks.SMOOTH_SKY_STONE_STAIRS,
//            AEBlocks.SKY_STONE_BRICK_STAIRS,
//            AEBlocks.SKY_STONE_SMALL_BRICK_STAIRS,
//            AEBlocks.SKY_STONE_WALL,
//            AEBlocks.SMOOTH_SKY_STONE_WALL,
//            AEBlocks.SKY_STONE_BRICK_WALL,
//            AEBlocks.SKY_STONE_SMALL_BRICK_WALL,
//            AEBlocks.SKY_STONE_SLAB,
//            AEBlocks.SMOOTH_SKY_STONE_SLAB,
//            AEBlocks.SKY_STONE_BRICK_SLAB,
//            AEBlocks.SKY_STONE_SMALL_BRICK_SLAB
//    };
//
//    private void addEffectiveTools() {
//        Map<BCBlockDefinition<?>, List<TagKey<Block>>> specialTags = new HashMap<>();
//        for (var skyStoneBlock : SKY_STONE_BLOCKS) {
//            specialTags.put(skyStoneBlock, List.of(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL));
//        }
//        var defaultTags = List.of(BlockTags.MINEABLE_WITH_PICKAXE);
//
//        for (var block : AEBlocks.getBlocks()) {
//            for (var desiredTag : specialTags.getOrDefault(block, defaultTags)) {
//                tag(desiredTag).add(block.block());
//            }
//        }
//
//    }

    @Override
    protected Path getPath(ResourceLocation id)
    {
        return this.generator.getOutputFolder()
                .resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
    }

    @Override
    public String getName()
    {
        return "BuildCraft Block Tags";
    }
}

