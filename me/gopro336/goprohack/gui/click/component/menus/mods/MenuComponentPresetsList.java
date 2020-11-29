// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.click.component.menus.mods;

import java.util.Iterator;
import me.gopro336.goprohack.gui.click.component.item.ComponentPresetItem;
import me.gopro336.goprohack.gui.click.component.item.ComponentItem;
import me.gopro336.goprohack.gui.click.component.listeners.ComponentItemListener;
import me.gopro336.goprohack.preset.Preset;
import me.gopro336.goprohack.managers.PresetsManager;
import me.gopro336.goprohack.modules.ui.ClickGuiModule;
import me.gopro336.goprohack.modules.ui.ColorsModule;
import me.gopro336.goprohack.modules.Module;
import me.gopro336.goprohack.gui.click.component.MenuComponent;

public class MenuComponentPresetsList extends MenuComponent
{
    private final float Width = 90.0f;
    private final float Height = 13.0f;
    
    public MenuComponentPresetsList(final String p_DisplayName, final Module.ModuleType p_Type, final float p_X, final float p_Y, final String p_Image, final ColorsModule p_Colors, final ClickGuiModule p_Click) {
        super(p_DisplayName, p_X, p_Y, 100.0f, 90.0f, p_Image, p_Colors, p_Click);
        PresetsManager.Get().GetItems().forEach(preset -> this.AddPreset(preset));
    }
    
    public void AddPreset(final Preset preset) {
        final ComponentItemListener listener = new ComponentItemListener() {
            @Override
            public void OnEnabled() {
            }
            
            @Override
            public void OnToggled() {
                PresetsManager.Get().SetPresetActive(preset);
            }
            
            @Override
            public void OnDisabled() {
            }
            
            @Override
            public void OnHover() {
            }
            
            @Override
            public void OnMouseEnter() {
            }
            
            @Override
            public void OnMouseLeave() {
            }
        };
        final int flags = ComponentItem.Clickable | ComponentItem.Hoverable | ComponentItem.Tooltip;
        int state = 0;
        if (preset.isActive()) {
            state |= ComponentItem.Clicked;
        }
        final ComponentItem item = new ComponentPresetItem(preset, flags, state, listener, 90.0f, 13.0f);
        this.AddItem(item);
    }
    
    public void RemovePreset(final Preset toRemove) {
        ComponentItem removeItem = null;
        for (final ComponentItem item : this.Items) {
            if (item instanceof ComponentPresetItem) {
                final ComponentPresetItem comp = (ComponentPresetItem)item;
                if (comp.getPreset() == toRemove) {
                    removeItem = comp;
                    break;
                }
                continue;
            }
        }
        if (removeItem != null) {
            this.Items.remove(removeItem);
        }
    }
}
