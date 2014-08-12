package kay.kaySkillz.common;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Keybindings {

    public static KeyBinding BloodDestructionA;
    public static KeyBinding BloodDestructionB;

    public static void init() {
        BloodDestructionA = new KeyBinding("key.BloodDestructionA", Keyboard.KEY_H, "key.categories.DrZedsMods");
        BloodDestructionB = new KeyBinding("key.BloodDestructionB", Keyboard.KEY_G, "key.categories.DrZedsMods");

        ClientRegistry.registerKeyBinding(BloodDestructionA);
        ClientRegistry.registerKeyBinding(BloodDestructionB);
    }

}