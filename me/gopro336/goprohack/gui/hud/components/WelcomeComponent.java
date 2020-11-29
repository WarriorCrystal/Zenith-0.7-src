//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class WelcomeComponent extends HudComponentItem
{
    public final Value<Boolean> Message;
    public String WelcomeString;
    
    public WelcomeComponent() {
        super("Welcome", 100.0f, 1.0f);
        this.Message = new Value<Boolean>("Message", new String[] { "" }, "Shows a 1 line welcome message", false);
        this.WelcomeString = "Welcome to Zenith, " + ChatFormatting.GREEN + this.mc.getSession().getUsername() + ChatFormatting.WHITE + " looking hot today!";
        this.SetHidden(false);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        if (this.Message.getValue()) {
            RenderUtil.drawStringWithShadow(this.WelcomeString, this.GetX(), this.GetY(), 16777215);
            this.SetWidth(RenderUtil.getStringWidth(this.WelcomeString));
            this.SetHeight(RenderUtil.getStringHeight(this.WelcomeString));
        }
        else {
            RenderUtil.drawStringWithShadow("Welcome,", this.GetX() + 20.0f, this.GetY() + 1.0f, 16777215);
            RenderUtil.drawStringWithShadow(this.mc.getSession().getUsername(), this.GetX() + 20.0f, this.GetY() + 11.0f, 261185);
            this.SetWidth(45.0f);
            this.SetHeight(25.0f);
        }
    }
}
