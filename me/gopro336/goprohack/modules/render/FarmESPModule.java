//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import java.util.Iterator;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.block.state.IBlockState;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Collection;
import java.util.function.Predicate;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.client.renderer.culling.Frustum;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import net.minecraft.client.renderer.culling.ICamera;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class FarmESPModule extends Module
{
    public final Value<Integer> Radius;
    public final Value<Float> Red;
    public final Value<Float> Green;
    public final Value<Float> Blue;
    public final Value<Float> Alpha;
    public final Value<Modes> Mode;
    public final Value<Float> Delay;
    private ICamera camera;
    private ArrayList<BlockPos> PositionsToHighlight;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public FarmESPModule() {
        super("FarmESP", new String[] { "FarmlandESP" }, "Various rendering tools for farms", "NONE", 5131167, ModuleType.RENDER);
        this.Radius = new Value<Integer>("Radius", new String[] { "Radius", "Range", "Distance" }, "Radius in blocks to scan for blocks.", 32, 0, 100, 1);
        this.Red = new Value<Float>("Red", new String[] { "oRed" }, "Red for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.Green = new Value<Float>("Green", new String[] { "oGreen" }, "Green for rendering", 1.0f, 0.0f, 1.0f, 0.1f);
        this.Blue = new Value<Float>("Blue", new String[] { "oBlue" }, "Blue for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.Alpha = new Value<Float>("Alpha", new String[] { "oAlpha" }, "Alpha for rendering", 0.5f, 0.0f, 1.0f, 0.1f);
        this.Mode = new Value<Modes>("Mode", new String[] { "M" }, "Mode for rendering around blocks", Modes.Full);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Delay for updating, Higher this if you experience fps drops.", 1.0f, 0.0f, 10.0f, 1.0f);
        this.camera = (ICamera)new Frustum();
        this.PositionsToHighlight = new ArrayList<BlockPos>();
        this.timer = new Timer();
        IBlockState l_State;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.timer.passed(this.Delay.getValue() * 1000.0f)) {
                this.timer.reset();
                new Thread(() -> {
                    this.PositionsToHighlight.clear();
                    BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), this.Radius.getValue(), this.Radius.getValue(), false, true, 0).forEach(p_Pos -> {
                        l_State = this.mc.world.getBlockState(p_Pos);
                        if (l_State != null && l_State.getBlock() == Blocks.FARMLAND && !this.hasWater((World)this.mc.world, p_Pos) && !this.mc.world.isRainingAt(p_Pos.up())) {
                            this.PositionsToHighlight.add(p_Pos);
                        }
                    });
                }).start();
            }
            return;
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        AxisAlignedBB bb;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() != null && this.mc.getRenderManager().options != null) {
                new ArrayList(this.PositionsToHighlight).forEach(p_Pos -> {
                    bb = new AxisAlignedBB(p_Pos.getX() - this.mc.getRenderManager().viewerPosX, p_Pos.getY() - this.mc.getRenderManager().viewerPosY, p_Pos.getZ() - this.mc.getRenderManager().viewerPosZ, p_Pos.getX() + 1 - this.mc.getRenderManager().viewerPosX, p_Pos.getY() + 1 - this.mc.getRenderManager().viewerPosY, p_Pos.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
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
                        this.Render(bb, this.Red.getValue(), this.Green.getValue(), this.Blue.getValue(), this.Alpha.getValue());
                        GL11.glDisable(2848);
                        GlStateManager.depthMask(true);
                        GlStateManager.enableDepth();
                        GlStateManager.enableTexture2D();
                        GlStateManager.disableBlend();
                        GlStateManager.popMatrix();
                    }
                });
            }
        }, (Predicate<RenderEvent>[])new Predicate[0]);
    }
    
    private void Render(final AxisAlignedBB bb, final float p_Red, final float p_Green, final float p_Blue, final float p_Alpha) {
        switch (this.Mode.getValue()) {
            case Flat: {
                RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case FlatOutline: {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case Full: {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case Outline: {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
        }
    }
    
    private boolean hasWater(final World worldIn, final BlockPos pos) {
        for (final BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
            if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos).getMaterial() == Material.WATER) {
                return true;
            }
        }
        return false;
    }
    
    private enum Modes
    {
        FlatOutline, 
        Flat, 
        Outline, 
        Full;
    }
}
