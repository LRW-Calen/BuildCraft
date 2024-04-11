package buildcraft.datagen.core;

import buildcraft.core.BCCore;
import buildcraft.core.BCCoreBlocks;
import buildcraft.core.BCCoreItems;
import buildcraft.datagen.base.BCBaseAdvancementProvider;
import buildcraft.lib.BCLibItems;
import buildcraft.lib.oredicttag.OreDictTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

// Calen Completed
public class CoreAdvancementProvider extends BCBaseAdvancementProvider
{
    private static final String NAMESPACE = BCCore.MOD_ID;

    public static Advancement ROOT;
    public static Advancement GUIDE;
    public static Advancement MARKERS;
    public static Advancement GEARS;
    public static Advancement WRENCHED;

    private static final ImpossibleTrigger.TriggerInstance IMPOSSIBLE = new ImpossibleTrigger.TriggerInstance();

    public static void registerCoreAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper)
    {
        // root
        Advancement root = Advancement.Builder.advancement().display(
                        BCCoreItems.gearWood.get(),
                        new TranslatableComponent("advancements.buildcraftcore.root.title"),
                        new TranslatableComponent("advancements.buildcraftcore.root.description"),
                        new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/adventure.png"),
                        FrameType.TASK,
                        false, false, false)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("has_stick",
//                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.STICK)
                        InventoryChangeTrigger.TriggerInstance.hasItems(tag(Tags.Items.RODS_WOODEN))
                )
                .save(consumer, NAMESPACE + ":root");
        ROOT = root;
        // gears
        Advancement gears = Advancement.Builder.advancement().display(
                        BCCoreItems.gearDiamond.get(),
                        new TranslatableComponent("advancements.buildcraftcore.gears.title"),
                        new TranslatableComponent("advancements.buildcraftcore.gears.description"),
                        null,
                        FrameType.GOAL,
                        true, true, false)
                .parent(root)
                .requirements(RequirementsStrategy.AND)
                .addCriterion("gear_wood",
//                        InventoryChangeTrigger.TriggerInstance.hasItems(BCCoreItems.gearWood.get())
                        InventoryChangeTrigger.TriggerInstance.hasItems(tag(OreDictTags.GEAR_WOOD))
                )
                .addCriterion("gear_stone",
//                        InventoryChangeTrigger.TriggerInstance.hasItems(BCCoreItems.gearStone.get())
                        InventoryChangeTrigger.TriggerInstance.hasItems(tag(OreDictTags.GEAR_STONE))
                )
                .addCriterion("gear_iron",
//                        InventoryChangeTrigger.TriggerInstance.hasItems(BCCoreItems.gearIron.get())
                        InventoryChangeTrigger.TriggerInstance.hasItems(tag(OreDictTags.GEAR_IRON))
                )
                .addCriterion("gear_gold",
//                        InventoryChangeTrigger.TriggerInstance.hasItems(BCCoreItems.gearGold.get())
                        InventoryChangeTrigger.TriggerInstance.hasItems(tag(OreDictTags.GEAR_GOLD))
                )
                .addCriterion("gear_diamond",
//                        InventoryChangeTrigger.TriggerInstance.hasItems(BCCoreItems.gearDiamond.get())
                        InventoryChangeTrigger.TriggerInstance.hasItems(tag(OreDictTags.GEAR_DIAMOND))
                )
                .save(consumer, NAMESPACE + ":gears");
        GEARS = gears;
        // wrenched
        Advancement wrenched = Advancement.Builder.advancement().display(
                        BCCoreItems.wrench.get(),
                        new TranslatableComponent("advancements.buildcraftcore.wrenched.title"),
                        new TranslatableComponent("advancements.buildcraftcore.wrenched.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(root)
                .requirements(RequirementsStrategy.OR)
                .addCriterion(
                        "code_trigger",
                        IMPOSSIBLE
                )
                .save(consumer, NAMESPACE + ":wrenched");
        WRENCHED = wrenched;
        // free_power
        Advancement free_power = Advancement.Builder.advancement().display(
                        BCCoreBlocks.engineWood.get(),
                        new TranslatableComponent("advancements.buildcraftcore.freePowar.title"),
                        new TranslatableComponent("advancements.buildcraftcore.freePowar.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(wrenched)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":free_power");
        // guide
        Advancement guide = Advancement.Builder.advancement().display(
                        BCLibItems.guide.get(),
                        new TranslatableComponent("advancements.buildcraftcore.guide.title"),
                        new TranslatableComponent("advancements.buildcraftcore.guide.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(root)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":guide");
        GUIDE = guide;
        // markers
        Advancement markers = Advancement.Builder.advancement().display(
                        BCCoreBlocks.markerVolume.get(),
                        new TranslatableComponent("advancements.buildcraftcore.markers.title"),
                        new TranslatableComponent("advancements.buildcraftcore.markers.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(guide)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":markers");
        MARKERS = markers;
        // list
        Advancement list = Advancement.Builder.advancement().display(
                        BCCoreItems.list.get(),
                        new TranslatableComponent("advancements.buildcraftcore.list.title"),
                        new TranslatableComponent("advancements.buildcraftcore.list.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(guide)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":list");
        // paper
        Advancement paper = Advancement.Builder.advancement().display(
                        Items.PAPER,
                        new TranslatableComponent("advancements.buildcraftcore.paper.title"),
                        new TranslatableComponent("advancements.buildcraftcore.paper.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(list)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":paper");
        // goggles
        Advancement goggles = Advancement.Builder.advancement().display(
                        // TODO Calen goggles texture
//                        BCCoreItems.GOOGLES.get(),
                        Items.IRON_HELMET,
                        new TranslatableComponent("advancements.buildcraftcore.goggles.title"),
                        new TranslatableComponent("advancements.buildcraftcore.goggles.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(guide)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":goggles");
        // path_markers
        Advancement path_markers = Advancement.Builder.advancement().display(
                        BCCoreBlocks.markerPath.get(),
                        new TranslatableComponent("advancements.buildcraftcore.path_markers.title"),
                        new TranslatableComponent("advancements.buildcraftcore.path_markers.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(markers)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":path_markers");
    }
}
