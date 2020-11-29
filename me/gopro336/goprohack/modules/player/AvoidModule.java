//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.player;

import java.util.function.Predicate;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class AvoidModule extends Module
{
    public final Value<Boolean> NoVoid;
    private boolean SendElytraPacket;
    private boolean SendInvPackets;
    private int ElytraSlot;
    private Timer ReplaceChestTimer;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AvoidModule() {
        super("Avoid", new String[] { "NoVoid" }, "Prevents falling into the void", "NONE", 2415509, ModuleType.PLAYER);
        this.NoVoid = new Value<Boolean>("NoVoid", new String[] { "NoVoid" }, "Prevents you from falling into the void", true);
        this.SendElytraPacket = false;
        this.SendInvPackets = false;
        this.ElytraSlot = -1;
        this.ReplaceChestTimer = new Timer();
        RayTraceResult l_Trace;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() == MinecraftEvent.Era.PRE) {
                if (this.NoVoid.getValue() && this.mc.player.posY <= 5.0) {
                    l_Trace = this.mc.world.rayTraceBlocks(this.mc.player.getPositionVector(), new Vec3d(this.mc.player.posX, 0.0, this.mc.player.posZ), false, false, false);
                    if (l_Trace == null || l_Trace.typeOfHit != RayTraceResult.Type.BLOCK) {
                        this.mc.player.setVelocity(0.0, 0.0, 0.0);
                    }
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.SendElytraPacket = false;
        this.SendInvPackets = false;
    }
}
