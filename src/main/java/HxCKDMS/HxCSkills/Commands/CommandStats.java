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

public class CommandStats implements ISubCommand {
    public static CommandStats instance = new CommandStats();

    @Override
    public String getName() {
        return "Stats";
    }

    @Override
    public void execute(ICommandSender sender, String[] args) throws CommandException {
        EntityPlayerMP player = (EntityPlayerMP)sender;
        String UUID = player.getUniqueID().toString();

        File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");

        NBTTagCompound Skills = NBTFileIO.getNbtTagCompound(CustomPlayerData, "skills");

        int SavedSkillPoints = Skills.getInteger("SkillPoints");

        int ArmStrength = Skills.getInteger("ArmStrengthLevel");
        int LegStrength = Skills.getInteger("LegStrengthLevel");
        int TorsoStrength = Skills.getInteger("TorsoStrengthLevel");
        int HeadStrength = Skills.getInteger("HeadStrengthLevel");
        int FeetStrength = Skills.getInteger("FeetStrengthLevel");
        int Health = Skills.getInteger("HealthLevel");
        int Stamina = Skills.getInteger("StaminaLevel");

        player.addChatMessage(new ChatComponentText("\u00A7b\u00A76     \u00A7nSTATS"));
        player.addChatMessage(new ChatComponentText("\u00A71Skill Points: " + SavedSkillPoints));
        player.addChatMessage(new ChatComponentText("\u00A73ArmStrength: " + ArmStrength));
        player.addChatMessage(new ChatComponentText("\u00A79LegStrength: " + LegStrength));
        player.addChatMessage(new ChatComponentText("\u00A73TorsoStrength: " + TorsoStrength));
        player.addChatMessage(new ChatComponentText("\u00A79HeadStrength: " + HeadStrength));
        player.addChatMessage(new ChatComponentText("\u00A73FeetStrength: " + FeetStrength));
        player.addChatMessage(new ChatComponentText("\u00A79Health: " + Health));
        player.addChatMessage(new ChatComponentText("\u00A73Stamina: " + Stamina));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }
}
