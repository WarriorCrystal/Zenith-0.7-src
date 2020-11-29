// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.gui.click.component.menus.mods.MenuComponentHUDList;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class SelectorMenuComponent extends HudComponentItem
{
    MenuComponentHUDList l_Component;
    
    public SelectorMenuComponent() {
        super("Selector", 300.0f, 100.0f);
        this.l_Component = new MenuComponentHUDList("Interface", 300.0f, 100.0f);
        this.SetHidden(false);
        this.AddFlag(HudComponentItem.OnlyVisibleInHudEditor);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        this.l_Component.Render(p_MouseX, p_MouseY, true, true, 0.0f);
        this.SetWidth(this.l_Component.GetWidth());
        this.SetHeight(this.l_Component.GetHeight());
        this.SetX(this.l_Component.GetX());
        this.SetY(this.l_Component.GetY());
    }
    
    @Override
    public boolean OnMouseClick(final int p_MouseX, final int p_MouseY, final int p_MouseButton) {
        return this.l_Component.MouseClicked(p_MouseX, p_MouseY, p_MouseButton, 0.0f);
    }
    
    @Override
    public void OnMouseRelease(final int p_MouseX, final int p_MouseY, final int p_State) {
        super.OnMouseRelease(p_MouseX, p_MouseY, p_State);
        this.l_Component.MouseReleased(p_MouseX, p_MouseY, p_State);
    }
}
