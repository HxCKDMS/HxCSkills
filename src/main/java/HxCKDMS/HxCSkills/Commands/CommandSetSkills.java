package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.Handlers.PermissionsHandler;
import HxCKDMS.HxCCore.api.ISubCommand;
import HxCKDMS.HxCSkills.Configurations;
import HxCKDMS.HxCSkills.lib.Skills;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.List;

import static HxCKDMS.HxCSkills.lib.Skills.SkillNames;
import static HxCKDMS.HxCSkills.lib.Skills.SkillNamesFull;

public class CommandSetSkills implements ISubCommand {
    public static CommandSetSkills instance = new CommandSetSkills();
    String SkillToLevel = "";
    String HowManyLevels = "";
    String TargetSkillName = SkillNamesFull[0];

    int SL;
    int TargetSkill = 0;
    int HML = 1;

    EntityPlayerMP target;
    //TODO: REWRITE && Make fully working

    @Override
    public String getCommandName() {
        return "set";
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) throws PlayerNotFoundException {
        EntityPlayerMP Sender = (EntityPlayerMP)sender;
        if (args.length >= 2) SkillToLevel = args[1].toLowerCase();
        if (args.length >= 3) HowManyLevels = args[2];
        if (args.length >= 4) target = CommandBase.getPlayer(sender, args[3]);
        else target = Sender;
        Skills.update(target);
        NBTTagCompound data = Skills.SkillData(target);

        if (!HowManyLevels.equals(""))HML = Integer.parseInt(HowManyLevels);

        if (!SkillToLevel.equals("")){
            for (int i = 0; i <= SkillNames.length; i++) {
                if (SkillToLevel.toLowerCase().contains(SkillNames[i].toLowerCase())) TargetSkill = i;
                else Sender.addChatMessage(new ChatComponentText("Current compatible arguments are: Stealth, Immunity, Fly, LegStrength, TorsoStrength, HeadStrength, FeetStrength, Health, Stamina."));
            }
            SL = data.getInteger(SkillNamesFull[TargetSkill]);
            TargetSkillName = SkillNamesFull[TargetSkill];

            if (PermissionsHandler.canUseCommand(Configurations.SetSkillsPL, Sender)) data.setInteger(TargetSkillName, (SL + HML));
            else Sender.addChatMessage(new ChatComponentText("\u00A74You're unable to use this command."));
            Skills.saveSkillData(target, data);

        } else Sender.addChatMessage(new ChatComponentText("\u00A74You must specify a skill name!"));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 2) return CommandBase.getListOfStringsMatchingLastWord(args, SkillNames);
        else if (args.length == 4) return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        else return null;
    }
}
