package kay.kaySkillz.common;

import cpw.mods.fml.common.Mod;
import net.minecraftforge.common.IExtendedEntityProperties;

public class NBTHandler{

    public static int SkillAgilityXP = 0;
    public static int SkillAgilityLVL = 0;

    public static int SkillImmuneXP = 0;
    public static int SkillImmuneLVL = 0;
    public static int BDTime = 0;

    @Mod.EventHandler
    public void NBTHandlerCore(IExtendedEntityProperties tag, int data){

    }

    @Mod.EventHandler
    public void ModifyLVLStats(){
        int LevelReqAg = Config.Difficulty * (SkillAgilityLVL + 1);
        int LevelReqIm = Config.Difficulty * (SkillImmuneLVL + 1);
        if (SkillAgilityXP >= LevelReqAg){
            SkillAgilityXP = SkillAgilityXP - LevelReqAg;
            SkillAgilityLVL = SkillAgilityLVL + 1;
        }
        if (SkillImmuneXP >= LevelReqIm){
            SkillImmuneXP = SkillImmuneXP - LevelReqIm;
            SkillImmuneLVL = SkillImmuneLVL + 1;
        }
    }
}
