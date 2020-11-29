//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import net.minecraft.init.Blocks;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.liquid.EventLiquidCollisionBB;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class JesusModule extends Module
{
    public final Value<Mode> mode;
    private Timer timer;
    public final Value<Float> offset;
    public final Value<Float> JumpHeight;
    @EventHandler
    private Listener<EventLiquidCollisionBB> OnLiquidCollisionBB;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public JesusModule() {
        super("Jesus", new String[] { "LiquidWalk", "WaterWalk" }, "Allows you to walk on water", "NONE", 5131167, ModuleType.MOVEMENT);
        this.mode = new Value<Mode>("Mode", new String[] { "Mode", "M" }, "The current Jesus/WaterWalk mode to use.", Mode.NCP);
        this.timer = new Timer();
        this.offset = new Value<Float>("Offset", new String[] { "Off", "O" }, "Amount to offset the player into the water's bounding box.", 0.18f, 0.0f, 0.9f, 0.01f);
        this.JumpHeight = new Value<Float>("JumpHeight", new String[] { "J" }, "JumpHeight for trampoline", 1.18f, 0.0f, 50.0f, 1.0f);
        this.OnLiquidCollisionBB = new Listener<EventLiquidCollisionBB>(p_Event -> {
            if (this.mc.world != null && this.mc.player != null && this.checkCollide() && this.mc.player.motionY < 0.10000000149011612 && p_Event.getBlockPos().getY() < this.mc.player.posY - this.offset.getValue()) {
                if (this.mc.player.getRidingEntity() != null) {
                    p_Event.setBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, (double)(1.0f - this.offset.getValue()), 1.0));
                }
                else if (this.mode.getValue() == Mode.BOUNCE) {
                    p_Event.setBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.8999999761581421, 1.0));
                }
                else {
                    p_Event.setBoundingBox(Block.FULL_BLOCK_AABB);
                }
                p_Event.cancel();
            }
            return;
        }, (Predicate<EventLiquidCollisionBB>[])new Predicate[0]);
        CPacketPlayer packet;
        final CPacketPlayer cPacketPlayer;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof CPacketPlayer && this.mode.getValue() != Mode.VANILLA && this.mc.player.getRidingEntity() == null && !this.mc.gameSettings.keyBindJump.isKeyDown()) {
                packet = (CPacketPlayer)p_Event.getPacket();
                if (!this.isInLiquid() && this.isOnLiquid(this.offset.getValue()) && this.checkCollide() && this.mc.player.ticksExisted % 3 == 0) {
                    cPacketPlayer.y -= this.offset.getValue();
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return this.mode.getValue().name();
    }
    
    private boolean checkCollide() {
        return !this.mc.player.isSneaking() && (this.mc.player.getRidingEntity() == null || this.mc.player.getRidingEntity().fallDistance < 3.0f) && this.mc.player.fallDistance < 3.0f;
    }
    
    public boolean isInLiquid() {
        if (this.mc.player.fallDistance >= 3.0f) {
            return false;
        }
        if (this.mc.player != null) {
            boolean inLiquid = false;
            final AxisAlignedBB bb = (this.mc.player.getRidingEntity() != null) ? this.mc.player.getRidingEntity().getEntityBoundingBox() : this.mc.player.getEntityBoundingBox();
            final int y = (int)bb.minY;
            for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; ++x) {
                for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; ++z) {
                    final Block block = this.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (!(block instanceof BlockAir)) {
                        if (!(block instanceof BlockLiquid)) {
                            return false;
                        }
                        inLiquid = true;
                    }
                }
            }
            return inLiquid;
        }
        return false;
    }
    
    public boolean isOnLiquid(final double offset) {
        if (this.mc.player.fallDistance >= 3.0f) {
            return false;
        }
        if (this.mc.player != null) {
            final AxisAlignedBB bb = (this.mc.player.getRidingEntity() != null) ? this.mc.player.getRidingEntity().getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(0.0, -offset, 0.0) : this.mc.player.getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(0.0, -offset, 0.0);
            boolean onLiquid = false;
            final int y = (int)bb.minY;
            for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX + 1.0); ++x) {
                for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ + 1.0); ++z) {
                    final Block block = this.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != Blocks.AIR) {
                        if (!(block instanceof BlockLiquid)) {
                            return false;
                        }
                        onLiquid = true;
                    }
                }
            }
            return onLiquid;
        }
        return false;
    }
    
    private enum Mode
    {
        VANILLA, 
        NCP, 
        BOUNCE;
    }
}
