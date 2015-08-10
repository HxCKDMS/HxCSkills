package HxCKDMS.HxCSkills.Proxy;

import HxCKDMS.HxCSkills.Handlers.Keybindings;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {
    public void registerKeyBindings(){
        ClientRegistry.registerKeyBinding(Keybindings.BloodDestruction);
        ClientRegistry.registerKeyBinding(Keybindings.Stats);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        registerKeyBindings();
    }
    public void postInit(FMLPostInitializationEvent event) {}
    public void init(FMLInitializationEvent event) {}
}
