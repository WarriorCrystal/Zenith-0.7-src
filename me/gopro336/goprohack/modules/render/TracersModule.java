//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import me.gopro336.goprohack.managers.FriendManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityBoat;
import me.gopro336.goprohack.util.entity.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Iterator;
import java.util.function.Predicate;
import me.gopro336.goprohack.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import me.gopro336.goprohack.util.MathUtil;
import net.minecraft.entity.Entity;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class TracersModule extends Module
{
    public final Value<Boolean> Players;
    public final Value<Boolean> Friends;
    public final Value<Boolean> Invisibles;
    public final Value<Boolean> Monsters;
    public final Value<Boolean> Animals;
    public final Value<Boolean> Vehicles;
    public final Value<Boolean> Items;
    public final Value<Boolean> Others;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public TracersModule() {
        super("Tracers", new String[] { "Tracers" }, "Draws tracer to a given entity", "NONE", -1, ModuleType.RENDER);
        this.Players = new Value<Boolean>("Players", new String[] { "Players" }, "Traces players", true);
        this.Friends = new Value<Boolean>("Friends", new String[] { "Friends" }, "Traces friends", true);
        this.Invisibles = new Value<Boolean>("Invisibles", new String[] { "Invisibles" }, "Traces invisibles", true);
        this.Monsters = new Value<Boolean>("Monsters", new String[] { "Monsters" }, "Traces monsters", false);
        this.Animals = new Value<Boolean>("Animals", new String[] { "Animals" }, "Traces animals", false);
        this.Vehicles = new Value<Boolean>("Vehicles", new String[] { "Vehicles" }, "Traces Vehicles", false);
        this.Items = new Value<Boolean>("Items", new String[] { "Items" }, "Traces items", true);
        this.Others = new Value<Boolean>("Others", new String[] { "Others" }, "Traces others", false);
        final Iterator<Entity> iterator;
        Entity entity;
        Vec3d pos;
        boolean bobbing;
        Vec3d forward;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() != null && this.mc.getRenderManager().options != null) {
                this.mc.world.loadedEntityList.iterator();
                while (iterator.hasNext()) {
                    entity = iterator.next();
                    if (this.shouldRenderTracer(entity)) {
                        pos = MathUtil.interpolateEntity(entity, p_Event.getPartialTicks()).subtract(this.mc.getRenderManager().renderPosX, this.mc.getRenderManager().renderPosY, this.mc.getRenderManager().renderPosZ);
                        if (pos != null) {
                            bobbing = this.mc.gameSettings.viewBobbing;
                            this.mc.gameSettings.viewBobbing = false;
                            this.mc.entityRenderer.setupCameraTransform(p_Event.getPartialTicks(), 0);
                            forward = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Minecraft.getMinecraft().player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Minecraft.getMinecraft().player.rotationYaw));
                            RenderUtil.drawLine3D((float)forward.x, (float)forward.y + this.mc.player.getEyeHeight(), (float)forward.z, (float)pos.x, (float)pos.y, (float)pos.z, 0.5f, this.getColor(entity));
                            this.mc.gameSettings.viewBobbing = bobbing;
                            this.mc.entityRenderer.setupCameraTransform(p_Event.getPartialTicks(), 0);
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }, (Predicate<RenderEvent>[])new Predicate[0]);
    }
    
    public boolean shouldRenderTracer(final Entity e) {
        if (e == Minecraft.getMinecraft().player) {
            return false;
        }
        if (e instanceof EntityPlayer) {
            return this.Players.getValue();
        }
        if (EntityUtil.isHostileMob(e) || EntityUtil.isNeutralMob(e)) {
            return this.Monsters.getValue();
        }
        if (EntityUtil.isPassive(e)) {
            return this.Animals.getValue();
        }
        if (e instanceof EntityBoat || e instanceof EntityMinecart) {
            return this.Vehicles.getValue();
        }
        if (e instanceof EntityItem) {
            return this.Items.getValue();
        }
        return this.Others.getValue();
    }
    
    private int getColor(final Entity e) {
        if (e instanceof EntityPlayer) {
            if (FriendManager.Get().IsFriend(e)) {
                return -16711698;
            }
            return -16711936;
        }
        else {
            if (e.isInvisible()) {
                return -16777216;
            }
            if (EntityUtil.isHostileMob(e) || EntityUtil.isNeutralMob(e)) {
                return -65536;
            }
            if (EntityUtil.isPassive(e)) {
                return -29440;
            }
            if (e instanceof EntityBoat || e instanceof EntityMinecart) {
                return -256;
            }
            if (e instanceof EntityItem) {
                return -5635841;
            }
            return -1;
        }
    }
}
