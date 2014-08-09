package kay.kaySkillz.common;

import net.minecraftforge.common.config.*;

public class Config {
    public static boolean FlySkillEnable;

    public static boolean EnableAgilityReq;

    public static int BaseStatModifier;
    public static int XPRate;

    public Config(Configuration config) {
        config.load();
        FlySkillEnable = config.get("Skills", "Enable the Fly Skill", true).getBoolean(true);

        EnableAgilityReq = config.get("Requirements", "Enable the Boots Requirement for Agility skill", true).getBoolean(true);

        BaseStatModifier = config.get("Meh", "This is the Base Modifier for stats if 5 then u can fall 5 more blocks per level of Agility", 5).getInt();
        XPRate = config.get("Meh", "This is Base XP Rate for gaining XP for skills Note: this is multiplied by 5 in some areas", 5).getInt();


        if (config.hasChanged()) {
            config.save();
        }


    }
}