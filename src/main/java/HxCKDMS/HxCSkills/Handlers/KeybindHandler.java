package HxCKDMS.HxCSkills.Handlers;

import HxCKDMS.HxCSkills.HxCSkills;
import HxCKDMS.HxCSkills.network.BloodDestructionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeybindHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keybindings.Stats.isPressed()) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayer player = mc.thePlayer;
            player.openGui(HxCSkills.instance, 1, mc.theWorld, player.serverPosX, player.serverPosY, player.serverPosZ);
        }
        if (Keybindings.BloodDestruction.isPressed()) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayer player = mc.thePlayer;
            HxCSkills.Network.sendToServer(new BloodDestructionPacket(player.getGameProfile().getId().toString()));
        }
        if (Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayer player = mc.thePlayer;
            if (!player.onGround) {
                MoonWalk.doMoonWalk(player, false);
            }
        }
    }
}
