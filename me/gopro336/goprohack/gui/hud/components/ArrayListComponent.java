//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import java.util.Iterator;
import net.minecraft.client.gui.ScaledResolution;
import java.util.Comparator;
import java.util.ArrayList;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.managers.ModuleManager;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.util.colors.GoproRainbowUtil;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Module;
import java.util.HashMap;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class ArrayListComponent extends HudComponentItem
{
    public final Value<Boolean> RainbowVal;
    public final Value<Boolean> NoBackground;
    private HashMap<Module, String> m_StaticModuleNames;
    private Timer ReorderTimer;
    private GoproRainbowUtil Rainbow;
    
    public ArrayListComponent() {
        super("ModuleList", 0.0f, 0.0f);
        this.RainbowVal = new Value<Boolean>("Rainbow", new String[] { "" }, "Makes a dynamic rainbow", true);
        this.NoBackground = new Value<Boolean>("NoBackground", new String[] { "" }, "NoBackground on arraylist", false);
        this.m_StaticModuleNames = new HashMap<Module, String>();
        this.ReorderTimer = new Timer();
        this.Rainbow = new GoproRainbowUtil(9);
        this.SetHidden(false);
        this.ClampLevel = 1;
    }
    
    public String GenerateModuleDisplayName(final Module p_Mod) {
        String l_DisplayName = p_Mod.GetArrayListDisplayName();
        if (p_Mod.getMetaData() != null) {
            l_DisplayName = l_DisplayName + " " + ChatFormatting.GRAY + "[" + ChatFormatting.GRAY + p_Mod.getMetaData() + ChatFormatting.GRAY + "]";
        }
        return l_DisplayName;
    }
    
    public String GetStaticModuleNames(final Module p_Mod) {
        if (!this.m_StaticModuleNames.containsKey(p_Mod)) {
            this.m_StaticModuleNames.put(p_Mod, this.GenerateModuleDisplayName(p_Mod));
        }
        return this.m_StaticModuleNames.get(p_Mod);
    }
    
    @Override
    public void SetHidden(final boolean p_Hidden) {
        super.SetHidden(p_Hidden);
        ModuleManager.Get().GetModuleList().forEach(p_Mod -> {
            if (p_Mod != null && p_Mod.getType() != Module.ModuleType.HIDDEN && p_Mod.isEnabled() && !p_Mod.isHidden()) {
                p_Mod.RemainingXAnimation = RenderUtil.getStringWidth(p_Mod.GetFullArrayListDisplayName()) + 10.0f;
                ModuleManager.Get().OnModEnable(p_Mod);
            }
        });
    }
    
    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        ModuleManager.Get().Update();
        final ArrayList<Module> mods = new ArrayList<Module>();
        for (final Module mod : ModuleManager.Get().GetModuleList()) {
            if (mod != null && mod.getType() != Module.ModuleType.HIDDEN && mod.isEnabled() && !mod.isHidden()) {
                mods.add(mod);
            }
        }
        if (this.ReorderTimer.passed(1000.0)) {
            this.ReorderTimer.reset();
            this.m_StaticModuleNames.clear();
        }
        final String firstName;
        final String secondName;
        final float dif;
        final Comparator<Module> comparator = (first, second) -> {
            firstName = this.GetStaticModuleNames(first);
            secondName = this.GetStaticModuleNames(second);
            dif = RenderUtil.getStringWidth(secondName) - RenderUtil.getStringWidth(firstName);
            return (dif != 0.0f) ? ((int)dif) : secondName.compareTo(firstName);
        };
        mods.sort(comparator);
        float xOffset = 0.0f;
        float yOffset = 0.0f;
        float maxWidth = 0.0f;
        this.Rainbow.OnRender();
        int l_I = 0;
        for (final Module mod2 : mods) {
            if (mod2 != null && mod2.getType() != Module.ModuleType.HIDDEN && mod2.isEnabled() && !mod2.isHidden()) {
                final String name = this.GenerateModuleDisplayName(mod2);
                final float width = RenderUtil.getStringWidth(name);
                if (width >= maxWidth) {
                    maxWidth = width;
                }
                final float l_StringYHeight = 8.0f;
                final float l_RemainingXOffset = mod2.GetRemainingXArraylistOffset();
                switch (this.Side) {
                    case 0:
                    case 1: {
                        xOffset = this.GetWidth() - RenderUtil.getStringWidth(name) + l_RemainingXOffset;
                        break;
                    }
                    case 2:
                    case 3: {
                        xOffset = -l_RemainingXOffset;
                        break;
                    }
                }
                l_I += 20;
                if (l_I >= 355) {
                    l_I = 0;
                }
                switch (this.Side) {
                    case 0:
                    case 3: {
                        if (!this.NoBackground.getValue()) {
                            RenderUtil.drawRect(this.GetX() + xOffset - 2.0f + mod2.GetRemainingXArraylistOffset(), this.GetY() + yOffset, this.GetX() + xOffset + RenderUtil.getStringWidth(name) + 4.0f, this.GetY() + yOffset + (l_StringYHeight + 1.5f), 1963986960);
                        }
                        RenderUtil.drawStringWithShadow(name, this.GetX() + xOffset, this.GetY() + yOffset, ((boolean)this.RainbowVal.getValue()) ? this.Rainbow.GetRainbowColorAt(l_I) : mod2.getColor());
                        yOffset += l_StringYHeight + 1.5f;
                        continue;
                    }
                    case 1:
                    case 2: {
                        if (!this.NoBackground.getValue()) {
                            RenderUtil.drawRect(this.GetX() + xOffset - 2.0f + mod2.GetRemainingXArraylistOffset(), this.GetY() + (this.GetHeight() - l_StringYHeight) + yOffset, this.GetX() + xOffset + RenderUtil.getStringWidth(name) + 4.0f, this.GetY() + (this.GetHeight() - l_StringYHeight) + yOffset + (l_StringYHeight + 1.5f), 1963986960);
                        }
                        RenderUtil.drawStringWithShadow(name, this.GetX() + xOffset, this.GetY() + (this.GetHeight() - l_StringYHeight) + yOffset, ((boolean)this.RainbowVal.getValue()) ? this.Rainbow.GetRainbowColorAt(l_I) : mod2.getColor());
                        yOffset -= l_StringYHeight + 1.5f;
                        continue;
                    }
                }
            }
        }
        if (this.ClampLevel > 0) {
            final ScaledResolution l_Res = new ScaledResolution(this.mc);
            switch (this.Side) {
                case 0: {
                    this.SetX(l_Res.getScaledWidth() - maxWidth + 8.0f);
                    if (this.ClampLevel == 2) {
                        this.SetY(Math.max(this.GetY(), 1.0f));
                        break;
                    }
                    this.SetY(1.0f);
                    break;
                }
                case 1: {
                    this.SetX(l_Res.getScaledWidth() - maxWidth + 8.0f);
                    if (this.ClampLevel == 2) {
                        this.SetY(Math.min(this.GetY(), l_Res.getScaledHeight() + yOffset));
                        break;
                    }
                    this.SetY(l_Res.getScaledHeight() + yOffset);
                    break;
                }
                case 2: {
                    this.SetX(1.0f);
                    if (this.ClampLevel == 2) {
                        this.SetY(Math.min(this.GetY(), l_Res.getScaledHeight() + yOffset));
                        break;
                    }
                    this.SetY(l_Res.getScaledHeight() + yOffset);
                    break;
                }
                case 3: {
                    this.SetX(1.0f);
                    if (this.ClampLevel == 2) {
                        this.SetY(Math.max(this.GetY(), 1.0f));
                        break;
                    }
                    this.SetY(1.0f);
                    break;
                }
            }
        }
        this.SetWidth(maxWidth - 10.0f);
        this.SetHeight(Math.abs(yOffset));
    }
}
