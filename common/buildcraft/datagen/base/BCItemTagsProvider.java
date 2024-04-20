package buildcraft.datagen.base;

import buildcraft.api.BCModules;
import buildcraft.core.BCCoreItems;
import buildcraft.lib.oredicttag.OreDictTags;
import buildcraft.transport.BCTransportItems;
import buildcraft.transport.pipe.PipeRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BCItemTagsProvider extends ItemTagsProvider {
    public BCItemTagsProvider(
            DataGenerator generator,
            ExistingFileHelper existingFileHelper,
            BlockTagsProvider blockTagsProvider
    ) {
        super(generator, blockTagsProvider, BCModules.BUILDCRAFT, existingFileHelper);
    }

    @Override
    protected void addTags() {
//        copyBlockTags();

        tag(OreDictTags.GEAR_WOOD)
                .add(BCCoreItems.gearWood.get())
        ;
        tag(OreDictTags.GEAR_STONE)
                .add(BCCoreItems.gearStone.get())
        ;
        tag(OreDictTags.GEAR_IRON)
                .add(BCCoreItems.gearIron.get())
        ;
        tag(OreDictTags.GEAR_GOLD)
                .add(BCCoreItems.gearGold.get())
        ;
        tag(OreDictTags.GEAR_DIAMOND)
                .add(BCCoreItems.gearDiamond.get())
        ;
        tag(OreDictTags.GEARS)
                .addTag(OreDictTags.GEAR_WOOD)
                .addTag(OreDictTags.GEAR_STONE)
                .addTag(OreDictTags.GEAR_IRON)
                .addTag(OreDictTags.GEAR_GOLD)
                .addTag(OreDictTags.GEAR_DIAMOND)
        ;

        tag(OreDictTags.WRENCH)
                .add(BCCoreItems.wrench.get())
        ;
        tag(OreDictTags.PAINT_BRUSH)
                .add((Item[]) BCCoreItems.colourBrushMap.values().stream().map(r -> r.get()).toArray(n -> new Item[n]))
        ;
        tag(OreDictTags.SEALANT)
                .addTag(Tags.Items.DYES_GREEN)
                .addTag(Tags.Items.SLIMEBALLS)
        ;

        tag(OreDictTags.WORKBENCHES_ITEM)
                .add(Items.CRAFTING_TABLE)
        ;

        tag(OreDictTags.CLAY)
                .add(Items.CLAY)
        ;

        // pipe plugs
        tag(OreDictTags.waterproof).add(BCTransportItems.waterproof.get());
        // pipes
        OreDictTags.pipeColorTags.forEach((c, t) ->
        {
            TagsProvider.TagAppender<Item> tagProvider = tag(t);
            PipeRegistry.INSTANCE.getAllRegisteredPipes().forEach(d -> tagProvider.add((Item) PipeRegistry.INSTANCE.getItemForPipe(d, c)));
        });

        tag(OreDictTags.pipeStructure).add(BCTransportItems.pipeStructure.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemWood).add(BCTransportItems.pipeItemWood.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeFluidWood).add(BCTransportItems.pipeFluidWood.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipePowerWood).add(BCTransportItems.pipePowerWood.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemStone).add(BCTransportItems.pipeItemStone.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeFluidStone).add(BCTransportItems.pipeFluidStone.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipePowerStone).add(BCTransportItems.pipePowerStone.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemCobble).add(BCTransportItems.pipeItemCobble.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeFluidCobble).add(BCTransportItems.pipeFluidCobble.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipePowerCobble).add(BCTransportItems.pipePowerCobble.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemQuartz).add(BCTransportItems.pipeItemQuartz.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeFluidQuartz).add(BCTransportItems.pipeFluidQuartz.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipePowerQuartz).add(BCTransportItems.pipePowerQuartz.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemGold).add(BCTransportItems.pipeItemGold.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeFluidGold).add(BCTransportItems.pipeFluidGold.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipePowerGold).add(BCTransportItems.pipePowerGold.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemSandstone).add(BCTransportItems.pipeItemSandstone.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeFluidSandstone).add(BCTransportItems.pipeFluidSandstone.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipePowerSandstone).add(BCTransportItems.pipePowerSandstone.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemIron).add(BCTransportItems.pipeItemIron.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeFluidIron).add(BCTransportItems.pipeFluidIron.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
//        tag(OreDictTag.pipePowerIron).add(BCTransportItems.pipePowerIron.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemDiamond).add(BCTransportItems.pipeItemDiamond.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeFluidDiamond).add(BCTransportItems.pipeFluidDiamond.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
//        tag(OreDictTag.pipePowerDiamond).add(BCTransportItems.pipePowerDiamond.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemDiaWood).add(BCTransportItems.pipeItemDiaWood.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeFluidDiaWood).add(BCTransportItems.pipeFluidDiaWood.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
//        tag(OreDictTag.pipePowerDiaWood).add(BCTransportItems.pipePowerDiaWood.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemClay).add(BCTransportItems.pipeItemClay.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeFluidClay).add(BCTransportItems.pipeFluidClay.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemVoid).add(BCTransportItems.pipeItemVoid.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeFluidVoid).add(BCTransportItems.pipeFluidVoid.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemObsidian).add(BCTransportItems.pipeItemObsidian.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
//        tag(OreDictTag.pipeFluidObsidian).add(BCTransportItems.pipeFluidObsidian.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemLapis).add(BCTransportItems.pipeItemLapis.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemDaizuli).add(BCTransportItems.pipeItemDaizuli.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemEmzuli).add(BCTransportItems.pipeItemEmzuli.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));
        tag(OreDictTags.pipeItemStripes).add(BCTransportItems.pipeItemStripes.values().stream().map(r -> (Item) r.get()).toArray(Item[]::new));

    }

    @Override
    public String getName() {
        return "BuildCraft Item Tags";
    }
}
