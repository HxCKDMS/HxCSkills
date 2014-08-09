package kay.kaySkillz.common;

import cpw.mods.fml.common.Mod;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class NBTStuff {


    NBTTagCompound tag = new NBTTagCompound();

    public static int SkillAgilityXP = 0;
    public static int SkillAgilityLVL = 0;

    public static int SkillImmuneXP = 0;
    public static int SkillImmuneLVL = 0;

    int sal = tag.getInteger("SkillAgilityLVL");
    int sil = tag.getInteger("SkillImmuneLVL");

    @Mod.EventHandler
    public void NBTStuffz(EntityPlayerMP Player){
        tag.setInteger("SkillAgilityXP", SkillAgilityXP);
        Player.writeToNBT(tag);
        tag.setInteger("SkillAgilityLVL", SkillAgilityLVL);
        Player.writeToNBT(tag);
        tag.setInteger("SkillImmuneXP", SkillAgilityXP);
        Player.writeToNBT(tag);
        tag.setInteger("SkillImmuneLVL", SkillAgilityLVL);
        Player.writeToNBT(tag);
        if (tag != null && sal != SkillAgilityLVL) {
            SkillAgilityLVL = sal;
        }
    }
    @Mod.EventHandler
    public void ModifyLVLStats(){

        int LevelReq = Config.Difficulty * (SkillAgilityLVL + 1);
        if (SkillAgilityXP >= LevelReq){
            SkillAgilityXP = SkillAgilityXP - LevelReq;
            SkillAgilityLVL = SkillAgilityLVL + 1;
        }



    }
}
