package buildcraft.compat;


import buildcraft.api.BCModules;
import net.minecraftforge.fml.ModList;

public abstract class CompatModuleBase
{
    public CompatModuleBase()
    {
    }

    public boolean canLoad()
    {
//        return Loader.isModLoaded(this.compatModId());
        return ModList.get().isLoaded(this.compatModId());
    }

    public abstract String compatModId();

    public String comment()
    {
        return null;
    }

    public void preInit()
    {
    }

    public void init()
    {
    }

    public void postInit()
    {
    }
}
