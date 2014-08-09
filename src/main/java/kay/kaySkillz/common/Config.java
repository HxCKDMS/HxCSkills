package kay.kaySkillz.common;

import net.minecraftforge.common.config.*;

public class Config {
    public static boolean AgilitySkillEnable;
    public static boolean FlySkillEnable;
    public static boolean ImunityToPoisonSkillEnable;

    public static boolean EnableAgilityReq;

    public static int BaseStatModifier;
    public static int XPRate;
    public static int Difficulty;

    public Config(Configuration config) {
        config.load();
        AgilitySkillEnable = config.get("Skills", "Enable the Agility Skill", true).getBoolean(true);
        FlySkillEnable = config.get("Skills", "Enable the Fly Skill(Requires Agility)", true).getBoolean(true);
        ImunityToPoisonSkillEnable = config.get("Skills", "Enable the Immunity to poisons/wither Skill", true).getBoolean(true);

        EnableAgilityReq = config.get("Requirements", "Enable the Boots Requirement for Agility skill", true).getBoolean(true);

        BaseStatModifier = config.get("Meh", "This is the Base Modifier for stats if 5 then u can fall 5 more blocks per level of Agility", 5).getInt();
        XPRate = config.get("Meh", "This is Base XP Rate for gaining XP for skills Note: this is multiplied by 5 in some areas", 5).getInt();
        Difficulty = config.get("Meh", "This is the difficulty of the mod Note: keep this divisible by 5 minimum 10 max 1000000", 100).getInt();


        if (config.hasChanged()) {
            config.save();
        }


    }
}