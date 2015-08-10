package HxCKDMS.HxCSkills;

import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCCore.api.Configuration.Category;
import HxCKDMS.HxCCore.api.Configuration.HxCConfig;
import HxCKDMS.HxCSkills.Commands.CommandBase;
import HxCKDMS.HxCSkills.Guis.GuiHandler;
import HxCKDMS.HxCSkills.Handlers.EventsHandler;
import HxCKDMS.HxCSkills.Handlers.KeybindHandler;
import HxCKDMS.HxCSkills.Proxy.IProxy;
import HxCKDMS.HxCSkills.lib.Reference;
import HxCKDMS.HxCSkills.network.BloodDestructionPacket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
@SuppressWarnings("unused")
public class HxCSkills {
    @Mod.Instance(Reference.MOD_ID)
    public static HxCSkills instance;

    public static SimpleNetworkWrapper Network;

    @SidedProxy(serverSide = "HxCKDMS.HxCSkills.Proxy.ServerProxy", clientSide = "HxCKDMS.HxCSkills.Proxy.ClientProxy")
    public static IProxy iProxy;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        Network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.CHANNEL_NAME);
        Network.registerMessage(BloodDestructionPacket.Handler.class, BloodDestructionPacket.class, 0, Side.SERVER);
        iProxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        iProxy.init(event);
        MinecraftForge.EVENT_BUS.register(new EventsHandler());
        FMLCommonHandler.instance().bus().register(new KeybindHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        event.getModState();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        iProxy.postInit(event);
        event.getModState();
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        CommandBase.initCommands(event);
    }

    public void registerNewConfigSys(HxCConfig config) {
        config.registerCategory(new Category("General", "General Stuff"));
        config.registerCategory(new Category("Skills", "Enabled skills, pre-requisites and etc..."));
        config.registerCategory(new Category("Tweaks", "Tweaks to skills, and balance and such..."));
        config.registerCategory(new Category("Commands", "Command permissions, enabled, etc..."));
        config.handleConfig(Configurations.class, new File(HxCCore.HxCConfigDir, "HxCSkills.cfg"));
    }
}
