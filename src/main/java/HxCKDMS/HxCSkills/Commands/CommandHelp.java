package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.api.ISubCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class CommandHelp implements ISubCommand {

    public static CommandHelp instance = new CommandHelp();

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        sender.addChatMessage(new ChatComponentText("\u00A71Commands:"));

        boolean b = false;
        for (String line : LINES) {
            ChatComponentText message = new ChatComponentText(line);
            message.getChatStyle().setColor((b = !b) ? EnumChatFormatting.BLUE : EnumChatFormatting.DARK_AQUA);
            sender.addChatMessage(message);
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }

    /** TODO: THESE SHOULD ALL BE LOADED FROM A LANGUAGE FILE **/
    private static final String[] LINES = {
            "/HxCSkills help: shows all commands with explanation.",
            "/HxCSkills stats: used to list your current Skill Levels.",
            "/HxCSkills resetSkills: sets all skill points to 0.",
            "/HxCSkills setSkills: used to list your current Skill Levels.",
    };
}
