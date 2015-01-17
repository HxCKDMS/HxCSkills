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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
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
    int FTimer;

    double Speed;
    double Damage;
    double HPBuff;

    int SkillPoints;
    int StrengthArms;
    int StrengthLegs;
    int StrengthFeet;
    int StrengthTorso;
    int StrengthHead;
    int Health;
    int Stamina;
    int FlySkillLevel;

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event){
        if (HTimer != 0) {
            --HTimer;
        }
        if (FTimer != 0) {
            --FTimer;
        }

        if (event.entityLiving instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP)event.entityLiving;

            String pUUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + pUUID + ".dat");
            NBTTagCompound Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");

            SkillPoints = 0;
            StrengthLegs = 0;
            StrengthArms = 0;
            StrengthFeet = 0;
            StrengthTorso = 0;
            StrengthHead = 0;
            Stamina = 0;
            Health = 0;
            FlySkillLevel = 0;

            SkillPoints = Skills.getInteger("SkillPoints");

            StrengthArms = Skills.getInteger("ArmStrengthLevel");
            StrengthLegs = Skills.getInteger("LegStrengthLevel");
            StrengthFeet = Skills.getInteger("FootStrengthLevel");
            StrengthTorso = Skills.getInteger("TorsoStrengthLevel");
            StrengthHead = Skills.getInteger("HeadStrengthLevel");

            Health = Skills.getInteger("HealthLevel");
            Stamina = Skills.getInteger("StaminaLevel");
            FlySkillLevel = Skills.getInteger("FlyLevel");

            if (SkillPoints < 0) {Skills.setInteger("SkillPoints", 0);}
            NBTFileIO.setNbtTagCompound(CustomPlayerData, "skills", Skills);

            IAttributeInstance playerhp = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
            IAttributeInstance playerspeed = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
            IAttributeInstance playerstrength = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);

            if (StrengthLegs > 0){
                Speed = (playerspeed.getBaseValue() + ((StrengthLegs/5)*.15));
            }else{
                Speed = 0;
            }
            if (StrengthArms > 0){
                Damage = (playerstrength.getBaseValue() + ((StrengthArms)*.15));
            }else{
                Damage = 0;
            }

            if (Health > 0 && Health <= HxCKDMS.HxCCore.Config.HPMax/10){
                HPBuff = Health;
            }else if (Health > HxCKDMS.HxCCore.Config.HPMax) {
                HPBuff = 10;
            }else{
                HPBuff = 0;
            }

            if (!player.capabilities.allowFlying) {
                player.capabilities.allowFlying = FlySkillLevel>0;
            }

            if (StrengthHead >= 10 && !(player.worldObj.canSeeSky(new BlockPos(player.posX, player.posY, player.posZ))) && (player.worldObj.getLight(new BlockPos(player.posX, player.posY, player.posZ)) == 0)) {
                player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 60, 0, false, false));
            }

            if (StrengthArms >= 5) {
                int Haste = Math.round((float)(StrengthArms/5));
                player.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 5, Haste-1, false, false));
            }

            if (Stamina >= 5) {
                int dt = 120;
                int fud = Math.round((float)(Stamina/25));
                int FudT = Math.round((float)(dt/fud));
                if (FTimer <= 0) {
                    player.addPotionEffect(new PotionEffect(Potion.saturation.getId(), 1, fud-1, false, false));
                    FTimer = FudT;
                }
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
                if (HTimer <= 0){
                    player.heal(2);
                    HTimer = (HSpeed-Health);
                }
            }

            if (StrengthTorso >= 1) {
                int ResistanceLevel = Math.round(StrengthTorso/25);
                player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 5, ResistanceLevel-1, true, false));
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
            int StrengthLegs = Skills.getInteger("LegStrengthLevel");
            int StrengthFeet = Skills.getInteger("FootStrengthLevel");
            if (StrengthFeet > 5 || StrengthLegs > 5) {
                double dmg = 0;
                double pwr = ((StrengthFeet + StrengthLegs)/5);
                double sneakmod = 0;
                if (playerMP.isSneaking()) {
                    sneakmod = 0.50;
                }
                int p = Math.round((float) pwr);
                if (p > 0 && p < 21) {
                    dmg = (-0.05 * p);
                } else if (p > 20) {
                    dmg = 1;
                } else {
                    dmg = 0;
                }
                double fdmg = (event.damageMultiplier + dmg) - sneakmod;
                if (fdmg < 0) {
                    event.damageMultiplier = 0;
                } else {
                    event.damageMultiplier = (float) fdmg;
                }
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
            int StrengthLegs = Skills.getInteger("LegStrengthLevel");

            if (StrengthLegs > 5 && StrengthLegs < 50) {
                double JumpBuff = (0.1 * (StrengthLegs / 10));
                player.motionY += JumpBuff;
            }
        }
    }
}
