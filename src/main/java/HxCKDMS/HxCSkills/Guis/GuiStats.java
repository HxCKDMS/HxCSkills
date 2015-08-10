package HxCKDMS.HxCSkills.Guis;

import HxCKDMS.HxCSkills.lib.Skills;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;

public class GuiStats extends GuiScreen {

    public double HP, Strength, Speed, StepHeight, KnockbackResistance;
    public int Score;

    public void getStats(EntityPlayer player){
        NBTTagCompound skills = Skills.SkillData(player);
        IAttributeInstance PDMG = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);
        IAttributeInstance PSPD = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
        IAttributeInstance PKR = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.knockbackResistance);
        HP = player.getMaxHealth();
        Strength = PDMG.getAttributeValue();
        Speed = PSPD.getAttributeValue();
        Score = player.getScore();
        StepHeight = player.stepHeight;
        KnockbackResistance = PKR.getAttributeValue();
        Skills.update(player);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        ScaledResolution sres = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        getStats(mc.thePlayer);
        int button_width = 160;
        int GUITCX = (sres.getScaledWidth() - button_width) / 2;
        int GUITCY = (sres.getScaledHeight() - 160) / 2 - 30;
        int modbut = 12, pad = 4, negbutx = GUITCX-90, posbutx = GUITCX+30, b = modbut+pad;

        buttonList.add(new GuiButton(0, GUITCX + 50, GUITCY + 200, 60, 20, "Done"));

        for (int i = 0; i < 10; i++) {
            buttonList.add(new GuiButton(1+(i*2), posbutx, GUITCY +(b*i), modbut, modbut, "+"));
            buttonList.add(new GuiButton(2+(i*2), negbutx, GUITCY +(b*i), modbut, modbut, "-"));
        }

        super.initGui();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
        int color = 0x00FFFF, dist = 50, dist2 = 120;
        drawRect(width / 2 - 105, 60, width / 2 - 35, height / 2 + 5, new Color(50, 170, 170, 70).getRGB());
        drawCenteredString(fontRendererObj, "Stats", width / 2, 25, color);
        drawCenteredString(fontRendererObj, "Skill Points : " + Skills.SP, width / 2, 50, color);
        super.drawScreen(mouseX, mouseY, f);
        for (Object button : buttonList) {
            if (button instanceof GuiButton) {
                GuiButton btn = (GuiButton) button;
                switch (btn.id) {
                    case 1:
                        drawCenteredString(fontRendererObj, "Health : " + Skills.Health, btn.xPosition-dist, btn.yPosition + 2, color);
                        drawCenteredString(fontRendererObj, "Player Health : " + HP, btn.xPosition+dist2, btn.yPosition + 2, color);
                        break;
                    case 3:
                        drawCenteredString(fontRendererObj, "Head Strength : " + Skills.HeadStrength, btn.xPosition - dist, btn.yPosition + 2, color);
                        drawCenteredString(fontRendererObj, "TODO ADD Head EFfect", btn.xPosition + dist2, btn.yPosition + 2, color);
                        break;
                    case 5:
                        drawCenteredString(fontRendererObj, "Arm Strength : " + Skills.ArmStrength, btn.xPosition - dist, btn.yPosition + 2, color);
                        drawCenteredString(fontRendererObj, "Player Strength : " + Strength, btn.xPosition + dist2, btn.yPosition + 2, color);
                        break;
                    case 7:
                        drawCenteredString(fontRendererObj, "Torso Strength : " + Skills.TorsoStrength, btn.xPosition - dist, btn.yPosition + 2, color);
                        drawCenteredString(fontRendererObj, "Player Resistance : " + KnockbackResistance, btn.xPosition + dist2, btn.yPosition + 2, color);
                        break;
                    case 9:
                        drawCenteredString(fontRendererObj, "Leg Strength : " + Skills.LegStrength, btn.xPosition - dist, btn.yPosition + 2, color);
                        drawCenteredString(fontRendererObj, "Player Speed : " + Speed, btn.xPosition + dist2, btn.yPosition + 2, color);
                        break;
                    case 11:
                        drawCenteredString(fontRendererObj, "Foot Strength : " + Skills.FeetStrength, btn.xPosition - dist, btn.yPosition + 2, color);
                        drawCenteredString(fontRendererObj, "Player Fall Resistance : " + Math.round((Skills.FeetStrength + Skills.LegStrength)/4 + 3), btn.xPosition + dist2, btn.yPosition + 2, color);
                        break;
                    case 13:
                        drawCenteredString(fontRendererObj, "Stamina Level : " + Skills.Stamina, btn.xPosition - dist, btn.yPosition + 2, color);
                        drawCenteredString(fontRendererObj, "Step Height : " + StepHeight, btn.xPosition + dist2, btn.yPosition + 2, color);
                        break;
                    case 15:
                        drawCenteredString(fontRendererObj, "Immunity Level : " + Skills.Immunity, btn.xPosition - dist, btn.yPosition + 2, color);
                        drawCenteredString(fontRendererObj, "Disease/Poison Resistance : " + Skills.Immunity/120, btn.xPosition + dist2, btn.yPosition + 2, color);
                        break;
                    case 17:
                        drawCenteredString(fontRendererObj, "Stealth Level : " + Skills.Stealth, btn.xPosition - dist, btn.yPosition + 2, color);
                        drawCenteredString(fontRendererObj, "Silence : " + Skills.Stealth/100, btn.xPosition + dist2, btn.yPosition + 2, color);
                        break;
                    case 19:
                        drawCenteredString(fontRendererObj, "Flight Level : " + Skills.Fly, btn.xPosition - dist, btn.yPosition + 2, color);
                        drawCenteredString(fontRendererObj, "Score : " + Score, btn.xPosition + dist2, btn.yPosition+2, color);
                        break;
                    default : break;
                }
            }
        }
    }
}
