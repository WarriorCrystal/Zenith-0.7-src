//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import me.gopro336.goprohack.managers.ModuleManager;
import java.util.List;
import me.gopro336.goprohack.util.render.RenderUtil;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Collection;
import java.util.function.Predicate;
import me.gopro336.goprohack.util.HilbertCurve;
import net.minecraft.entity.player.EntityPlayer;
import me.gopro336.goprohack.util.entity.EntityUtil;
import net.minecraft.client.renderer.culling.Frustum;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class StashFinderModule extends Module
{
    public final Value<Integer> Curve;
    public final Value<Boolean> Render;
    public final Value<Boolean> Loop;
    public final Value<Boolean> ToggleLog;
    private ArrayList<BlockPos> WaypointPath;
    private ICamera camera;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public StashFinderModule() {
        super("StashFinder", new String[] { "BaseFinder" }, "Automatically pilots you towards generated waypoints", "NONE", -1, ModuleType.WORLD);
        this.Curve = new Value<Integer>("Curve", new String[] { "Curves" }, "Curves to use for hilbert curve, more = bigger path", 5, 1, 5, 1);
        this.Render = new Value<Boolean>("Visualizer", new String[] { "Render" }, "Renders the path", true);
        this.Loop = new Value<Boolean>("Loop", new String[] { "Loop" }, "Loops after a finish", false);
        this.ToggleLog = new Value<Boolean>("ToggleStashLogger", new String[] { "ToggleLog" }, "Automatically toggles on StashLogger if not already enabled", true);
        this.WaypointPath = new ArrayList<BlockPos>();
        this.camera = (ICamera)new Frustum();
        BlockPos first;
        double[] rotations;
        int order;
        int n;
        List<HilbertCurve.Point> points;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (!this.WaypointPath.isEmpty()) {
                first = this.WaypointPath.get(0);
                rotations = EntityUtil.calculateLookAt(first.getX() + 0.5, first.getY() - 0.5, first.getZ() + 0.5, (EntityPlayer)this.mc.player);
                this.mc.player.rotationYaw = (float)rotations[0];
                if (this.getDistance2D(first) < 10.0) {
                    this.WaypointPath.remove(first);
                }
            }
            else if (this.Loop.getValue()) {
                order = this.Curve.getValue();
                n = 1 << order;
                points = HilbertCurve.getPointsForCurve(n);
                this.WaypointPath.clear();
                points.forEach(p -> this.WaypointPath.add(new BlockPos((int)this.mc.player.posX + p.x * 16 * 8, 165, (int)this.mc.player.posZ + p.y * 16 * 8)));
            }
            return;
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        AxisAlignedBB bb;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() != null && this.Render.getValue()) {
                new ArrayList(this.WaypointPath).forEach(pos -> {
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
                        RenderUtil.drawBoundingBox(bb, 1.0f, -1869021031);
                        RenderUtil.drawFilledBox(bb, -1869021031);
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
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        final int order = this.Curve.getValue();
        final int n = 1 << order;
        final List<HilbertCurve.Point> points = HilbertCurve.getPointsForCurve(n);
        this.WaypointPath.clear();
        points.forEach(p -> this.WaypointPath.add(new BlockPos((int)this.mc.player.posX + p.x * 16 * 8, 165, (int)this.mc.player.posZ + p.y * 16 * 8)));
        this.SendMessage("Turn on AutoWalk and StashLogger to begin!");
        if (this.ToggleLog.getValue()) {
            final Module mod = ModuleManager.Get().GetMod(StashLoggerModule.class);
            if (!mod.isEnabled()) {
                mod.toggle();
            }
        }
    }
    
    private double getDistance2D(final BlockPos pos) {
        final double posX = Math.abs(this.mc.player.posX - pos.getX());
        final double posZ = Math.abs(this.mc.player.posZ - pos.getZ());
        return posX + posZ;
    }
}
