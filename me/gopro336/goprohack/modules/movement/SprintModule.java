//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import java.util.function.Predicate;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class SprintModule extends Module
{
    public final Value<Modes> Mode;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public SprintModule() {
        super("Sprint", new String[] { "AutoSprint", "Spr" }, "Automatically sprints for you", "NONE", 5131167, ModuleType.MOVEMENT);
        this.Mode = new Value<Modes>("Mode", new String[] { "Mode", "M" }, "The sprint mode to use.", Modes.Rage);
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() == MinecraftEvent.Era.PRE) {
                switch (this.Mode.getValue()) {
                    case Rage: {
                        if ((this.mc.gameSettings.keyBindForward.isKeyDown() || this.mc.gameSettings.keyBindBack.isKeyDown() || this.mc.gameSettings.keyBindLeft.isKeyDown() || this.mc.gameSettings.keyBindRight.isKeyDown()) && !this.mc.player.isSneaking() && !this.mc.player.collidedHorizontally && this.mc.player.getFoodStats().getFoodLevel() > 6.0f) {
                            this.mc.player.setSprinting(true);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Legit: {
                        if (this.mc.gameSettings.keyBindForward.isKeyDown() && !this.mc.player.isSneaking() && !this.mc.player.isHandActive() && !this.mc.player.collidedHorizontally && this.mc.currentScreen == null && this.mc.player.getFoodStats().getFoodLevel() > 6.0f) {
                            this.mc.player.setSprinting(true);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mc.world != null) {
            this.mc.player.setSprinting(false);
        }
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    private enum Modes
    {
        Rage, 
        Legit;
    }
}
