package HxCKDMS.HxCSkills.Handlers;

import HxCKDMS.HxCCore.api.Utils.AABBUtils;
import HxCKDMS.HxCSkills.lib.Skills;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;

import java.util.List;

@SuppressWarnings("unchecked")
public class BloodDestruction {
    public static int BDTimer = 0;
    public static int BDTimer2 = 0;
    public static DamageSource BloodDestruction = new DamageSource("BloodDestruction").setDamageIsAbsolute().setDamageBypassesArmor().setDamageAllowedInCreativeMode();
    public static void doBloodDestruction(EntityPlayer player){
        NBTTagCompound data = Skills.SkillData(player);
        boolean CanUseBloodDestruction = (data.getInteger("BDCooldown") == 0 && Skills.TorsoStrength > 25 && Skills.ArmStrength > 25 && Skills.LegStrength > 25 && Skills.HeadStrength > 25 && Skills.FeetStrength > 25 && Skills.Health >= 1 && Skills.Stamina > 25);
        if (CanUseBloodDestruction){
            if (!isActivated(player)) ActivateBloodDestruction(player);
            else DeactivateBloodDestruction(player);
        }
    }

    public static void ActivateBloodDestruction(EntityPlayer player){
        NBTTagCompound data = Skills.SkillData(player);
        if (!data.getBoolean("BloodDestruction")) {
            data.setBoolean("BloodDestruction", true);
            player.attackEntityFrom(BloodDestruction, 20f);
            player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 5, 100, true, false));
            player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ, 10f, false);
            player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "mob.wither.spawn", 1, 0.03f);
            List<Entity> ents = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, AABBUtils.getAreaBoundingBox(player.serverPosX, player.serverPosY, player.serverPosZ, 16));
            for (Entity ent : ents) {
                if (ent instanceof EntityLivingBase && player.getDistanceToEntity(ent) < 32) {
                    ent.attackEntityFrom(BloodDestruction, 48 - player.getDistanceToEntity(ent));
                }
            }
            Skills.saveSkillData(player, data);
            BDTimer = 6000;
        }
    }

    public static void DeactivateBloodDestruction(EntityPlayer player){
        NBTTagCompound data = Skills.SkillData(player);
        if (data.getBoolean("BloodDestruction")) {
            data.setBoolean("BloodDestruction", false);
            data.setInteger("BDCooldown", 8000/data.getInteger("StaminaLevel"));
            player.removePotionEffect(Potion.resistance.getId());
            player.addPotionEffect(new PotionEffect(Potion.digSlowdown.getId(), 600, 4, true, false));
            player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "random.fizz", 1, 1);
            player.addChatComponentMessage(new ChatComponentText("You feel sluggish and tired."));
            Skills.saveSkillData(player, data);
            BDTimer2 = 300;
        }
    }

    public static void update(EntityPlayer player) {
        if (!isActivated(player) && isOnCooldown(player)) {
            NBTTagCompound data = Skills.SkillData(player);
            int tmp = data.getInteger("BDCooldown");
            player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "random.fizz", 1, 1);
            data.setInteger("BDCooldown", --tmp);
            Skills.saveSkillData(player, data);
            BDTimer2 = 300;
        }
    }

    public static boolean isActivated (EntityPlayer player) {
        return Skills.SkillData(player).getBoolean("BloodDestruction");
    }

    public static boolean isOnCooldown (EntityPlayer player) {
        return Skills.SkillData(player).getInteger("BDCooldown") > 0;
    }
}
