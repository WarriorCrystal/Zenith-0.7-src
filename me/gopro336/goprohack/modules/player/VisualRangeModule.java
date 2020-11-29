//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.player;

import me.gopro336.goprohack.managers.NotificationManager;
import me.gopro336.goprohack.managers.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import java.util.function.Predicate;
import java.util.ArrayList;
import me.gopro336.goprohack.events.entity.EventEntityRemoved;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.entity.EventEntityAdded;
import me.zero.alpine.fork.listener.Listener;
import java.util.List;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class VisualRangeModule extends Module
{
    public final Value<Modes> Mode;
    public final Value<Boolean> Friends;
    public final Value<Boolean> Enter;
    public final Value<Boolean> Leave;
    private List<String> Entities;
    @EventHandler
    private Listener<EventEntityAdded> OnEntityAdded;
    @EventHandler
    private Listener<EventEntityRemoved> OnEntityRemove;
    
    public VisualRangeModule() {
        super("PlayerRange", new String[] { "PR" }, "Notifies you when one enters or leaves your render range.", "NONE", -1, ModuleType.PLAYER);
        this.Mode = new Value<Modes>("Mode", new String[] { "M" }, "Mode of notifying to use", Modes.Both);
        this.Friends = new Value<Boolean>("Friends", new String[] { "Friend" }, "Notifies if a friend comes in range", true);
        this.Enter = new Value<Boolean>("Enter", new String[] { "Enters" }, "Notifies when the entity enters range", true);
        this.Leave = new Value<Boolean>("Leave", new String[] { "Leaves" }, "Notifies when the entity leaves range", true);
        this.Entities = new ArrayList<String>();
        this.OnEntityAdded = new Listener<EventEntityAdded>(p_Event -> {
            if (!this.Enter.getValue()) {
                return;
            }
            else if (!this.VerifyEntity(p_Event.GetEntity())) {
                return;
            }
            else {
                if (!this.Entities.contains(p_Event.GetEntity().getName())) {
                    this.Entities.add(p_Event.GetEntity().getName());
                    this.Notify(String.format("%s has entered your visual range.", p_Event.GetEntity().getName()));
                }
                return;
            }
        }, (Predicate<EventEntityAdded>[])new Predicate[0]);
        this.OnEntityRemove = new Listener<EventEntityRemoved>(p_Event -> {
            if (!(!this.Leave.getValue())) {
                if (!(!this.VerifyEntity(p_Event.GetEntity()))) {
                    if (this.Entities.contains(p_Event.GetEntity().getName())) {
                        this.Entities.remove(p_Event.GetEntity().getName());
                        this.Notify(String.format("%s has left your visual range.", p_Event.GetEntity().getName()));
                    }
                }
            }
        }, (Predicate<EventEntityRemoved>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.Entities.clear();
    }
    
    private boolean VerifyEntity(final Entity p_Entity) {
        return p_Entity instanceof EntityPlayer && p_Entity != this.mc.player && (this.Friends.getValue() || !FriendManager.Get().IsFriend(p_Entity));
    }
    
    private void Notify(final String p_Msg) {
        switch (this.Mode.getValue()) {
            case Chat: {
                this.SendMessage(p_Msg);
                break;
            }
            case Notification: {
                NotificationManager.Get().AddNotification("GoproHack", p_Msg);
                break;
            }
            case Both: {
                this.SendMessage(p_Msg);
                NotificationManager.Get().AddNotification("GoproHack", p_Msg);
                break;
            }
        }
    }
    
    private enum Modes
    {
        Chat, 
        Notification, 
        Both;
    }
}
