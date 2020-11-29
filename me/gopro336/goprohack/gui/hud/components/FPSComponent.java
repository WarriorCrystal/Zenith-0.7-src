//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class FPSComponent extends HudComponentItem
{
    public FPSComponent() {
        super("FPS", 2.0f, 140.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        final String l_FPS = String.format(ChatFormatting.GRAY + "FPS %s%s", ChatFormatting.WHITE, Minecraft.getDebugFPS());
        RenderUtil.drawStringWithShadow(l_FPS, this.GetX(), this.GetY(), -1);
        this.SetWidth(RenderUtil.getStringWidth(l_FPS));
        this.SetHeight(RenderUtil.getStringHeight(l_FPS) + 1.0f);
    }
}
