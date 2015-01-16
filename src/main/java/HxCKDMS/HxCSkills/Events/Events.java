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
    public static UUID SpeedUUID = UUID.fromString("fe15f490-62d7-11e4-b116-123b93f75cbb");
    public static UUID DamageUUID = UUID.fromString("fe15f490-62d7-11e4-b116-123b93f75cbc");
    String pUUID;
    File CustomPlayerData;
    NBTTagCompound Skills;
    EntityPlayer player;

    int HSpeed = Config.HealSpeed;
    int HTimer;

    double Speed;
    double Damage;

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
    @SubscribeEvent
    public void LivingUpdateEvent(LivingEvent.LivingUpdateEvent event){
        if (event.entityLiving instanceof EntityPlayerMP){
            EntityPlayerMP ePlayer = (EntityPlayerMP)event.entityLiving;

            IAttributeInstance ph = ePlayer.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
            IAttributeInstance ps = ePlayer.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
            IAttributeInstance pd = ePlayer.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);

            if (StrengthLegs > 0){
                Speed = (ps.getBaseValue() + ((StrengthLegs/5)*.15));
            }
            if (StrengthArms > 0){
                Damage = (pd.getBaseValue() + ((StrengthArms)*.15));
            }

            AttributeModifier HealthBuff = new AttributeModifier(HealthUUID, "HealthSkill", Health, 1);
            AttributeModifier SpeedBuff = new AttributeModifier(SpeedUUID, "LegStrengthSkill", Speed, 1);
            AttributeModifier DamageBuff = new AttributeModifier(DamageUUID, "ArmStrengthSkill", Damage, 1);

            ph.removeModifier(HealthBuff);
            ps.removeModifier(SpeedBuff);
            pd.removeModifier(DamageBuff);

            ph.applyModifier(HealthBuff);
            ps.applyModifier(SpeedBuff);
            pd.applyModifier(DamageBuff);

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
        if (event.entityLiving instanceof EntityPlayer && StrengthLegs >= 5) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            double JumpBuff = player.motionY + 0.1 * (StrengthLegs/5);
            player.motionY += JumpBuff;
        }
    }
}
