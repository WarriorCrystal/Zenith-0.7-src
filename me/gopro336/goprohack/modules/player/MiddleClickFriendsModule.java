//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.player;

import net.minecraft.entity.Entity;
import java.util.function.Predicate;
import me.gopro336.goprohack.managers.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public class MiddleClickFriendsModule extends Module
{
    private boolean Clicked;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public MiddleClickFriendsModule() {
        super("MiddleClick", new String[] { "MCF", "MiddleClickF" }, "Middle click friends", "NONE", -1, ModuleType.PLAYER);
        this.Clicked = false;
        RayTraceResult l_Result;
        Entity l_Entity;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.mc.currentScreen == null) {
                if (!Mouse.isButtonDown(2)) {
                    this.Clicked = false;
                }
                else if (!this.Clicked) {
                    this.Clicked = true;
                    l_Result = this.mc.objectMouseOver;
                    if (l_Result != null && l_Result.typeOfHit == RayTraceResult.Type.ENTITY) {
                        l_Entity = l_Result.entityHit;
                        if (l_Entity != null && l_Entity instanceof EntityPlayer) {
                            if (FriendManager.Get().IsFriend(l_Entity)) {
                                FriendManager.Get().RemoveFriend(l_Entity.getName().toLowerCase());
                                this.SendMessage(String.format("%s has been removed.", l_Entity.getName()));
                            }
                            else {
                                FriendManager.Get().AddFriend(l_Entity.getName().toLowerCase());
                                this.SendMessage(String.format("%s has been added.", l_Entity.getName()));
                            }
                        }
                    }
                }
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
    }
}
