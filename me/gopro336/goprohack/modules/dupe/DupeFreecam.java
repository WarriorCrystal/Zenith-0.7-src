//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.dupe;

import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.List;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import me.gopro336.goprohack.modules.Module;

public final class DupeFreecam extends Module
{
    private static Vec3d pos;
    private static Vec2f pitchyaw;
    private static boolean isRidingEntity;
    public static boolean enabled;
    private static Entity ridingEntity;
    private static EntityOtherPlayerMP originalPlayer;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    private List<CPacketPlayer> PacketsToIgnore;
    
    public DupeFreecam() {
        super("DupeFreecam", new String[] { "DupeFreecam" }, "DupeFreecam", "NONE", 14361655, ModuleType.DUPE);
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            this.mc.addScheduledTask(() -> {
                if (this.mc.player == null || this.mc.player.capabilities == null) {
                    return;
                }
                else {
                    this.mc.player.capabilities.allowFlying = true;
                    this.mc.player.capabilities.isFlying = true;
                    return;
                }
            });
            this.mc.player.capabilities.setFlySpeed(0.5f);
            this.mc.player.noClip = true;
            this.mc.player.onGround = false;
            this.mc.player.fallDistance = 0.0f;
            if (!this.mc.gameSettings.keyBindForward.isPressed() && !this.mc.gameSettings.keyBindBack.isPressed() && !this.mc.gameSettings.keyBindLeft.isPressed() && !this.mc.gameSettings.keyBindRight.isPressed() && !this.mc.gameSettings.keyBindJump.isPressed() && !this.mc.gameSettings.keyBindSneak.isPressed()) {
                this.mc.player.setVelocity(0.0, 0.0, 0.0);
            }
            return;
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        this.PacketsToIgnore = new ArrayList<CPacketPlayer>();
    }
    
    @Override
    public void onEnable() {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        super.onEnable();
        DupeFreecam.enabled = true;
        if (DupeFreecam.isRidingEntity = this.mc.player.isRiding()) {
            DupeFreecam.ridingEntity = this.mc.player.getRidingEntity();
            this.mc.player.dismountRidingEntity();
        }
        DupeFreecam.pos = this.mc.player.getPositionVector();
        DupeFreecam.pitchyaw = this.mc.player.getPitchYaw();
        (DupeFreecam.originalPlayer = new EntityOtherPlayerMP((World)this.mc.world, this.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)this.mc.player);
        DupeFreecam.originalPlayer.rotationYawHead = this.mc.player.rotationYawHead;
        DupeFreecam.originalPlayer.inventory = this.mc.player.inventory;
        DupeFreecam.originalPlayer.inventoryContainer = this.mc.player.inventoryContainer;
        this.mc.world.addEntityToWorld(-100, (Entity)DupeFreecam.originalPlayer);
        if (DupeFreecam.isRidingEntity) {
            DupeFreecam.originalPlayer.startRiding(DupeFreecam.ridingEntity, true);
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.PacketsToIgnore.clear();
        final EntityPlayerSP entityPlayerSP;
        PlayerCapabilities gmCaps;
        PlayerCapabilities capabilities;
        this.mc.addScheduledTask(() -> {
            entityPlayerSP = this.mc.player;
            if (entityPlayerSP == null || entityPlayerSP.capabilities == null) {
                return;
            }
            else {
                gmCaps = new PlayerCapabilities();
                this.mc.playerController.getCurrentGameType().configurePlayerCapabilities(gmCaps);
                capabilities = entityPlayerSP.capabilities;
                capabilities.allowFlying = gmCaps.allowFlying;
                capabilities.isFlying = (gmCaps.allowFlying && capabilities.isFlying);
                capabilities.setFlySpeed(gmCaps.getFlySpeed());
                return;
            }
        });
        if (this.mc.player == null || DupeFreecam.originalPlayer == null) {
            return;
        }
        DupeFreecam.enabled = false;
        DupeFreecam.originalPlayer.dismountRidingEntity();
        this.mc.world.removeEntityFromWorld(-100);
        DupeFreecam.originalPlayer = null;
        this.mc.player.noClip = false;
        this.mc.player.setVelocity(0.0, 0.0, 0.0);
        if (DupeFreecam.isRidingEntity) {
            this.mc.player.startRiding(DupeFreecam.ridingEntity, true);
            DupeFreecam.ridingEntity = null;
            DupeFreecam.isRidingEntity = false;
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        if (!DupeFreecam.enabled || DupeFreecam.originalPlayer == null || this.mc.player == null) {
            return;
        }
        DupeFreecam.pos = this.mc.player.getPositionVector();
    }
    
    @SubscribeEvent
    public void onEntityRender(final RenderLivingEvent.Pre<?> event) {
        if (DupeFreecam.originalPlayer != null && this.mc.player != null && this.mc.player.equals((Object)event.getEntity())) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onRenderTag(final RenderLivingEvent.Specials.Pre<?> event) {
        if (DupeFreecam.originalPlayer != null && this.mc.player != null && this.mc.player.equals((Object)event.getEntity())) {
            event.setCanceled(true);
        }
    }
    
    static {
        DupeFreecam.pos = Vec3d.ZERO;
        DupeFreecam.pitchyaw = Vec2f.ZERO;
        DupeFreecam.enabled = false;
    }
}
