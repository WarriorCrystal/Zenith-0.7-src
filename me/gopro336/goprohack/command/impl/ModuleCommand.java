// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command.impl;

import java.util.Iterator;
import me.gopro336.goprohack.modules.Value;
import java.util.List;
import me.gopro336.goprohack.command.util.ModuleCommandListener;
import me.gopro336.goprohack.command.Command;

public class ModuleCommand extends Command
{
    private ModuleCommandListener Listener;
    private final List<Value> Values;
    
    public ModuleCommand(final String p_Name, final String p_Description, final ModuleCommandListener p_Listener, final List<Value> p_Values) {
        super(p_Name, p_Description);
        this.Listener = p_Listener;
        this.Values = p_Values;
        this.CommandChunks.add("hide");
        this.CommandChunks.add("toggle");
        this.CommandChunks.add("rename <newname>");
        for (final Value l_Val : this.Values) {
            this.CommandChunks.add(String.format("%s <%s>", l_Val.getName(), "value"));
        }
    }
    
    @Override
    public void ProcessCommand(final String p_Args) {
        final String[] l_Split = p_Args.split(" ");
        if (l_Split == null || l_Split.length <= 1) {
            for (final Value l_Val : this.Values) {
                this.SendToChat(String.format("%s : %s", l_Val.getName(), l_Val.getValue()));
            }
            return;
        }
        if (l_Split[1].equalsIgnoreCase("hide")) {
            this.Listener.OnHide();
            return;
        }
        if (l_Split[1].equalsIgnoreCase("toggle")) {
            this.Listener.OnHide();
            return;
        }
        if (l_Split[1].equalsIgnoreCase("rename")) {
            if (l_Split.length <= 3) {
                this.Listener.OnRename(l_Split[2]);
            }
            return;
        }
        for (final Value l_Val : this.Values) {
            if (l_Val.getName().toLowerCase().startsWith(l_Split[1].toLowerCase())) {
                if (l_Split.length <= 2) {
                    break;
                }
                final String l_Value = l_Split[2].toLowerCase();
                if (l_Val.getValue() instanceof Number && !(l_Val.getValue() instanceof Enum)) {
                    if (l_Val.getValue() instanceof Integer) {
                        l_Val.SetForcedValue(Integer.parseInt(l_Value));
                    }
                    else if (l_Val.getValue() instanceof Float) {
                        l_Val.SetForcedValue(Float.parseFloat(l_Value));
                    }
                    else if (l_Val.getValue() instanceof Double) {
                        l_Val.SetForcedValue(Double.parseDouble(l_Value));
                    }
                }
                else if (l_Val.getValue() instanceof Boolean) {
                    l_Val.SetForcedValue(l_Value.equalsIgnoreCase("true"));
                }
                else if (l_Val.getValue() instanceof Enum) {
                    l_Val.SetForcedValue(l_Val.GetEnumReal(l_Value));
                }
                else if (l_Val.getValue() instanceof String) {
                    l_Val.SetForcedValue(l_Value);
                }
                this.SendToChat(String.format("Set the value of %s to %s", l_Val.getName(), l_Val.getValue()));
                break;
            }
        }
    }
    
    @Override
    public String GetHelp() {
        return this.GetDescription();
    }
}
