//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.init.MobEffects;
import net.minecraft.entity.Entity;
import java.util.function.Predicate;
import net.minecraft.entity.item.EntityItem;
import me.gopro336.goprohack.events.render.EventRenderBossHealth;
import me.gopro336.goprohack.events.render.EventRenderMap;
import me.gopro336.goprohack.events.render.EventRenderArmorLayer;
import me.gopro336.goprohack.events.render.EventRenderSign;
import me.gopro336.goprohack.events.render.EventRenderUpdateLightmap;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.gopro336.goprohack.events.player.EventPlayerIsPotionActive;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import me.gopro336.goprohack.events.render.EventRenderHurtCameraEffect;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.render.EventRenderEntity;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class NoRenderModule extends Module
{
    public final Value<NoItemsMode> NoItems;
    public final Value<Boolean> Fire;
    public final Value<Boolean> NoHurtCam;
    public final Value<Boolean> PumpkinOverlay;
    public final Value<Boolean> Blindness;
    public final Value<Boolean> TotemAnimation;
    public final Value<Boolean> Skylight;
    public final Value<Boolean> SignText;
    public final Value<Boolean> NoArmor;
    public final Value<Boolean> NoArmorPlayers;
    public final Value<Boolean> Maps;
    public final Value<Boolean> BossHealth;
    private Timer timer;
    @EventHandler
    private Listener<EventRenderEntity> OnRenderEntity;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventRenderHurtCameraEffect> OnHurtCameraEffect;
    @EventHandler
    private Listener<RenderBlockOverlayEvent> OnBlockOverlayEvent;
    @EventHandler
    private Listener<EventPlayerIsPotionActive> IsPotionActive;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    @EventHandler
    private Listener<EventRenderUpdateLightmap> OnSkylightUpdate;
    @EventHandler
    private Listener<EventRenderSign> OnRenderSign;
    @EventHandler
    private Listener<EventRenderArmorLayer> OnRenderArmorLayer;
    @EventHandler
    private Listener<EventRenderMap> OnRenderMap;
    @EventHandler
    private Listener<EventRenderBossHealth> OnRenderBossHealth;
    
    public NoRenderModule() {
        super("NoRender", new String[] { "NR" }, "Doesn't render certain things, if enabled", "NONE", -1, ModuleType.RENDER);
        this.NoItems = new Value<NoItemsMode>("NoItemsMode", new String[] { "NoItems" }, "Prevents items from being rendered", NoItemsMode.Off);
        this.Fire = new Value<Boolean>("Fire", new String[] { "Fire" }, "Doesn't render Fire overlay", true);
        this.NoHurtCam = new Value<Boolean>("HurtCamera", new String[] { "NHC" }, "Doesn't render the Hurt camera", true);
        this.PumpkinOverlay = new Value<Boolean>("PumpkinOverlay", new String[] { "PO" }, "Doesn't render the pumpkin overlay", false);
        this.Blindness = new Value<Boolean>("Blindness", new String[] { "Blindness" }, "Doesn't render the blindness effect", true);
        this.TotemAnimation = new Value<Boolean>("TotemAnimation", new String[] { "TotemAnimation" }, "Doesn't render the totem animation", false);
        this.Skylight = new Value<Boolean>("Skylight", new String[] { "Skylight" }, "Doesn't render skylight updates", false);
        this.SignText = new Value<Boolean>("SignText", new String[] { "SignText" }, "Doesn't render SignText", false);
        this.NoArmor = new Value<Boolean>("NoArmor", new String[] { "NoArmor" }, "Doesn't render armor", false);
        this.NoArmorPlayers = new Value<Boolean>("NoArmorPlayers", new String[] { "NoArmorPlayers" }, "Use in conjunction with the above mod", false);
        this.Maps = new Value<Boolean>("Maps", new String[] { "Maps" }, "Doesn't render maps", false);
        this.BossHealth = new Value<Boolean>("BossHealth", new String[] { "WitherNames" }, "Doesn't render wither names, and other boss health", false);
        this.timer = new Timer();
        this.OnRenderEntity = new Listener<EventRenderEntity>(event -> {
            if (event.GetEntity() instanceof EntityItem && this.NoItems.getValue() == NoItemsMode.Hide) {
                event.cancel();
            }
            return;
        }, (Predicate<EventRenderEntity>[])new Predicate[0]);
        Iterator<Entity> itr;
        Entity entity;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            switch (this.NoItems.getValue()) {
                case Remove: {
                    if (!this.timer.passed(5000.0)) {
                        return;
                    }
                    else {
                        this.timer.reset();
                        itr = this.mc.world.loadedEntityList.iterator();
                        while (itr.hasNext()) {
                            entity = itr.next();
                            if (entity != null && entity instanceof EntityItem) {
                                this.mc.world.removeEntity(entity);
                            }
                        }
                        break;
                    }
                    break;
                }
            }
            return;
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        this.OnHurtCameraEffect = new Listener<EventRenderHurtCameraEffect>(p_Event -> {
            if (this.NoHurtCam.getValue()) {
                p_Event.cancel();
            }
            return;
        }, (Predicate<EventRenderHurtCameraEffect>[])new Predicate[0]);
        this.OnBlockOverlayEvent = new Listener<RenderBlockOverlayEvent>(p_Event -> {
            if (this.Fire.getValue() && p_Event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) {
                p_Event.setCanceled(true);
            }
            if (this.PumpkinOverlay.getValue() && p_Event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.BLOCK) {
                p_Event.setCanceled(true);
            }
            return;
        }, (Predicate<RenderBlockOverlayEvent>[])new Predicate[0]);
        this.IsPotionActive = new Listener<EventPlayerIsPotionActive>(p_Event -> {
            if (p_Event.potion == MobEffects.BLINDNESS && this.Blindness.getValue()) {
                p_Event.cancel();
            }
            return;
        }, (Predicate<EventPlayerIsPotionActive>[])new Predicate[0]);
        SPacketEntityStatus l_Packet;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (this.mc.world == null || this.mc.player == null) {
                return;
            }
            else {
                if (p_Event.getPacket() instanceof SPacketEntityStatus) {
                    l_Packet = (SPacketEntityStatus)p_Event.getPacket();
                    if (l_Packet.getOpCode() == 35 && this.TotemAnimation.getValue()) {
                        p_Event.cancel();
                    }
                }
                return;
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
        this.OnSkylightUpdate = new Listener<EventRenderUpdateLightmap>(p_Event -> {
            if (this.Skylight.getValue()) {
                p_Event.cancel();
            }
            return;
        }, (Predicate<EventRenderUpdateLightmap>[])new Predicate[0]);
        this.OnRenderSign = new Listener<EventRenderSign>(p_Event -> {
            if (this.SignText.getValue()) {
                p_Event.cancel();
            }
            return;
        }, (Predicate<EventRenderSign>[])new Predicate[0]);
        this.OnRenderArmorLayer = new Listener<EventRenderArmorLayer>(p_Event -> {
            if (this.NoArmor.getValue()) {
                if (p_Event.Entity instanceof EntityPlayer || !this.NoArmorPlayers.getValue()) {
                    p_Event.cancel();
                }
            }
            return;
        }, (Predicate<EventRenderArmorLayer>[])new Predicate[0]);
        this.OnRenderMap = new Listener<EventRenderMap>(p_Event -> {
            if (this.Maps.getValue()) {
                p_Event.cancel();
            }
            return;
        }, (Predicate<EventRenderMap>[])new Predicate[0]);
        this.OnRenderBossHealth = new Listener<EventRenderBossHealth>(p_Event -> {
            if (this.BossHealth.getValue()) {
                p_Event.cancel();
            }
        }, (Predicate<EventRenderBossHealth>[])new Predicate[0]);
    }
    
    public enum NoItemsMode
    {
        Off, 
        Remove, 
        Hide;
    }
}
