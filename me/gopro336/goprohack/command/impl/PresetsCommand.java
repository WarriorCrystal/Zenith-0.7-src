// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command.impl;

import me.gopro336.goprohack.preset.Preset;
import me.gopro336.goprohack.managers.PresetsManager;
import me.gopro336.goprohack.command.Command;

public class PresetsCommand extends Command
{
    public PresetsCommand() {
        super("Presets", "Allows you to create custom presets");
        this.CommandChunks.add("create <name>");
        this.CommandChunks.add("delete <name>");
        this.CommandChunks.add("list");
    }
    
    @Override
    public void ProcessCommand(final String p_Args) {
        final String[] l_Split = p_Args.split(" ");
        if (l_Split == null || l_Split.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        if (l_Split[1].toLowerCase().startsWith("c")) {
            if (l_Split.length > 1) {
                final String presetName = l_Split[2].toLowerCase();
                if (!presetName.equalsIgnoreCase("Deault")) {
                    PresetsManager.Get().CreatePreset(presetName);
                    this.SendToChat("Created a preset named " + presetName);
                }
                else {
                    this.SendToChat("Default preset is reserved!");
                }
                return;
            }
            this.SendToChat("Usage: preset create <name>");
        }
        else {
            if (!l_Split[1].toLowerCase().startsWith("d")) {
                if (l_Split[1].toLowerCase().startsWith("l")) {
                    PresetsManager.Get().GetItems().forEach(p -> this.SendToChat(p.getName()));
                }
                return;
            }
            if (l_Split.length > 1) {
                final String presetName = l_Split[2].toLowerCase();
                if (!presetName.equalsIgnoreCase("Deault")) {
                    PresetsManager.Get().RemovePreset(presetName);
                    this.SendToChat("Removed a preset named " + presetName);
                }
                else {
                    this.SendToChat("Default preset is reserved!");
                }
                return;
            }
            this.SendToChat("Usage: preset remove <name>");
        }
    }
    
    @Override
    public String GetHelp() {
        return "Allows you to create, remove and list the presets";
    }
}
