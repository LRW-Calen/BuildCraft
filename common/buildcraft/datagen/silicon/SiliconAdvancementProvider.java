package buildcraft.datagen.silicon;

import buildcraft.datagen.base.BCBaseAdvancementProvider;
import buildcraft.silicon.BCSilicon;
import buildcraft.silicon.BCSiliconBlocks;
import buildcraft.datagen.core.CoreAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

// Calen Completed
public class SiliconAdvancementProvider extends BCBaseAdvancementProvider
{
    private static final String NAMESPACE = BCSilicon.MOD_ID;
    private static Advancement ROOT;

    private static final ImpossibleTrigger.TriggerInstance IMPOSSIBLE = new ImpossibleTrigger.TriggerInstance();


    public static void registerSiliconAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper)
    {
        ROOT = CoreAdvancementProvider.ROOT;
        // fluid_storage
        Advancement laser_power = Advancement.Builder.advancement().display(
                        BCSiliconBlocks.laser.get(),
                        new TranslatableComponent("advancements.buildcraftsilicon.laser_power.title"),
                        new TranslatableComponent("advancements.buildcraftsilicon.laser_power.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(ROOT)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("get_laser",
                        InventoryChangeTrigger.TriggerInstance.hasItems(BCSiliconBlocks.laser.get())
                )
                .save(consumer, NAMESPACE + ":laser_power");
        // precision_crafting
        Advancement precision_crafting = Advancement.Builder.advancement().display(
                        BCSiliconBlocks.assemblyTable.get(),
                        new TranslatableComponent("advancements.buildcraftsilicon.precision_crafting.title"),
                        new TranslatableComponent("advancements.buildcraftsilicon.precision_crafting.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .parent(laser_power)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("code_trigger", IMPOSSIBLE)
                .save(consumer, NAMESPACE + ":precision_crafting");
    }
}
