package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.Commands.ISubCommand;
import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCSkills.Config;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import java.io.File;
import java.util.List;

public class CommandXPtoSP implements ISubCommand {
    public static CommandXPtoSP instance = new CommandXPtoSP();

    @Override
    public String getName() {
        return "XPtoSP";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public void execute(ICommandSender sender, String[] args) throws CommandException {
        EntityPlayerMP player = (EntityPlayerMP)sender;
        String UUID = player.getUniqueID().toString();

        File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");

        NBTTagCompound Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");

        int SavedSkillPoints = Skills.getInteger("SkillPoints");

        int SPAdded = 1;
        if (args.length >= 2) {
            SPAdded = Integer.parseInt(args[1]);
        }

        int XPLevels = player.experienceLevel;
        int XPLevelsRequired = Config.Difficulty*SPAdded;
        int SkillPoints = SavedSkillPoints;

        if (XPLevels > XPLevelsRequired) {
            player.removeExperienceLevel(XPLevelsRequired);
            SkillPoints += SPAdded;
            player.playSound("random.orb", 1, 1);
            Skills.setInteger("SkillPoints", SkillPoints);
            player.addChatMessage(new ChatComponentText("\u00A79You have gained " + SPAdded + " skill point(s)!"));
        } else {
            player.addChatMessage(new ChatComponentText("\u00A74You don't have enough XP levels. " + (XPLevelsRequired - XPLevels) + " more xp levels needed!"));
        }
        NBTFileIO.setNbtTagCompound(CustomPlayerData, "skills", Skills);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }
}
