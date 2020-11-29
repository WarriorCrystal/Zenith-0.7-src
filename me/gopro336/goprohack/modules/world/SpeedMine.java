//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import me.gopro336.goprohack.events.player.EventPlayerDamageBlock;
import me.gopro336.goprohack.events.player.EventPlayerClickBlock;
import me.gopro336.goprohack.events.player.EventPlayerResetBlockRemoving;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class SpeedMine extends Module
{
    public final Value<Mode> mode;
    public final Value<Float> Speed;
    public final Value<Boolean> reset;
    public final Value<Boolean> doubleBreak;
    public final Value<Boolean> FastFall;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerResetBlockRemoving> ResetBlock;
    @EventHandler
    private Listener<EventPlayerClickBlock> ClickBlock;
    @EventHandler
    private Listener<EventPlayerDamageBlock> OnDamageBlock;
    
    public SpeedMine() {
        super("SpeedMine", new String[] { "SpeedMine" }, "Allows you to break blocks faster", "NONE", 5131167, ModuleType.WORLD);
        this.mode = new Value<Mode>("Mode", new String[] { "Mode", "M" }, "The speed-mine mode to use.", Mode.Instant);
        this.Speed = new Value<Float>("Speed", new String[] { "S" }, "Speed for Bypass Mode", 1.0f, 0.0f, 1.0f, 0.1f);
        this.reset = new Value<Boolean>("Reset", new String[] { "Res" }, "Stops current block destroy damage from resetting if enabled.", true);
        this.doubleBreak = new Value<Boolean>("DoubleBreak", new String[] { "DoubleBreak", "Double", "DB" }, "Mining a block will also mine the block above it, if enabled.", false);
        this.FastFall = new Value<Boolean>("FastFall", new String[] { "FF" }, "Makes it so you fall faster.", false);
        EntityPlayerSP player;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            this.mc.playerController.blockHitDelay = 0;
            if (this.reset.getValue() && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown()) {
                this.mc.playerController.isHittingBlock = false;
            }
            if (this.FastFall.getValue() && this.mc.player.onGround) {
                player = this.mc.player;
                --player.motionY;
            }
            return;
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        this.ResetBlock = new Listener<EventPlayerResetBlockRemoving>(p_Event -> {
            if (this.reset.getValue()) {
                p_Event.cancel();
            }
            return;
        }, (Predicate<EventPlayerResetBlockRemoving>[])new Predicate[0]);
        this.ClickBlock = new Listener<EventPlayerClickBlock>(p_Event -> {
            if (this.reset.getValue() && this.mc.playerController.curBlockDamageMP > 0.1f) {
                this.mc.playerController.isHittingBlock = true;
            }
            return;
        }, (Predicate<EventPlayerClickBlock>[])new Predicate[0]);
        IBlockState blockState;
        float n;
        BlockPos above;
        this.OnDamageBlock = new Listener<EventPlayerDamageBlock>(p_Event -> {
            if (this.canBreak(p_Event.getPos())) {
                if (this.reset.getValue()) {
                    this.mc.playerController.isHittingBlock = false;
                }
                switch (this.mode.getValue()) {
                    case Packet: {
                        this.mc.player.swingArm(EnumHand.MAIN_HAND);
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, p_Event.getPos(), p_Event.getDirection()));
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, p_Event.getPos(), p_Event.getDirection()));
                        p_Event.cancel();
                        break;
                    }
                    case Damage: {
                        if (this.mc.playerController.curBlockDamageMP >= 0.7f) {
                            this.mc.playerController.curBlockDamageMP = 1.0f;
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Instant: {
                        this.mc.player.swingArm(EnumHand.MAIN_HAND);
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, p_Event.getPos(), p_Event.getDirection()));
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, p_Event.getPos(), p_Event.getDirection()));
                        this.mc.playerController.onPlayerDestroyBlock(p_Event.getPos());
                        this.mc.world.setBlockToAir(p_Event.getPos());
                        break;
                    }
                    case Bypass: {
                        this.mc.player.swingArm(EnumHand.MAIN_HAND);
                        blockState = Minecraft.getMinecraft().world.getBlockState(p_Event.getPos());
                        n = blockState.getPlayerRelativeBlockHardness((EntityPlayer)this.mc.player, (World)this.mc.world, p_Event.getPos()) * this.Speed.getValue();
                        break;
                    }
                }
            }
            if (this.doubleBreak.getValue()) {
                above = p_Event.getPos().add(0, 1, 0);
                if (this.canBreak(above) && this.mc.player.getDistance((double)above.getX(), (double)above.getY(), (double)above.getZ()) <= 5.0) {
                    this.mc.player.swingArm(EnumHand.MAIN_HAND);
                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, above, p_Event.getDirection()));
                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, above, p_Event.getDirection()));
                    this.mc.playerController.onPlayerDestroyBlock(above);
                    this.mc.world.setBlockToAir(above);
                }
            }
        }, (Predicate<EventPlayerDamageBlock>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return this.mode.getValue().name();
    }
    
    private boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = this.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)Minecraft.getMinecraft().world, pos) != -1.0f;
    }
    
    private enum Mode
    {
        Packet, 
        Damage, 
        Instant, 
        Bypass;
    }
}
