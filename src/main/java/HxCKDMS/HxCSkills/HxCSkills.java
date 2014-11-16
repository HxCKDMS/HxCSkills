package HxCKDMS.HxCSkills;

import HxCKDMS.HxCSkills.Events.Events;
import HxCKDMS.HxCSkills.lib.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class HxCSkills
{
    @Instance(Reference.MOD_ID)
    public static HxCSkills instance;

    public static Config Config;

    public static Events events;
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        Config = new Config(new Configuration(event.getSuggestedConfigurationFile()));
        FMLCommonHandler.instance().bus().register(new Events());
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        event.getModState();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        event.getModState();
    }
}
