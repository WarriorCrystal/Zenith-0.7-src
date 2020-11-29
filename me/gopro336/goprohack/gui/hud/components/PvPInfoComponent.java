// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.modules.combat.AutoCrystalOptimise;
import me.gopro336.goprohack.modules.movement.SpeedModule;
import me.gopro336.goprohack.modules.combat.AutoTrap;
import me.gopro336.goprohack.modules.combat.AutoCrystalModule;
import me.gopro336.goprohack.modules.combat.KillAuraModule;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class PvPInfoComponent extends HudComponentItem
{
    private KillAuraModule _killAura;
    private AutoCrystalModule _autoCrystal;
    private AutoTrap _autoTrap;
    private SpeedModule _speed;
    private AutoCrystalOptimise _autoCrystalOptimise;
    
    public PvPInfoComponent() {
        super("PvPInfo", 2.0f, 290.0f);
        this._killAura = (KillAuraModule)ModuleManager.Get().GetMod(KillAuraModule.class);
        this._autoCrystal = (AutoCrystalModule)ModuleManager.Get().GetMod(AutoCrystalModule.class);
        this._autoTrap = (AutoTrap)ModuleManager.Get().GetMod(AutoTrap.class);
        this._speed = (SpeedModule)ModuleManager.Get().GetMod(SpeedModule.class);
        this._autoCrystalOptimise = (AutoCrystalOptimise)ModuleManager.Get().GetMod(AutoCrystalOptimise.class);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        final String aura = ChatFormatting.GRAY + "AURA " + ChatFormatting.WHITE + (this._killAura.isEnabled() ? (ChatFormatting.GREEN + "ON") : (ChatFormatting.RED + "OFF"));
        final String crystal = ChatFormatting.GRAY + "CAURA " + ChatFormatting.WHITE + ((this._autoCrystal.isEnabled() || this._autoCrystalOptimise.isEnabled()) ? (ChatFormatting.GREEN + "ON") : (ChatFormatting.RED + "OFF"));
        final String autoTrap = ChatFormatting.GRAY + "AT " + ChatFormatting.WHITE + (this._autoTrap.isEnabled() ? (ChatFormatting.GREEN + "ON") : (ChatFormatting.RED + "OFF"));
        final String speed = ChatFormatting.GRAY + "SP " + ChatFormatting.WHITE + (this._speed.isEnabled() ? (ChatFormatting.GREEN + "ON") : (ChatFormatting.RED + "OFF"));
        RenderUtil.drawStringWithShadow(aura, this.GetX(), this.GetY(), -1);
        RenderUtil.drawStringWithShadow(crystal, this.GetX(), this.GetY() + 12.0f, -1);
        RenderUtil.drawStringWithShadow(autoTrap, this.GetX(), this.GetY() + 24.0f, -1);
        RenderUtil.drawStringWithShadow(speed, this.GetX(), this.GetY() + 36.0f, -1);
        this.SetWidth(RenderUtil.getStringWidth(aura));
        this.SetHeight(RenderUtil.getStringHeight(crystal) + RenderUtil.getStringHeight(aura) + RenderUtil.getStringHeight(autoTrap) + RenderUtil.getStringHeight(speed));
    }
}
