package HxCKDMS.HxCSkills;

import HxCKDMS.HxCSkills.Guis.GuiHandler;
import HxCKDMS.HxCSkills.lib.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class HxCSkills
{
    @Mod.Instance(Reference.MOD_ID)
    public static HxCSkills instance;

    public static Config Config;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        Config = new Config(new Configuration(event.getSuggestedConfigurationFile()));
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        event.getModState();
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        event.getModState();
    }
}
