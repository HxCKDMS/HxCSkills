package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.api.ISubCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandBase extends net.minecraft.command.CommandBase {
    
    public static CommandBase instance = new CommandBase();
    private static HashMap<String, ISubCommand> commands = new HashMap<>();
    
    static {
        registerSubCommand(CommandHelp.instance);
        registerSubCommand(CommandStats.instance);
        registerSubCommand(CommandResetSkills.instance);
        registerSubCommand(CommandSetSkills.instance);
    }
    
    public static void initCommands(FMLServerStartingEvent event) {
        event.registerServerCommand(instance);
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "HxCSkills";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName() + " help";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            String k = args[0].toLowerCase();
            if (commands.containsKey(k)) {
                commands.get(k).handleCommand(sender, args);
            } else {
                throw new WrongUsageException("Type '" + getCommandUsage(sender) + "' for help.");
            }
        } else {
            throw new WrongUsageException("Type '" + getCommandUsage(sender) + "' for help.");
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getCommandAliases() {
        List aliases = new ArrayList();
        aliases.add("HxCSkills");
        aliases.add("HxCS");
        return aliases;
    }
    public static boolean registerSubCommand(ISubCommand subCommand) {
        String k = subCommand.getCommandName().toLowerCase();
        
        if (!commands.containsKey(k)) {
            commands.put(k, subCommand);
            return true;
        }
        return false;
    }
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, String.valueOf(commands.keySet()));
        } else if (commands.containsKey(args[0])) {
            return commands.get(args[0]).addTabCompletionOptions(sender, args);
        }
        return null;
    }
}
