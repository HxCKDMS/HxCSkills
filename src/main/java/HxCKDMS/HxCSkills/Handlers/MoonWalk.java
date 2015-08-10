package HxCKDMS.HxCSkills.Handlers;

import HxCKDMS.HxCSkills.lib.Skills;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

@SuppressWarnings("unchecked")
public class MoonWalk {
    public static void doMoonWalk(EntityPlayer player, boolean toggle){
        boolean CanUseMoonWalk = (Skills.LegStrength > 25 && Skills.Stamina > 10);
        if (CanUseMoonWalk) {
            if (toggle) {
                NBTTagCompound data = Skills.SkillData(player);
                if (!isActivated(player)) {
                    data.setBoolean("MoonWalk", true);
                } else {
                    data.setBoolean("MoonWalk", false);
                }
                Skills.saveSkillData(player, data);
            } else {
                if (isActivated(player) && !player.onGround && !player.capabilities.allowFlying) {
                    player.motionY += 1;
                }
            }
        }
    }
    
    public static boolean isActivated (EntityPlayer player) {
        return Skills.SkillData(player).getBoolean("MoonWalk");
    }
}
