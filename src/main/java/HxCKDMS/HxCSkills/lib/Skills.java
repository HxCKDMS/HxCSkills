package HxCKDMS.HxCSkills.lib;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;

public class Skills {
    public static String[] SkillNamesFull = new String[]{
        "DummySkillLevel", "ArmStrengthLevel", "LegStrengthLevel", "TorsoStrengthLevel", "HeadStrengthLevel",
        "FeetStrengthLevel", "HealthLevel", "StaminaLevel", "FlyLevel", "StealthLevel", "ImmunityLevel"
    };
    public static String[] SkillNames = new String[]{
            "Dummy", "Arm", "Leg", "Torso", "Head", "Feet", "Health", "Stamina", "Fly", "Stealth", "Immunity"
    };

    public static NBTTagCompound SkillData (EntityPlayer player) {
        String UUID = player.getUniqueID().toString();
        File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
        return NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");
    }

    public static void saveSkillData (EntityPlayer player, NBTTagCompound data) {
        String UUID = player.getUniqueID().toString();
        File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
        NBTFileIO.setNbtTagCompound(CustomPlayerData, "skills", data);
    }

    public static void update (EntityPlayer player) {
        NBTTagCompound dat = SkillData(player);
        SP = dat.getInteger("SkillPoints");
        ArmStrength = dat.getInteger("ArmStrengthLevel");
        LegStrength = dat.getInteger("LegStrengthLevel");
        TorsoStrength = dat.getInteger("LegStrengthLevel");
        HeadStrength = dat.getInteger("HeadStrengthLevel");
        FeetStrength = dat.getInteger("FeetStrengthLevel");
        Health = dat.getInteger("HealthLevel");
        Stamina = dat.getInteger("StaminaLevel");
        Fly = dat.getInteger("FlyLevel");
        Stealth = dat.getInteger("StealthLevel");
        Immunity = dat.getInteger("ImmunityLevel");
    }

    public static int SP, ArmStrength, LegStrength, TorsoStrength, HeadStrength, FeetStrength, Health, Stamina, Fly, Stealth, Immunity;
}
