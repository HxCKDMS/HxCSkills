package HxCKDMS.HxCSkills.Events;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.io.File;

public class EventBloodDestruction {
    public void BloodDestruction(EntityPlayer player){
        String UUID = player.getUniqueID().toString();
        File CustomPlayerData = new File(HxCKDMS.HxCSkills.HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
        NBTTagCompound Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");
        int Torso = Skills.getInteger("TorsoStrengthLevel");
        int Arms = Skills.getInteger("ArmStrengthLevel");
        int Legs = Skills.getInteger("LegsStrengthLevel");
        int Head = Skills.getInteger("HeadStrengthLevel");
        int Feet = Skills.getInteger("FeetStrengthLevel");
        int Health = Skills.getInteger("HealthLevel");
        int Stamina = Skills.getInteger("StaminaLevel");
        boolean BloodDestructionActive = Skills.getBoolean("BloodDestruction");
        boolean CanUseBloodDestruction = (Torso > 25 && Arms > 25 && Legs > 25 && Head > 25 && Feet > 25 && Health >= 1 && Stamina > 25);
        if (CanUseBloodDestruction){
            if (!BloodDestructionActive){
                Skills.setBoolean("BloodDestruction", true);
            }else{
                Skills.setBoolean("BloodDestruction", false);
            }
        }
    }
    public void ActivateBloodDestruction(EntityPlayer player){
        float curhp = player.getHealth();
        float dmghp = curhp - 1;
        player.setHealth(dmghp);
        player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 5, 100, true, false));
        player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ, 1f, true);
    }
    public void DeactivateBloodDestruction(EntityPlayer player){
    }
}
