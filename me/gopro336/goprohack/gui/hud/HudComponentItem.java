//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud;

import me.gopro336.goprohack.managers.CommandManager;
import java.util.Iterator;
import java.io.Reader;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;
import java.io.File;
import net.minecraft.client.gui.ScaledResolution;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.managers.HudManager;
import me.gopro336.goprohack.main.Wrapper;
import net.minecraft.client.Minecraft;
import me.gopro336.goprohack.modules.Value;
import java.util.ArrayList;

public class HudComponentItem
{
    public ArrayList<Value> ValueList;
    private String DisplayName;
    private float X;
    private float Y;
    private float DefaultX;
    private float DefaultY;
    private float Width;
    private float Height;
    protected float DeltaX;
    protected float DeltaY;
    protected float ClampX;
    protected float ClampY;
    private int Flags;
    private boolean Hidden;
    private boolean Dragging;
    protected int ClampLevel;
    protected int Side;
    private boolean Selected;
    private boolean MultiSelectedDragging;
    protected Minecraft mc;
    public static int OnlyVisibleInHudEditor;
    
    public HudComponentItem(final String p_DisplayName, final float p_X, final float p_Y) {
        this.ValueList = new ArrayList<Value>();
        this.Hidden = true;
        this.Dragging = false;
        this.ClampLevel = 0;
        this.Side = 0;
        this.Selected = false;
        this.MultiSelectedDragging = false;
        this.mc = Wrapper.GetMC();
        this.DisplayName = p_DisplayName;
        this.X = p_X;
        this.Y = p_Y;
        this.DefaultX = p_X;
        this.DefaultY = p_Y;
    }
    
    public String GetDisplayName() {
        return this.DisplayName;
    }
    
    public void SetWidth(final float p_Width) {
        this.Width = p_Width;
    }
    
    public void SetHeight(final float p_Height) {
        this.Height = p_Height;
    }
    
    public float GetWidth() {
        return this.Width;
    }
    
    public float GetHeight() {
        return this.Height;
    }
    
    public boolean IsHidden() {
        return this.Hidden;
    }
    
    public void SetHidden(final boolean p_Hide) {
        this.Hidden = p_Hide;
        HudManager.Get().ScheduleSave(this);
    }
    
    public float GetX() {
        return this.X;
    }
    
    public float GetY() {
        return this.Y;
    }
    
    public void SetX(final float p_X) {
        if (this.X == p_X) {
            return;
        }
        this.X = p_X;
        if (this.ClampLevel == 0) {
            HudManager.Get().ScheduleSave(this);
        }
    }
    
    public void SetY(final float p_Y) {
        if (this.Y == p_Y) {
            return;
        }
        this.Y = p_Y;
        if (this.ClampLevel == 0) {
            HudManager.Get().ScheduleSave(this);
        }
    }
    
    public boolean IsDragging() {
        return this.Dragging;
    }
    
    public void SetDragging(final boolean p_Dragging) {
        this.Dragging = p_Dragging;
    }
    
    protected void SetClampPosition(final float p_X, final float p_Y) {
        this.ClampX = p_X;
        this.ClampY = p_Y;
    }
    
    protected void SetClampLevel(final int p_ClampLevel) {
        this.ClampLevel = p_ClampLevel;
    }
    
    public boolean Render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        final boolean l_Inside = p_MouseX >= this.GetX() && p_MouseX < this.GetX() + this.GetWidth() && p_MouseY >= this.GetY() && p_MouseY < this.GetY() + this.GetHeight();
        if (l_Inside) {
            RenderUtil.drawRect(this.GetX(), this.GetY(), this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), 1345864260);
        }
        if (this.IsDragging()) {
            final ScaledResolution l_Res = new ScaledResolution(this.mc);
            final float l_X = p_MouseX - this.DeltaX;
            final float l_Y = p_MouseY - this.DeltaY;
            this.SetX(Math.min(Math.max(0.0f, l_X), l_Res.getScaledWidth() - this.GetWidth()));
            this.SetY(Math.min(Math.max(0.0f, l_Y), l_Res.getScaledHeight() - this.GetHeight()));
        }
        this.render(p_MouseX, p_MouseY, p_PartialTicks);
        if (this.IsSelected()) {
            RenderUtil.drawRect(this.GetX(), this.GetY(), this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), 903732701);
        }
        return l_Inside;
    }
    
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
    }
    
    public boolean OnMouseClick(final int p_MouseX, final int p_MouseY, final int p_MouseButton) {
        if (p_MouseX >= this.GetX() && p_MouseX < this.GetX() + this.GetWidth() && p_MouseY >= this.GetY() && p_MouseY < this.GetY() + this.GetHeight()) {
            if (p_MouseButton == 0) {
                this.SetDragging(true);
                this.DeltaX = p_MouseX - this.GetX();
                this.DeltaY = p_MouseY - this.GetY();
                HudManager.Get().Items.forEach(p_Item -> {
                    if (p_Item.IsMultiSelectedDragging()) {
                        p_Item.SetDragging(true);
                        p_Item.SetDeltaX(p_MouseX - p_Item.GetX());
                        p_Item.SetDeltaY(p_MouseY - p_Item.GetY());
                    }
                    return;
                });
            }
            else if (p_MouseButton == 1) {
                ++this.Side;
                if (this.Side > 3) {
                    this.Side = 0;
                }
                HudManager.Get().ScheduleSave(this);
            }
            else if (p_MouseButton == 2) {
                ++this.ClampLevel;
                if (this.ClampLevel > 2) {
                    this.ClampLevel = 0;
                }
                this.SetClampPosition(this.GetX(), this.GetY());
                HudManager.Get().ScheduleSave(this);
            }
            return true;
        }
        return false;
    }
    
    public void SetDeltaX(final float p_X) {
        this.DeltaX = p_X;
    }
    
    public void SetDeltaY(final float p_Y) {
        this.DeltaY = p_Y;
    }
    
    public void OnMouseRelease(final int p_MouseX, final int p_MouseY, final int p_State) {
        this.SetDragging(false);
    }
    
    public void LoadSettings() {
        final File l_Exists = new File("GoproHack/HUD/" + this.GetDisplayName() + ".json");
        if (!l_Exists.exists()) {
            return;
        }
        try {
            final Gson gson = new Gson();
            final Reader reader = Files.newBufferedReader(Paths.get("GoproHack/HUD/" + this.GetDisplayName() + ".json", new String[0]));
            final Map<?, ?> map = (Map<?, ?>)gson.fromJson(reader, (Class)Map.class);
            for (final Map.Entry<?, ?> entry : map.entrySet()) {
                final String l_Key = (String)entry.getKey();
                final String l_Value = (String)entry.getValue();
                if (l_Key.equalsIgnoreCase("displayname")) {
                    this.SetDisplayName(l_Value, false);
                }
                else if (l_Key.equalsIgnoreCase("visible")) {
                    this.SetHidden(l_Value.equalsIgnoreCase("false"));
                }
                else if (l_Key.equalsIgnoreCase("PositionX")) {
                    this.SetX(Float.parseFloat(l_Value));
                }
                else if (l_Key.equalsIgnoreCase("PositionY")) {
                    this.SetY(Float.parseFloat(l_Value));
                }
                else if (l_Key.equalsIgnoreCase("ClampLevel")) {
                    this.SetClampLevel(Integer.parseInt(l_Value));
                }
                else if (l_Key.equalsIgnoreCase("ClampPositionX")) {
                    this.ClampX = Float.parseFloat(l_Value);
                }
                else if (l_Key.equalsIgnoreCase("ClampPositionY")) {
                    this.ClampY = Float.parseFloat(l_Value);
                }
                else if (l_Key.equalsIgnoreCase("Side")) {
                    this.Side = Integer.parseInt(l_Value);
                }
                else {
                    for (final Value l_Val : this.ValueList) {
                        if (l_Val.getName().equalsIgnoreCase((String)entry.getKey())) {
                            if (l_Val.getValue() instanceof Number && !(l_Val.getValue() instanceof Enum)) {
                                if (l_Val.getValue() instanceof Integer) {
                                    l_Val.SetForcedValue(Integer.parseInt(l_Value));
                                    break;
                                }
                                if (l_Val.getValue() instanceof Float) {
                                    l_Val.SetForcedValue(Float.parseFloat(l_Value));
                                    break;
                                }
                                if (l_Val.getValue() instanceof Double) {
                                    l_Val.SetForcedValue(Double.parseDouble(l_Value));
                                    break;
                                }
                                break;
                            }
                            else {
                                if (l_Val.getValue() instanceof Boolean) {
                                    l_Val.SetForcedValue(l_Value.equalsIgnoreCase("true"));
                                    break;
                                }
                                if (l_Val.getValue() instanceof Enum) {
                                    l_Val.SetForcedValue(l_Val.GetEnumReal(l_Value));
                                    break;
                                }
                                if (l_Val.getValue() instanceof String) {
                                    l_Val.SetForcedValue(l_Value);
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
            }
            reader.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int GetSide() {
        return this.Side;
    }
    
    public int GetClampLevel() {
        return this.ClampLevel;
    }
    
    public boolean HasFlag(final int p_Flag) {
        return (this.Flags & p_Flag) != 0x0;
    }
    
    public void AddFlag(final int p_Flags) {
        this.Flags |= p_Flags;
    }
    
    public void ResetToDefaultPos() {
        this.SetX(this.DefaultX);
        this.SetY(this.DefaultY);
    }
    
    public void SetSelected(final boolean p_Selected) {
        this.Selected = p_Selected;
    }
    
    public boolean IsInArea(final float p_MouseX1, final float p_MouseX2, final float p_MouseY1, final float p_MouseY2) {
        return this.GetX() >= p_MouseX1 && this.GetX() + this.GetWidth() <= p_MouseX2 && this.GetY() >= p_MouseY1 && this.GetY() + this.GetHeight() <= p_MouseY2;
    }
    
    public boolean IsSelected() {
        return this.Selected;
    }
    
    public void SetMultiSelectedDragging(final boolean b) {
        this.MultiSelectedDragging = b;
    }
    
    public boolean IsMultiSelectedDragging() {
        return this.MultiSelectedDragging;
    }
    
    public void SetDisplayName(final String p_NewName, final boolean p_Save) {
        this.DisplayName = p_NewName;
        if (p_Save) {
            HudManager.Get().ScheduleSave(this);
            CommandManager.Get().Reload();
        }
    }
    
    static {
        HudComponentItem.OnlyVisibleInHudEditor = 1;
    }
}
