//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.ingame;

import me.gopro336.goprohack.events.render.EventRenderGameOverlay;
import net.minecraft.client.gui.ScaledResolution;
import me.gopro336.goprohack.GoproHackMod;
import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.modules.ui.CustomFontChatModule;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.GuiIngameForge;

public class GoproGuiIngame extends GuiIngameForge
{
    public GoproGuiIngame(final Minecraft mc) {
        super(mc);
        ObfuscationReflectionHelper.setPrivateValue((Class)GuiIngame.class, (Object)this, (Object)new GoproGuiPlayerTabOverlay(mc, (GuiIngame)this), new String[] { "overlayPlayerList" });
        final CustomFontChatModule l_Mod = (CustomFontChatModule)ModuleManager.Get().GetMod(CustomFontChatModule.class);
        if (l_Mod != null && l_Mod.isEnabled()) {
            l_Mod.Activate();
        }
    }
    
    public void renderGameOverlay(final float partialTicks) {
        super.renderGameOverlay(partialTicks);
        GoproHackMod.EVENT_BUS.post(new EventRenderGameOverlay(partialTicks, new ScaledResolution(this.mc)));
    }
}
