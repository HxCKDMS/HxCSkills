package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.Commands.ISubCommand;
import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import java.io.File;
import java.util.List;

public class CommandSkillPoints implements ISubCommand {
    public static CommandSkillPoints instance = new CommandSkillPoints();

    @Override
    public String getName() {
        return "SP";
    }

    @Override
    public void execute(ICommandSender sender, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP)sender;
        String UUID = player.getUniqueID().toString();

        File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");

        NBTTagCompound Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");

        int SavedSkillPoints = Skills.getInteger("SkillPoints");
        player.addChatMessage(new ChatComponentText("\u00A73You have " + SavedSkillPoints + " Skill Points!"));
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }
}
