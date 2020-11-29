//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import net.minecraft.item.ItemBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import java.util.function.Predicate;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.gopro336.goprohack.events.player.EventPlayerMove;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class ScaffoldModule extends Module
{
    public final Value<Modes> Mode;
    public final Value<Boolean> StopMotion;
    public final Value<Float> Delay;
    private Timer _timer;
    private Timer _towerPauseTimer;
    private Timer _towerTimer;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdate;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    @EventHandler
    private Listener<EventPlayerMove> OnPlayerMove;
    
    public ScaffoldModule() {
        super("Scaffold", new String[] { "Scaffold" }, "Places blocks under you", "NONE", 3595044, ModuleType.WORLD);
        this.Mode = new Value<Modes>("Mode", new String[] { "" }, "Tower lets you go up fast when holding space and placing blocks, normal will disable that", Modes.Tower);
        this.StopMotion = new Value<Boolean>("StopMotion", new String[] { "" }, "Stops you from moving if the block isn't placed yet", true);
        this.Delay = new Value<Float>("Delay", new String[] { "Delay" }, "Delay of the place", 0.0f, 0.0f, 1.0f, 0.1f);
        this._timer = new Timer();
        this._towerPauseTimer = new Timer();
        this._towerTimer = new Timer();
        ItemStack stack;
        int prevSlot;
        int i;
        BlockPos toPlaceAt;
        BlockPos feetBlock;
        boolean placeAtFeet;
        float towerMotion;
        BlockInteractionHelper.ValidResult result;
        BlockPos[] array;
        BlockPos toSelect;
        double lastDistance;
        final BlockPos[] array2;
        int length;
        int j = 0;
        BlockPos pos;
        double dist;
        Vec3d eyesPos;
        final EnumFacing[] array3;
        int length2;
        int k = 0;
        EnumFacing side;
        BlockPos neighbor;
        EnumFacing side2;
        Vec3d hitVec;
        float[] rotations;
        this.onMotionUpdate = new Listener<EventPlayerMotionUpdate>(event -> {
            if (event.isCancelled()) {
                return;
            }
            else if (event.getEra() != MinecraftEvent.Era.PRE) {
                return;
            }
            else if (!this._timer.passed(this.Delay.getValue() * 1000.0f)) {
                return;
            }
            else {
                stack = this.mc.player.getHeldItemMainhand();
                prevSlot = -1;
                if (!this.verifyStack(stack)) {
                    i = 0;
                    while (i < 9) {
                        stack = this.mc.player.inventory.getStackInSlot(i);
                        if (this.verifyStack(stack)) {
                            prevSlot = this.mc.player.inventory.currentItem;
                            this.mc.player.inventory.currentItem = i;
                            this.mc.playerController.updateController();
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                }
                if (!this.verifyStack(stack)) {
                    return;
                }
                else {
                    this._timer.reset();
                    toPlaceAt = null;
                    feetBlock = PlayerUtil.GetLocalPlayerPosFloored().down();
                    placeAtFeet = this.isValidPlaceBlockState(feetBlock);
                    if (this.Mode.getValue() == Modes.Tower && placeAtFeet && this.mc.player.movementInput.jump && this._towerTimer.passed(250.0) && !this.mc.player.isElytraFlying()) {
                        if (this._towerPauseTimer.passed(1500.0)) {
                            this._towerPauseTimer.reset();
                            this.mc.player.motionY = -0.2800000011920929;
                        }
                        else {
                            towerMotion = 0.42f;
                            this.mc.player.setVelocity(0.0, 0.41999998688697815, 0.0);
                        }
                    }
                    if (placeAtFeet) {
                        toPlaceAt = feetBlock;
                    }
                    else {
                        result = BlockInteractionHelper.valid(feetBlock);
                        if (result != BlockInteractionHelper.ValidResult.Ok && result != BlockInteractionHelper.ValidResult.AlreadyBlockThere) {
                            array = new BlockPos[] { feetBlock.north(), feetBlock.south(), feetBlock.east(), feetBlock.west() };
                            toSelect = null;
                            lastDistance = 420.0;
                            for (length = array2.length; j < length; ++j) {
                                pos = array2[j];
                                if (!(!this.isValidPlaceBlockState(pos))) {
                                    dist = pos.getDistance((int)this.mc.player.posX, (int)this.mc.player.posY, (int)this.mc.player.posZ);
                                    if (lastDistance > dist) {
                                        lastDistance = dist;
                                        toSelect = pos;
                                    }
                                }
                            }
                            if (toSelect != null) {
                                toPlaceAt = toSelect;
                            }
                        }
                    }
                    if (toPlaceAt != null) {
                        eyesPos = new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ);
                        EnumFacing.values();
                        for (length2 = array3.length; k < length2; ++k) {
                            side = array3[k];
                            neighbor = toPlaceAt.offset(side);
                            side2 = side.getOpposite();
                            if (this.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(this.mc.world.getBlockState(neighbor), false)) {
                                hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                                if (eyesPos.distanceTo(hitVec) <= 5.0) {
                                    rotations = BlockInteractionHelper.getFacingRotations(toPlaceAt.getX(), toPlaceAt.getY(), toPlaceAt.getZ(), side);
                                    event.cancel();
                                    PlayerUtil.PacketFacePitchAndYaw(rotations[1], rotations[0]);
                                    break;
                                }
                            }
                        }
                        if (BlockInteractionHelper.place(toPlaceAt, 5.0f, false, false, true) == BlockInteractionHelper.PlaceResult.Placed) {}
                    }
                    else {
                        this._towerPauseTimer.reset();
                    }
                    if (prevSlot != -1) {
                        this.mc.player.inventory.currentItem = prevSlot;
                        this.mc.playerController.updateController();
                    }
                    return;
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(event -> {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                this._towerTimer.reset();
            }
            return;
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
        double x;
        double y;
        double z;
        double increment;
        this.OnPlayerMove = new Listener<EventPlayerMove>(p_Event -> {
            if (!(!this.StopMotion.getValue())) {
                x = p_Event.X;
                y = p_Event.Y;
                z = p_Event.Z;
                if (this.mc.player.onGround && !this.mc.player.noClip) {
                    increment = 0.05;
                    while (x != 0.0 && this.isOffsetBBEmpty(x, -1.0, 0.0)) {
                        if (x < increment && x >= -increment) {
                            x = 0.0;
                        }
                        else if (x > 0.0) {
                            x -= increment;
                        }
                        else {
                            x += increment;
                        }
                    }
                    while (z != 0.0 && this.isOffsetBBEmpty(0.0, -1.0, z)) {
                        if (z < increment && z >= -increment) {
                            z = 0.0;
                        }
                        else if (z > 0.0) {
                            z -= increment;
                        }
                        else {
                            z += increment;
                        }
                    }
                    while (x != 0.0 && z != 0.0 && this.isOffsetBBEmpty(x, -1.0, z)) {
                        if (x < increment && x >= -increment) {
                            x = 0.0;
                        }
                        else if (x > 0.0) {
                            x -= increment;
                        }
                        else {
                            x += increment;
                        }
                        if (z < increment && z >= -increment) {
                            z = 0.0;
                        }
                        else if (z > 0.0) {
                            z -= increment;
                        }
                        else {
                            z += increment;
                        }
                    }
                }
                p_Event.X = x;
                p_Event.Y = y;
                p_Event.Z = z;
                p_Event.cancel();
            }
        }, (Predicate<EventPlayerMove>[])new Predicate[0]);
    }
    
    private boolean isOffsetBBEmpty(final double x, final double y, final double z) {
        return this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(x, y, z)).isEmpty();
    }
    
    private boolean isValidPlaceBlockState(final BlockPos pos) {
        final BlockInteractionHelper.ValidResult result = BlockInteractionHelper.valid(pos);
        if (result == BlockInteractionHelper.ValidResult.AlreadyBlockThere) {
            return this.mc.world.getBlockState(pos).getMaterial().isReplaceable();
        }
        return result == BlockInteractionHelper.ValidResult.Ok;
    }
    
    private boolean verifyStack(final ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof ItemBlock;
    }
    
    public enum Modes
    {
        Tower, 
        Normal;
    }
}
