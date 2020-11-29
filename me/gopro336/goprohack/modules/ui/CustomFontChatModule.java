//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.ui;

import net.minecraft.client.gui.GuiNewChat;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.gui.GuiIngame;
import me.gopro336.goprohack.gui.chat.GoproGuiNewChat;
import me.gopro336.goprohack.modules.Module;

public class CustomFontChatModule extends Module
{
    private GoproGuiNewChat m_Chat;
    
    public CustomFontChatModule() {
        super("CustomChat", new String[] { "CustomChat" }, "better font rendering for chat", "NONE", -1, ModuleType.UI);
        this.m_Chat = null;
    }
    
    public void Activate() {
        if (this.mc.ingameGUI == null) {
            return;
        }
        if (this.m_Chat == null) {
            this.m_Chat = new GoproGuiNewChat(this.mc);
        }
        ObfuscationReflectionHelper.setPrivateValue((Class)GuiIngame.class, (Object)this.mc.ingameGUI, (Object)this.m_Chat, new String[] { "persistantChatGUI" });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.Activate();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mc.ingameGUI == null) {
            return;
        }
        ObfuscationReflectionHelper.setPrivateValue((Class)GuiIngame.class, (Object)this.mc.ingameGUI, (Object)new GuiNewChat(this.mc), new String[] { "persistantChatGUI" });
    }
}
