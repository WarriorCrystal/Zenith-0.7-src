//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.click.component.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import me.gopro336.goprohack.main.GoproHack;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.gui.click.component.listeners.ComponentItemListener;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Module;

public class ComponentItemKeybind extends ComponentItem
{
    public boolean Listening;
    final Module Mod;
    private String LastKey;
    private Timer timer;
    private String DisplayString;
    
    public ComponentItemKeybind(final Module p_Mod, final String p_DisplayText, final String p_Description, final int p_Flags, final int p_State, final ComponentItemListener p_Listener, final float p_Width, final float p_Height) {
        super(p_DisplayText, p_Description, p_Flags, p_State, p_Listener, p_Width, p_Height);
        this.Listening = false;
        this.LastKey = "";
        this.timer = new Timer();
        this.DisplayString = "";
        this.Mod = p_Mod;
        this.Flags |= ComponentItem.RectDisplayAlways;
    }
    
    @Override
    public String GetDisplayText() {
        if (this.Listening) {
            return "Depress a Key...";
        }
        String l_DisplayText = "Keybind " + this.Mod.getKey();
        if (this.HasState(ComponentItem.Hovered) && RenderUtil.getStringWidth(l_DisplayText) > this.GetWidth() - 3.0f) {
            if (this.DisplayString == null) {
                this.DisplayString = "Keybind " + this.Mod.getKey() + " ";
            }
            l_DisplayText = this.DisplayString;
            for (float l_Width = RenderUtil.getStringWidth(l_DisplayText); l_Width > this.GetWidth() - 3.0f; l_Width = RenderUtil.getStringWidth(l_DisplayText), l_DisplayText = l_DisplayText.substring(0, l_DisplayText.length() - 1)) {}
            if (this.timer.passed(75.0) && this.DisplayString.length() > 0) {
                final String l_FirstChar = String.valueOf(this.DisplayString.charAt(0));
                this.DisplayString = this.DisplayString.substring(1) + l_FirstChar;
                this.timer.reset();
            }
            return l_DisplayText;
        }
        this.DisplayString = null;
        for (float l_Width = RenderUtil.getStringWidth(l_DisplayText); l_Width > this.GetWidth() - 3.0f; l_Width = RenderUtil.getStringWidth(l_DisplayText), l_DisplayText = l_DisplayText.substring(0, l_DisplayText.length() - 1)) {}
        return l_DisplayText;
    }
    
    @Override
    public String GetDescription() {
        return "Sets the key of the Module: " + this.Mod.getDisplayName();
    }
    
    @Override
    public void OnMouseClick(final int p_MouseX, final int p_MouseY, final int p_MouseButton) {
        super.OnMouseClick(p_MouseX, p_MouseY, p_MouseButton);
        this.LastKey = "";
        if (p_MouseButton == 0) {
            this.Listening = !this.Listening;
        }
        else if (p_MouseButton == 1) {
            this.Listening = false;
        }
        else if (p_MouseButton == 2) {
            this.Mod.setKey("NONE");
            GoproHack.SendMessage("Unbinded the module: " + this.Mod.getDisplayName());
            this.Listening = false;
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this.Listening) {
            String l_Key = String.valueOf(Keyboard.getKeyName(keyCode)).toUpperCase();
            if (l_Key.length() < 1) {
                this.Listening = false;
                return;
            }
            if (l_Key.equals("END") || l_Key.equals("BACK") || l_Key.equals("DELETE")) {
                l_Key = "NONE";
            }
            if (!l_Key.equals("NONE") && !l_Key.contains("CONTROL") && !l_Key.contains("SHIFT") && !l_Key.contains("MENU")) {
                if (GuiScreen.isAltKeyDown()) {
                    l_Key = (Keyboard.isKeyDown(56) ? "LMENU" : "RMENU") + " + " + l_Key;
                }
                else if (GuiScreen.isCtrlKeyDown()) {
                    String l_CtrlKey = "";
                    if (Minecraft.IS_RUNNING_ON_MAC) {
                        l_CtrlKey = (Keyboard.isKeyDown(219) ? "LCONTROL" : "RCONTROL");
                    }
                    else {
                        l_CtrlKey = (Keyboard.isKeyDown(29) ? "LCONTROL" : "RCONTROL");
                    }
                    l_Key = l_CtrlKey + " + " + l_Key;
                }
                else if (GuiScreen.isShiftKeyDown()) {
                    l_Key = (Keyboard.isKeyDown(42) ? "LSHIFT" : "RSHIFT") + " + " + l_Key;
                }
            }
            this.LastKey = l_Key;
        }
    }
    
    @Override
    public void Update() {
        if (!Keyboard.getEventKeyState() && this.Listening && this.LastKey != "") {
            this.Mod.setKey(this.LastKey);
            GoproHack.SendMessage("Set the key of " + this.Mod.getDisplayName() + " to " + this.LastKey);
            this.Listening = false;
        }
    }
}
