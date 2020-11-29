// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import java.util.function.Predicate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.render.EventRenderOrientCamera;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public class ViewClipModule extends Module
{
    @EventHandler
    private Listener<EventRenderOrientCamera> OnRenderOrientCamera;
    
    public ViewClipModule() {
        super("NoClip", new String[] { "xclip" }, "Prevents the third person camera from ray-tracing", "NONE", -1, ModuleType.RENDER);
        this.OnRenderOrientCamera = new Listener<EventRenderOrientCamera>(event -> event.cancel(), (Predicate<EventRenderOrientCamera>[])new Predicate[0]);
    }
}
