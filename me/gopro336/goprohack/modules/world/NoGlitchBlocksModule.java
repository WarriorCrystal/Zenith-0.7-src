// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import java.util.function.Predicate;
import me.gopro336.goprohack.events.world.EventWorldSetBlockState;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerDestroyBlock;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class NoGlitchBlocksModule extends Module
{
    public final Value<Boolean> Destroy;
    public final Value<Boolean> Place;
    @EventHandler
    private Listener<EventPlayerDestroyBlock> OnPlayerDestroyBlock;
    @EventHandler
    private Listener<EventWorldSetBlockState> OnSetBlockState;
    
    public NoGlitchBlocksModule() {
        super("NoGhostBlocks", new String[] { "AntiGhostBlocks" }, "Synchronizes client and server communication by canceling clientside destroy/place for blocks", "NONE", -1, ModuleType.WORLD);
        this.Destroy = new Value<Boolean>("Destroy", new String[] { "destroy" }, "Syncs Destroying", true);
        this.Place = new Value<Boolean>("Place", new String[] { "placement" }, "Syncs placement.", true);
        this.OnPlayerDestroyBlock = new Listener<EventPlayerDestroyBlock>(p_Event -> {
            if (!this.Destroy.getValue()) {
                return;
            }
            else {
                p_Event.cancel();
                return;
            }
        }, (Predicate<EventPlayerDestroyBlock>[])new Predicate[0]);
        this.OnSetBlockState = new Listener<EventWorldSetBlockState>(p_Event -> {
            if (!(!this.Place.getValue())) {
                if (p_Event.Flags != 3) {
                    p_Event.cancel();
                }
            }
        }, (Predicate<EventWorldSetBlockState>[])new Predicate[0]);
    }
}
