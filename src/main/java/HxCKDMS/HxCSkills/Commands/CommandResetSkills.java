package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.Handlers.PermissionsHandler;
import HxCKDMS.HxCCore.api.ISubCommand;
import HxCKDMS.HxCSkills.lib.Skills;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CommandResetSkills implements ISubCommand {
    public static CommandResetSkills instance = new CommandResetSkills();

    @Override
    public String getCommandName() {
        return "reset";
    }
    //TODO: REWRITE

    @Override
    public void handleCommand(ICommandSender sender, String[] args) throws PlayerNotFoundException {
        EntityPlayerMP Sender = (EntityPlayerMP)sender;
        EntityPlayer target;

        if (args.length >= 2) target = CommandBase.getPlayer(sender, args[1]);
        else target = Sender;
        NBTTagCompound data = Skills.SkillData(target);

        if (PermissionsHandler.canUseCommand(5 , Sender)) {
            data.setInteger("SkillPoints", 0);
            data.setInteger("ArmStrengthLevel", 0);
            data.setInteger("LegStrengthLevel", 0);
            data.setInteger("TorsoStrengthLevel", 0);
            data.setInteger("HeadStrengthLevel", 0);
            data.setInteger("FeetStrengthLevel", 0);
            data.setInteger("HealthLevel", 0);
            data.setInteger("StaminaLevel", 0);
            data.setInteger("FlyLevel", 0);
            data.setInteger("StealthLevel", 0);
            data.setInteger("ImmunityLevel", 0);

            target.capabilities.allowFlying = false;
            target.capabilities.isFlying = false;

            sender.addChatMessage(new ChatComponentText(target.getDisplayName() + "'s \u00A74skills have been reset"));
            Skills.saveSkillData(target, data);
        } else {
            sender.addChatMessage(new ChatComponentText("\u00A74You cannot use this command!"));
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }
}
