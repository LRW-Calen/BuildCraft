package buildcraft.compat.module.crafttweaker;

import buildcraft.compat.CompatModuleBase;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;

public class CompatModuleCraftTweaker extends CompatModuleBase
{
    // TODO Calen /ct recipes manager <recipetype:buildcraftsilicon:assembly> -> error
    public CompatModuleCraftTweaker() {
    }

    public String compatModId() {
        return "crafttweaker";
    }

    public void preInit() {
//        CraftTweakerAPI.registerClass(AssemblyTable.class);
//        CraftTweakerAPI.getRegistry()..registerClass(AssemblyTable.class);
//        CraftTweakerAPI.registerClass(CombustionEngine.class);
    }
}
