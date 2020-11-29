//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import net.minecraft.entity.Entity;
import java.util.function.Predicate;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class YawModule extends Module
{
    public final Value<Boolean> yawLock;
    public final Value<Boolean> pitchLock;
    public final Value<Boolean> Cardinal;
    private float Yaw;
    private float Pitch;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public YawModule() {
        super("Yaw", new String[] { "RotLock", "Rotation" }, "Locks you rotation for precision", "NONE", 5131167, ModuleType.MOVEMENT);
        this.yawLock = new Value<Boolean>("Yaw", new String[] { "Y" }, "Lock the player's rotation yaw if enabled.", true);
        this.pitchLock = new Value<Boolean>("Pitch", new String[] { "P" }, "Lock the player's rotation pitch if enabled.", false);
        this.Cardinal = new Value<Boolean>("Cardinal", new String[] { "C" }, "Locks the yaw to one of the cardinal directions", false);
        Entity l_Entity;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() == MinecraftEvent.Era.PRE) {
                l_Entity = (Entity)(this.mc.player.isRiding() ? this.mc.player.getRidingEntity() : this.mc.player);
                if (this.yawLock.getValue()) {
                    l_Entity.rotationYaw = this.Yaw;
                }
                if (this.pitchLock.getValue()) {
                    l_Entity.rotationPitch = this.Pitch;
                }
                if (this.Cardinal.getValue()) {
                    l_Entity.rotationYaw = Math.round((l_Entity.rotationYaw + 1.0f) / 45.0f) * 45.0f;
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        if (this.Cardinal.getValue()) {
            return "Cardinal";
        }
        return "One";
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player != null) {
            this.Yaw = this.mc.player.rotationYaw;
            this.Pitch = this.mc.player.rotationPitch;
        }
    }
    
    @Override
    public void toggleNoSave() {
    }
}
