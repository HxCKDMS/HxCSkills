package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.api.ISubCommand;
import HxCKDMS.HxCSkills.lib.Skills;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CommandStats implements ISubCommand {
    public static CommandStats instance = new CommandStats();

    @Override
    public String getCommandName() {
        return "Stats";
    }
    //TODO: REWRITE || REMOVE

    @Override
    public void handleCommand(ICommandSender sender, String[] args) throws PlayerNotFoundException {
        EntityPlayerMP target = (args.length==2) ? CommandBase.getPlayer(sender, args[1]) : (EntityPlayerMP)sender;
        Skills.update(target);
        NBTTagCompound data = Skills.SkillData(target);

        String[] msgs = new String[16];

        msgs[0] = ("\u00A73ArmStrength: " + data.getInteger("ArmStrengthLevel"));
        msgs[1] = ("\u00A79LegStrength: " + data.getInteger("LegStrengthLevel"));
        msgs[2] = ("\u00A73TorsoStrength: " + data.getInteger("LegStrengthLevel"));
        msgs[3] = ("\u00A79HeadStrength: " + data.getInteger("HeadStrengthLevel"));
        msgs[4] = ("\u00A73FeetStrength: " + data.getInteger("FeetStrengthLevel"));
        msgs[5] = ("\u00A79Health: " + data.getInteger("HealthLevel"));
        msgs[6] = ("\u00A73Stamina: " + data.getInteger("StaminaLevel"));
        msgs[7] = ("\u00A79Fly Level: " + data.getInteger("FlyLevel"));
        msgs[8] = ("\u00A73Stealth: " + data.getInteger("StealthLevel"));
        msgs[9] = ("\u00A79Immunity Level: " + data.getInteger("ImmunityLevel"));

        if (args.length == 2) {
            target.addChatMessage(new ChatComponentText("\u00A7b\u00A76" + target.getDisplayName() + "\u00A7nSTATS"));
            sendStats(target, msgs);
        } else {
            target.addChatMessage(new ChatComponentText("\u00A7b\u00A76     \u00A7nSTATS"));
            target.addChatMessage(new ChatComponentText("\u00A71Skill Points: " + data.getInteger("SkillPoints")));
            sendStats(target, msgs);
        }
    }

    public void sendStats (EntityPlayerMP target, String[] Msg) {
        target.addChatMessage(new ChatComponentText(Msg[0]));
        target.addChatMessage(new ChatComponentText(Msg[1]));
        target.addChatMessage(new ChatComponentText(Msg[2]));
        target.addChatMessage(new ChatComponentText(Msg[3]));
        target.addChatMessage(new ChatComponentText(Msg[4]));
        target.addChatMessage(new ChatComponentText(Msg[5]));
        target.addChatMessage(new ChatComponentText(Msg[6]));
        target.addChatMessage(new ChatComponentText(Msg[7]));
        target.addChatMessage(new ChatComponentText(Msg[8]));
        target.addChatMessage(new ChatComponentText(Msg[9]));
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
