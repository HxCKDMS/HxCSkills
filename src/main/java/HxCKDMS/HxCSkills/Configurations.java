package HxCKDMS.HxCSkills;

import HxCKDMS.HxCCore.api.Configuration.Config;

public class Configurations {
    @Config.Boolean(category = "Skills")
    public static boolean FlySkillEnable = true;
    @Config.Boolean(category = "Tweaks")
    public static boolean HPDebuff;

    @Config.Integer(category = "Tweaks")
    public static int HealSpeed = 60, SkillCostAmplifier = 1;

    @Config.Integer(description = "Permission level to use command with similar name...", category = "Commands")
    public static int SetSkillsPL = 4, ResetSkillsPL= 5;

    @Config.Integer(category = "Tweaks", description = "This is the number of hearts the player starts with. 1 min (else players will never be able to login...) 20 normal")
    public static int PlayerHP = 6;
}