// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.click.component.item;

import org.lwjgl.input.Keyboard;
import java.math.BigDecimal;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.gui.click.component.listeners.ComponentItemListener;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Value;

public class ComponentItemValue extends ComponentItem
{
    final Value Val;
    private boolean IsDraggingSlider;
    private Timer timer;
    private String DisplayString;
    private boolean _isEditingString;
    
    public ComponentItemValue(final Value p_Val, final String p_DisplayText, final String p_Description, final int p_Flags, final int p_State, final ComponentItemListener p_Listener, final float p_Width, final float p_Height) {
        super(p_DisplayText, p_Description, p_Flags, p_State, p_Listener, p_Width, p_Height);
        this.IsDraggingSlider = false;
        this.timer = new Timer();
        this.DisplayString = "";
        this._isEditingString = false;
        this.Val = p_Val;
        if (p_Val.getValue() instanceof Number && !(p_Val.getValue() instanceof Enum)) {
            this.Flags |= ComponentItem.Slider;
            this.Flags |= ComponentItem.DontDisplayClickableHighlight;
            this.Flags |= ComponentItem.RectDisplayAlways;
            this.SetCurrentWidth(this.CalculateXPositionFromValue(p_Val));
        }
        else if (p_Val.getValue() instanceof Boolean) {
            this.Flags |= ComponentItem.Boolean;
            this.Flags |= ComponentItem.RectDisplayOnClicked;
            this.Flags |= ComponentItem.DontDisplayClickableHighlight;
            if (p_Val.getValue()) {
                this.State |= ComponentItem.Clicked;
            }
        }
        else if (p_Val.getValue() instanceof Enum) {
            this.Flags |= ComponentItem.Enum;
            this.Flags |= ComponentItem.DontDisplayClickableHighlight;
            this.Flags |= ComponentItem.RectDisplayAlways;
        }
        else if (p_Val.getValue() instanceof String) {
            this.Flags |= ComponentItem.Enum;
        }
    }
    
    private void SetCurrentWidth(final float p_Width) {
        this.CurrentWidth = p_Width;
    }
    
    @Override
    public void Update() {
    }
    
    @Override
    public boolean HasState(final int p_State) {
        if ((p_State & ComponentItem.Clicked) != 0x0) {
            return !(this.Val.getValue() instanceof Boolean) || this.Val.getValue();
        }
        return super.HasState(p_State);
    }
    
    public float CalculateXPositionFromValue(final Value p_Val) {
        final float l_MinX = this.GetX();
        final float l_MaxX = this.GetX() + this.GetWidth();
        if (p_Val.getMax() == null) {
            return l_MinX;
        }
        final Number l_Val = p_Val.getValue();
        final Number l_Max = p_Val.getMax();
        return (l_MaxX - l_MinX) * (l_Val.floatValue() / l_Max.floatValue());
    }
    
    @Override
    public String GetDisplayText() {
        if (this.Val.getValue() instanceof Boolean) {
            String l_DisplayText = this.Val.getName();
            if (this.HasState(ComponentItem.Hovered) && RenderUtil.getStringWidth(l_DisplayText) > this.GetWidth() - 3.0f) {
                if (this.DisplayString == null) {
                    this.DisplayString = this.Val.getName();
                }
                l_DisplayText = this.DisplayString;
                for (float l_Width = RenderUtil.getStringWidth(l_DisplayText); l_Width > this.GetWidth() - 3.0f; l_Width = RenderUtil.getStringWidth(l_DisplayText), l_DisplayText = l_DisplayText.substring(0, l_DisplayText.length() - 1)) {}
                if (this.timer.passed(75.0) && this.DisplayString.length() > 0) {
                    final String l_FirstChar = String.valueOf(this.DisplayString.charAt(0));
                    this.DisplayString = this.DisplayString.substring(1) + l_FirstChar;
                    this.timer.reset();
                }
                return l_DisplayText;
            }
            this.DisplayString = null;
            for (float l_Width = RenderUtil.getStringWidth(l_DisplayText); l_Width > this.GetWidth() - 3.0f; l_Width = RenderUtil.getStringWidth(l_DisplayText), l_DisplayText = l_DisplayText.substring(0, l_DisplayText.length() - 1)) {}
            return l_DisplayText;
        }
        else {
            String l_DisplayText = this.Val.getName() + " " + ((this.Val.getValue() == null) ? "null" : this.Val.getValue().toString()) + " ";
            if (this.HasState(ComponentItem.Hovered) && RenderUtil.getStringWidth(l_DisplayText) > this.GetWidth() - 3.0f) {
                if (this.DisplayString == null) {
                    this.DisplayString = this.Val.getName() + " " + this.Val.getValue().toString() + " ";
                }
                l_DisplayText = this.DisplayString;
                for (float l_Width = RenderUtil.getStringWidth(l_DisplayText); l_Width > this.GetWidth() - 3.0f; l_Width = RenderUtil.getStringWidth(l_DisplayText), l_DisplayText = l_DisplayText.substring(0, l_DisplayText.length() - 1)) {}
                if (this.timer.passed(75.0) && this.DisplayString.length() > 0) {
                    final String l_FirstChar = String.valueOf(this.DisplayString.charAt(0));
                    this.DisplayString = this.DisplayString.substring(1) + l_FirstChar;
                    this.timer.reset();
                }
                return l_DisplayText;
            }
            this.DisplayString = null;
            for (float l_Width = RenderUtil.getStringWidth(l_DisplayText); l_Width > this.GetWidth() - 3.0f; l_Width = RenderUtil.getStringWidth(l_DisplayText), l_DisplayText = l_DisplayText.substring(0, l_DisplayText.length() - 1)) {}
            return l_DisplayText;
        }
    }
    
    @Override
    public void OnMouseClick(final int p_MouseX, final int p_MouseY, final int p_MouseButton) {
        super.OnMouseClick(p_MouseX, p_MouseY, p_MouseButton);
        if (this.Val.getValue() instanceof Enum) {
            this.Val.setEnumValue(this.Val.GetNextEnumValue(p_MouseButton == 1));
        }
        else if (this.Val.getValue() instanceof String) {
            this._isEditingString = !this._isEditingString;
            this.Val.setValue("");
        }
        else if (this.Val.getValue() instanceof Boolean) {
            this.Val.setValue(!this.Val.getValue());
        }
        else {
            this.IsDraggingSlider = !this.IsDraggingSlider;
        }
    }
    
    @Override
    public void OnMouseRelease(final int p_MouseX, final int p_MouseY) {
        if (this.IsDraggingSlider) {
            this.IsDraggingSlider = false;
        }
    }
    
    @Override
    public void OnMouseMove(final float p_MouseX, final float p_MouseY, final float p_X, final float p_Y) {
        if (!this.HasFlag(ComponentItem.Slider)) {
            return;
        }
        if (!this.IsDraggingSlider) {
            return;
        }
        float l_X = p_X + this.GetX();
        if (p_MouseX >= l_X && p_MouseX <= p_X + this.GetX() + this.GetWidth()) {
            l_X = p_MouseX;
        }
        if (p_MouseX > p_X + this.GetX() + this.GetWidth()) {
            l_X = p_X + this.GetX() + this.GetWidth();
        }
        l_X -= p_X;
        this.SetCurrentWidth(l_X - this.GetX());
        final float l_Pct = (l_X - this.GetX()) / this.GetWidth();
        if (this.Val.getValue().getClass() == Float.class) {
            final BigDecimal l_Decimal = new BigDecimal(((this.Val.getMax().getClass() == Float.class) ? this.Val.getMax() : ((this.Val.getMax().getClass() == Double.class) ? this.Val.getMax() : ((double)(int)this.Val.getMax()))) * (double)l_Pct);
            this.Val.setValue(l_Decimal.setScale(2, 6).floatValue());
        }
        else if (this.Val.getValue().getClass() == Double.class) {
            final BigDecimal l_Decimal = new BigDecimal(((this.Val.getMax().getClass() == Double.class) ? this.Val.getMax() : ((this.Val.getMax().getClass() == Float.class) ? this.Val.getMax() : ((double)(int)this.Val.getMax()))) * (double)l_Pct);
            this.Val.setValue(l_Decimal.setScale(2, 6).doubleValue());
        }
        else if (this.Val.getValue().getClass() == Integer.class) {
            this.Val.setValue((int)(this.Val.getMax() * l_Pct));
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this._isEditingString) {
            if (Keyboard.isKeyDown(1) || Keyboard.isKeyDown(28)) {
                this._isEditingString = false;
                return;
            }
            String string = this.Val.getValue();
            if (string == null) {
                return;
            }
            if (Keyboard.isKeyDown(14)) {
                if (string.length() > 0) {
                    string = string.substring(0, string.length() - 1);
                }
            }
            else if (Character.isDigit(typedChar) || Character.isLetter(typedChar)) {
                string += typedChar;
            }
            this.Val.setValue(string);
        }
    }
}
