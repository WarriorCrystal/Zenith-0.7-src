//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import net.minecraft.util.EnumHand;
import java.util.function.Predicate;
import me.gopro336.goprohack.events.render.EventRenderUpdateEquippedItem;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class HandProgressModule extends Module
{
    public final Value<Float> MainProgress;
    public final Value<Float> OffProgress;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventRenderUpdateEquippedItem> OnUpdateEquippedItem;
    
    public HandProgressModule() {
        super("SmallTotem", new String[] { "SmallShield", "SS" }, "Smaller view of mainhand/offhand, smallshield", "NONE", 5131167, ModuleType.RENDER);
        this.MainProgress = new Value<Float>("MainLevel", new String[] { "" }, "Mainhand Level", 0.5f, 0.0f, 1.0f, 0.1f);
        this.OffProgress = new Value<Float>("OffLevel", new String[] { "" }, "Offhand Level", 0.5f, 0.0f, 1.0f, 0.1f);
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            this.mc.entityRenderer.itemRenderer.equippedProgressMainHand = this.MainProgress.getValue();
            this.mc.entityRenderer.itemRenderer.equippedProgressOffHand = this.OffProgress.getValue();
            return;
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        this.OnUpdateEquippedItem = new Listener<EventRenderUpdateEquippedItem>(p_Event -> {
            this.mc.entityRenderer.itemRenderer.itemStackMainHand = this.mc.player.getHeldItem(EnumHand.MAIN_HAND);
            this.mc.entityRenderer.itemRenderer.itemStackOffHand = this.mc.player.getHeldItem(EnumHand.OFF_HAND);
        }, (Predicate<EventRenderUpdateEquippedItem>[])new Predicate[0]);
    }
}
