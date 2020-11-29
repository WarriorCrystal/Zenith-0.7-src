//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class PlayerCountComponent extends HudComponentItem
{
    public PlayerCountComponent() {
        super("PlayerCount", 2.0f, 185.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        final String playerCount = ChatFormatting.GRAY + "Players: " + ChatFormatting.WHITE + this.mc.player.connection.getPlayerInfoMap().size();
        RenderUtil.drawStringWithShadow(playerCount, this.GetX(), this.GetY(), -1);
        this.SetWidth(RenderUtil.getStringWidth(playerCount));
        this.SetHeight(RenderUtil.getStringHeight(playerCount) + 1.0f);
    }
}
