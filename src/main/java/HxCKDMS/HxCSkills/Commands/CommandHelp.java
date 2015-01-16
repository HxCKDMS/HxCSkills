package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.Commands.ISubCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class CommandHelp implements ISubCommand {
    
    public static CommandHelp instance = new CommandHelp();
    
    @Override
    public String getName() {
        return "help";
    }
    
    @Override
    public void execute(ICommandSender sender, String[] args) throws CommandException {
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
            "NOTE: These commands are temporary until we move to GUI system",
            "/HxCSkills help: shows all commands with explanation.",
            "/HxCSkills XPtoSP: used to convert 5 levels XP to 1 Skillpoint",
    };
}
