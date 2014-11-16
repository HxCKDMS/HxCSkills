package HxCKDMS.HxCSkills.Events;

import HxCKDMS.HxCSkills.Config;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.EnumSet;
import java.util.EventListener;

public class Events implements EventListener {

    @SubscribeEvent
    public void Update(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP)event.entityLiving;
            boolean Fly = false;
            if (Config.FlySkillEnable) {
                Fly = NBTHandler.SkillAgilityLVL >= 1000;
            }
            if (Fly) {
                player.capabilities.allowFlying = true;
                if (player.capabilities.isFlying && player.worldObj.isRemote) {
                    int MaxFlyHeight = NBTHandler.SkillAgilityLVL - 940;
                    int h = (int) player.getEyeHeight();
                    player.addExhaustion(1);
                    player.worldObj.spawnParticle("smoke", player.posX + Math.random() - 0.5d, player.posY - 1.62d, player.posZ + Math.random() - 0.5d, 0.0d, 0.0d, 0.0d);
                    if (h > MaxFlyHeight){
                        player.addChatMessage(new ChatComponentText("\u00A74You are not skilled enough to fly this high!"));
                        player.addPotionEffect(new PotionEffect(Potion.confusion.id, 5, 2, false));
                        player.addPotionEffect(new PotionEffect(Potion.blindness.id, 5, 2, false));
                        player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 5, 2, false));
                        player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5, 2, false));
                        player.addPotionEffect(new PotionEffect(Potion.weakness.id, 5, 2, false));
                        player.addPotionEffect(new PotionEffect(Potion.wither.id, 5, 2, false));
                    }
                }
            }
        }
    }
    public EnumSet<TickEvent.Type> ticks() {
        return EnumSet.of(TickEvent.Type.PLAYER);
    }

    @Mod.EventHandler
    @SideOnly(Side.SERVER)
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        int XPGainPF = Config.XPRate * 3;
        int XPGainPR = Config.XPRate * 5;



        if (event.type.equals(DamageSource.fall) && (event.player != null) && !player.worldObj.isRemote && Config.AgilitySkillEnable)
            if (!player.capabilities.isCreativeMode)
                if (Config.EnableAgilityReq)
                    if (player.getCurrentArmor(3) != null) {
                        if (event.player.isSneaking() && event.player.fallDistance <= NBTHandler.SkillAgilityLVL * Config.BaseStatModifier) {
                            event.setCanceled(true);
                            NBTHandler.SkillAgilityXP = NBTHandler.SkillAgilityXP + XPGainPR;
                        }
                    }
                if (!Config.EnableAgilityReq) {
                    if (event.player.fallDistance <= NBTHandler.SkillAgilityLVL * Config.BaseStatModifier) {
                        if (event.player.isSneaking())
                            event.setCanceled(true);
                            NBTHandler.SkillAgilityXP = NBTHandler.SkillAgilityXP + XPGainPR;
                            System.out.println(NBTHandler.SkillAgilityXP);
                        if (!event.player.isSneaking())
                            event.setCanceled(false);
                            NBTHandler.SkillAgilityXP = NBTHandler.SkillAgilityXP + XPGainPF;
                    }
                    else{
                        NBTHandler.SkillAgilityXP = NBTHandler.SkillAgilityXP - Config.XPRate;
                        if (NBTHandler.SkillAgilityLVL >= 100){
                            float dmg = event.player.fallDistance - NBTHandler.SkillAgilityLVL;
                            float Damage = dmg * -1;
                            event.setCanceled(true);
                            player.heal(Damage);
                        }
                    }
                }

        if (event.type.equals(DamageSource.magic) || event.type.equals(DamageSource.wither) && (event.player != null) && !player.worldObj.isRemote && Config.ImunityToPoisonSkillEnable){
            if (!player.capabilities.isCreativeMode && event.player.fallDistance <= NBTHandler.SkillImmuneLVL * Config.BaseStatModifier) {
                if (NBTHandler.SkillImmuneLVL >= 1000)
                    event.setCanceled(true);
                else
                    event.setCanceled(false);
                    if (NBTHandler.SkillImmuneLVL >= 25) {
                        player.addPotionEffect(new PotionEffect(Potion.regeneration.id, NBTHandler.SkillImmuneLVL / 250, 2));
                        if (player.getActivePotionEffect(Potion.poison).getIsAmbient() || player.getActivePotionEffect(Potion.wither).getIsAmbient() || event.type.equals(DamageSource.magic))
                        NBTHandler.SkillImmuneXP = (int) (NBTHandler.SkillImmuneXP + Config.XPRate * 0.1);
                    }
            }
        }
    }
    public static void BloodDestruction(EntityPlayerMP Player){

        Player.worldObj.spawnParticle("instantSpell", Player.posX + Math.random(), Player.posY + Math.random(), Player.posZ + Math.random(), 0, 0, 0);
        Player.worldObj.createExplosion(Player, Player.posX, Player.posY, Player.posZ, 10, true);
        Player.worldObj.playSoundEffect(1, 1, 1, "mob.wither.spawn", 1, 1);

    }
}
