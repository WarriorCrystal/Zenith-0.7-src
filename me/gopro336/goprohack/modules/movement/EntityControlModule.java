// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import java.util.function.Predicate;
import me.gopro336.goprohack.events.entity.EventHorseSaddled;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.entity.EventSteerEntity;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public class EntityControlModule extends Module
{
    @EventHandler
    private Listener<EventSteerEntity> OnSteerEntity;
    @EventHandler
    private Listener<EventHorseSaddled> OnHorseSaddled;
    
    public EntityControlModule() {
        super("EntityControl", new String[] { "AntiSaddle", "EntityRide", "NoSaddle" }, "Allows you to control llamas, horses, pigs without a saddle/carrot", "NONE", -1, ModuleType.MOVEMENT);
        this.OnSteerEntity = new Listener<EventSteerEntity>(p_Event -> p_Event.cancel(), (Predicate<EventSteerEntity>[])new Predicate[0]);
        this.OnHorseSaddled = new Listener<EventHorseSaddled>(p_Event -> p_Event.cancel(), (Predicate<EventHorseSaddled>[])new Predicate[0]);
    }
}
