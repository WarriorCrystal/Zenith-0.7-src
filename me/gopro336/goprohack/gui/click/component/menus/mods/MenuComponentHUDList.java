// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.click.component.menus.mods;

import java.util.Iterator;
import me.gopro336.goprohack.gui.click.component.item.ComponentItemValue;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.gui.click.component.item.ComponentItemHUD;
import me.gopro336.goprohack.gui.click.component.item.ComponentItem;
import me.gopro336.goprohack.gui.click.component.listeners.ComponentItemListener;
import me.gopro336.goprohack.gui.hud.HudComponentItem;
import me.gopro336.goprohack.managers.HudManager;
import me.gopro336.goprohack.modules.ui.ClickGuiModule;
import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.modules.ui.ColorsModule;
import me.gopro336.goprohack.gui.click.component.MenuComponent;

public class MenuComponentHUDList extends MenuComponent
{
    public MenuComponentHUDList(final String p_DisplayName, final float p_X, final float p_Y) {
        super(p_DisplayName, p_X, p_Y, 100.0f, 90.0f, "", (ColorsModule)ModuleManager.Get().GetMod(ColorsModule.class), null);
        final float Width = 85.0f;
        final float Height = 13.0f;
        for (final HudComponentItem l_Item : HudManager.Get().Items) {
            ComponentItemListener l_Listener = new ComponentItemListener() {
                @Override
                public void OnEnabled() {
                }
                
                @Override
                public void OnToggled() {
                    l_Item.SetHidden(!l_Item.IsHidden());
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
            if (!l_Item.ValueList.isEmpty()) {
                l_Flags |= ComponentItem.HasValues;
            }
            int l_State = 0;
            if (!l_Item.IsHidden()) {
                l_State |= ComponentItem.Clicked;
            }
            final ComponentItem l_CItem = new ComponentItemHUD(l_Item, l_Item.GetDisplayName(), "", l_Flags, l_State, l_Listener, 85.0f, 13.0f);
            for (final Value l_Val : l_Item.ValueList) {
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
                final ComponentItemValue l_ValItem = new ComponentItemValue(l_Val, l_Val.getName(), l_Val.getDesc(), ComponentItem.Clickable | ComponentItem.Hoverable | ComponentItem.Tooltip, 0, l_Listener, 85.0f, 13.0f);
                l_CItem.DropdownItems.add(l_ValItem);
            }
            l_Listener = new ComponentItemListener() {
                @Override
                public void OnEnabled() {
                }
                
                @Override
                public void OnToggled() {
                    l_Item.ResetToDefaultPos();
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
            final ComponentItem l_ResetButton = new ComponentItem("Reset", "Resets the position of " + l_Item.GetDisplayName() + " to default.", ComponentItem.Clickable | ComponentItem.Hoverable | ComponentItem.Tooltip | ComponentItem.Enum | ComponentItem.DontDisplayClickableHighlight | ComponentItem.RectDisplayAlways, 0, l_Listener, 85.0f, 13.0f);
            l_CItem.DropdownItems.add(l_ResetButton);
            this.AddItem(l_CItem);
        }
    }
}
