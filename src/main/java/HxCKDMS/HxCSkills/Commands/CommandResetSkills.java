package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.Commands.ISubCommand;
import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.util.List;

public class CommandResetSkills implements ISubCommand {
    public static CommandResetSkills instance = new CommandResetSkills();

    @Override
    public String getName() {
        return "Reset";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

    @Override
    public void execute(ICommandSender sender, String[] args) throws CommandException {
        EntityPlayerMP player = (EntityPlayerMP)sender;
        String UUID = player.getUniqueID().toString();

        File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");

        if (args.length >= 2){
            EntityPlayerMP player2 = CommandBase.getPlayer(sender, args[1]);
            String P2UUID = player2.getUniqueID().toString();
            CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + P2UUID + ".dat");
        }

        NBTTagCompound Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");

        Skills.setInteger("SkillPoints", 0);
        Skills.setInteger("ArmStrengthLevel", 0);
        Skills.setInteger("LegStrengthLevel", 0);
        Skills.setInteger("TorsoStrengthLevel", 0);
        Skills.setInteger("HeadStrengthLevel", 0);
        Skills.setInteger("FeetStrengthLevel", 0);
        Skills.setInteger("HealthLevel", 0);
        Skills.setInteger("StaminaLevel", 0);
        Skills.setInteger("FlyLevel", 0);

        NBTFileIO.setNbtTagCompound(CustomPlayerData, "skills", Skills);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }
}
