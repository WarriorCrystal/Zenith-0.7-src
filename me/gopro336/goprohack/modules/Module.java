//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules;

import me.gopro336.goprohack.managers.PresetsManager;
import me.gopro336.goprohack.main.GoproHack;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiScreen;
import me.gopro336.goprohack.managers.CommandManager;
import java.util.Iterator;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.util.text.Style;
import java.text.DecimalFormat;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;
import me.gopro336.goprohack.events.goprohack.EventGoproHackModuleDisable;
import me.gopro336.goprohack.events.goprohack.EventGoproHackModuleEnable;
import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.GoproHackMod;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import me.zero.alpine.fork.listener.Listenable;

public class Module implements Listenable
{
    public String displayName;
    private String[] alias;
    private String desc;
    public String key;
    private int color;
    public boolean hidden;
    private boolean enabled;
    private ModuleType type;
    private boolean m_NeedsClickGuiValueUpdate;
    protected final Minecraft mc;
    public List<Value> valueList;
    public float RemainingXAnimation;
    
    private Module(final String displayName, final String[] alias, final String key, final int color, final ModuleType type) {
        this.hidden = false;
        this.enabled = false;
        this.mc = Minecraft.getMinecraft();
        this.valueList = new ArrayList<Value>();
        this.RemainingXAnimation = 0.0f;
        this.displayName = displayName;
        this.alias = alias;
        this.key = key;
        this.color = color;
        this.type = type;
    }
    
    public Module(final String displayName, final String[] alias, final String desc, final String key, final int color, final ModuleType type) {
        this(displayName, alias, key, color, type);
        this.desc = desc;
    }
    
    public void onEnable() {
        GoproHackMod.EVENT_BUS.subscribe(this);
        this.RemainingXAnimation = RenderUtil.getStringWidth(this.GetFullArrayListDisplayName()) + 10.0f;
        ModuleManager.Get().OnModEnable(this);
        GoproHackMod.EVENT_BUS.post(new EventGoproHackModuleEnable(this));
    }
    
    public void onDisable() {
        GoproHackMod.EVENT_BUS.unsubscribe(this);
        GoproHackMod.EVENT_BUS.post(new EventGoproHackModuleDisable(this));
    }
    
    public void onToggle() {
    }
    
    public void toggle() {
        this.setEnabled(!this.isEnabled());
        if (this.isEnabled()) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
        this.onToggle();
        this.SaveSettings();
    }
    
    public void toggleNoSave() {
        this.setEnabled(!this.isEnabled());
        if (this.isEnabled()) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
        this.onToggle();
    }
    
    public void ToggleOnlySuper() {
        this.setEnabled(!this.isEnabled());
        this.onToggle();
    }
    
    public String getMetaData() {
        return null;
    }
    
    public TextComponentString toUsageTextComponent() {
        if (this.valueList.size() <= 0) {
            return null;
        }
        final String valuePrefix = " " + ChatFormatting.RESET;
        final TextComponentString msg = new TextComponentString("");
        final DecimalFormat df = new DecimalFormat("#.##");
        for (final Value v : this.getValueList()) {
            if (v.getValue() instanceof Boolean) {
                msg.appendSibling(new TextComponentString(valuePrefix + v.getName() + ": " + (v.getValue() ? ChatFormatting.GREEN : ChatFormatting.RED) + v.getValue()).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(v.getName() + "\n" + ChatFormatting.GOLD + ((v.getDesc() == null || v.getDesc().equals("")) ? "There is no description for this boolean value." : v.getDesc()) + ChatFormatting.RESET + "\n " + ChatFormatting.GRAY + "<true / false>")))));
            }
            if (v.getValue() instanceof Number && !(v.getValue() instanceof Enum)) {
                msg.appendSibling(new TextComponentString(valuePrefix + v.getName() + ChatFormatting.GRAY + " <amount>" + ChatFormatting.RESET + ": " + ChatFormatting.AQUA + df.format(v.getValue())).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(v.getName() + "\n" + ChatFormatting.GOLD + ((v.getDesc() == null || v.getDesc().equals("")) ? "There is no description for this number value." : v.getDesc()) + ChatFormatting.RESET + "\n " + ChatFormatting.GRAY + "<" + v.getMin() + " - " + v.getMax() + ">")))));
            }
            if (v.getValue() instanceof String) {
                msg.appendSibling(new TextComponentString(valuePrefix + v.getName() + ChatFormatting.GRAY + " <text>" + ChatFormatting.RESET + ": " + v.getValue()).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(v.getName() + "\n" + ChatFormatting.GOLD + ((v.getDesc() == null || v.getDesc().equals("")) ? "There is no description for this string value." : v.getDesc()) + ChatFormatting.RESET + "\n " + ChatFormatting.GRAY + "<text>")))));
            }
            if (v.getValue() instanceof Enum) {
                final Enum val = v.getValue();
                final StringBuilder options = new StringBuilder();
                for (int size = ((Enum[])val.getClass().getEnumConstants()).length, i = 0; i < size; ++i) {
                    final Enum option = ((Enum[])val.getClass().getEnumConstants())[i];
                    options.append(option.name().toLowerCase() + ((i == size - 1) ? "" : ", "));
                }
                msg.appendSibling(new TextComponentString(valuePrefix + v.getName() + ChatFormatting.GRAY + " <" + options.toString() + ">" + ChatFormatting.RESET + ": " + ChatFormatting.YELLOW + val.name().toLowerCase()).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(v.getName() + "\n" + ChatFormatting.GOLD + ((v.getDesc() == null || v.getDesc().equals("")) ? "There is no description for this enum value." : v.getDesc()) + ChatFormatting.RESET + "\n " + ChatFormatting.GRAY + "<" + options.toString() + ">")))));
            }
        }
        return msg;
    }
    
    public Value find(final String alias) {
        for (final Value v : this.getValueList()) {
            for (final String s : v.getAlias()) {
                if (alias.equalsIgnoreCase(s)) {
                    return v;
                }
            }
            if (v.getName().equalsIgnoreCase(alias)) {
                return v;
            }
        }
        return null;
    }
    
    public void unload() {
        this.valueList.clear();
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
        CommandManager.Get().Reload();
        this.SaveSettings();
    }
    
    public String[] getAlias() {
        return this.alias;
    }
    
    public void setAlias(final String[] alias) {
        this.alias = alias;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(final String desc) {
        this.desc = desc;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public boolean IsKeyPressed(final String p_KeyCode) {
        if (GuiScreen.isAltKeyDown() || GuiScreen.isCtrlKeyDown() || GuiScreen.isShiftKeyDown()) {
            if (this.key.contains(" + ")) {
                if (GuiScreen.isAltKeyDown() && this.key.contains("MENU")) {
                    final String l_Result = this.key.replace(Keyboard.isKeyDown(56) ? "LMENU + " : "RMENU + ", "");
                    return l_Result.equals(p_KeyCode);
                }
                if (GuiScreen.isCtrlKeyDown() && this.key.contains("CONTROL")) {
                    String l_CtrlKey = "";
                    if (Minecraft.IS_RUNNING_ON_MAC) {
                        l_CtrlKey = (Keyboard.isKeyDown(219) ? "LCONTROL" : "RCONTROL");
                    }
                    else {
                        l_CtrlKey = (Keyboard.isKeyDown(29) ? "LCONTROL" : "RCONTROL");
                    }
                    final String l_Result2 = this.key.replace(l_CtrlKey + " + ", "");
                    return l_Result2.equals(p_KeyCode);
                }
                if (GuiScreen.isShiftKeyDown() && this.key.contains("SHIFT")) {
                    final String l_Result = this.key.replace((Keyboard.isKeyDown(42) ? "LSHIFT" : "RSHIFT") + " + ", "");
                    return l_Result.equals(p_KeyCode);
                }
            }
            if (!ModuleManager.Get().IgnoreStrictKeybinds()) {
                return (p_KeyCode.contains("SHIFT") || p_KeyCode.contains("CONTROL") || p_KeyCode.contains("MENU")) && this.key.equals(p_KeyCode);
            }
        }
        return this.key.equals(p_KeyCode);
    }
    
    public void setKey(final String key) {
        this.key = key;
        this.SaveSettings();
    }
    
    public int getColor() {
        return this.color;
    }
    
    public void setColor(final int color) {
        this.color = color;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
        this.SaveSettings();
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public ModuleType getType() {
        return this.type;
    }
    
    public void setType(final ModuleType type) {
        this.type = type;
    }
    
    public List<Value> getValueList() {
        return this.valueList;
    }
    
    public void setValueList(final List<Value> valueList) {
        this.valueList = valueList;
    }
    
    public float GetRemainingXArraylistOffset() {
        return this.RemainingXAnimation;
    }
    
    public void SignalEnumChange() {
    }
    
    public void SignalValueChange(final Value p_Val) {
        this.SaveSettings();
    }
    
    public List<Value> GetVisibleValueList() {
        return this.valueList;
    }
    
    public void SetClickGuiValueUpdate(final boolean p_Val) {
        this.m_NeedsClickGuiValueUpdate = p_Val;
    }
    
    public boolean NeedsClickGuiValueUpdate() {
        return this.m_NeedsClickGuiValueUpdate;
    }
    
    public String GetNextStringValue(final Value<String> p_Val, final boolean p_Recursive) {
        return null;
    }
    
    public String GetArrayListDisplayName() {
        return this.getDisplayName();
    }
    
    public String GetFullArrayListDisplayName() {
        return this.getDisplayName() + ((this.getMetaData() != null) ? (" " + ChatFormatting.GRAY + this.getMetaData()) : "");
    }
    
    protected void SendMessage(final String p_Message) {
        if (this.mc.player != null) {
            GoproHack.SendMessage(ChatFormatting.AQUA + "[" + this.GetArrayListDisplayName() + "]: " + ChatFormatting.RESET + p_Message);
        }
    }
    
    public void SaveSettings() {
        new Thread(() -> PresetsManager.Get().getActivePreset().addModuleSettings(this)).start();
    }
    
    public void init() {
    }
    
    public enum ModuleType
    {
        COMBAT, 
        EXPLOIT, 
        MOVEMENT, 
        PLAYER, 
        RENDER, 
        WORLD, 
        MISC, 
        HIDDEN, 
        UI, 
        BOT, 
        DUPE, 
        SCHEMATICA;
    }
}
