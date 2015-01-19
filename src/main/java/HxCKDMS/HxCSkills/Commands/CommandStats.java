package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.Commands.ISubCommand;
import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
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
    public void execute(ICommandSender sender, String[] args) throws PlayerNotFoundException {
        EntityPlayerMP target = (args.length==2) ? CommandBase.getPlayer(sender, args[1]) : (EntityPlayerMP)sender;
        String UUID = target.getUniqueID().toString();

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
        int FlyLevel = Skills.getInteger("FlyLevel");

        if (args.length == 2) {
            target.addChatMessage(new ChatComponentText("\u00A7b\u00A76" + target.getName() + "\u00A7nSTATS"));
            ChatComponentText AS = new ChatComponentText("\u00A73ArmStrength: " + ArmStrength);
            ChatComponentText LS = new ChatComponentText("\u00A79LegStrength: " + LegStrength);
            ChatComponentText TS = new ChatComponentText("\u00A73TorsoStrength: " + TorsoStrength);
            ChatComponentText HS = new ChatComponentText("\u00A79HeadStrength: " + HeadStrength);
            ChatComponentText FS = new ChatComponentText("\u00A73FeetStrength: " + FeetStrength);
            ChatComponentText HL = new ChatComponentText("\u00A79Health: " + Health);
            ChatComponentText SL = new ChatComponentText("\u00A73Stamina: " + Stamina);
            ChatComponentText FL = new ChatComponentText("\u00A79Fly Level: " + FlyLevel);
            sendStats(target, AS, LS, TS, HS, FS, HL, SL, FL);
        } else {
            target.addChatMessage(new ChatComponentText("\u00A7b\u00A76     \u00A7nSTATS"));
            target.addChatMessage(new ChatComponentText("\u00A71Skill Points: " + SavedSkillPoints));
            ChatComponentText AS = new ChatComponentText("\u00A73ArmStrength: " + ArmStrength);
            ChatComponentText LS = new ChatComponentText("\u00A79LegStrength: " + LegStrength);
            ChatComponentText TS = new ChatComponentText("\u00A73TorsoStrength: " + TorsoStrength);
            ChatComponentText HS = new ChatComponentText("\u00A79HeadStrength: " + HeadStrength);
            ChatComponentText FS = new ChatComponentText("\u00A73FeetStrength: " + FeetStrength);
            ChatComponentText HL = new ChatComponentText("\u00A79Health: " + Health);
            ChatComponentText SL = new ChatComponentText("\u00A73Stamina: " + Stamina);
            ChatComponentText FL = new ChatComponentText("\u00A79Fly Level: " + FlyLevel);
            sendStats(target, AS, LS, TS, HS, FS, HL, SL, FL);
        }
    }
    
    public void sendStats (EntityPlayerMP target, ChatComponentText AS, ChatComponentText LS, ChatComponentText TS, ChatComponentText HS, ChatComponentText FS, ChatComponentText HL, ChatComponentText SL, ChatComponentText FL) {
        target.addChatMessage(AS);
        target.addChatMessage(LS);
        target.addChatMessage(TS);
        target.addChatMessage(HS);
        target.addChatMessage(FS);
        target.addChatMessage(HL);
        target.addChatMessage(SL);
        target.addChatMessage(FL);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length == 2){
            return HxCKDMS.HxCCore.Commands.CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        return null;
    }
}
