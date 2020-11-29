//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.chat;

import net.minecraft.util.TabCompleter;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiChat;

public class GoproGuiChat extends GuiChat
{
    public GoproGuiChat(final GuiChat oldChat) {
        this.defaultInputFieldText = oldChat.inputField.getText();
        this.setWorldAndResolution(oldChat.mc, oldChat.width, oldChat.height);
    }
    
    public GoproGuiChat(final String defaultText) {
        this.defaultInputFieldText = defaultText;
    }
    
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        (this.inputField = new GoproGuiTextField(0, this.fontRenderer, 4, this.height - 12, this.width - 4, 12)).setMaxStringLength(256);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
        this.tabCompleter = (TabCompleter)new GuiChat.ChatTabCompleter(this.inputField);
    }
}
