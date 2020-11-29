//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import net.minecraft.block.state.IBlockState;
import java.util.Iterator;
import me.gopro336.goprohack.util.render.RenderUtil;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import me.gopro336.goprohack.managers.BlockManager;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3i;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import me.gopro336.goprohack.events.MinecraftEvent;
import net.minecraft.client.renderer.culling.Frustum;
import java.util.concurrent.CopyOnWriteArrayList;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class AutoTunnelModule extends Module
{
    public final Value<Modes> Mode;
    public final Value<MiningModes> MiningMode;
    public final Value<Boolean> Visualize;
    public final Value<Boolean> PauseAutoWalk;
    private List<BlockPos> _blocksToDestroy;
    private ICamera camera;
    private boolean _needPause;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdates;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public AutoTunnelModule() {
        super("AutoTunnel", new String[] { "" }, "Automatically mines different kind of 2d tunnels, in the direction you're facing", "NONE", -1, ModuleType.WORLD);
        this.Mode = new Value<Modes>("Mode", new String[] { "" }, "Mode", Modes.Tunnel1x2);
        this.MiningMode = new Value<MiningModes>("MiningMode", new String[] { "" }, "Mode of mining to use", MiningModes.Normal);
        this.Visualize = new Value<Boolean>("Visualize", new String[] { "Render" }, "Visualizes where blocks are to be destroyed", true);
        this.PauseAutoWalk = new Value<Boolean>("PauseAutoWalk", new String[] { "PauseAutoWalk" }, "Pauses autowalk if you are mining", true);
        this._blocksToDestroy = new CopyOnWriteArrayList<BlockPos>();
        this.camera = (ICamera)new Frustum();
        this._needPause = false;
        BlockPos playerPos;
        int i;
        int j;
        int k;
        int l;
        int m;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        BlockPos toDestroy;
        final Iterator<BlockPos> iterator;
        BlockPos pos2;
        IBlockState state;
        float[] rotations;
        this.onMotionUpdates = new Listener<EventPlayerMotionUpdate>(event -> {
            if (event.getEra() != MinecraftEvent.Era.PRE || event.isCancelled()) {
                return;
            }
            else {
                this._blocksToDestroy.clear();
                playerPos = PlayerUtil.GetLocalPlayerPosFloored();
                Label_2281_2: {
                    switch (PlayerUtil.GetFacing()) {
                        case East: {
                            switch (this.Mode.getValue()) {
                                case Tunnel1x2: {
                                    for (i = 0; i < 3; ++i) {
                                        this._blocksToDestroy.add(playerPos.east());
                                        this._blocksToDestroy.add(playerPos.east().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).east();
                                    }
                                    break Label_2281_2;
                                }
                                case Tunnel2x2: {
                                    for (j = 0; j < 3; ++j) {
                                        this._blocksToDestroy.add(playerPos.east());
                                        this._blocksToDestroy.add(playerPos.east().up());
                                        this._blocksToDestroy.add(playerPos.east().north());
                                        this._blocksToDestroy.add(playerPos.east().north().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).east();
                                    }
                                    break Label_2281_2;
                                }
                                case Tunnel2x3: {
                                    for (k = 0; k < 3; ++k) {
                                        this._blocksToDestroy.add(playerPos.east());
                                        this._blocksToDestroy.add(playerPos.east().up());
                                        this._blocksToDestroy.add(playerPos.east().up().up());
                                        this._blocksToDestroy.add(playerPos.east().north());
                                        this._blocksToDestroy.add(playerPos.east().north().up());
                                        this._blocksToDestroy.add(playerPos.east().north().up().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).east();
                                    }
                                    break Label_2281_2;
                                }
                                case Tunnel3x3: {
                                    for (l = 0; l < 3; ++l) {
                                        this._blocksToDestroy.add(playerPos.east());
                                        this._blocksToDestroy.add(playerPos.east().up());
                                        this._blocksToDestroy.add(playerPos.east().up().up());
                                        this._blocksToDestroy.add(playerPos.east().north());
                                        this._blocksToDestroy.add(playerPos.east().north().up());
                                        this._blocksToDestroy.add(playerPos.east().north().up().up());
                                        this._blocksToDestroy.add(playerPos.east().north().north());
                                        this._blocksToDestroy.add(playerPos.east().north().north().up());
                                        this._blocksToDestroy.add(playerPos.east().north().north().up().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).east();
                                    }
                                    break Label_2281_2;
                                }
                                default: {
                                    break Label_2281_2;
                                }
                            }
                            break;
                        }
                        case North: {
                            switch (this.Mode.getValue()) {
                                case Tunnel1x2: {
                                    for (m = 0; m < 3; ++m) {
                                        this._blocksToDestroy.add(playerPos.north());
                                        this._blocksToDestroy.add(playerPos.north().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).north();
                                    }
                                    break Label_2281_2;
                                }
                                case Tunnel2x2: {
                                    for (i2 = 0; i2 < 3; ++i2) {
                                        this._blocksToDestroy.add(playerPos.north());
                                        this._blocksToDestroy.add(playerPos.north().up());
                                        this._blocksToDestroy.add(playerPos.north().east());
                                        this._blocksToDestroy.add(playerPos.north().east().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).north();
                                    }
                                    break Label_2281_2;
                                }
                                case Tunnel2x3: {
                                    for (i3 = 0; i3 < 3; ++i3) {
                                        this._blocksToDestroy.add(playerPos.north());
                                        this._blocksToDestroy.add(playerPos.north().up());
                                        this._blocksToDestroy.add(playerPos.north().up().up());
                                        this._blocksToDestroy.add(playerPos.north().east());
                                        this._blocksToDestroy.add(playerPos.north().east().up());
                                        this._blocksToDestroy.add(playerPos.north().east().up().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).north();
                                    }
                                    break Label_2281_2;
                                }
                                case Tunnel3x3: {
                                    for (i4 = 0; i4 < 3; ++i4) {
                                        this._blocksToDestroy.add(playerPos.north());
                                        this._blocksToDestroy.add(playerPos.north().up());
                                        this._blocksToDestroy.add(playerPos.north().up().up());
                                        this._blocksToDestroy.add(playerPos.north().east());
                                        this._blocksToDestroy.add(playerPos.north().east().up());
                                        this._blocksToDestroy.add(playerPos.north().east().up().up());
                                        this._blocksToDestroy.add(playerPos.north().east().east());
                                        this._blocksToDestroy.add(playerPos.north().east().east().up());
                                        this._blocksToDestroy.add(playerPos.north().east().east().up().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).north();
                                    }
                                    break Label_2281_2;
                                }
                                default: {
                                    break Label_2281_2;
                                }
                            }
                            break;
                        }
                        case South: {
                            switch (this.Mode.getValue()) {
                                case Tunnel1x2: {
                                    for (i5 = 0; i5 < 3; ++i5) {
                                        this._blocksToDestroy.add(playerPos.south());
                                        this._blocksToDestroy.add(playerPos.south().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).south();
                                    }
                                    break Label_2281_2;
                                }
                                case Tunnel2x2: {
                                    for (i6 = 0; i6 < 3; ++i6) {
                                        this._blocksToDestroy.add(playerPos.south());
                                        this._blocksToDestroy.add(playerPos.south().up());
                                        this._blocksToDestroy.add(playerPos.south().west());
                                        this._blocksToDestroy.add(playerPos.south().west().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).south();
                                    }
                                    break Label_2281_2;
                                }
                                case Tunnel2x3: {
                                    for (i7 = 0; i7 < 3; ++i7) {
                                        this._blocksToDestroy.add(playerPos.south());
                                        this._blocksToDestroy.add(playerPos.south().up());
                                        this._blocksToDestroy.add(playerPos.south().up().up());
                                        this._blocksToDestroy.add(playerPos.south().west());
                                        this._blocksToDestroy.add(playerPos.south().west().up());
                                        this._blocksToDestroy.add(playerPos.south().west().up().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).south();
                                    }
                                    break Label_2281_2;
                                }
                                case Tunnel3x3: {
                                    for (i8 = 0; i8 < 3; ++i8) {
                                        this._blocksToDestroy.add(playerPos.south());
                                        this._blocksToDestroy.add(playerPos.south().up());
                                        this._blocksToDestroy.add(playerPos.south().up().up());
                                        this._blocksToDestroy.add(playerPos.south().west());
                                        this._blocksToDestroy.add(playerPos.south().west().up());
                                        this._blocksToDestroy.add(playerPos.south().west().up().up());
                                        this._blocksToDestroy.add(playerPos.south().west().west());
                                        this._blocksToDestroy.add(playerPos.south().west().west().up());
                                        this._blocksToDestroy.add(playerPos.south().west().west().up().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).south();
                                    }
                                    break Label_2281_2;
                                }
                                default: {
                                    break Label_2281_2;
                                }
                            }
                            break;
                        }
                        case West: {
                            switch (this.Mode.getValue()) {
                                case Tunnel1x2: {
                                    for (i9 = 0; i9 < 3; ++i9) {
                                        this._blocksToDestroy.add(playerPos.west());
                                        this._blocksToDestroy.add(playerPos.west().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).west();
                                    }
                                    break Label_2281_2;
                                }
                                case Tunnel2x2: {
                                    for (i10 = 0; i10 < 3; ++i10) {
                                        this._blocksToDestroy.add(playerPos.west());
                                        this._blocksToDestroy.add(playerPos.west().up());
                                        this._blocksToDestroy.add(playerPos.west().south());
                                        this._blocksToDestroy.add(playerPos.west().south().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).west();
                                    }
                                    break Label_2281_2;
                                }
                                case Tunnel2x3: {
                                    for (i11 = 0; i11 < 3; ++i11) {
                                        this._blocksToDestroy.add(playerPos.west());
                                        this._blocksToDestroy.add(playerPos.west().up());
                                        this._blocksToDestroy.add(playerPos.west().up().up());
                                        this._blocksToDestroy.add(playerPos.west().south());
                                        this._blocksToDestroy.add(playerPos.west().south().up());
                                        this._blocksToDestroy.add(playerPos.west().south().up().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).west();
                                    }
                                    break Label_2281_2;
                                }
                                case Tunnel3x3: {
                                    for (i12 = 0; i12 < 3; ++i12) {
                                        this._blocksToDestroy.add(playerPos.west());
                                        this._blocksToDestroy.add(playerPos.west().up());
                                        this._blocksToDestroy.add(playerPos.west().up().up());
                                        this._blocksToDestroy.add(playerPos.west().south());
                                        this._blocksToDestroy.add(playerPos.west().south().up());
                                        this._blocksToDestroy.add(playerPos.west().south().up().up());
                                        this._blocksToDestroy.add(playerPos.west().south().south());
                                        this._blocksToDestroy.add(playerPos.west().south().south().up());
                                        this._blocksToDestroy.add(playerPos.west().south().south().up().up());
                                        playerPos = new BlockPos((Vec3i)playerPos).west();
                                    }
                                    break Label_2281_2;
                                }
                                default: {
                                    break Label_2281_2;
                                }
                            }
                            break;
                        }
                    }
                }
                toDestroy = null;
                this._blocksToDestroy.iterator();
                while (iterator.hasNext()) {
                    pos2 = iterator.next();
                    state = this.mc.world.getBlockState(pos2);
                    if (state.getBlock() != Blocks.AIR && !(state.getBlock() instanceof BlockDynamicLiquid) && !(state.getBlock() instanceof BlockStaticLiquid)) {
                        if (state.getBlock() == Blocks.BEDROCK) {
                            continue;
                        }
                        else {
                            toDestroy = pos2;
                            break;
                        }
                    }
                }
                if (toDestroy != null) {
                    event.cancel();
                    rotations = BlockInteractionHelper.getLegitRotations(new Vec3d((double)toDestroy.getX(), (double)toDestroy.getY(), (double)toDestroy.getZ()));
                    PlayerUtil.PacketFacePitchAndYaw(rotations[1], rotations[0]);
                    switch (this.MiningMode.getValue()) {
                        case Normal: {
                            if (BlockManager.GetCurrBlock() == null) {
                                BlockManager.SetCurrentBlock(toDestroy);
                            }
                            BlockManager.Update(5.0f, true);
                            break;
                        }
                        case Packet: {
                            this.mc.player.swingArm(EnumHand.MAIN_HAND);
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, toDestroy, EnumFacing.UP));
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, toDestroy, EnumFacing.UP));
                            break;
                        }
                    }
                    this._needPause = true;
                }
                else {
                    this._needPause = false;
                }
                return;
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
        IBlockState l_State;
        AxisAlignedBB bb;
        this.OnRenderEvent = new Listener<RenderEvent>(event -> {
            if (!(!this.Visualize.getValue())) {
                this._blocksToDestroy.forEach(pos -> {
                    l_State = this.mc.world.getBlockState(pos);
                    if (l_State != null && l_State.getBlock() != Blocks.AIR && l_State.getBlock() != Blocks.BEDROCK && !(l_State.getBlock() instanceof BlockDynamicLiquid) && !(l_State.getBlock() instanceof BlockStaticLiquid)) {
                        bb = new AxisAlignedBB(pos.getX() - this.mc.getRenderManager().viewerPosX, pos.getY() - this.mc.getRenderManager().viewerPosY, pos.getZ() - this.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - this.mc.getRenderManager().viewerPosX, pos.getY() + 1 - this.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
                        this.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
                        if (this.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + this.mc.getRenderManager().viewerPosX, bb.minY + this.mc.getRenderManager().viewerPosY, bb.minZ + this.mc.getRenderManager().viewerPosZ, bb.maxX + this.mc.getRenderManager().viewerPosX, bb.maxY + this.mc.getRenderManager().viewerPosY, bb.maxZ + this.mc.getRenderManager().viewerPosZ))) {
                            GlStateManager.pushMatrix();
                            GlStateManager.enableBlend();
                            GlStateManager.disableDepth();
                            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                            GlStateManager.disableTexture2D();
                            GlStateManager.depthMask(false);
                            GL11.glEnable(2848);
                            GL11.glHint(3154, 4354);
                            GL11.glLineWidth(1.5f);
                            RenderUtil.drawBoundingBox(bb, 1.0f, 1358888960);
                            RenderUtil.drawFilledBox(bb, 1358888960);
                            GL11.glDisable(2848);
                            GlStateManager.depthMask(true);
                            GlStateManager.enableDepth();
                            GlStateManager.enableTexture2D();
                            GlStateManager.disableBlend();
                            GlStateManager.popMatrix();
                        }
                    }
                });
            }
        }, (Predicate<RenderEvent>[])new Predicate[0]);
    }
    
    public boolean PauseAutoWalk() {
        return this._needPause && this.PauseAutoWalk.getValue();
    }
    
    public enum Modes
    {
        Tunnel1x2, 
        Tunnel2x2, 
        Tunnel2x3, 
        Tunnel3x3;
    }
    
    public enum MiningModes
    {
        Normal, 
        Packet;
    }
}
