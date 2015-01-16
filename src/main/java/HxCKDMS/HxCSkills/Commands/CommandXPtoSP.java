package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
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
    public void execute(ICommandSender sender, String[] args) throws CommandException {
        EntityPlayerMP player = (EntityPlayerMP)sender;
        String UUID = player.getUniqueID().toString();

        File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");

        NBTTagCompound Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");

        int SavedSkillPoints = Skills.getInteger("SkillPoints");

        int xpl = player.experienceLevel;
        int SkillPoints = SavedSkillPoints;

        if (xpl > 5){
            player.removeExperienceLevel(5);
            ++SkillPoints;
        }
        Skills.setInteger("SkillPoints", SkillPoints);
        player.addChatMessage(new ChatComponentText("\u00A79You have gained a skill point!"));
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }
}
