package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.Commands.ISubCommand;
import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.Handlers.PermissionsHandler;
import HxCKDMS.HxCCore.HxCCore;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import java.io.File;
import java.util.List;

public class CommandResetSkills implements ISubCommand {
    public static CommandResetSkills instance = new CommandResetSkills();

    @Override
    public String getName() {
        return "Reset";
    }

    @Override
    public void execute(ICommandSender sender, String[] args) throws PlayerNotFoundException {
        EntityPlayerMP Sender = (EntityPlayerMP)sender;
        EntityPlayer target;

        if (args.length >= 2){
            target = CommandBase.getPlayer(sender, args[1]);
        } else {
            target = Sender;
        }
        String UUID = target.getUniqueID().toString();
        File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
        NBTTagCompound Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");

        if (PermissionsHandler.canUseCommand(5 , target)) {
            Skills.setInteger("SkillPoints", 0);
            Skills.setInteger("ArmStrengthLevel", 0);
            Skills.setInteger("LegStrengthLevel", 0);
            Skills.setInteger("TorsoStrengthLevel", 0);
            Skills.setInteger("HeadStrengthLevel", 0);
            Skills.setInteger("FeetStrengthLevel", 0);
            Skills.setInteger("HealthLevel", 0);
            Skills.setInteger("StaminaLevel", 0);
            Skills.setInteger("FlyLevel", 0);

            target.capabilities.allowFlying = false;
            target.capabilities.isFlying = false;

            NBTFileIO.setNbtTagCompound(CustomPlayerData, "skills", Skills);
        } else {
            sender.addChatMessage(new ChatComponentText("\u00A74You cannot use this command!"));
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }
}
