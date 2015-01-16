package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.Commands.ISubCommand;
import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandBase extends net.minecraft.command.CommandBase {
    
    public static CommandBase instance = new CommandBase();
    private static TMap<String, ISubCommand> commands = new THashMap<String, ISubCommand>();
    
    static {
        registerSubCommand(CommandHelp.instance);
        registerSubCommand(CommandXPtoSP.instance);
        registerSubCommand(CommandSkillPoints.instance);
        registerSubCommand(CommandAddPoint.instance);
        registerSubCommand(CommandStats.instance);
    }
    
    public static void initCommands(FMLServerStartingEvent event) {
        event.registerServerCommand(instance);
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

    @Override
    public String getName() {
        return "HxCSkills";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getName() + " help";
    }

    @Override
    public void execute(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            String k = args[0].toLowerCase();
            if (commands.containsKey(k)) {
                commands.get(k).execute(sender, args);
            } else {
                throw new WrongUsageException("Type '" + getCommandUsage(sender) + "' for help.");
            }
        } else {
            throw new WrongUsageException("Type '" + getCommandUsage(sender) + "' for help.");
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getAliases() {
        List aliases = new ArrayList();
        aliases.add("HxCSkills");
        aliases.add("HxCS");
        return aliases;
    }
    
    public static boolean registerSubCommand(ISubCommand subCommand) {
        String k = subCommand.getName().toLowerCase();
        
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
