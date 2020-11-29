// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.click.component.menus.mods;

import java.util.Iterator;
import me.gopro336.goprohack.gui.click.component.item.ComponentItemKeybind;
import me.gopro336.goprohack.gui.click.component.item.ComponentItemHiddenMod;
import me.gopro336.goprohack.gui.click.component.item.ComponentItemValue;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.gui.click.component.item.ComponentItemMod;
import me.gopro336.goprohack.gui.click.component.item.ComponentItem;
import me.gopro336.goprohack.gui.click.component.listeners.ComponentItemListener;
import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.modules.ui.ClickGuiModule;
import me.gopro336.goprohack.modules.ui.ColorsModule;
import me.gopro336.goprohack.modules.Module;
import me.gopro336.goprohack.gui.click.component.MenuComponent;

public class MenuComponentModList extends MenuComponent
{
    public MenuComponentModList(final String p_DisplayName, final Module.ModuleType p_Type, final float p_X, final float p_Y, final String p_Image, final ColorsModule p_Colors, final ClickGuiModule p_Click) {
        super(p_DisplayName, p_X, p_Y, 100.0f, 100.0f, p_Image, p_Colors, p_Click);
        final float Width = 100.0f;
        final float Height = 11.0f;
        for (final Module l_Mod : ModuleManager.Get().GetModuleList(p_Type)) {
            ComponentItemListener l_Listener = new ComponentItemListener() {
                @Override
                public void OnEnabled() {
                }
                
                @Override
                public void OnToggled() {
                    l_Mod.toggle();
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
            int l_Flags = ComponentItem.Clickable | ComponentItem.Hoverable | ComponentItem.Tooltip;
            if (!l_Mod.getValueList().isEmpty()) {
                l_Flags |= ComponentItem.HasValues;
            }
            int l_State = 0;
            if (l_Mod.isEnabled()) {
                l_State |= ComponentItem.Clicked;
            }
            final ComponentItem l_Item = new ComponentItemMod(l_Mod, l_Mod.getDisplayName(), l_Mod.getDesc(), l_Flags, l_State, l_Listener, 100.0f, 11.0f);
            for (final Value l_Val : l_Mod.getValueList()) {
                l_Listener = new ComponentItemListener() {
                    @Override
                    public void OnEnabled() {
                    }
                    
                    @Override
                    public void OnToggled() {
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
                final ComponentItemValue l_ValItem = new ComponentItemValue(l_Val, l_Val.getName(), l_Val.getDesc(), ComponentItem.Clickable | ComponentItem.Hoverable | ComponentItem.Tooltip, 0, l_Listener, 100.0f, 11.0f);
                l_Item.DropdownItems.add(l_ValItem);
            }
            l_Listener = new ComponentItemListener() {
                @Override
                public void OnEnabled() {
                }
                
                @Override
                public void OnToggled() {
                    l_Mod.setHidden(!l_Mod.isHidden());
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
            final ComponentItem l_HideButton = new ComponentItemHiddenMod(l_Mod, "Hidden", "Hides " + l_Mod.getDisplayName() + " from the arraylist", ComponentItem.Clickable | ComponentItem.Hoverable | ComponentItem.Tooltip | ComponentItem.RectDisplayOnClicked | ComponentItem.DontDisplayClickableHighlight, 0, l_Listener, 100.0f, 11.0f);
            l_Item.DropdownItems.add(l_HideButton);
            l_Item.DropdownItems.add(new ComponentItemKeybind(l_Mod, "Keybind:" + l_Mod.getDisplayName(), l_Mod.getDesc(), ComponentItem.Clickable | ComponentItem.Hoverable | ComponentItem.Tooltip, 0, null, 100.0f, 11.0f));
            this.AddItem(l_Item);
        }
    }
}
