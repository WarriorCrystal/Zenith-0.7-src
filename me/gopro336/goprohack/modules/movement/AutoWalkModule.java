//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import me.gopro336.goprohack.managers.ModuleManager;
import net.minecraft.util.MovementInput;
import java.util.function.Predicate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdateMoveState;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.world.AutoTunnelModule;
import me.gopro336.goprohack.modules.Module;

public final class AutoWalkModule extends Module
{
    private AutoTunnelModule _autoTunnel;
    @EventHandler
    private Listener<EventPlayerUpdateMoveState> OnUpdateMoveState;
    
    public AutoWalkModule() {
        super("AutoWalk", new String[] { "AW" }, "Automatically walks forward", "NONE", 5131167, ModuleType.MOVEMENT);
        MovementInput movementInput;
        this.OnUpdateMoveState = new Listener<EventPlayerUpdateMoveState>(p_Event -> {
            if (!this.NeedPause()) {
                movementInput = this.mc.player.movementInput;
                ++movementInput.moveForward;
            }
        }, (Predicate<EventPlayerUpdateMoveState>[])new Predicate[0]);
    }
    
    @Override
    public void init() {
        this._autoTunnel = (AutoTunnelModule)ModuleManager.Get().GetMod(AutoTunnelModule.class);
    }
    
    private boolean NeedPause() {
        return this._autoTunnel.PauseAutoWalk();
    }
}
