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

import java.io.File;
import java.util.UUID;

public class Events {

    public static UUID HealthUUID = UUID.fromString("fe15f490-62d7-11e4-b116-123b93f75cba");
    public static UUID SpeedUUID = UUID.fromString("fe15f490-62d7-11e4-b116-123b93f75cbb");
    public static UUID DamageUUID = UUID.fromString("fe15f490-62d7-11e4-b116-123b93f75cbc");

    int HSpeed = Config.HealSpeed;
    int HTimer;

    double Speed;
    double Damage;
    double HPBuff;

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event){
        if (HTimer != 0) {
            --HTimer;
//            System.out.println(HTimer);
        }

        if (event.entityLiving instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP)event.entityLiving;

            String pUUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + pUUID + ".dat");
            NBTTagCompound Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");
            int SkillPoints = Skills.getInteger("SkillPoints");

            int StrengthArms = Skills.getInteger("ArmStrengthLevel");
            int StrengthLegs = Skills.getInteger("LegStrengthLevel");
            int StrengthFeet = Skills.getInteger("FootStrengthLevel");
            int StrengthTorso = Skills.getInteger("TorsoStrengthLevel");
            int StrengthHead = Skills.getInteger("HeadStrengthLevel");

            int Health = Skills.getInteger("HealthLevel");
            int Stamina = Skills.getInteger("StaminaLevel");
            int FlySkillLevel = Skills.getInteger("FlyLevel");

            IAttributeInstance playerhp = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
            IAttributeInstance playerspeed = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
            IAttributeInstance playerstrength = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);

            if (StrengthLegs > 0){
                Speed = (playerspeed.getBaseValue() + ((StrengthLegs/5)*.15));
            }
            if (StrengthArms > 0){
                Damage = (playerstrength.getBaseValue() + ((StrengthArms)*.15));
            }

            if (Health > 0 && Health <= HxCKDMS.HxCCore.Config.HPMax){
                HPBuff = Health/5;
            }else{
                HPBuff = 100;
            }

            AttributeModifier HealthBuff = new AttributeModifier(HealthUUID, "HealthSkill", HPBuff, 1);
            AttributeModifier SpeedBuff = new AttributeModifier(SpeedUUID, "LegStrengthSkill", Speed, 1);
            AttributeModifier DamageBuff = new AttributeModifier(DamageUUID, "ArmStrengthSkill", Damage, 1);

            playerhp.removeModifier(HealthBuff);
            playerspeed.removeModifier(SpeedBuff);
            playerstrength.removeModifier(DamageBuff);

            playerhp.applyModifier(HealthBuff);
            playerspeed.applyModifier(SpeedBuff);
            playerstrength.applyModifier(DamageBuff);

            player.sendPlayerAbilities();

            if (Health > 15 && player.getHealth() < player.getMaxHealth()){
                int H1 = Math.round(Health/15);
                if (HTimer <= 0){
                    player.heal(2);
                    HTimer = Math.round((float)(HSpeed/H1));
                }
            }
        }
    }
    @SubscribeEvent
    public void LivingFallEvent(LivingFallEvent event){
        if (event.entityLiving instanceof EntityPlayerMP){
            EntityPlayerMP playerMP = (EntityPlayerMP)event.entityLiving;

            String pUUID = playerMP.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + pUUID + ".dat");
            NBTTagCompound Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");
            int StrengthLegs = Skills.getInteger("LegStrength");
            int StrengthFeet = Skills.getInteger("FootStrength");

            double dmg = 0;
            double pwr = (((StrengthFeet + StrengthLegs) * 0.3) + 1);
            double sneakmod = 0;
            if (playerMP.isSneaking()){
                sneakmod = 0.50;
            }
            int p = Math.round((float)pwr);
            if (0 > p && p < 21){
                dmg = (-0.05*p)+1;
            }else{
                dmg = 1;
            }
            double fdmg = (event.damageMultiplier - dmg)-sneakmod;
            if (fdmg < 0) {
                event.damageMultiplier = 0;
            }else{
                event.damageMultiplier = (float)fdmg;
            }
        }
    }
    @SubscribeEvent
    public void LivingJumpEvent(LivingEvent.LivingJumpEvent event){
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;

            String pUUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + pUUID + ".dat");
            NBTTagCompound Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");
            int StrengthLegs = Skills.getInteger("LegStrength");

            if (StrengthLegs > 5) {
                double JumpBuff = player.motionY + 0.1 * (StrengthLegs / 5);
                player.motionY += JumpBuff;
            }
        }
    }
}
