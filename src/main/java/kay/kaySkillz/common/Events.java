package kay.kaySkillz.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kay.kayXEnchants.common.NBTStuff;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;

import java.util.EnumSet;
import java.util.EventListener;

public class Events implements EventListener {

    @Mod.EventHandler
    @SideOnly(Side.SERVER)
    public void Tick(EnumSet<TickEvent.Type> type, Object... tickData) {
        if (type.contains(TickEvent.Type.PLAYER)) {
            EntityPlayer player = (EntityPlayer) tickData[0];
            boolean Fly = false;
            if (Config.FlySkillEnable) {
                if (NBTStuff.SkillAgilityLVL >= 1000) {
                    Fly = true;
                } else {
                    Fly = false;
                }
            }
            if (Fly == true) {
                player.capabilities.allowFlying = true;
                if (player.capabilities.isFlying && player.worldObj.isRemote) {
                    int MaxFlyHeight = NBTStuff.SkillAgilityLVL - 940;
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

        if (event.type.equals(DamageSource.fall) && (event.player != null) && !player.worldObj.isRemote)
            if (!player.capabilities.isCreativeMode)
                if (Config.EnableAgilityReq)
                    if (player.getCurrentArmor(3) != null) {
                        if (event.player.isSneaking() && event.player.fallDistance <= NBTStuff.SkillAgilityLVL * Config.BaseStatModifier) {
                            event.setCanceled(true);
                            NBTStuff.SkillAgilityXP = NBTStuff.SkillAgilityXP + XPGainPR;
                        }
                    }
                if (!Config.EnableAgilityReq) {
                    if (event.player.fallDistance <= NBTStuff.SkillAgilityLVL * Config.BaseStatModifier) {
                        if (event.player.isSneaking())
                            event.setCanceled(true);
                            NBTStuff.SkillAgilityXP = NBTStuff.SkillAgilityXP + XPGainPR;
                        if (!event.player.isSneaking())
                            event.setCanceled(false);
                            NBTStuff.SkillAgilityXP = NBTStuff.SkillAgilityXP + XPGainPF;
                    }
                    else{
                        NBTStuff.SkillAgilityXP = NBTStuff.SkillAgilityXP - Config.XPRate;
                        if (NBTStuff.SkillAgilityLVL >= 100){
                            float dmg = event.player.fallDistance - NBTStuff.SkillAgilityLVL;
                            float Damage = dmg * -1;
                            event.setCanceled(true);
                            player.heal(Damage);
                        }
                    }
                }

    }
}
