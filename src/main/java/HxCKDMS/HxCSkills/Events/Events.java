package HxCKDMS.HxCSkills.Events;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;

public class Events {
    String UUID;
    File CustomPlayerData;
    NBTTagCompound Skills;
    EntityPlayer player;

    int SkillPoints;

    //skill levels
    int StrengthArms;
    int StrengthLegs;
    int StrengthFeet;
    int StrengthTorso;
    int StrengthHead;

    int Health;
    int Stamina;

    @SubscribeEvent
    public void PlayerTickEvent(TickEvent.PlayerTickEvent event){
        player = event.player;
        SetData(player);
    }

    public void SetData(EntityPlayer player){
        UUID = player.getUniqueID().toString();
        CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
        Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");
        SkillPoints = Skills.getInteger("SkillPoints");

        StrengthArms = Skills.getInteger("ArmStrength");
        StrengthLegs = Skills.getInteger("LegStrength");
        StrengthFeet = Skills.getInteger("FootStrength");
        StrengthTorso = Skills.getInteger("TorsoStrength");
        StrengthHead = Skills.getInteger("HeadStrength");

        Health = Skills.getInteger("Health");
        Stamina = Skills.getInteger("Stamina");
    }

    public void LivingUpdateEvent(LivingEvent.LivingUpdateEvent event){
        if (event.entityLiving instanceof EntityPlayerMP){
            EntityPlayerMP ePlayer = (EntityPlayerMP)event.entityLiving;

        }
    }
    @SubscribeEvent
    public void LivingFallEvent(LivingFallEvent event){
        if (event.entityLiving instanceof EntityPlayerMP){
            EntityPlayerMP playerMP = (EntityPlayerMP)event.entityLiving;
            double dmg = 0;
            double pwr = (((StrengthFeet + StrengthLegs) * 0.3) + 1);
            int p = Math.round((float)pwr);
            switch (p) {
                case 1: dmg = 0.95;
                    break;
                case 2: dmg = 0.9;
                    break;
                case 3: dmg = 0.85;
                    break;
                case 4: dmg = 0.8;
                    break;
                case 5: dmg = 0.75;
                    break;
                case 6: dmg = 0.7;
                    break;
                case 7: dmg = 0.65;
                    break;
                case 8: dmg = 0.6;
                    break;
                case 9: dmg = 0.55;
                    break;
                case 10: dmg = 0.50;
                    break;
                case 11: dmg = 0.45;
                    break;
                case 12: dmg = 0.4;
                    break;
                case 13: dmg = 0.35;
                    break;
                case 14: dmg = 0.3;
                    break;
                case 15: dmg = 0.25;
                    break;
                case 16: dmg = 0.2;
                    break;
                case 17: dmg = 0.15;
                    break;
                case 18: dmg = 0.1;
                    break;
                case 19: dmg = 0.05;
                    break;
                case 20: dmg = 0.025;
                    break;
            }
            double fdmg = event.damageMultiplier - dmg;
            event.damageMultiplier = (float)fdmg;
            System.out.println(event.damageMultiplier + fdmg);
        }
    }
}
