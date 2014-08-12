package kay.kaySkillz.common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import org.lwjgl.input.Keyboard;

public class KeybindHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(Keybindings.BloodDestructionA.isPressed())
            System.out.println("Banana Pushed");
            ActivateBloodDestruction();
        if(Keybindings.BloodDestructionB.isPressed())
            ActivateBloodDestruction();
    }

    private void ActivateBloodDestruction() {
        NBTHandler.BDTime++;
    }

}