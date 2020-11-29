//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.text.DecimalFormat;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class CoordsComponent extends HudComponentItem
{
    public final Value<Boolean> NetherCoords;
    public final Value<Modes> Mode;
    final DecimalFormat Formatter;
    
    public CoordsComponent() {
        super("Coords", 2.0f, 245.0f);
        this.NetherCoords = new Value<Boolean>("NetherCoords", new String[] { "NC" }, "Displays nether coords.", true);
        this.Mode = new Value<Modes>("Mode", new String[] { "Mode" }, "Mode of displaying coordinates", Modes.Inline);
        this.Formatter = new DecimalFormat("#.#");
    }
    
    public String format(final double p_Input) {
        String l_Result = this.Formatter.format(p_Input);
        if (!l_Result.contains(".")) {
            l_Result += ".0";
        }
        return l_Result;
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        switch (this.Mode.getValue()) {
            case Inline: {
                String l_Coords = String.format("%sXYZ %s%s, %s, %s", ChatFormatting.GRAY, ChatFormatting.WHITE, this.format(this.mc.player.posX), this.format(this.mc.player.posY), this.format(this.mc.player.posZ));
                if (this.NetherCoords.getValue()) {
                    l_Coords += String.format(" %s[%s%s, %s%s]", ChatFormatting.GRAY, ChatFormatting.WHITE, (this.mc.player.dimension != -1) ? this.format(this.mc.player.posX / 8.0) : this.format(this.mc.player.posX * 8.0), (this.mc.player.dimension != -1) ? this.format(this.mc.player.posZ / 8.0) : this.format(this.mc.player.posZ * 8.0), ChatFormatting.GRAY);
                }
                this.SetWidth(RenderUtil.getStringWidth(l_Coords));
                this.SetHeight(RenderUtil.getStringHeight(l_Coords));
                RenderUtil.drawStringWithShadow(l_Coords, this.GetX(), this.GetY(), -1);
                break;
            }
            case NextLine: {
                final String l_X = String.format("%sX: %s%s [%s]", ChatFormatting.GRAY, ChatFormatting.WHITE, this.format(this.mc.player.posX), this.NetherCoords.getValue() ? ((this.mc.player.dimension != -1) ? this.format(this.mc.player.posX / 8.0) : this.format(this.mc.player.posX * 8.0)) : "");
                final String l_Y = String.format("%sY: %s%s [%s]", ChatFormatting.GRAY, ChatFormatting.WHITE, this.format(this.mc.player.posY), this.NetherCoords.getValue() ? this.format(this.mc.player.posY) : "");
                final String l_Z = String.format("%sZ: %s%s [%s]", ChatFormatting.GRAY, ChatFormatting.WHITE, this.format(this.mc.player.posZ), this.NetherCoords.getValue() ? ((this.mc.player.dimension != -1) ? this.format(this.mc.player.posZ / 8.0) : this.format(this.mc.player.posZ * 8.0)) : "");
                RenderUtil.drawStringWithShadow(l_X, this.GetX(), this.GetY(), -1);
                RenderUtil.drawStringWithShadow(l_Y, this.GetX(), this.GetY() + RenderUtil.getStringHeight(l_X), -1);
                RenderUtil.drawStringWithShadow(l_Z, this.GetX(), this.GetY() + RenderUtil.getStringHeight(l_X) + RenderUtil.getStringHeight(l_Y), -1);
                this.SetWidth(RenderUtil.getStringWidth(l_X));
                this.SetHeight(RenderUtil.getStringHeight(l_X) * 3.0f);
                break;
            }
        }
    }
    
    public enum Modes
    {
        Inline, 
        NextLine;
    }
}
