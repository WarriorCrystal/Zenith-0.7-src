//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import net.minecraft.util.math.MathHelper;
import net.minecraft.client.Minecraft;
import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class DirectionComponent extends HudComponentItem
{
    public DirectionComponent() {
        super("Direction", 2.0f, 155.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        final String direction = String.format("%s " + ChatFormatting.GRAY + "%s", this.getFacing(), this.getTowards());
        RenderUtil.drawStringWithShadow(direction, this.GetX(), this.GetY(), -1);
        this.SetWidth(RenderUtil.getStringWidth(direction));
        this.SetHeight(RenderUtil.getStringHeight(direction) + 1.0f);
    }
    
    private String getFacing() {
        switch (MathHelper.floor(Minecraft.getMinecraft().player.rotationYaw * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0: {
                return "South";
            }
            case 1: {
                return "South West";
            }
            case 2: {
                return "West";
            }
            case 3: {
                return "North West";
            }
            case 4: {
                return "North";
            }
            case 5: {
                return "North East";
            }
            case 6: {
                return "East";
            }
            case 7: {
                return "South East";
            }
            default: {
                return "Invalid";
            }
        }
    }
    
    private String getTowards() {
        switch (MathHelper.floor(Minecraft.getMinecraft().player.rotationYaw * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0: {
                return "+Z";
            }
            case 1: {
                return "-X +Z";
            }
            case 2: {
                return "-X";
            }
            case 3: {
                return "-X -Z";
            }
            case 4: {
                return "-Z";
            }
            case 5: {
                return "+X -Z";
            }
            case 6: {
                return "+X";
            }
            case 7: {
                return "+X +Z";
            }
            default: {
                return "Invalid";
            }
        }
    }
}
