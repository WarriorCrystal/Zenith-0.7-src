//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Collection;
import java.util.function.Predicate;
import me.gopro336.goprohack.util.render.ESPUtil;
import net.minecraft.util.math.Vec3i;
import net.minecraft.client.renderer.culling.Frustum;
import java.util.ArrayList;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class VoidESPModule extends Module
{
    public final Value<Integer> Radius;
    public final List<BlockPos> VoidBlocks;
    private ICamera camera;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public VoidESPModule() {
        super("VoidESP", new String[] { "" }, "Highlights void blocks", "NONE", -1, ModuleType.RENDER);
        this.Radius = new Value<Integer>("Radius", new String[] { "Radius", "Range", "Distance" }, "Radius in blocks to scan for void blocks.", 8, 0, 32, 1);
        this.VoidBlocks = new ArrayList<BlockPos>();
        this.camera = (ICamera)new Frustum();
        Vec3i playerPos;
        int x;
        int z;
        int y;
        BlockPos blockPos;
        IBlockState blockState;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.mc.player == null) {
                return;
            }
            else {
                this.VoidBlocks.clear();
                for (playerPos = new Vec3i(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ), x = playerPos.getX() - this.Radius.getValue(); x < playerPos.getX() + this.Radius.getValue(); ++x) {
                    for (z = playerPos.getZ() - this.Radius.getValue(); z < playerPos.getZ() + this.Radius.getValue(); ++z) {
                        for (y = playerPos.getY() + this.Radius.getValue(); y > playerPos.getY() - this.Radius.getValue(); --y) {
                            blockPos = new BlockPos(x, y, z);
                            blockState = this.mc.world.getBlockState(blockPos);
                            if (ESPUtil.IsVoidHole(blockPos, blockState)) {
                                this.VoidBlocks.add(blockPos);
                            }
                        }
                    }
                }
                return;
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        AxisAlignedBB bb;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() != null && this.mc.getRenderManager().options != null) {
                new ArrayList(this.VoidBlocks).forEach(p_Pos -> {
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
                        RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, 0.5f, 0.0f, 1.0f, 0.5f);
                        RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, 0.5f, 0.0f, 1.0f, 0.22f);
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
}
