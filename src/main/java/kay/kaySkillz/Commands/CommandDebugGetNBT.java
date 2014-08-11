package kay.kayXEnchants.Commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandDebugGetNBT implements ICommand
{
  private List aliases;
  public CommandDebugGetNBT()
  {
    this.aliases = new ArrayList();
    this.aliases.add("getNBT");
    this.aliases.add("NBT");
  }

  @Override
  public String getCommandName()
  {
    return "DebugGetNBT";
  }

  @Override
  public String getCommandUsage(ICommandSender icommandsender)
  {
    return "DebugGetNBT <tag>";
  }

  @Override
  public List getCommandAliases()
  {
    return this.aliases;
  }

  @Override
  public void processCommand(ICommandSender icommandsender, String[] string)
  {
	  if(icommandsender instanceof EntityPlayer)
	  {
		  EntityPlayer player = (EntityPlayer) icommandsender;
	  }

  }

  public static final String DAGGER = String.copyValueOf(Character.toChars(0x86));
  public static final String COLOR = String.copyValueOf(Character.toChars(0xA7));

  @Override
  public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
  {
    return true;
  }

  @Override
  public List addTabCompletionOptions(ICommandSender icommandsender,
      String[] astring)
  {
    return null;
  }

  @Override
  public boolean isUsernameIndex(String[] astring, int i)
  {
    return false;
  }

  @Override
  public int compareTo(Object o)
  {
    return 0;
  }
}