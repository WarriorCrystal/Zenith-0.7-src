// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.managers;

import me.gopro336.goprohack.modules.Module;
import me.gopro336.goprohack.main.GoproHack;
import org.apache.commons.io.FileUtils;
import java.util.Iterator;
import java.io.IOException;
import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;
import me.gopro336.goprohack.gui.click.component.menus.mods.MenuComponentPresetsList;
import me.gopro336.goprohack.preset.Preset;
import java.util.List;

public class PresetsManager
{
    private List<Preset> _presets;
    private MenuComponentPresetsList _presetList;
    
    public PresetsManager() {
        this._presets = new CopyOnWriteArrayList<Preset>();
    }
    
    public void LoadPresets() {
        try {
            final File[] listFiles;
            final File[] directories = listFiles = new File(DirectoryManager.Get().GetCurrentDirectory() + "/GoproHack/Presets/").listFiles(File::isDirectory);
            for (final File file : listFiles) {
                System.out.println("" + file.getName().toString());
                final Preset preset = new Preset(file.getName().toString());
                preset.load(file);
                this._presets.add(preset);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Preset defaultPreset = null;
        boolean alreadyEnabled = false;
        for (final Preset p : this._presets) {
            if (p.getName().equalsIgnoreCase("default")) {
                defaultPreset = p;
            }
            else {
                if (p.isActive()) {
                    alreadyEnabled = true;
                    break;
                }
                continue;
            }
        }
        if (!alreadyEnabled && defaultPreset != null) {
            defaultPreset.setActive(true);
        }
    }
    
    public void CreatePreset(final String presetName) {
        try {
            new File(DirectoryManager.Get().GetCurrentDirectory() + "/GoproHack/Presets/" + presetName).mkdirs();
            new File(DirectoryManager.Get().GetCurrentDirectory() + "/GoproHack/Presets/" + presetName + "/Modules").mkdirs();
            final Preset preset = new Preset(presetName);
            this._presets.add(preset);
            preset.initNewPreset();
            preset.save();
            this.SetPresetActive(preset);
            if (this._presetList != null) {
                this._presetList.AddPreset(preset);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void RemovePreset(final String presetName) {
        Preset toRemove = null;
        for (final Preset p : this._presets) {
            if (p.getName().equalsIgnoreCase(presetName)) {
                toRemove = p;
                break;
            }
        }
        if (toRemove != null) {
            try {
                FileUtils.deleteDirectory(new File(DirectoryManager.Get().GetCurrentDirectory() + "/GoproHack/Presets/" + toRemove.getName()));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            this._presets.remove(toRemove);
            if (this._presetList != null) {
                this._presetList.RemovePreset(toRemove);
            }
        }
    }
    
    public Preset getActivePreset() {
        for (final Preset p : this._presets) {
            if (p.isActive()) {
                return p;
            }
        }
        return this._presets.get(0);
    }
    
    public void SetPresetActive(final Preset preset) {
        for (final Preset p : this._presets) {
            p.setActive(false);
        }
        preset.setActive(true);
        ModuleManager.Get().Mods.forEach(mod -> preset.initValuesForMod(mod));
    }
    
    public final List<Preset> GetItems() {
        return this._presets;
    }
    
    public static PresetsManager Get() {
        return GoproHack.GetPresetsManager();
    }
    
    public void InitalizeGUIComponent(final MenuComponentPresetsList presetList) {
        this._presetList = presetList;
    }
}
