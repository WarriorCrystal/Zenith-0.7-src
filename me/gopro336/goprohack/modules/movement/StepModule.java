//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import me.gopro336.goprohack.events.MinecraftEvent;
import java.util.function.Predicate;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdateMoveState;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class StepModule extends Module
{
    public final Value<Modes> Mode;
    public final Value<Boolean> EntityStep;
    public final Value<Float> Height;
    private double previousX;
    private double previousY;
    private double previousZ;
    private double offsetX;
    private double offsetY;
    private double offsetZ;
    private double frozenX;
    private double frozenZ;
    private byte cancelStage;
    private float _prevEntityStep;
    @EventHandler
    private Listener<EventPlayerUpdateMoveState> onInputUpdate;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnMotionUpdate;
    
    public StepModule() {
        super("Step", new String[] { "Spider", "NCPStep", "Stairstep" }, "Allows you to walk up blocks like stairs", "NONE", -1, ModuleType.MOVEMENT);
        this.Mode = new Value<Modes>("Mode", new String[] { "M" }, "The mode used for step on different types of servers", Modes.Normal);
        this.EntityStep = new Value<Boolean>("EntityStep", new String[] { "ES" }, "Modifies your riding entity to max step height", false);
        this.Height = new Value<Float>("Height", new String[] { "H" }, "Modifier of height", 1.0f, 0.0f, 10.0f, 1.0f);
        this.onInputUpdate = new Listener<EventPlayerUpdateMoveState>(event -> {
            if (this.cancelStage != 0 && this.Mode.getValue() == Modes.AAC) {
                this.mc.player.movementInput.jump = false;
            }
            if (this.EntityStep.getValue() && this.mc.player.isRiding()) {
                this.mc.player.getRidingEntity().stepHeight = 256.0f;
            }
            return;
        }, (Predicate<EventPlayerUpdateMoveState>[])new Predicate[0]);
        double yDist;
        double hDistSq;
        EntityPlayerSP player;
        EntityPlayerSP player2;
        EntityPlayerSP player3;
        EntityPlayerSP player4;
        AxisAlignedBB box;
        double stepHeight;
        final Iterator<AxisAlignedBB> iterator;
        AxisAlignedBB bb;
        double stepHeight2;
        this.OnMotionUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            switch (this.Mode.getValue()) {
                case AAC: {
                    if (p_Event.getEra() == MinecraftEvent.Era.PRE) {
                        this.offsetX = 0.0;
                        this.offsetY = 0.0;
                        this.offsetZ = 0.0;
                        this.mc.player.stepHeight = ((this.mc.player.onGround && this.mc.player.collidedHorizontally && this.cancelStage == 0 && this.mc.player.posY % 1.0 == 0.0) ? 1.1f : 0.5f);
                        if (this.cancelStage == -1) {
                            this.cancelStage = 0;
                            return;
                        }
                        else {
                            yDist = this.mc.player.posY - this.previousY;
                            hDistSq = (this.mc.player.posX - this.previousX) * (this.mc.player.posX - this.previousX) + (this.mc.player.posZ - this.previousZ) * (this.mc.player.posZ - this.previousZ);
                            if (yDist > 0.5 && yDist < 1.05 && hDistSq < 1.0 && this.cancelStage == 0) {
                                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.previousX, this.previousY + 0.42, this.previousZ, false));
                                this.offsetX = this.previousX - this.mc.player.posX;
                                this.offsetY = 0.755 - yDist;
                                this.offsetZ = this.previousZ - this.mc.player.posZ;
                                this.frozenX = this.previousX;
                                this.frozenZ = this.previousZ;
                                this.mc.player.stepHeight = 1.05f;
                                this.cancelStage = 1;
                            }
                            switch (this.cancelStage) {
                                case 1: {
                                    this.cancelStage = 2;
                                    this.mc.player.setEntityBoundingBox(this.mc.player.getEntityBoundingBox().offset(this.frozenX - this.mc.player.posX, 0.0, this.frozenZ - this.mc.player.posZ));
                                    break;
                                }
                                case 2: {
                                    p_Event.cancel();
                                    this.cancelStage = -1;
                                    break;
                                }
                            }
                            this.previousX = this.mc.player.posX;
                            this.previousY = this.mc.player.posY;
                            this.previousZ = this.mc.player.posZ;
                            if (this.offsetX != 0.0 || this.offsetY != 0.0 || this.offsetZ != 0.0) {
                                player = this.mc.player;
                                player.posX += this.offsetX;
                                this.mc.player.setEntityBoundingBox(this.mc.player.getEntityBoundingBox().offset(0.0, this.offsetY, 0.0));
                                player2 = this.mc.player;
                                player2.posZ += this.offsetZ;
                            }
                            break;
                        }
                    }
                    else if (this.offsetX != 0.0 || this.offsetY != 0.0 || this.offsetZ != 0.0) {
                        player3 = this.mc.player;
                        player3.posX -= this.offsetX;
                        this.mc.player.setEntityBoundingBox(this.mc.player.getEntityBoundingBox().offset(0.0, -this.offsetY, 0.0));
                        player4 = this.mc.player;
                        player4.posZ -= this.offsetZ;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case Normal: {
                    if (p_Event.getEra() != MinecraftEvent.Era.PRE) {
                        return;
                    }
                    else if (this.mc.player.collidedHorizontally && this.mc.player.onGround && this.mc.player.fallDistance == 0.0f && !this.mc.player.isInWeb && !this.mc.player.isOnLadder() && !this.mc.player.movementInput.jump) {
                        box = this.mc.player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).grow(0.05);
                        if (!this.mc.world.getCollisionBoxes((Entity)this.mc.player, box.offset(0.0, 1.0, 0.0)).isEmpty()) {
                            return;
                        }
                        else {
                            stepHeight = -1.0;
                            this.mc.world.getCollisionBoxes((Entity)this.mc.player, box).iterator();
                            while (iterator.hasNext()) {
                                bb = iterator.next();
                                if (bb.maxY > stepHeight) {
                                    stepHeight = bb.maxY;
                                }
                            }
                            stepHeight2 = stepHeight - this.mc.player.posY;
                            if (stepHeight2 < 0.0 || stepHeight2 > 1.0) {
                                return;
                            }
                            else {
                                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.42, this.mc.player.posZ, this.mc.player.onGround));
                                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.75, this.mc.player.posZ, this.mc.player.onGround));
                                this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + 1.0, this.mc.player.posZ);
                                break;
                            }
                        }
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.cancelStage = 0;
        if (this.mc.player != null && this.mc.player.isRiding()) {
            this._prevEntityStep = this.mc.player.getRidingEntity().stepHeight;
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.player.stepHeight = 0.5f;
        if (this.mc.player.isRiding()) {
            this.mc.player.getRidingEntity().stepHeight = this._prevEntityStep;
        }
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    public enum Modes
    {
        Normal, 
        AAC;
    }
}
