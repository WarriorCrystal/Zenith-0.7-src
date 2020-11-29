//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.player;

import net.minecraft.world.World;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketUseEntity;
import me.gopro336.goprohack.util.MathUtil;
import java.util.function.Predicate;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.gopro336.goprohack.events.blocks.EventSetOpaqueCube;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMove;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class FreecamModule extends Module
{
    public final Value<Float> speed;
    public final Value<Boolean> CancelPackes;
    public final Value<Modes> Mode;
    private Entity riding;
    private EntityOtherPlayerMP Camera;
    private Vec3d position;
    private float yaw;
    private float pitch;
    @EventHandler
    private Listener<EventPlayerMove> OnPlayerMove;
    @EventHandler
    private Listener<EventSetOpaqueCube> OnEventSetOpaqueCube;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EntityJoinWorldEvent> OnWorldEvent;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public FreecamModule() {
        super("Freecam", new String[] { "OutOfBody" }, "Allows out of body movement", "NONE", -1, ModuleType.PLAYER);
        this.speed = new Value<Float>("Speed", new String[] { "Spd" }, "Speed of freecam flight, higher number equals quicker motion.", 1.0f, 0.0f, 10.0f, 0.1f);
        this.CancelPackes = new Value<Boolean>("Cancel Packets", new String[] { "" }, "Cancels the packets, you won't be able to freely move without this.", true);
        this.Mode = new Value<Modes>("Mode", new String[] { "M" }, "Mode of freecam to use, camera allows you to watch baritone, etc", Modes.Normal);
        this.OnPlayerMove = new Listener<EventPlayerMove>(p_Event -> {
            if (this.Mode.getValue() == Modes.Normal) {
                this.mc.player.noClip = true;
            }
            return;
        }, (Predicate<EventPlayerMove>[])new Predicate[0]);
        this.OnEventSetOpaqueCube = new Listener<EventSetOpaqueCube>(p_Event -> p_Event.cancel(), (Predicate<EventSetOpaqueCube>[])new Predicate[0]);
        double[] dir;
        EntityPlayerSP player;
        EntityPlayerSP player2;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.Mode.getValue() == Modes.Normal) {
                this.mc.player.noClip = true;
                this.mc.player.setVelocity(0.0, 0.0, 0.0);
                dir = MathUtil.directionSpeed(this.speed.getValue());
                if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
                    this.mc.player.motionX = dir[0];
                    this.mc.player.motionZ = dir[1];
                }
                else {
                    this.mc.player.motionX = 0.0;
                    this.mc.player.motionZ = 0.0;
                }
                this.mc.player.setSprinting(false);
                if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                    player = this.mc.player;
                    player.motionY += this.speed.getValue();
                }
                if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    player2 = this.mc.player;
                    player2.motionY -= this.speed.getValue();
                }
            }
            return;
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        this.OnWorldEvent = new Listener<EntityJoinWorldEvent>(p_Event -> {
            if (p_Event.getEntity() == this.mc.player) {
                this.toggle();
            }
            return;
        }, (Predicate<EntityJoinWorldEvent>[])new Predicate[0]);
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (this.Mode.getValue() == Modes.Normal) {
                if (!(!this.CancelPackes.getValue())) {
                    if (p_Event.getPacket() instanceof CPacketUseEntity || p_Event.getPacket() instanceof CPacketPlayerTryUseItem || p_Event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock || p_Event.getPacket() instanceof CPacketPlayer || p_Event.getPacket() instanceof CPacketVehicleMove || p_Event.getPacket() instanceof CPacketChatMessage) {
                        p_Event.cancel();
                    }
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.world == null) {
            return;
        }
        if (this.Mode.getValue() == Modes.Normal) {
            this.riding = null;
            if (this.mc.player.getRidingEntity() != null) {
                this.riding = this.mc.player.getRidingEntity();
                this.mc.player.dismountRidingEntity();
            }
            (this.Camera = new EntityOtherPlayerMP((World)this.mc.world, this.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)this.mc.player);
            this.Camera.prevRotationYaw = this.mc.player.rotationYaw;
            this.Camera.rotationYawHead = this.mc.player.rotationYawHead;
            this.Camera.inventory.copyInventory(this.mc.player.inventory);
            this.mc.world.addEntityToWorld(-69, (Entity)this.Camera);
            this.position = this.mc.player.getPositionVector();
            this.yaw = this.mc.player.rotationYaw;
            this.pitch = this.mc.player.rotationPitch;
            this.mc.player.noClip = true;
        }
        else {
            (this.Camera = new EntityOtherPlayerMP((World)this.mc.world, this.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)this.mc.player);
            this.Camera.prevRotationYaw = this.mc.player.rotationYaw;
            this.Camera.rotationYawHead = this.mc.player.rotationYawHead;
            this.Camera.inventory.copyInventory(this.mc.player.inventory);
            this.Camera.noClip = true;
            this.mc.world.addEntityToWorld(-69, (Entity)this.Camera);
            this.mc.setRenderViewEntity((Entity)this.Camera);
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mc.world != null && this.Mode.getValue() == Modes.Normal) {
            if (this.riding != null) {
                this.mc.player.startRiding(this.riding, true);
                this.riding = null;
            }
            if (this.Camera != null) {
                this.mc.world.removeEntity((Entity)this.Camera);
            }
            if (this.position != null) {
                this.mc.player.setPosition(this.position.x, this.position.y, this.position.z);
            }
            this.mc.player.rotationYaw = this.yaw;
            this.mc.player.rotationPitch = this.pitch;
            this.mc.player.noClip = false;
            this.mc.player.setVelocity(0.0, 0.0, 0.0);
        }
        else if (this.Mode.getValue() == Modes.Camera) {
            if (this.Camera != null) {
                this.mc.world.removeEntity((Entity)this.Camera);
            }
            this.mc.setRenderViewEntity((Entity)this.mc.player);
        }
    }
    
    public enum Modes
    {
        Normal, 
        Camera;
    }
}
