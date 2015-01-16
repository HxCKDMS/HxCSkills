package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.Commands.ISubCommand;
import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import java.io.File;
import java.util.List;

public class CommandAddPoint implements ISubCommand {
    public static CommandAddPoint instance = new CommandAddPoint();

    @Override
    public String getName() {
        return "AP";
    }

    @Override
    public void execute(ICommandSender sender, String[] args) throws CommandException {
        EntityPlayerMP player = (EntityPlayerMP)sender;
        String UUID = player.getUniqueID().toString();

        File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");

        NBTTagCompound Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");

        int SavedSkillPoints = Skills.getInteger("SkillPoints");

        String SkillToLevel;
        String HowManyLevels;
        if (args.length == 1){
            SkillToLevel = args[1];
        }else if(args.length == 2){
            SkillToLevel = args[1];
            HowManyLevels = args[2];
        }else{
            player.addChatMessage(new ChatComponentText("/HxCSkills AP <Skill> [Levels]"));
            player.addChatMessage(new ChatComponentText("The number of levels isn't required."));
        }

    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }
}
