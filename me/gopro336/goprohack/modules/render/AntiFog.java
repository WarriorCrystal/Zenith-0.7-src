//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import java.util.function.Predicate;
import net.minecraft.client.renderer.GlStateManager;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.render.EventRenderSetupFog;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public class AntiFog extends Module
{
    @EventHandler
    private Listener<EventRenderSetupFog> SetupFog;
    
    public AntiFog() {
        super("AntiFog", new String[] { "NoFog" }, "Prevents fog from being rendered", "NONE", 5131167, ModuleType.RENDER);
        this.SetupFog = new Listener<EventRenderSetupFog>(p_Event -> {
            p_Event.cancel();
            this.mc.entityRenderer.setupFogColor(false);
            GlStateManager.glNormal3f(0.0f, -1.0f, 0.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.colorMaterial(1028, 4608);
        }, (Predicate<EventRenderSetupFog>[])new Predicate[0]);
    }
}
