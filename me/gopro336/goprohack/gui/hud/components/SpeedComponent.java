//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.math.MathHelper;
import me.gopro336.goprohack.util.Timer;
import java.text.DecimalFormat;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class SpeedComponent extends HudComponentItem
{
    final DecimalFormat Formatter;
    private double PrevPosX;
    private double PrevPosZ;
    private Timer timer;
    
    public SpeedComponent() {
        super("Speed", 2.0f, 80.0f);
        this.Formatter = new DecimalFormat("#.#");
        this.timer = new Timer();
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        if (this.timer.passed(1000.0)) {
            this.PrevPosX = this.mc.player.prevPosX;
            this.PrevPosZ = this.mc.player.prevPosZ;
        }
        final double deltaX = this.mc.player.posX - this.PrevPosX;
        final double deltaZ = this.mc.player.posZ - this.PrevPosZ;
        final float l_Distance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        final double l_KMH = Math.floor(l_Distance / 1000.0f / 1.3888889E-5f);
        String l_Formatter = this.Formatter.format(l_KMH);
        if (!l_Formatter.contains(".")) {
            l_Formatter += ".0";
        }
        final String bps = ChatFormatting.GRAY + "Speed " + ChatFormatting.WHITE + l_Formatter + "km/h";
        this.SetWidth(RenderUtil.getStringWidth(bps));
        this.SetHeight(RenderUtil.getStringHeight(bps) + 1.0f);
        RenderUtil.drawStringWithShadow(bps, this.GetX(), this.GetY(), -1);
    }
}
