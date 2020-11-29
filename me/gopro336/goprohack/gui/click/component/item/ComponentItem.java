// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.click.component.item;

import java.util.ArrayList;
import me.gopro336.goprohack.gui.click.component.listeners.ComponentItemListener;

public class ComponentItem
{
    public static int Clickable;
    public static int Hoverable;
    public static int Tooltip;
    public static int HasValues;
    public static int RectDisplayAlways;
    public static int Slider;
    public static int Boolean;
    public static int Enum;
    public static int DontDisplayClickableHighlight;
    public static int RectDisplayOnClicked;
    public static int Clicked;
    public static int Hovered;
    public static int Extended;
    private String DisplayText;
    private String Description;
    protected int Flags;
    protected int State;
    protected ComponentItemListener Listener;
    private float X;
    private float Y;
    private float Width;
    private float Height;
    protected float CurrentWidth;
    public ArrayList<ComponentItem> DropdownItems;
    
    public ComponentItem(final String p_DisplayText, final String p_Description, final int p_Flags, final int p_State, final ComponentItemListener p_Listener, final float p_Width, final float p_Height) {
        this.DisplayText = p_DisplayText;
        this.Description = p_Description;
        this.Flags = p_Flags;
        this.State = p_State;
        this.Listener = p_Listener;
        this.DropdownItems = new ArrayList<ComponentItem>();
        this.X = 0.0f;
        this.Y = 0.0f;
        this.Width = p_Width;
        this.Height = p_Height;
        this.CurrentWidth = p_Width;
    }
    
    public String GetDisplayText() {
        return this.DisplayText;
    }
    
    public String GetDescription() {
        return this.Description;
    }
    
    public boolean HasFlag(final int p_Flag) {
        return (this.Flags & p_Flag) != 0x0;
    }
    
    public boolean HasState(final int p_State) {
        return (this.State & p_State) != 0x0;
    }
    
    public void AddState(final int p_State) {
        this.State |= p_State;
    }
    
    public void RemoveState(final int p_State) {
        this.State &= ~p_State;
    }
    
    public float GetX() {
        return this.X;
    }
    
    public void SetX(final float x) {
        this.X = x;
    }
    
    public float GetY() {
        return this.Y;
    }
    
    public void SetY(final float y) {
        this.Y = y;
    }
    
    public float GetWidth() {
        return this.Width;
    }
    
    public void SetWidth(final float width) {
        this.Width = width;
    }
    
    public float GetHeight() {
        return this.Height;
    }
    
    public void SetHeight(final float height) {
        this.Height = height;
    }
    
    public float GetCurrentWidth() {
        return this.CurrentWidth;
    }
    
    public void OnMouseClick(final int p_MouseX, final int p_MouseY, final int p_MouseButton) {
        if (p_MouseButton == 0) {
            if (this.Listener != null) {
                this.Listener.OnToggled();
            }
            if (this.HasState(ComponentItem.Clicked)) {
                this.RemoveState(ComponentItem.Clicked);
            }
            else {
                this.AddState(ComponentItem.Clicked);
            }
        }
        else if (p_MouseButton == 1) {
            if (this.HasState(ComponentItem.Extended)) {
                this.RemoveState(ComponentItem.Extended);
            }
            else {
                this.AddState(ComponentItem.Extended);
            }
        }
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    public void OnMouseMove(final float p_MouseX, final float p_MouseY, final float p_X, final float p_Y) {
    }
    
    public void Update() {
    }
    
    public void OnMouseRelease(final int p_MouseX, final int p_MouseY) {
    }
    
    public void OnMouseClickMove(final int p_MouseX, final int p_MouseY, final int p_ClickedMouseButton) {
    }
    
    static {
        ComponentItem.Clickable = 1;
        ComponentItem.Hoverable = 2;
        ComponentItem.Tooltip = 4;
        ComponentItem.HasValues = 8;
        ComponentItem.RectDisplayAlways = 16;
        ComponentItem.Slider = 32;
        ComponentItem.Boolean = 64;
        ComponentItem.Enum = 128;
        ComponentItem.DontDisplayClickableHighlight = 256;
        ComponentItem.RectDisplayOnClicked = 512;
        ComponentItem.Clicked = 1;
        ComponentItem.Hovered = 2;
        ComponentItem.Extended = 4;
    }
}
