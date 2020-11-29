//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.click.component;

import java.util.Iterator;
import me.gopro336.goprohack.main.Wrapper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import me.gopro336.goprohack.managers.FontManager;
import me.gopro336.goprohack.util.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import me.gopro336.goprohack.managers.ImageManager;
import me.gopro336.goprohack.modules.ui.ClickGuiModule;
import me.gopro336.goprohack.modules.ui.ColorsModule;
import me.gopro336.goprohack.util.imgs.GoproDynamicTexture;
import me.gopro336.goprohack.gui.click.component.item.ComponentItem;
import java.util.ArrayList;

public class MenuComponent
{
    private String DisplayName;
    protected ArrayList<ComponentItem> Items;
    private float DefaultX;
    private float DefaultY;
    private float X;
    private float Y;
    private float Height;
    private float Width;
    private boolean Dragging;
    private float DeltaX;
    private float DeltaY;
    private ComponentItem HoveredItem;
    private boolean Minimized;
    private boolean IsMinimizing;
    private float RemainingMinimizingY;
    private boolean IsMaximizing;
    private float RemainingMaximizingY;
    private int MousePlayAnim;
    private GoproDynamicTexture BarTexture;
    private ColorsModule Colors;
    private ClickGuiModule ClickGUI;
    final float BorderLength = 15.0f;
    final float Padding = 3.0f;
    
    public MenuComponent(final String p_DisplayName, final float p_X, final float p_Y, final float p_Height, final float p_Width, final String p_Image, final ColorsModule p_Colors, final ClickGuiModule p_ClickGui) {
        this.Items = new ArrayList<ComponentItem>();
        this.Dragging = false;
        this.DeltaX = 0.0f;
        this.DeltaY = 0.0f;
        this.HoveredItem = null;
        this.Minimized = false;
        this.IsMinimizing = false;
        this.IsMaximizing = false;
        this.BarTexture = null;
        this.DisplayName = p_DisplayName;
        this.DefaultX = p_X;
        this.DefaultY = p_Y;
        this.X = p_X;
        this.Y = p_Y;
        this.Height = p_Height;
        this.Width = p_Width;
        this.RemainingMinimizingY = 0.0f;
        this.RemainingMaximizingY = 0.0f;
        this.MousePlayAnim = 0;
        if (p_Image != null) {
            this.BarTexture = ImageManager.Get().GetDynamicTexture(p_Image);
        }
        this.Colors = p_Colors;
        this.ClickGUI = p_ClickGui;
    }
    
    public void AddItem(final ComponentItem p_Item) {
        this.Items.add(p_Item);
    }
    
    public boolean Render(final int p_MouseX, final int p_MouseY, final boolean p_CanHover, final boolean p_AllowsOverflow, final float p_OffsetY) {
        if (this.Dragging) {
            this.X = p_MouseX - this.DeltaX;
            this.Y = p_MouseY - this.DeltaY;
        }
        if (!p_AllowsOverflow) {
            final ScaledResolution l_Res = new ScaledResolution(Minecraft.getMinecraft());
            if (this.X + this.GetWidth() >= l_Res.getScaledWidth()) {
                this.X = l_Res.getScaledWidth() - this.GetWidth();
            }
            else if (this.X < 0.0f) {
                this.X = 0.0f;
            }
            if (this.Y + this.GetHeight() >= l_Res.getScaledHeight()) {
                this.Y = l_Res.getScaledHeight() - this.GetHeight();
            }
            else if (this.Y < 0.0f) {
                this.Y = 0.0f;
            }
        }
        for (final ComponentItem l_Item : this.Items) {
            l_Item.OnMouseMove((float)p_MouseX, (float)p_MouseY, this.GetX(), this.GetY() - p_OffsetY);
        }
        if (this.IsMinimizing) {
            if (this.RemainingMinimizingY > 0.0f) {
                this.RemainingMinimizingY -= 20.0f;
                this.RemainingMinimizingY = Math.max(this.RemainingMinimizingY, 0.0f);
                if (this.RemainingMinimizingY == 0.0f) {
                    this.Minimized = true;
                    this.IsMinimizing = false;
                    this.Height = 0.0f;
                }
            }
        }
        else if (this.IsMaximizing && this.RemainingMaximizingY < 500.0f) {
            this.RemainingMaximizingY += 20.0f;
            this.RemainingMaximizingY = Math.min(this.RemainingMaximizingY, 500.0f);
            if (this.RemainingMaximizingY == 500.0f) {
                this.IsMaximizing = false;
                this.Height = 0.0f;
            }
        }
        RenderUtil.drawGradientRect(this.GetX(), this.GetY() + 12.0f - p_OffsetY, this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), 1694498816, 1694498816);
        RenderUtil.drawLine(this.GetX() + 1.0f, this.GetY() - p_OffsetY, this.GetX(), this.GetY() + this.GetHeight(), 2.0f, -4046525);
        RenderUtil.drawLine(this.GetX() + this.GetWidth() - 1.0f, this.GetY() - p_OffsetY, this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), 2.0f, -4046525);
        RenderUtil.drawLine(this.GetX(), this.GetY() + this.GetHeight(), this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), 2.25f, -4046525);
        RenderUtil.drawRect(this.GetX(), this.GetY() - p_OffsetY, this.GetX() + this.GetWidth(), this.GetY() + 14.0f - p_OffsetY, -4046525);
        FontManager.Get().TwCenMtStd28.drawStringWithShadow(this.GetDisplayName(), this.GetX() + 16.0f, this.GetY() + 1.0f - p_OffsetY, 16777215);
        if (this.BarTexture != null) {
            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            final float l_X = this.GetX() + this.GetWidth() - 15.0f;
            Wrapper.GetMC().renderEngine.bindTexture(this.BarTexture.GetResourceLocation());
            GlStateManager.glTexParameteri(3553, 10240, 9729);
            GlStateManager.glTexParameteri(3553, 10241, 9729);
            GlStateManager.color((float)this.Colors.ImageRed.getValue(), (float)this.Colors.ImageGreen.getValue(), (float)this.Colors.ImageBlue.getValue(), (float)this.Colors.ImageAlpha.getValue());
            GlStateManager.enableTexture2D();
            RenderUtil.drawTexture(l_X, this.GetY() + 3.0f - p_OffsetY, (float)(this.BarTexture.GetWidth() / 3), (float)(this.BarTexture.GetHeight() / 3), 0.0f, 0.0f, 1.0f, 1.0f);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
        if (!this.Minimized) {
            float l_Y = this.GetY() + 5.0f - p_OffsetY;
            this.HoveredItem = null;
            boolean l_Break = false;
            for (final ComponentItem l_Item2 : this.Items) {
                l_Y = this.DisplayComponentItem(l_Item2, l_Y, p_MouseX, p_MouseY, p_CanHover, false, this.IsMinimizing ? this.RemainingMinimizingY : (this.IsMaximizing ? this.RemainingMaximizingY : 0.0f));
                final float l_MenuY = Math.abs(this.Y - l_Y - 15.0f);
                if (this.IsMinimizing && l_MenuY >= this.RemainingMinimizingY) {
                    l_Break = true;
                }
                else if (this.IsMaximizing && l_MenuY >= this.RemainingMaximizingY) {
                    l_Break = true;
                }
                if (l_Break) {
                    break;
                }
            }
            if (!l_Break) {
                this.IsMinimizing = false;
                this.IsMaximizing = false;
            }
            if (this.HoveredItem != null && (this.ClickGUI == null || this.ClickGUI.HoverDescriptions.getValue()) && this.HoveredItem.GetDescription() != null && this.HoveredItem.GetDescription() != "") {
                RenderUtil.drawRect((float)(p_MouseX + 15), (float)p_MouseY, p_MouseX + 19 + RenderUtil.getStringWidth(this.HoveredItem.GetDescription()), p_MouseY + RenderUtil.getStringHeight(this.HoveredItem.GetDescription()) + 3.0f, -1879048192);
                RenderUtil.drawStringWithShadow(this.HoveredItem.GetDescription(), (float)(p_MouseX + 17), (float)p_MouseY, 16777215);
            }
            this.Height = Math.abs(this.Y - l_Y - 12.0f);
        }
        if (this.MousePlayAnim > 0) {
            --this.MousePlayAnim;
            RenderUtil.DrawPolygon(p_MouseX, p_MouseY, this.MousePlayAnim, 360, -1711276033);
        }
        return p_CanHover && p_MouseX > this.GetX() && p_MouseX < this.GetX() + this.GetWidth() && p_MouseY > this.GetY() - p_OffsetY && p_MouseY < this.GetY() + this.GetHeight() - p_OffsetY;
    }
    
    public float DisplayComponentItem(final ComponentItem p_Item, float p_Y, final int p_MouseX, final int p_MouseY, final boolean p_CanHover, final boolean p_DisplayExtendedLine, final float p_MaxY) {
        p_Y += p_Item.GetHeight();
        p_Item.OnMouseMove((float)p_MouseX, (float)p_MouseY, this.GetX(), this.GetY());
        p_Item.Update();
        if (p_Item.HasState(ComponentItem.Extended)) {
            RenderUtil.drawRect(this.X + 1.0f, p_Y, this.X + p_Item.GetWidth() - 3.0f, p_Y + RenderUtil.getStringHeight(p_Item.GetDisplayText()) + 3.0f, 526344);
        }
        int l_Color = 16777215;
        final boolean l_Hovered = p_CanHover && p_MouseX > this.X && p_MouseX < this.X + p_Item.GetWidth() && p_MouseY > p_Y && p_MouseY < p_Y + p_Item.GetHeight();
        final boolean l_DropDown = p_Item.HasState(ComponentItem.Extended);
        if (l_Hovered) {
            if (!l_DropDown) {
                RenderUtil.drawGradientRect(this.GetX(), p_Y, this.GetX() + p_Item.GetWidth(), p_Y + 11.0f, -1727790076, -1728053248);
            }
            l_Color = ((p_Item.HasState(ComponentItem.Clicked) && !p_Item.HasFlag(ComponentItem.DontDisplayClickableHighlight)) ? this.GetTextColor() : l_Color);
            (this.HoveredItem = p_Item).AddState(ComponentItem.Hovered);
        }
        else {
            if (p_Item.HasState(ComponentItem.Clicked) && !p_Item.HasFlag(ComponentItem.DontDisplayClickableHighlight)) {
                l_Color = this.GetTextColor();
            }
            p_Item.RemoveState(ComponentItem.Hovered);
        }
        if (l_DropDown) {
            RenderUtil.drawGradientRect(this.GetX(), p_Y, this.GetX() + p_Item.GetWidth(), p_Y + 11.0f, -1727790076, -1728053248);
        }
        if (p_Item.HasFlag(ComponentItem.RectDisplayAlways) || (p_Item.HasFlag(ComponentItem.RectDisplayOnClicked) && p_Item.HasState(ComponentItem.Clicked))) {
            RenderUtil.drawRect(this.GetX(), p_Y, this.GetX() + p_Item.GetCurrentWidth(), p_Y + 11.0f, (p_Item.HasState(ComponentItem.Clicked) || p_Item.HasFlag(ComponentItem.DontDisplayClickableHighlight)) ? this.GetColor() : this.GetColor());
        }
        RenderUtil.drawStringWithShadow(p_Item.GetDisplayText(), this.X + 3.0f, p_Y, l_Color);
        if (p_Item.HasState(ComponentItem.Extended) || p_DisplayExtendedLine) {
            RenderUtil.drawLine(this.X + p_Item.GetWidth() - 1.0f, p_Y, this.X + p_Item.GetWidth() - 1.0f, p_Y + 11.0f, 3.0f, this.GetColor());
        }
        if (p_Item.HasState(ComponentItem.Extended)) {
            for (final ComponentItem l_ValItem : p_Item.DropdownItems) {
                p_Y = this.DisplayComponentItem(l_ValItem, p_Y, p_MouseX, p_MouseY, p_CanHover, true, p_MaxY);
                if (p_MaxY > 0.0f) {
                    final float l_MenuY = Math.abs(this.Y - p_Y - 15.0f);
                    if (l_MenuY >= p_MaxY) {
                        break;
                    }
                    continue;
                }
            }
        }
        return p_Y;
    }
    
    public boolean MouseClicked(final int p_MouseX, final int p_MouseY, final int p_MouseButton, final float offsetY) {
        if (p_MouseX > this.GetX() && p_MouseX < this.GetX() + this.GetWidth() && p_MouseY > this.GetY() - offsetY && p_MouseY < this.GetY() + 15.0f - offsetY) {
            if (p_MouseButton == 0) {
                this.Dragging = true;
                this.DeltaX = p_MouseX - this.X;
                this.DeltaY = p_MouseY - this.Y;
            }
            else if (p_MouseButton == 1) {
                if (!this.Minimized) {
                    this.IsMinimizing = true;
                    this.RemainingMinimizingY = this.Height;
                    this.IsMaximizing = false;
                    this.RemainingMaximizingY = 0.0f;
                }
                else {
                    this.Minimized = false;
                    this.IsMinimizing = false;
                    this.RemainingMinimizingY = 0.0f;
                    this.IsMaximizing = true;
                    this.RemainingMaximizingY = 0.0f;
                }
            }
        }
        if (this.HoveredItem != null) {
            this.HoveredItem.OnMouseClick(p_MouseX, p_MouseY, p_MouseButton);
            if (p_MouseButton == 0) {
                this.MousePlayAnim = 20;
            }
            return true;
        }
        return this.Dragging;
    }
    
    public void MouseReleased(final int p_MouseX, final int p_MouseY, final int p_State) {
        if (this.Dragging) {
            this.Dragging = false;
        }
        for (final ComponentItem l_Item : this.Items) {
            this.HandleMouseReleaseCompItem(l_Item, p_MouseX, p_MouseY);
        }
    }
    
    public void HandleMouseReleaseCompItem(final ComponentItem p_Item, final int p_MouseX, final int p_MouseY) {
        p_Item.OnMouseRelease(p_MouseX, p_MouseY);
        for (final ComponentItem l_Item : p_Item.DropdownItems) {
            l_Item.OnMouseRelease(p_MouseX, p_MouseY);
        }
    }
    
    public void MouseClickMove(final int p_MouseX, final int p_MouseY, final int p_ClickedMouseButton, final long p_TimeSinceLastClick) {
        for (final ComponentItem l_Item : this.Items) {
            this.HandleMouseClickMoveCompItem(l_Item, p_MouseX, p_MouseY, p_ClickedMouseButton);
        }
    }
    
    private void HandleMouseClickMoveCompItem(final ComponentItem l_Item, final int p_MouseX, final int p_MouseY, final int p_ClickedMouseButton) {
        l_Item.OnMouseClickMove(p_MouseX, p_MouseY, p_ClickedMouseButton);
        for (final ComponentItem l_Item2 : l_Item.DropdownItems) {
            l_Item2.OnMouseClickMove(p_MouseX, p_MouseY, p_ClickedMouseButton);
        }
    }
    
    public String GetDisplayName() {
        return this.DisplayName;
    }
    
    public float GetX() {
        return this.X;
    }
    
    public float GetY() {
        return this.Y;
    }
    
    public float GetWidth() {
        return this.Width;
    }
    
    public float GetHeight() {
        return this.Height;
    }
    
    public void SetX(final float p_X) {
        this.X = p_X;
    }
    
    public void SetY(final float p_Y) {
        this.Y = p_Y;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        for (final ComponentItem l_Item : this.Items) {
            this.HandleKeyTypedForItem(l_Item, typedChar, keyCode);
        }
    }
    
    public void HandleKeyTypedForItem(final ComponentItem p_Item, final char typedChar, final int keyCode) {
        p_Item.keyTyped(typedChar, keyCode);
        for (final ComponentItem l_Item : p_Item.DropdownItems) {
            this.HandleKeyTypedForItem(l_Item, typedChar, keyCode);
        }
    }
    
    private int GetColor() {
        return (this.Colors.Alpha.getValue() << 24 & 0xFF000000) | (this.Colors.Red.getValue() << 16 & 0xFF0000) | (this.Colors.Green.getValue() << 8 & 0xFF00) | (this.Colors.Blue.getValue() & 0xFF);
    }
    
    public int GetTextColor() {
        return (this.Colors.Red.getValue() << 16 & 0xFF0000) | (this.Colors.Green.getValue() << 8 & 0xFF00) | (this.Colors.Blue.getValue() & 0xFF);
    }
    
    public void Default() {
        this.X = this.DefaultX;
        this.Y = this.DefaultY;
        this.Items.forEach(comp -> {
            if (comp.HasState(ComponentItem.Extended)) {
                comp.RemoveState(ComponentItem.Extended);
            }
        });
    }
}
