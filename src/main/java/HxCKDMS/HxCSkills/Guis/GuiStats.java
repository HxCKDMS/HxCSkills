package HxCKDMS.HxCSkills.Guis;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class GuiStats extends GuiScreen {

    public double Health;
    public double Strength;
    public double Speed;
    public double Score;
    public double StepHeight;
    public double FlySpeed;
    public double KnockbackResistance;

    public void stats(NBTFileIO nbtfileio, EntityPlayer player){
        IAttributeInstance PDMG = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);
        IAttributeInstance PSPD = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
        IAttributeInstance PKR = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.knockbackResistance);
        Health = player.getMaxHealth();
        Strength = PDMG.getAttributeValue();
        Speed = PSPD.getAttributeValue();
        Score = player.getScore();
        StepHeight = player.stepHeight;
        FlySpeed = player.capabilities.getFlySpeed();
        KnockbackResistance = PKR.getAttributeValue();
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {

        this.fontRendererObj.drawString(StatCollector.translateToLocal("STATS"), 100, 0, 0x404040);
        this.fontRendererObj.drawString(StatCollector.translateToLocal(Health + " : Max Health"), 126, 20, 0x404040);
        /*int k;

        for (k = 0; k < this.buttonList.size(); ++k)
        {
            ((GuiButton)this.buttonList.get(k)).drawButton(this.mc, mouseX, mouseY);
        }

        for (k = 0; k < this.labelList.size(); ++k)
        {
            ((GuiLabel)this.labelList.get(k)).drawLabel(this.mc, mouseX, mouseY);
        }*/
    }
    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }
    @Override
    public boolean doesGuiPauseGame()
    {
        return true;
    }

}
