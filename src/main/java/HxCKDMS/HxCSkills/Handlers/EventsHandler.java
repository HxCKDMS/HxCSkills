package HxCKDMS.HxCSkills.Handlers;

import HxCKDMS.HxCSkills.Configurations;
import HxCKDMS.HxCSkills.lib.Skills;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

@SuppressWarnings("unused")
public class EventsHandler {
    //TODO: Clean
    public static UUID
            HealthUUID = UUID.fromString("fe15f490-62d7-11e4-b116-123b93f75cba"),
            HealthDecUUID = UUID.fromString("fe15f490-62d7-11e4-b116-123b93f75cb5"),
            SpeedUUID = UUID.fromString("fe15f490-62d7-11e4-b116-123b93f75cbb"),
            DamageUUID = UUID.fromString("fe15f490-62d7-11e4-b116-123b93f75cbc");

    int HSpeed = Configurations.HealSpeed, HTimer, FTimer;
    public int BDTimer, BDTimer2;
    double Speed, Damage, HPBuff;

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event){
        if (event.entityLiving instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP)event.entityLiving;
            if (HTimer != 0) HTimer--;

            if (FTimer != 0) FTimer--;

            if (BDTimer != 0) {BDTimer--;}
            else {
                BloodDestruction.DeactivateBloodDestruction(player);
                BDTimer = BloodDestruction.BDTimer;
            }

            if (BDTimer2 != 0) {BDTimer2--;}
            else {
                BloodDestruction.update(player);
                BDTimer2 = BloodDestruction.BDTimer2;
            }

            Skills.update(player);

            IAttributeInstance playerHP = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
            IAttributeInstance playerSpeed = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
            IAttributeInstance playerStrength = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);

            if (Skills.LegStrength > 0) Speed = (playerSpeed.getBaseValue() + ((Skills.LegStrength/5)*.15));
            else Speed = 0;

            if (Skills.ArmStrength > 0) Damage = (playerStrength.getBaseValue() + ((Skills.ArmStrength)*.15));
            else Damage = 0;

            if (Skills.Health > 0 && Skills.Health <= HxCKDMS.HxCCore.Configs.Configurations.HPMax) HPBuff = Skills.Health*0.1;
            else if (Skills.Health > HxCKDMS.HxCCore.Configs.Configurations.HPMax) HPBuff = 10;
            else HPBuff = 0;


            if (!player.capabilities.allowFlying) player.capabilities.allowFlying = Skills.Fly>0;

            if (Skills.HeadStrength >= 10 && !(player.worldObj.canSeeSky(new BlockPos(player.posX, player.posY, player.posZ)) && (player.worldObj.getLight(new BlockPos(player.posX, player.posY, player.posZ)) < 3))) player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 60, 0));

            boolean darkEnough = (player.worldObj.getLight(new BlockPos(player.posX, player.posY, player.posZ)) < Skills.Stealth/5);
            if (Skills.Stealth >= 10 && darkEnough) player.setInvisible(true);
            else if (!player.isPotionActive(Potion.invisibility) && Skills.Stealth < 10 && !darkEnough) player.setInvisible(false);

            if (Skills.Stamina >= 5) {
                int dt = 120;
                int fud = Math.round((float)(Skills.Stamina/25));
                int FudT = Math.round((float)(dt/fud));
                if (FTimer <= 0) {
                    player.getFoodStats().addStats(fud / 2, fud / 2);
                    FTimer = FudT;
                }
            }

            AttributeModifier HealthBuff = new AttributeModifier(HealthUUID, "HealthSkill", HPBuff, 1);
            AttributeModifier SpeedBuff = new AttributeModifier(SpeedUUID, "LegStrengthSkill", Speed, 1);
            AttributeModifier DamageBuff = new AttributeModifier(DamageUUID, "ArmStrengthSkill", Damage, 1);

            playerHP.removeModifier(HealthBuff);
            playerSpeed.removeModifier(SpeedBuff);
            playerStrength.removeModifier(DamageBuff);

            playerHP.applyModifier(HealthBuff);
            playerSpeed.applyModifier(SpeedBuff);
            playerStrength.applyModifier(DamageBuff);

            player.sendPlayerAbilities();

            if (Skills.Health > 15 && player.getHealth() < player.getMaxHealth()){
                if (HTimer <= 0){
                    player.heal(2);
                    HTimer = (HSpeed- Skills.Health);
                }
            }
        }
    }
    @SubscribeEvent
    public void LivingHurtEvent(LivingHurtEvent event){
        if (event.entityLiving instanceof EntityPlayerMP){
            EntityPlayerMP playerMP = (EntityPlayerMP)event.entityLiving;
            if (event.source.damageType.equalsIgnoreCase("fall")) {
                int CombinedStrength = Skills.FeetStrength + Skills.LegStrength;
                if (CombinedStrength > 5) {
                    float dmg = event.ammount;
                    event.ammount = dmg - (CombinedStrength / 4);
                }
            } else if (event.source.damageType.equalsIgnoreCase("explosion") || event.source.damageType.equalsIgnoreCase("generic") || event.source.damageType.equalsIgnoreCase("mob") || event.source.damageType.equalsIgnoreCase("player") || event.source.damageType.equalsIgnoreCase("arrow")) {
                float dmg = event.ammount;
                event.ammount = dmg - (Skills.TorsoStrength / 4);
            } else if (event.source.damageType.contains("magic")) {
                float dmg = event.ammount;
                event.ammount = dmg - (Skills.Immunity / 4);
            }
        }
    }
    @SubscribeEvent
    public void LivingJumpEvent(LivingEvent.LivingJumpEvent event){
        if (event.entityLiving instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.entityLiving;
            if (Skills.LegStrength > 5 && Skills.LegStrength < 50) {
                double JumpBuff = (0.1 * (Skills.LegStrength / 10));
                player.motionY += JumpBuff;
            }
        }
    }
    @SubscribeEvent
    public void EntityJoinWorld(EntityJoinWorldEvent event){
        if (event.entity instanceof EntityPlayerMP && Configurations.HPDebuff) {
            EntityPlayerMP player = (EntityPlayerMP) event.entity;
            IAttributeInstance playerhp = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
            HPBuff = 2;
            AttributeModifier HealthDebuff = new AttributeModifier(HealthDecUUID, "HealthDebuff", HPBuff, 1);
            playerhp.removeModifier(HealthDebuff);
            playerhp.applyModifier(HealthDebuff);
        }
    }

    @SubscribeEvent
    public void breakBlockEvent (PlayerEvent.BreakSpeed event) {
        Skills.update(event.entityPlayer);
        float Haste = Skills.ArmStrength/5 + event.originalSpeed;
        if (!BloodDestruction.isOnCooldown(event.entityPlayer) && Skills.ArmStrength >= 5) {
            event.newSpeed = Haste;
        }
        if (BloodDestruction.isOnCooldown(event.entityPlayer)) {
            Haste = Haste - Skills.SkillData(event.entityPlayer).getInteger("BDCooldown")/100;
            event.newSpeed = Haste;
        }
    }
}
