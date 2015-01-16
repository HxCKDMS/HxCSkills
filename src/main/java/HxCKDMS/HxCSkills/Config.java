package HxCKDMS.HxCSkills;

import net.minecraftforge.common.config.*;

public class Config {
    public static boolean FlySkillEnable;

    public static int Difficulty;

    public Config(Configuration config) {
        config.load();
        FlySkillEnable = config.get("Skills", "Enable the Fly Skill", true).getBoolean(true);

        Difficulty = config.get("Meh", "This is how many XP levels you need per skill point. (1 min, 2,147,483,647 max)", 5).getInt();


        if (config.hasChanged()) {
            config.save();
        }


    }
}