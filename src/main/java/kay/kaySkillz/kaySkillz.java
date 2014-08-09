package kay.kaySkillz;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import kay.kayXEnchants.common.Config;
import kay.kayXEnchants.common.Events;
import kay.kayXEnchants.enchantment.*;
import kay.kayXEnchants.lib.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class kaySkillz
{
    @Instance(Reference.MOD_ID)
    public static kaySkillz instance;

    public static Config Config;

    public static Events events;
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        Config = new Config(new Configuration(event.getSuggestedConfigurationFile()));
        events = new Events();
        FMLCommonHandler.instance().bus().register(new Events());
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        event.getModState();
        //MinecraftServer server = MinecraftServer.getServer();
        //ICommandManager command = server.getCommandManager();
        //ServerCommandManager manager = (ServerCommandManager) command;
        //manager.registerCommand(new CommandWhatChanged());
        //manager.registerCommand(new CommandListBetaTesters());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        event.getModState();
    }
}
