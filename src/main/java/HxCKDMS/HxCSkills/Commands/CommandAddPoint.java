package HxCKDMS.HxCSkills.Commands;

import HxCKDMS.HxCCore.Commands.ISubCommand;
import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCSkills.Config;
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
    public int getRequiredPermissionLevel()
    {
        return 0;
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
        int FlySkillLevel = Skills.getInteger("FlyLevel");

        int TargetSkill;
        int HML = 1;

        //N%@ New % @ (%=W Whatever||@=S Strength or L Level)
        int NAS;
        int NLS;
        int NTS;
        int NHS;
        int NFS; //not need for speed
        int NHL;
        int NSL;
        int NFL; // not football New Fly Level

        String SkillToLevel = null;
        String HowManyLevels = null;
        if (args.length >= 2){
            SkillToLevel = args[1].toLowerCase();
        }
        if (args.length >= 3) {
            HowManyLevels = args[2];
        }
        if (HowManyLevels != null)HML = Integer.parseInt(HowManyLevels);

        if (SkillToLevel != null){
            if (SkillToLevel.contains("arm")){
                TargetSkill = 1;
            }else if (SkillToLevel.contains("leg")){
                TargetSkill = 2;
            }else if (SkillToLevel.contains("torso")){
                TargetSkill = 3;
            }else if (SkillToLevel.contains("head")){
                TargetSkill = 4;
            }else if (SkillToLevel.contains("feet")){
                TargetSkill = 5;
            }else if (SkillToLevel.equalsIgnoreCase("health")){
                TargetSkill = 6;
            }else if (SkillToLevel.equalsIgnoreCase("stamina")){
                TargetSkill = 7;
            }else if (SkillToLevel.equalsIgnoreCase("fly") && Config.FlySkillEnable){
                if (SavedSkillPoints >= 25*HML) {
                    TargetSkill = 8;
                }else{
                    TargetSkill = 0;
                    player.addChatMessage(new ChatComponentText("\u00A74Fly skill requires 25 Skill Points!"));
                    if (!Config.FlySkillEnable){
                        player.addChatMessage(new ChatComponentText("\u00A74Fly skill is Disabled in your configs!"));
                    }
                }
            }else{
                TargetSkill = 0;
                player.addChatMessage(new ChatComponentText("Current compatible arguments are: Fly, LegStrength, TorsoStrength, HeadStrength, FeetStrength, Health, Stamina."));
            }

            if (TargetSkill != 0 && HML <= SavedSkillPoints){
                switch (TargetSkill){
                    case 1:
                        NAS = ArmStrength + HML;
                        Skills.setInteger("SkillPoints", (SavedSkillPoints - HML));
                        Skills.setInteger("ArmStrengthLevel", NAS);
                        player.addChatMessage(new ChatComponentText("\u00A72ArmStrength Level is now " + NAS));
                        break;
                    case 2:
                        NLS = LegStrength + HML;
                        Skills.setInteger("SkillPoints", (SavedSkillPoints - HML));
                        Skills.setInteger("LegStrengthLevel", NLS);
                        player.addChatMessage(new ChatComponentText("\u00A72LegStrength Level is now " + NLS));
                        break;
                    case 3:
                        NTS = TorsoStrength + HML;
                        Skills.setInteger("SkillPoints", (SavedSkillPoints - HML));
                        Skills.setInteger("TorsoStrengthLevel", NTS);
                        player.addChatMessage(new ChatComponentText("\u00A72TorsoStrength Level is now " + NTS));
                        break;
                    case 4:
                        NHS = HeadStrength + HML;
                        Skills.setInteger("SkillPoints", (SavedSkillPoints - HML));
                        Skills.setInteger("HeadStrengthLevel", NHS);
                        player.addChatMessage(new ChatComponentText("\u00A72HeadStrength Level is now " + NHS));
                        break;
                    case 5:
                        NFS = FeetStrength + HML;
                        Skills.setInteger("SkillPoints", (SavedSkillPoints - HML));
                        Skills.setInteger("FeetStrengthLevel", NFS);
                        player.addChatMessage(new ChatComponentText("\u00A72FeetStrength Level is now " + NFS));
                        break;
                    case 6:
                        NHL = Health + HML;
                        Skills.setInteger("SkillPoints", (SavedSkillPoints - HML));
                        Skills.setInteger("HealthLevel", NHL);
                        player.addChatMessage(new ChatComponentText("\u00A72Health Level is now " + NHL));
                        break;
                    case 7:
                        NSL = Stamina + HML;
                        Skills.setInteger("SkillPoints", (SavedSkillPoints - HML));
                        Skills.setInteger("StaminaLevel", NSL);
                        player.addChatMessage(new ChatComponentText("\u00A72Stamina Level is now " + NSL));
                        break;
                    case 8:
                        NFL = FlySkillLevel + HML;
                        Skills.setInteger("SkillPoints", (SavedSkillPoints - (HML*25)));
                        Skills.setInteger("FlyLevel", NFL);
                        player.addChatMessage(new ChatComponentText("\u00A72Fly Level is now " + NFL));
                        break;
                    default: player.addChatMessage(new ChatComponentText("\u00A7eSomething went wrong."));
                        break;
                }
            }
        }
        NBTFileIO.setNbtTagCompound(CustomPlayerData, "skills", Skills);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }
}
