//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.math.MathHelper;
import java.text.DecimalFormat;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class YawComponent extends HudComponentItem
{
    public YawComponent() {
        super("Yaw", 2.0f, 200.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        final DecimalFormat l_Format = new DecimalFormat("#.##");
        final float l_Yaw = MathHelper.wrapDegrees(this.mc.player.rotationYaw);
        String direction = ChatFormatting.GRAY + "Yaw: " + ChatFormatting.WHITE + l_Format.format(l_Yaw);
        if (!direction.contains(".")) {
            direction += ".00";
        }
        else {
            final String[] l_Split = direction.split("\\.");
            if (l_Split != null && l_Split[1] != null && l_Split[1].length() != 2) {
                direction += 0;
            }
        }
        this.SetWidth(RenderUtil.getStringWidth(direction));
        this.SetHeight(RenderUtil.getStringHeight(direction));
        RenderUtil.drawStringWithShadow(direction, this.GetX(), this.GetY(), -1);
    }
}
