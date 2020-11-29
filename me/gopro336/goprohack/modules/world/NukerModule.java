//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import java.util.Iterator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import me.gopro336.goprohack.util.entity.EntityUtil;
import me.gopro336.goprohack.managers.BlockManager;
import me.gopro336.goprohack.events.MinecraftEvent;
import java.util.function.Predicate;
import net.minecraft.init.Blocks;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerClickBlock;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class NukerModule extends Module
{
    public final Value<Modes> Mode;
    public final Value<Boolean> ClickSelect;
    public final Value<Boolean> Flatten;
    public final Value<Boolean> Rotates;
    public final Value<Boolean> Raytrace;
    public final Value<Float> Range;
    private Block _clickSelectBlock;
    @EventHandler
    private Listener<EventPlayerClickBlock> onClickBlock;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onPlayerUpdate;
    
    public NukerModule() {
        super("Nuker", new String[] { "Nukes" }, "Destroys blocks in a radius around you", "NONE", -1, ModuleType.WORLD);
        this.Mode = new Value<Modes>("Mode", new String[] { "M" }, "Mode of breaking to use, Creative will get you kicked on most servers.", Modes.Survival);
        this.ClickSelect = new Value<Boolean>("Click Select", new String[] { "CS" }, "Click blocks you want nuker to only target", false);
        this.Flatten = new Value<Boolean>("Flatten", new String[] { "F" }, "Flattens at your feet", false);
        this.Rotates = new Value<Boolean>("Rotates", new String[] { "R" }, "Rotates towards selected blocks, you won't bypass NCP without this", true);
        this.Raytrace = new Value<Boolean>("Raytrace", new String[] { "Ray" }, "Performs a raytrace calculation in order to determine the best facing towards the block", true);
        this.Range = new Value<Float>("Range", new String[] { "Range" }, "The range to search for blocks", 3.0f, 0.0f, 10.0f, 1.0f);
        this._clickSelectBlock = null;
        final IBlockState state;
        this.onClickBlock = new Listener<EventPlayerClickBlock>(event -> {
            state = this.mc.world.getBlockState(event.Location);
            if (state == null || state.getBlock() == Blocks.AIR) {
                return;
            }
            else {
                this._clickSelectBlock = state.getBlock();
                return;
            }
        }, (Predicate<EventPlayerClickBlock>[])new Predicate[0]);
        BlockPos selectedBlock;
        double[] rotations;
        float range;
        BlockPos flooredPos;
        final Iterator<BlockPos> iterator;
        BlockPos pos;
        IBlockState state2;
        double dist;
        this.onPlayerUpdate = new Listener<EventPlayerMotionUpdate>(event -> {
            if (event.getEra() == MinecraftEvent.Era.PRE && !event.isCancelled()) {
                if (!this.ClickSelect.getValue() || this._clickSelectBlock != null) {
                    selectedBlock = null;
                    if (BlockManager.GetCurrBlock() != null) {
                        event.cancel();
                        rotations = EntityUtil.calculateLookAt(BlockManager.GetCurrBlock().getX() + 0.5, BlockManager.GetCurrBlock().getY() - 0.5, BlockManager.GetCurrBlock().getZ() + 0.5, (EntityPlayer)this.mc.player);
                        PlayerUtil.PacketFacePitchAndYaw((float)rotations[1], (float)rotations[0]);
                        if (BlockManager.Update(this.Range.getValue(), this.Raytrace.getValue())) {}
                    }
                    else {
                        range = this.Range.getValue();
                        flooredPos = PlayerUtil.GetLocalPlayerPosFloored();
                        BlockInteractionHelper.getSphere(flooredPos, range, (int)range, false, true, 0).iterator();
                        while (iterator.hasNext()) {
                            pos = iterator.next();
                            if (this.Flatten.getValue() && pos.getY() < flooredPos.getY()) {
                                continue;
                            }
                            else {
                                state2 = this.mc.world.getBlockState(pos);
                                if (this.ClickSelect.getValue() && this._clickSelectBlock != null && state2.getBlock() != this._clickSelectBlock) {
                                    continue;
                                }
                                else if (this.Mode.getValue() == Modes.Creative) {
                                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
                                }
                                else if (state2.getBlock() != Blocks.BEDROCK && state2.getBlock() != Blocks.AIR) {
                                    if (state2.getBlock() == Blocks.WATER) {
                                        continue;
                                    }
                                    else if (selectedBlock == null) {
                                        selectedBlock = pos;
                                    }
                                    else {
                                        dist = pos.getDistance((int)this.mc.player.posX, (int)this.mc.player.posY, (int)this.mc.player.posZ);
                                        if (selectedBlock.getDistance((int)this.mc.player.posX, (int)this.mc.player.posY, (int)this.mc.player.posZ) < dist) {
                                            continue;
                                        }
                                        else if (dist <= this.Range.getValue()) {
                                            selectedBlock = pos;
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                        if (selectedBlock != null) {
                            if (this.Mode.getValue() != Modes.Creative) {
                                BlockManager.SetCurrentBlock(selectedBlock);
                            }
                        }
                    }
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this._clickSelectBlock = null;
    }
    
    public enum Modes
    {
        Survival, 
        Creative;
    }
}
