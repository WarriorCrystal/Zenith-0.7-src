//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import net.minecraft.client.network.NetworkPlayerInfo;
import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class PingComponent extends HudComponentItem
{
    public PingComponent() {
        super("Ping", 2.0f, 230.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        if (this.mc.world == null || this.mc.player == null || this.mc.player.getUniqueID() == null) {
            return;
        }
        final NetworkPlayerInfo playerInfo = this.mc.getConnection().getPlayerInfo(this.mc.player.getUniqueID());
        if (playerInfo == null) {
            return;
        }
        final String ping = ChatFormatting.GRAY + "Ping " + ChatFormatting.WHITE + playerInfo.getResponseTime() + "ms";
        this.SetWidth(RenderUtil.getStringWidth(ping));
        this.SetHeight(RenderUtil.getStringHeight(ping) + 1.0f);
        RenderUtil.drawStringWithShadow(ping, this.GetX(), this.GetY(), -1);
    }
}
