//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import net.minecraft.client.renderer.GlStateManager;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import me.gopro336.goprohack.events.render.EventRenderChunkContainer;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.render.EventRenderChunk;
import me.zero.alpine.fork.listener.Listener;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.client.renderer.chunk.RenderChunk;
import java.util.WeakHashMap;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class ChunkAnimator extends Module
{
    public final Value<Integer> AnimationLength;
    public final Value<Boolean> EasingEnabled;
    private final WeakHashMap<RenderChunk, AtomicLong> lifespans;
    @EventHandler
    private final Listener<EventRenderChunk> RenderChunk;
    @EventHandler
    private final Listener<EventRenderChunkContainer> RenderChunkContainer;
    
    public ChunkAnimator() {
        super("ChunkAnimator", new String[] { "ChunkAnimation", "ChunkAnimate" }, "Animates chunks so they rise from the ground", "NONE", 5131167, ModuleType.RENDER);
        this.AnimationLength = new Value<Integer>("Length", new String[] { "L" }, "Controls how long (ms) the chunk animation is", 1000, 250, 5000, 1);
        this.EasingEnabled = new Value<Boolean>("Easing", new String[] { "E" }, "Control if easing is enabled for the chunk animation", true);
        this.lifespans = new WeakHashMap<RenderChunk, AtomicLong>();
        this.RenderChunk = new Listener<EventRenderChunk>(event -> {
            if (Minecraft.getMinecraft().player != null && !this.lifespans.containsKey(event.RenderChunk)) {
                this.lifespans.put(event.RenderChunk, new AtomicLong(-1L));
            }
            return;
        }, (Predicate<EventRenderChunk>[])new Predicate[0]);
        AtomicLong timeAlive;
        long timeClone;
        long timeDifference;
        double chunkY;
        double offsetY;
        this.RenderChunkContainer = new Listener<EventRenderChunkContainer>(event -> {
            if (this.lifespans.containsKey(event.RenderChunk)) {
                timeAlive = this.lifespans.get(event.RenderChunk);
                timeClone = timeAlive.get();
                if (timeClone == -1L) {
                    timeClone = System.currentTimeMillis();
                    timeAlive.set(timeClone);
                }
                timeDifference = System.currentTimeMillis() - timeClone;
                if (timeDifference <= this.AnimationLength.getValue()) {
                    chunkY = event.RenderChunk.getPosition().getY();
                    offsetY = chunkY / this.AnimationLength.getValue() * timeDifference;
                    if (this.EasingEnabled.getValue()) {
                        offsetY = chunkY * this.easeOutCubic(timeDifference / (double)this.AnimationLength.getValue());
                    }
                    GlStateManager.translate(0.0, -chunkY + offsetY, 0.0);
                }
            }
        }, (Predicate<EventRenderChunkContainer>[])new Predicate[0]);
    }
    
    private double easeOutCubic(double t) {
        return --t * t * t + 1.0;
    }
}
