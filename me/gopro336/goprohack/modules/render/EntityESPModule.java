//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.modules.ValueListeners;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import me.gopro336.goprohack.util.entity.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import java.util.function.Predicate;
import me.gopro336.goprohack.util.render.ESPUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import me.gopro336.goprohack.events.entity.EventEntityRemoved;
import me.gopro336.goprohack.events.entity.EventEntityAdded;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.culling.ICamera;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class EntityESPModule extends Module
{
    public final Value<ESPMode> Mode;
    public final Value<Boolean> Players;
    public final Value<Boolean> Monsters;
    public final Value<Boolean> Animals;
    public final Value<Boolean> Vehicles;
    public final Value<Boolean> Others;
    public final Value<Boolean> Items;
    public final Value<Boolean> Tamed;
    private ICamera camera;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    @EventHandler
    private Listener<EventEntityAdded> OnEntityAdded;
    @EventHandler
    private Listener<EventEntityRemoved> OnEntityRemove;
    
    public EntityESPModule() {
        super("EntityESP", new String[] { "" }, "Highlights different kind of storages", "NONE", -1, ModuleType.RENDER);
        this.Mode = new Value<ESPMode>("Mode", new String[] { "ESPMode" }, "Mode of rendering to use for ESP", ESPMode.Shader);
        this.Players = new Value<Boolean>("Players", new String[] { "Players" }, "Highlights players", true);
        this.Monsters = new Value<Boolean>("Monsters", new String[] { "Monsters" }, "Highlights Monsters", false);
        this.Animals = new Value<Boolean>("Animals", new String[] { "Animals" }, "Highlights Animals", false);
        this.Vehicles = new Value<Boolean>("Vehicles", new String[] { "Vehicles" }, "Highlights Vehicles", false);
        this.Others = new Value<Boolean>("Others", new String[] { "Others" }, "Highlights Others", false);
        this.Items = new Value<Boolean>("Items", new String[] { "Items" }, "Highlights Items", false);
        this.Tamed = new Value<Boolean>("Tamed", new String[] { "Tamed" }, "Highlights Tamed", false);
        this.camera = (ICamera)new Frustum();
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() == null || this.mc.getRenderManager().options == null) {
                return;
            }
            else {
                GlStateManager.pushMatrix();
                switch (this.Mode.getValue()) {
                    case CSGO: {
                        ESPUtil.RenderCSGO(this.camera, this, p_Event);
                        break;
                    }
                }
                GlStateManager.popMatrix();
                return;
            }
        }, (Predicate<RenderEvent>[])new Predicate[0]);
        boolean l_SetGlowing;
        this.OnEntityAdded = new Listener<EventEntityAdded>(p_Event -> {
            if (this.Mode.getValue() != ESPMode.Shader) {
                return;
            }
            else {
                l_SetGlowing = false;
                if (p_Event.GetEntity() instanceof EntityPlayer && this.Players.getValue()) {
                    l_SetGlowing = true;
                }
                if (EntityUtil.isFriendlyMob(p_Event.GetEntity()) && this.Animals.getValue()) {
                    l_SetGlowing = true;
                }
                if (EntityUtil.isHostileMob(p_Event.GetEntity()) && this.Monsters.getValue()) {
                    l_SetGlowing = true;
                }
                if (EntityUtil.IsVehicle(p_Event.GetEntity()) && this.Vehicles.getValue()) {
                    l_SetGlowing = true;
                }
                if (p_Event.GetEntity() instanceof EntityItem && this.Items.getValue()) {
                    l_SetGlowing = true;
                }
                if (p_Event.GetEntity() instanceof EntityEnderCrystal && this.Others.getValue()) {
                    l_SetGlowing = true;
                }
                p_Event.GetEntity().setGlowing(l_SetGlowing);
                return;
            }
        }, (Predicate<EventEntityAdded>[])new Predicate[0]);
        this.OnEntityRemove = new Listener<EventEntityRemoved>(p_Event -> p_Event.GetEntity().setGlowing(false), (Predicate<EventEntityRemoved>[])new Predicate[0]);
        this.Mode.Listener = new ValueListeners() {
            @Override
            public void OnValueChange(final Value p_Val) {
                if (EntityESPModule.this.mc.world == null) {
                    return;
                }
                if (p_Val.getValue() == ESPMode.Outline) {
                    Module.this.SendMessage("Outline is not yet implemented!");
                }
                EntityESPModule.this.UpdateShaders();
            }
        };
        final ValueListeners l_Listener = new ValueListeners() {
            @Override
            public void OnValueChange(final Value p_Val) {
                if (EntityESPModule.this.mc.world == null) {
                    return;
                }
                if (EntityESPModule.this.Mode.getValue() == ESPMode.Shader) {
                    EntityESPModule.this.UpdateShaders();
                }
            }
        };
        this.Players.Listener = l_Listener;
        this.Monsters.Listener = l_Listener;
        this.Animals.Listener = l_Listener;
        this.Vehicles.Listener = l_Listener;
        this.Others.Listener = l_Listener;
        this.Items.Listener = l_Listener;
        this.Tamed.Listener = l_Listener;
    }
    
    private void UpdateShaders() {
        boolean l_SetGlowing;
        this.mc.world.loadedEntityList.forEach(p_Entity -> {
            try {
                if (p_Entity != null) {
                    l_SetGlowing = false;
                    if (this.Mode.getValue() == ESPMode.Shader) {
                        if (p_Entity instanceof EntityPlayer && this.Players.getValue()) {
                            l_SetGlowing = true;
                        }
                        if (EntityUtil.isFriendlyMob(p_Entity) && this.Animals.getValue()) {
                            l_SetGlowing = true;
                        }
                        if (EntityUtil.isHostileMob(p_Entity) && this.Monsters.getValue()) {
                            l_SetGlowing = true;
                        }
                        if (EntityUtil.IsVehicle(p_Entity) && this.Vehicles.getValue()) {
                            l_SetGlowing = true;
                        }
                        if (p_Entity instanceof EntityItem && this.Items.getValue()) {
                            l_SetGlowing = true;
                        }
                        if (p_Entity instanceof EntityEnderCrystal && this.Others.getValue()) {
                            l_SetGlowing = true;
                        }
                    }
                    p_Entity.setGlowing(l_SetGlowing);
                }
            }
            catch (Exception ex) {}
        });
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.world.loadedEntityList.forEach(p_Entity -> {
            if (p_Entity != null) {
                p_Entity.setGlowing(false);
            }
        });
    }
    
    private enum ESPMode
    {
        None, 
        Outline, 
        CSGO, 
        Shader;
    }
}
