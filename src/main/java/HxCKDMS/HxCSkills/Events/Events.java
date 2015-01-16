package HxCKDMS.HxCSkills.Events;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCSkills.Config;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.util.UUID;

public class Events {

    public static UUID HealthUUID = UUID.fromString("fe15f490-62d7-11e4-b116-123b93f75cba");
    String pUUID;
    File CustomPlayerData;
    NBTTagCompound Skills;
    EntityPlayer player;

    int HSpeed = Config.HealSpeed;
    int HTimer;


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
        if (HTimer != 0) {
            --HTimer;
        }
        player = event.player;
        SetData(player);
    }

    public void SetData(EntityPlayer player){
        pUUID = player.getUniqueID().toString();
        CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + pUUID + ".dat");
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
            IAttributeInstance ph = ePlayer.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);

            AttributeModifier HealthBuff = new AttributeModifier(HealthUUID, "HealthSkill", Health, 1);
            ph.removeModifier(HealthBuff);
            ph.applyModifier(HealthBuff);
            if (Health > 15 && ePlayer.getHealth() < ePlayer.getMaxHealth()){
                int H1 = Math.round(Health/15);
                if (HTimer <= 0){
                    HTimer = Math.round((float)(HSpeed/H1));
                }
            }
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
                default: dmg = 1;
                    break;
            }
            double fdmg = event.damageMultiplier - dmg;
            event.damageMultiplier = (float)fdmg;
            System.out.println(event.damageMultiplier + fdmg);
        }
    }
}
